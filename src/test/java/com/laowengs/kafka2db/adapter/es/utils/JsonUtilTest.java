package com.laowengs.kafka2db.adapter.es.utils;

import com.laowengs.kafka2db.adapter.es.vo.CanalBinlogVo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {
    @Test
    void name() {
        String json = "{\"data\":[{\"log_id\":\"1\",\"request_url\":\"http://127.0.0.1:18080/mock/0d46f214f9a1430382dbc5511185892c/11\",\"header\":\"{\\\"sec-fetch-mode\\\":\\\"navigate\\\",\\\"sec-fetch-site\\\":\\\"none\\\",\\\"accept-language\\\":\\\"zh-CN,zh;q=0.9\\\",\\\"sec-fetch-user\\\":\\\"?1\\\",\\\"accept\\\":\\\"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\\\",\\\"sec-ch-ua\\\":\\\"\\\\\\\"Chromium\\\\\\\";v=\\\\\\\"112\\\\\\\", \\\\\\\"Google Chrome\\\\\\\";v=\\\\\\\"112\\\\\\\", \\\\\\\"Not:A-Brand\\\\\\\";v=\\\\\\\"99\\\\\\\"\\\",\\\"sec-ch-ua-mobile\\\":\\\"?0\\\",\\\"sec-ch-ua-platform\\\":\\\"\\\\\\\"Windows\\\\\\\"\\\",\\\"host\\\":\\\"127.0.0.1:18080\\\",\\\"upgrade-insecure-requests\\\":\\\"1\\\",\\\"connection\\\":\\\"keep-alive\\\",\\\"cache-control\\\":\\\"max-age=0\\\",\\\"accept-encoding\\\":\\\"gzip, deflate, br\\\",\\\"user-agent\\\":\\\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36\\\",\\\"sec-fetch-dest\\\":\\\"document\\\"}\",\"path_param\":\"\",\"request_body\":\"\",\"response_body\":\"\",\"call_date\":\"2023-04-08 01:58:31\",\"create_date\":\"2023-04-08 01:58:31\",\"request_uri\":\"/mock/0d46f214f9a1430382dbc5511185892c/11\",\"query_string\":null,\"caller_ip\":\"127.0.0.1\",\"caller_host\":\"127.0.0.1\",\"request_method\":\"GET\",\"interface_id\":\"999\"}],\"database\":\"mockdb\",\"es\":1684675974000,\"id\":6,\"isDdl\":false,\"mysqlType\":{\"log_id\":\"bigint\",\"request_url\":\"varchar(255)\",\"header\":\"varchar(1024)\",\"path_param\":\"varchar(255)\",\"request_body\":\"varchar(1024)\",\"response_body\":\"varchar(1024)\",\"call_date\":\"datetime\",\"create_date\":\"datetime\",\"request_uri\":\"varchar(255)\",\"query_string\":\"varchar(255)\",\"caller_ip\":\"varchar(255)\",\"caller_host\":\"varchar(255)\",\"request_method\":\"varchar(32)\",\"interface_id\":\"bigint\"},\"old\":[{\"interface_id\":\"1\"}],\"pkNames\":[\"log_id\"],\"sql\":\"\",\"sqlType\":{\"log_id\":-5,\"request_url\":12,\"header\":12,\"path_param\":12,\"request_body\":12,\"response_body\":12,\"call_date\":93,\"create_date\":93,\"request_uri\":12,\"query_string\":12,\"caller_ip\":12,\"caller_host\":12,\"request_method\":12,\"interface_id\":-5},\"table\":\"mock_log\",\"ts\":1684675975283,\"type\":\"UPDATE\"}";
        CanalBinlogVo canalBinlogVo = JsonUtil.convertObj(json, CanalBinlogVo.class);
        System.out.println(canalBinlogVo);
    }
}