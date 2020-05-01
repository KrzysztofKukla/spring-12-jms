package pl.kukla.krzys.jms.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kukla.krzys.jms.config.JmsConfig;
import pl.kukla.krzys.jms.model.HelloWorldMessage;

import java.util.UUID;

/**
 * @author Krzysztof Kukla
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HelloSender {

    //jmsTemplate automatically configured to talk to our ActiveMq server
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        log.debug("Sending the message ");
        HelloWorldMessage message = HelloWorldMessage.builder().id(UUID.randomUUID()).message("this is message").build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
        log.debug("Message has been sent");
    }

}
