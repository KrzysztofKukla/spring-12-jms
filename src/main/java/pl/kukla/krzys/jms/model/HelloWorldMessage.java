package pl.kukla.krzys.jms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Krzysztof Kukla
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//for JMS message we need Serializable here
public class HelloWorldMessage implements Serializable {
    private static final long serialVersionUID = -4334367832928030448L;

    private UUID id;
    private String message;

}
