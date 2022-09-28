package com.grootan.kafka.configuration;

import com.grootan.kafka.model.MessageResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.grootan.kafka.model.Constant.CONSUMER_GROUP_ID;

/**
 * kafka consumer configuration
 * @author <a href="sheikabdullah.m@grootan.com">sheik Abdullah</a>
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${kafka.broker.address}")
    private String kafkaBrokerAddress;

    @Bean
    public ConsumerFactory<String, MessageResponse> consumerFactory() {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerAddress);
        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configMap);
    }
}
