package pl.kukla.krzys.jms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Krzysztof Kukla
 */
//it enables task scheduler in Spring
@EnableScheduling
//it enables async tasks
@EnableAsync
@Configuration
public class TaskConfiguration {

    @Scheduled(fixedRate = 10)
    //it allows to run async task
    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

}
