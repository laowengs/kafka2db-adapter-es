package com.laowengs.kafka2db.adapter.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class Kafka2dbAdapterEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Kafka2dbAdapterEsApplication.class, args);
    }

}
