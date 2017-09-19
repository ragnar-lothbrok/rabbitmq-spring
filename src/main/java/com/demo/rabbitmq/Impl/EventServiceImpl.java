package com.demo.rabbitmq.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.rabbitmq.configurations.QueuConfig;
import com.demo.rabbitmq.services.EventService;

@Service
public class EventServiceImpl implements EventService {

	private final static Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public <T> Boolean sendEvent(T t) {
		LOGGER.info("Sending event through queue  = {} ", t);
		rabbitTemplate.convertAndSend(QueuConfig.TX_EVENT_QUEUE, t);

		return true;
	}

}
