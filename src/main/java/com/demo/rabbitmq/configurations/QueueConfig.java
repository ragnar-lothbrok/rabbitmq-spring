package com.demo.rabbitmq.configurations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

	public final static String TX_EVENT_QUEUE = "tx_event_queue";

	public final static String TOPIC_EXCHANGE_NAME = "rabbitmq-topic-exchange";

	public final static String FANOUT_EXCHANGE_NAME = "rabbitmq-fanout.exchange";

	public final static String HEADER_EXCHANGE_NAME = "rabbitmq-header.exchange";

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

	@Bean
	public List<Declarable> fanoutBindings() {
		Queue queue1 = new Queue("fanout.queue1", false);
		Queue queue2 = new Queue("fanout.queue2", false);
		FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE_NAME);

		return Arrays.asList(queue1, queue2, fanoutExchange, BindingBuilder.bind(queue1).to(fanoutExchange),
				BindingBuilder.bind(queue2).to(fanoutExchange));
	}

	@Bean
	public List<Declarable> topicBindings() {
		Queue queue1 = new Queue("topic.queue1", false);
		Queue queue2 = new Queue("topic.queue2", false);
		TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME);

		return Arrays.asList(queue1, queue2, topicExchange,
				BindingBuilder.bind(queue1).to(topicExchange).with("topic.one.#"),
				BindingBuilder.bind(queue2).to(topicExchange).with("topic.one.*"));
	}

	@Bean
	public List<Declarable> headerBindings() {
		Queue queue1 = new Queue("header.queue1", false);
		Queue queue2 = new Queue("header.queue2", false);
		HeadersExchange headerExchange = new HeadersExchange(HEADER_EXCHANGE_NAME);

		Map<String, Object> firstQueueMap = new HashMap<String, Object>();
		firstQueueMap.put("test", "queue1");

		Map<String, Object> secondQueueMap = new HashMap<String, Object>();
		secondQueueMap.put("test", "queue2");

		return Arrays.asList(queue1, queue2, headerExchange,
				BindingBuilder.bind(queue1).to(headerExchange).whereAny(firstQueueMap).match(),
				BindingBuilder.bind(queue2).to(headerExchange).whereAny(secondQueueMap).match());
	}

}
