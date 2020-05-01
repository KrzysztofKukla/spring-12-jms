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

import javax.jms.Message;

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

}
