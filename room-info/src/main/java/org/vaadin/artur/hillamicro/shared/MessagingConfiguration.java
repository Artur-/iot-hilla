package org.vaadin.artur.hillamicro.shared;

import jakarta.annotation.Nullable;

import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class MessagingConfiguration {
    public static final String QUEUE = "rabbit_queue";
    public static final String EXCHANGE = "rabbit_exchange";
    public static final String ROUTING_KEY = "rabbit_routingKey";

    public enum Type {
        DEPLOY, UNDEPLOY
    };

    public record ApplicationInfo(String tag, @Nullable String title,
            String path, String icon, String importPath) {
    };

    public record DeploymentInfo(Type type, ApplicationInfo applicationInfo) {
    };

    @Bean
    Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    RetryOperationsInterceptor retryInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .backOffOptions(1000, 3.0, 10000).maxAttempts(5)
                // .recoverer(observableRecoverer())
                .build();
    }

    @Bean
    SimpleRabbitListenerContainerFactory retryContainerFactory(
            ConnectionFactory connectionFactory,
            RetryOperationsInterceptor retryInterceptor) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        Advice[] adviceChain = { retryInterceptor };
        factory.setAdviceChain(adviceChain);

        return factory;
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(
                connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
