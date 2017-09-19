package com.demo.rabbitmq.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QueueListener {

	private final static Logger LOGGER = LoggerFactory.getLogger(QueueListener.class);

	public <T> void receiveMessage(T t) {
		LOGGER.info("Received <" + t + ">");
	}
}
