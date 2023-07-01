package com.laowengs.kafka2db.adapter.es;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableApolloConfig
public class Kafka2dbAdapterEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Kafka2dbAdapterEsApplication.class, args);
    }

}
