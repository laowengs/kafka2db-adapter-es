package com.laowengs.kafka2db.adapter.es.entity;

import com.laowengs.kafka2db.adapter.es.core.EsBaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
public class CanalBinlogEsEntity implements EsBaseEntity {

    // 数据列表
    private List<Map<String,Object>> data;
    //数据库
    private String database;
    // binlog executeTime, 执行耗时
    private Long es;
    private String gtid;
    private Integer id;
    private Boolean isDdl;
    // 字段数据库类型
    private Map<String,String> mysqlType;
    private List<Map<String,String>> old;
    private List<String> pkNames;
    //执行的sql,dml sql为空
    private String sql;
    //字段数据库类型
    private Map<String,Integer> sqlType;
    private String table;
    //dml build timeStamp, 同步时间
    private Long ts;
    // 类型:INSERT/UPDATE/DELETE
    private String type;

    @Override
    public String fetchEsDocumentId() {
        return String.valueOf(id);
    }
}
