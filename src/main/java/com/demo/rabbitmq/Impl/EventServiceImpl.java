package com.demo.rabbitmq.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.rabbitmq.configurations.QueueConfig;
import com.demo.rabbitmq.services.EventService;

@Service
public class EventServiceImpl implements EventService {

	private final static Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public <T> Boolean sendEvent(T t) {
		LOGGER.info("Sending event through queue  = {} ", t);
		rabbitTemplate.convertAndSend(QueueConfig.TOPIC_EXCHANGE_NAME, "topic.one.abc.cde", t);

		rabbitTemplate.convertAndSend(QueueConfig.FANOUT_EXCHANGE_NAME, "", t);

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("test", "queue2");
		Message receiveMessage = new Message("Queue2".toString().getBytes(), messageProperties);
		rabbitTemplate.convertAndSend(QueueConfig.HEADER_EXCHANGE_NAME, "", receiveMessage);

		return true;
	}

}
