package pl.kukla.krzys.jms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
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
public class HelloSender {

    //jmsTemplate automatically configured to talk to our ActiveMq server
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    //    @Scheduled(fixedRate = 9000)
    public void sendMessage() {
        log.debug("Sending the message ");
        HelloWorldMessage message = createMessage();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
        log.debug("Message has been sent");
    }

    //it allows to send message and expect to reply, so it breaks asynchronicity typical of JMS messaging
    //typically we send a message and forget like above, but in this case we expect response back from listener
    @Scheduled(fixedRate = 9000)
    public void sendAndReceiveMessage() {
        HelloWorldMessage helloWorldMessage = createMessage();
        MessageCreator messageCreator = (session) -> {
            try {
                String helloWorldMessageString = objectMapper.writeValueAsString(helloWorldMessage);
                Message helloMessage = session.createTextMessage(helloWorldMessageString);
                helloMessage.setStringProperty("_type", "pl.kukla.krzys.jms.model.HelloWorldMessage");

                log.debug("Sending message to queue-> {}", JmsConfig.SEND_AND_RECEIVE_QUEUE);
                return helloMessage;
            } catch (JsonProcessingException e) {
                throw new JMSException("Cannot send message");
            }
        };

        jmsTemplate.sendAndReceive(JmsConfig.SEND_AND_RECEIVE_QUEUE, messageCreator);
        log.debug("Message send to queue {}", JmsConfig.SEND_AND_RECEIVE_QUEUE);
    }

    private HelloWorldMessage createMessage() {
        return HelloWorldMessage.builder().id(UUID.randomUUID()).message("this is message").build();
    }

}
