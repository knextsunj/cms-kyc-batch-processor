package com.github.knextsunj.cmskycbatchprocessor.messaging;

public interface JmsConsumer {

    void receiveMessage(String id);
}
