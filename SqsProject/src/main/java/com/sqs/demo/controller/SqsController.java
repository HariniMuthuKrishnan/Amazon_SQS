package com.sqs.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqsController {

	private static final Logger LOG = LoggerFactory.getLogger(SqsController.class);

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Value("${cloud.aws.end-point.uri}")
	private String sqsEndPoint;

	/*
	 * Send the message to Queue
	 */
	@GetMapping("/send/{message}")
	public void sendMessageToQueue(@PathVariable String message) {
		queueMessagingTemplate.send(sqsEndPoint, MessageBuilder.withPayload(message).build());
	}

	/*
	 * Receives message from Queue
	 */
	@SqsListener("trans-queue")
	void getMessageToQueue(String message) {

		LOG.info("Message from SQS Queue - " + message);
	}
/*
	@PostMapping("/sendUsingSQSUri")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void sendMessageToUri() {
		LOG.info("Sending the message to Amazon sqs through Uri.");
		queueMessagingTemplate.send(sqsEndPoint, MessageBuilder.withPayload("hello from Spring Boot").build());
		LOG.info("Message sent successfully to the Amazon sqs through Uri.");
	}

	@PostMapping(value = "/sendUsingSQSQueue/{message}")

	@ResponseStatus(code = HttpStatus.CREATED)
	void sendMessageToQueue1(@PathVariable("message") final String message) {
		LOG.info("Sending the message to the Amazon sqs through queue name.");
		queueMessagingTemplate.convertAndSend("springBoot-sqs-queue", message);
		LOG.info("Message sent successfully to the Amazon sqs through queue name..");
	}

	@SqsListener("trans-queue")
	void getMessage(String message) {

		LOG.info("Message from SQS Queue - " + message);
	}*/
}