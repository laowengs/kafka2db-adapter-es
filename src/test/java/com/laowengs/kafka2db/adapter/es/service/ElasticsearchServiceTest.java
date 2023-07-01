package com.laowengs.kafka2db.adapter.es.service;

import com.laowengs.kafka2db.adapter.es.config.EsException;
import com.laowengs.kafka2db.adapter.es.entity.CanalBinlogEsEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;

@SpringBootTest
class ElasticsearchServiceTest {


    @Autowired
    private ElasticsearchService elasticsearchService;


    @Test
    void existIndex() throws EsException {
        System.out.println(elasticsearchService.existIndex("products"));
        System.out.println(elasticsearchService.existIndex("products2"));
    }


    @Test
    void createIndex() throws EsException {
        elasticsearchService.createIndex("products2");
    }


    @Test
    void addDocument() throws EsException {

    }
}