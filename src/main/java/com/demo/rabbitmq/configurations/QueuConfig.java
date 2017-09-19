package com.demo.rabbitmq.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.rabbitmq.listeners.QueueListener;

@Configuration
public class QueuConfig {

	public final static String TX_EVENT_QUEUE = "tx_event_queue";

	@Bean
	Queue queue() {
		return new Queue(TX_EVENT_QUEUE, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("spring-boot-rabbitmq-exchange");
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(TX_EVENT_QUEUE);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(TX_EVENT_QUEUE);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(QueueListener receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
}
