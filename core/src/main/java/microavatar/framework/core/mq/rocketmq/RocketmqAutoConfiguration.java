package microavatar.framework.core.mq.rocketmq;

import microavatar.framework.core.mq.rocketmq.property.RocketmqProperties;
import microavatar.framework.core.mq.rocketmq.property.Subscription;
import microavatar.framework.core.util.log.LogUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.ServiceState;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static microavatar.framework.core.mq.rocketmq.property.RocketmqProperties.PREFIX;

@Configuration
@EnableConfigurationProperties(RocketmqProperties.class)
@ConditionalOnProperty(prefix = PREFIX, value = "namesrvAddr")
public class RocketmqAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private RocketmqProperties properties;

    @Resource
    private ApplicationEventPublisher publisher;

    private DefaultMQPushConsumer consumer;

    /**
     * 初始化向rocketmq发送普通消息的生产者
     */
    @Bean
    @ConditionalOnProperty(prefix = PREFIX, value = "producer.instanceName")
    public DefaultMQProducer defaultProducer() throws MQClientException {
        /*
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例
         * 注意：ProducerGroupName需要由应用来保证唯一
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer(properties.getProducerGroupName());
        producer.setNamesrvAddr(properties.getNamesrvAddr());
        producer.setVipChannelEnabled(false);

        /*
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();
        LogUtil.getLogger().info("RocketMq defaultProducer 已启动。");

        Message msg = new Message("p2pMessage",
                "tagP2P",
                "key1",
                ("Hello p2p").getBytes());
        try {
            SendResult sendResult = producer.send(msg);
        } catch (Exception e) {
            LogUtil.getLogger().error("RocketMq defaultProducer 发送消息失败。{}", e.getMessage(), e);
        }

        Message msg2 = new Message("p2gMessage", ("Hello p2g").getBytes());
        try {
            SendResult sendResult = producer.send(msg2);
        } catch (Exception e) {
            LogUtil.getLogger().error("RocketMq defaultProducer 发送消息失败。{}", e.getMessage(), e);
        }

        return producer;
    }

    /**
     * 初始化向rocketmq发送事务消息的生产者
     */
    @Bean
    @ConditionalOnProperty(prefix = PREFIX, value = "producer.tranInstanceName")
    public TransactionMQProducer transactionProducer() throws MQClientException {
        /*
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例
         * 注意：ProducerGroupName需要由应用来保证唯一
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        TransactionMQProducer producer = new TransactionMQProducer(properties.getProducerGroupName() + "Tran");
        producer.setNamesrvAddr(properties.getNamesrvAddr());

        // 事务回查最小并发数
        producer.setCheckThreadPoolMinSize(2);
        // 事务回查最大并发数
        producer.setCheckThreadPoolMaxSize(2);
        // 队列数
        producer.setCheckRequestHoldMax(2000);

        // 由于社区版本的服务器阉割调了消息回查的功能，所以这个地方没有意义
        //TransactionCheckListener transactionCheckListener = new TransactionCheckListenerImpl();
        //producer.setTransactionCheckListener(transactionCheckListener);

        /*
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();
        LogUtil.getLogger().info("RocketMq TransactionMQProducer 已启动。");

        // Message msg = new Message("sendMsgToUser",// topic
        //         "TagA",// tag
        //         "OrderID001",// key
        //         ("Hello MetaQA").getBytes());// body
        // try {
        //     SendResult sendResult = producer.send(msg);
        // } catch (Exception e) {
        //     LogUtil.getLogger().error("RocketMq TransactionMQProducer 启动失败。{}", e.getMessage(), e);
        // }

        return producer;
    }

    /**
     * 初始化rocketmq消息监听方式的消费者
     */
    @Bean
    @ConditionalOnProperty(prefix = PREFIX, value = "consumer.instanceName")
    public DefaultMQPushConsumer pushConsumer() throws MQClientException {
        /*
         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例
         * 注意：ConsumerGroupName需要由应用来保证唯一
         */
        consumer = new DefaultMQPushConsumer(properties.getConsumerGroupName());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setNamesrvAddr(properties.getNamesrvAddr());
        consumer.setConsumeMessageBatchMaxSize(1);//设置批量消费，以提升消费吞吐量，默认是1

        /*
         * 订阅指定topic下tags
         */
        Map<String, Subscription> subscriptions = properties.getConsumer().getSubscriptions();
        subscriptions.forEach((k, v) -> {
            try {
                consumer.subscribe(v.getTopic(), (v.getTag() == null || v.getTag().length() == 0) ? "*" : v.getTag());
            } catch (MQClientException e) {
                LogUtil.getLogger().error(e.getMessage(), e);
            }
        });

        consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
            // 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
            LogUtil.getLogger().debug("接受到rocketmq消息：size={}", msgs.size());
            for (MessageExt msg : msgs) {
                try {
                    // 发布消息到达的事件，以便分发到每个tag的监听方法
                    LogUtil.getLogger().debug("发布rocketmq消息:topic[{}],tags[{}]", msg.getTopic(), msg.getTags());
                    this.publisher.publishEvent(new RocketmqEvent(msg, consumer));
                } catch (Exception e) {
                    LogUtil.getLogger().error("处理rocketmq消息失败：topic[{}],tags[{}],{}", msg.getTopic(), msg.getTags(), e.getMessage(), e);
                    if (msg.getReconsumeTimes() <= 3) {//重复消费3次
                        LogUtil.getLogger().error("等待重新消费消息，重试次数：{}", msg.getReconsumeTimes());
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
            }

            //如果没有return success，consumer会重复消费此信息，直到success。
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        return consumer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (consumer != null && consumer.getDefaultMQPushConsumerImpl().getServiceState() == ServiceState.CREATE_JUST) {
            try {
                 /*
                  * Consumer对象在使用之前必须要调用start初始化，初始化一次即可
                  * 等待spring事件监听相关程序初始化完成,才调用start，否则，会出现对RocketMQ的消息进行消费后立即发布消息到达的事件，
                  * 然而此事件的监听程序还未初始化，从而造成消息的丢失
                  */
                consumer.start();
                LogUtil.getLogger().info("RocketMq consumer 已启动。");
            } catch (MQClientException e) {
                LogUtil.getLogger().error("RocketMq consumer 启动失败。{}", e.getMessage(), e);
            }
        }
    }
}
