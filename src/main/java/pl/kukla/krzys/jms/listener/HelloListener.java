package pl.kukla.krzys.jms.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import pl.kukla.krzys.jms.config.JmsConfig;
import pl.kukla.krzys.jms.model.HelloWorldMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

/**
 * @author Krzysztof Kukla
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HelloListener {

    private final JmsTemplate jmsTemplate;

    //it allows to listen on this queue, so if there is any message on queue then send message to this method
    @JmsListener(destination = JmsConfig.MY_QUEUE)
//    @Payload allows to deserialize payload of JmsMessage - Jms message properties
//    @Headers allows to get MessageHeaders - Jms header properties
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders messageHeaders, Message message) {

        log.debug("Received message-> {}", helloWorldMessage);

        //if listener throws Exception then @JmsListener tries to listen again, so invoke listen method again
        throw new RuntimeException();
    }

    @JmsListener(destination = JmsConfig.SEND_AND_RECEIVE_QUEUE)
    public void listenSendAndReceive(@Payload HelloWorldMessage helloWorldMessage,
                                     @Headers MessageHeaders messageHeaders,
                                     Message message) throws JMSException {

        log.debug("Received message from queue {}", JmsConfig.SEND_AND_RECEIVE_QUEUE);

        //it gets message which come from sender and send response back
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), createResponseMessage());
        log.debug("Response message sent");
    }

    private HelloWorldMessage createResponseMessage() {
        return HelloWorldMessage.builder().id(UUID.randomUUID()).message("response message").build();
    }

}
