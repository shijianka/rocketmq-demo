package mq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送单向消息
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception{
        //1.创建消息生产者，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.254.128:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            //创建消息对象，指定主题Topic，tag，消息体
            Message msg = new Message("topic-base","tag2",("hello world,单向消息"+i).getBytes());
            //发送异步消息
            producer.sendOneway(msg);
            TimeUnit.SECONDS.sleep(1);

        }

        producer.shutdown();
    }
}
