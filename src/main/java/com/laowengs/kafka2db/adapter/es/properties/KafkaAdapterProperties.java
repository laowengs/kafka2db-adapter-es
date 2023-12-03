package com.laowengs.kafka2db.adapter.es.properties;

import javafx.fxml.Initializable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

@ConfigurationProperties(prefix = "kafka.adapter")
@Configuration
@Slf4j
@Data
public class KafkaAdapterProperties implements Initializable {
//    @Value("binlog_data_source")
    private Map<String, Set<String>> binlogDataSource;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("load properties :{}",this);
    }
}
