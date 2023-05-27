package org.vaadin.artur.hillamicro.shared;

import jakarta.annotation.Nullable;

import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
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

import com.vaadin.flow.shared.util.SharedUtil;

@Configuration
public class MessagingConfiguration {
    public static final String EXCHANGE = "deploy-undeploy-info";

    public enum Type {
        DEPLOY, UNDEPLOY
    };

    public record ApplicationInfo(String tag, @Nullable String title,
            String path, String icon, String importPath) {

        public static ApplicationInfo byConvention(String tag) {
            String title = SharedUtil.camelCaseToHumanFriendly(
                    SharedUtil.dashSeparatedToCamelCase(tag));
            return byConvention(tag, title);
        }

        public static ApplicationInfo byConvention(String tag, String title) {
            String path = "/" + tag;
            return new ApplicationInfo(tag, title, path, "",
                    path + "/VAADIN/build/" + tag + ".js");

        }
    }

    public record DeploymentInfo(Type type, ApplicationInfo applicationInfo) {
    };

    @Bean
    Queue queue() {
        return QueueBuilder.nonDurable().autoDelete().build();
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
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
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(
                connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        rabbitTemplate.setExchange(EXCHANGE);
        return rabbitTemplate;
    }

}
