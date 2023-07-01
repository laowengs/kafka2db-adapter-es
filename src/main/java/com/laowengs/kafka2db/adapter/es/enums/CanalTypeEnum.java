package com.laowengs.kafka2db.adapter.es.enums;

public enum CanalTypeEnum {
    INSERT(false),
    UPDATE(false),
    DELETE(false),
    QUERY(false),
    CREATE(true),
    ALTER(true),
    TRUNCATE(true),
    ERASE(true),
    ;
    boolean isDdl;

    public boolean isDdl() {
        return isDdl;
    }

    CanalTypeEnum(boolean isDdl) {
        this.isDdl = isDdl;
    }

    public static CanalTypeEnum switchMe(String type){
        for (CanalTypeEnum value : values()) {
            if(value.name().equals(type)){
                return value;
            }
        }
        return null;
    }
}
