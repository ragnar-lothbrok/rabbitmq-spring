package com.demo.rabbitmq.services;

public interface EventService {

	<T> Boolean sendEvent(T t);

}
