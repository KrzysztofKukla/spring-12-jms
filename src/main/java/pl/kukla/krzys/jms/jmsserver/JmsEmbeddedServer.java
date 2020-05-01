package pl.kukla.krzys.jms.jmsserver;

import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.springframework.boot.CommandLineRunner;

/**
 * @author Krzysztof Kukla
 */
public class JmsEmbeddedServer implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        ActiveMQServer activeMQServer = configureJmsServer();
        activeMQServer.start();
    }

    private ActiveMQServer configureJmsServer() throws Exception {
        return ActiveMQServers.newActiveMQServer(new ConfigurationImpl()
            .setPersistenceEnabled(false)
            .setJournalDirectory("target/data/journal")
            .setSecurityEnabled(false)
            .addAcceptorConfiguration("invm", "vm://0")
        );
    }

}
