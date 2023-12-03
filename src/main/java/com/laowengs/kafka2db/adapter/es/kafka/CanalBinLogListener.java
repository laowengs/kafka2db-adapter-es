package com.laowengs.kafka2db.adapter.es.kafka;

import com.laowengs.kafka2db.adapter.es.enums.CanalTypeEnum;
import com.laowengs.kafka2db.adapter.es.properties.KafkaAdapterProperties;
import com.laowengs.kafka2db.adapter.es.service.ElasticsearchService;
import com.laowengs.kafka2db.adapter.es.utils.JsonUtil;
import com.laowengs.kafka2db.adapter.es.vo.CanalBinlogVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class CanalBinLogListener {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private KafkaAdapterProperties kafkaAdapterProperties;

    @KafkaListener(topics = {"mysql_binlog"})
    public void consumer(List<ConsumerRecord<String,String>> consumerRecords, Consumer consumer) {
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            String msg = consumerRecord.value();
            log.info("消费kafka消息{} ",consumerRecord);
            try {
                CanalBinlogVo canalBinlogVo = JsonUtil.convertObj(msg, CanalBinlogVo.class);
                List<Map<String, Object>> data = canalBinlogVo.getData();
                String database = canalBinlogVo.getDatabase();
                String table = canalBinlogVo.getTable();
                String type = canalBinlogVo.getType();
                CanalTypeEnum canalTypeEnum = CanalTypeEnum.switchMe(type);
                if(canalTypeEnum == null){
                    log.info("consumerRecord topic {} partition {} offset {} :binlog type {} not found,message {} ",
                            consumerRecord.topic(),consumerRecord.partition(),consumerRecord.offset(),type,msg);
                    continue;
                }
                Boolean isDdl = canalBinlogVo.getIsDdl();
                if(canalTypeEnum.isDdl() || isDdl){
                    //ddl不处理
                    continue;
                }

                if(data.isEmpty()){
                    log.info("consumerRecord topic {} partition {} offset {} :binlog data is empty,message {} ",
                            consumerRecord.topic(),consumerRecord.partition(),consumerRecord.offset(),msg);
                    continue;
                }
                Map<String, Set<String>> binlogDataSource = kafkaAdapterProperties.getBinlogDataSource();
                if(CollectionUtils.isEmpty(binlogDataSource)){
                    log.debug("don`t configuration need sync dataSource info");
                    continue;
                }
                Set<String> tableSet = binlogDataSource.get(database);
                if(tableSet == null || !tableSet.contains(table)){
                    log.debug("database {} don`t configuration need sync table info",database);
                    continue;
                }
                String index = database+"-"+table;

                if(canalTypeEnum == CanalTypeEnum.INSERT || canalTypeEnum == CanalTypeEnum.UPDATE){
                    for (Map<String, Object> dataMap : data) {
                        // 获取主键内容
                        String pk = getPkValue(canalBinlogVo.getPkNames(), dataMap);
                        elasticsearchService.addDocument(index, dataMap, pk);
                    }
                }
                if(canalTypeEnum == CanalTypeEnum.DELETE ){
                    for (Map<String, Object> dataMap : data) {
                        String pk = getPkValue(canalBinlogVo.getPkNames(), dataMap);
                        elasticsearchService.deleteDocumentById(index, pk);
                    }
                }

            }catch (Exception e){
                log.error("consumerRecord topic {} partition {} offset {} : exception, msg {} ",
                        consumerRecord.topic(),consumerRecord.partition(),consumerRecord.offset(),msg,e);
            }finally {
                consumer.commitAsync();
            }
        }

    }

    private static String getPkValue(List<String> pkNames , Map<String, Object> dataMap) {
        // 获取主键内容
        String pk = null;
        if(!CollectionUtils.isEmpty(pkNames)){
            for (String pkName : pkNames) {
                Object valueObj = dataMap.get(pkName);
                if(valueObj == null){
                    return null;
                }
                String value = String.valueOf(dataMap.get(pkName));
                if(pk == null){
                    pk = value;
                }else{
                    pk = pk + ":" + value;
                }
            }
        }

        return pk;
    }

}