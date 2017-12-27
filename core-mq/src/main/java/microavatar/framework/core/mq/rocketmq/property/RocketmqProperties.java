package microavatar.framework.core.mq.rocketmq.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static microavatar.framework.core.mq.rocketmq.property.RocketmqProperties.PREFIX;

/**
 * Rocketmq配置
 */
@Configuration
@ConfigurationProperties(PREFIX)
public class RocketmqProperties {

    public static final String PREFIX = "spring.extend.rocketmq";

    /**
     * nameserver ip端口
     */
    private String namesrvAddr;

    @Value("${spring.application.name}")
    private String producerGroupName;

    @Value("${spring.application.name}")
    private String consumerGroupName;

    private Producer producer;

    private Consumer consumer;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public String getProducerGroupName() {
        return producerGroupName;
    }

    public void setProducerGroupName(String producerGroupName) {
        this.producerGroupName = producerGroupName;
    }

    public String getConsumerGroupName() {
        return consumerGroupName;
    }

    public void setConsumerGroupName(String consumerGroupName) {
        this.consumerGroupName = consumerGroupName;
    }
}
