package org.igrator.reactivetest;

import org.springframework.http.MediaType;
import org.springframework.messaging.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.HashMap;

@RestController
public class TestController {
    private final TestRepository repository;
    private final SubscribableChannel messageChannel;

    public TestController(TestRepository repository, SubscribableChannel messageChannel) {
        this.repository = repository;
        this.messageChannel = messageChannel;
    }

    @PostMapping(value = "")
    public Mono<Text> addText(@RequestBody Text text) {
        System.out.println("Received from post: " + text);
        messageChannel.send(new Message<Text>() {
            @Override
            public Text getPayload() {
                return text;
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(new HashMap<>());
            }
        });
        return this.repository.save(text);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Text> getAll() {
        Flux<Text> flux = Flux.create(fluxSink -> {
            MessageHandler handler = (message) -> {
                System.out.println("Message received: " + message.getPayload());
                fluxSink.next((Text) message.getPayload());
            };

            fluxSink.onCancel(() -> {
                messageChannel.unsubscribe(handler);
                System.out.println("Flux canceled: " + LocalTime.now());
            });

            messageChannel.subscribe(handler);
        });

        return repository.findAll().concatWith(flux);
//        return flux;
//        return this.repository.findAll();
    }

    @GetMapping(value = "/tail", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Text> getAllTailable() {
        return this.repository.findTextsBy();
    }
}
