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
		LOGGER.info("Received with tx_event_queue_one <" + t + ">");
	}

	@RabbitListener(bindings = {
			@org.springframework.amqp.rabbit.annotation.QueueBinding(key = "tx.one.*", exchange = @Exchange(type = ExchangeTypes.TOPIC, value = "rabbitmq-topic-exchange", durable = "true"), value = @Queue(value = "tx_event_queue_two", durable = "false")) })
	public <T> void receiveMessageTwi(T t) {
		LOGGER.info("Received with tx_event_queue_two <" + t + ">");
	}

	@RabbitListener(queues = { "fanout.queue1" })
	public <T> void receiveMessageFromFanout1(T t) {
		LOGGER.info("Received with fanout.queue1 <" + t + ">");
	}

	@RabbitListener(queues = { "fanout.queue2" })
	public <T> void receiveMessageFromFanout2(T t) {
		LOGGER.info("Received with fanout.queue2 <" + t + ">");
	}

	@RabbitListener(queues = { "topic.queue1" })
	public <T> void receiveMessageFromTopic1(T t) {
		LOGGER.info("Received with topic.queue1 <" + t + ">");
	}

	@RabbitListener(queues = { "topic.queue2" })
	public <T> void receiveMessageFromTopic2(T t) {
		LOGGER.info("Received with topic.queue2 <" + t + ">");
	}

	@RabbitListener(queues = { "header.queue1" })
	public <T> void receiveMessageFromHeader1(T t) {
		LOGGER.info("Received with header.queue1 <" + t + ">");
	}

	@RabbitListener(queues = { "header.queue2" })
	public <T> void receiveMessageFromHeader2(T t) {
		LOGGER.info("Received with header.queue2 <" + t + ">");
	}

	@RabbitListener(queues = { "direct.queue1" })
	public <T> void receiveMessageFromDirect1(T t) {
		LOGGER.info("Received with direct.queue1 <" + t + ">");
	}

	@RabbitListener(queues = { "direct.queue2" })
	public <T> void receiveMessageFromDirect2(T t) {
		LOGGER.info("Received with direct.queue2 <" + t + ">");
	}
}
