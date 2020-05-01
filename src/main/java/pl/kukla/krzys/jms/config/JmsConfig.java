package pl.kukla.krzys.jms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Krzysztof Kukla
 */
@Configuration
public class JmsConfig {

    @Scheduled(fixedRate = 10)
    //it allows to serialize/mapping message when we put it on the queue ( when we are sending message to Jms )
    //it takes Java object and converts to JSON payload and send to Queue
    //after that convert reverse form Json to Java object
    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        //we are setting property name of type, so Spring can decode that
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

}
