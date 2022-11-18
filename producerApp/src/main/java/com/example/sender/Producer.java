package com.example.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.model.PracticalAdvice;

@Component
@EnableScheduling
public class Producer {

	private final KafkaTemplate<String, Object> template;
	private final String topicName;
	
	private static long KEY = 0; 

	public Producer(KafkaTemplate<String, Object> template,
			@Value("${tpd.topic-name}") final String topicName) {
		this.template = template;
		this.topicName = topicName;
	}
	
	@Scheduled(fixedDelay = 2000L)
	public void periodic() {
		
		KEY++;
		
		String message = (KEY%10) == 0  ? "" : "A Practical Advice: " + KEY;
		
        template.send(topicName, String.valueOf(KEY),
                new PracticalAdvice(message, KEY));
	}
}
