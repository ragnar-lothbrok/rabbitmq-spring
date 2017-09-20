package com.demo.rabbitmq.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

	public final static String TX_EVENT_QUEUE = "tx_event_queue";

	public final static String TOPIC_EXCHANGE_NAME = "rabbitmq-topic-exchange";

	@Bean
	Queue queueOne() {
		return new Queue(TX_EVENT_QUEUE + "_one", false);
	}

	@Bean
	Queue queueTwo() {
		return new Queue(TX_EVENT_QUEUE + "_two", false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}

	@Bean
	Binding bindingOne() {
		return BindingBuilder.bind(queueOne()).to(exchange()).with("tx.one.#");
	}

	@Bean
	Binding bindingTwo() {
		return BindingBuilder.bind(queueTwo()).to(exchange()).with("tx.two.*");
	}

	// @Bean
	// SimpleMessageListenerContainer containerOne(ConnectionFactory
	// connectionFactory) {
	// SimpleMessageListenerContainer container = new
	// SimpleMessageListenerContainer();
	// container.setConnectionFactory(connectionFactory);
	//
	// RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
	// rabbitAdmin.declareBinding(bindingOne());
	//
	// container.setRabbitAdmin(rabbitAdmin);
	//
	// container.setConsumerArguments(Collections.<String,
	// Object>singletonMap("x-priority", Integer.valueOf(10)));
	// return container;
	// }

	// @Bean
	// SimpleMessageListenerContainer containerTwo(ConnectionFactory
	// connectionFactory) {
	// SimpleMessageListenerContainer container = new
	// SimpleMessageListenerContainer();
	// container.setConnectionFactory(connectionFactory);
	//
	// RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
	// rabbitAdmin.declareBinding(bindingTwo());
	//
	// container.setRabbitAdmin(rabbitAdmin);
	// return container;
	// }

}
