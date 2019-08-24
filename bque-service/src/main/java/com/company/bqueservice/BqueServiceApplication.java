package com.company.bqueservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD:level-up-queue/src/main/java/com/company/levelupqueue/LevelUpQueueApplication.java
<<<<<<< HEAD
import org.springframework.cloud.openfeign.EnableFeignClients;
=======
=======
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
>>>>>>> 3a85d0824b8625a2fd6168e55f831f4efe485850:bque-service/src/main/java/com/company/bqueservice/BqueServiceApplication.java
import org.springframework.cloud.netflix.feign.EnableFeignClients;
>>>>>>> d47480d11b233955b071f30045083c6f417bd26d
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
<<<<<<< HEAD:level-up-queue/src/main/java/com/company/levelupqueue/LevelUpQueueApplication.java
public class LevelUpQueueApplication {
<<<<<<< HEAD

=======
>>>>>>> d47480d11b233955b071f30045083c6f417bd26d
=======
public class BqueServiceApplication {

>>>>>>> 3a85d0824b8625a2fd6168e55f831f4efe485850:bque-service/src/main/java/com/company/bqueservice/BqueServiceApplication.java
	public static final String  TOPIC_EXCHANGE_NAME= "level-up-exchange";
	public static final String QUEUE_NAME="level-up-queue";
	public static final String ROUTING_KEY="level.#";

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public static void main(String[] args) {
		SpringApplication.run(BqueServiceApplication.class, args);
	}

}
