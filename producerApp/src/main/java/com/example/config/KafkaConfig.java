package com.example.config;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
	
	@Value("${tpd.topic-name}")
	private String topicName;
	
	private final KafkaProperties kafkaProperties;

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
	
	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
	}
	
	@Bean
	public NewTopic adviceTopic() {	
		return new NewTopic(topicName, 3, (short) 1);
	}
	
	@Bean
	public NewTopic deadLetterTopic(KafkaProperties properties) {
		
		return new NewTopic(topicName + ".DLT", 1, (short) 1)
		.configs(Map.of(TopicConfig.RETENTION_MS_CONFIG, ""+ 30*24*3600*1000));
	}

}
