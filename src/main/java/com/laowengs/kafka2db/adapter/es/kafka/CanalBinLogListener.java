package com.laowengs.kafka2db.adapter.es.kafka;

import com.laowengs.kafka2db.adapter.es.utils.JsonUtil;
import com.laowengs.kafka2db.adapter.es.vo.CanalBinlogVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CanalBinLogListener {

    @KafkaListener(topics = {"mysql_binlog"})
    public void consumer(List<ConsumerRecord<String,String>> consumerRecords, Consumer consumer) {
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            String msg = consumerRecord.value();
            log.info("消费kafka消息 {} ", msg);
            try {
                CanalBinlogVo canalBinlogVo = JsonUtil.convertObj(msg, CanalBinlogVo.class);
                List<Map<String, Object>> data = canalBinlogVo.getData();
                String database = canalBinlogVo.getDatabase();
                String table = canalBinlogVo.getTable();
                if(data.size() == 0){
                    return;
                }
                for (Map<String, Object> dataMap : data) {
                    log.info("index:{}_{},source:{}",database,table,dataMap);
                }

            }catch (Exception e){
                log.error("消息消费失败：",e);
            }finally {
                consumer.commitAsync();
            }
        }

    }

}