package com.demo.rabbitmq.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListener {

	private final static Logger LOGGER = LoggerFactory.getLogger(QueueListener.class);

	@RabbitListener(bindings = {
			@org.springframework.amqp.rabbit.annotation.QueueBinding(key = "tx.one.#", exchange = @Exchange(type = ExchangeTypes.TOPIC, value = "rabbitmq-topic-exchange", durable = "true"), value = @Queue(value = "tx_event_queue_one", durable = "false")) })
	public <T> void receiveMessageOne(T t) {
		LOGGER.info("Received with # <" + t + ">");
	}

	@RabbitListener(bindings = {
			@org.springframework.amqp.rabbit.annotation.QueueBinding(key = "tx.one.*", exchange = @Exchange(type = ExchangeTypes.TOPIC, value = "rabbitmq-topic-exchange", durable = "true"), value = @Queue(value = "tx_event_queue_two", durable = "false")) })
	public <T> void receiveMessageTwi(T t) {
		LOGGER.info("Received with * <" + t + ">");
	}
}
