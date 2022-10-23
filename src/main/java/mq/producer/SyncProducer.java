package mq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        //1.创建消息生产者，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.254.128:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            //创建消息对象，指定主题Topic，tag，消息体
            Message msg = new Message("topic-base","tag1",("hello world，同步消息"+i).getBytes());
            SendResult sendResult = producer.send(msg);
            //消息接收队列ID
            int queueId = sendResult.getMessageQueue().getQueueId();
            System.out.println(sendResult);
            TimeUnit.SECONDS.sleep(1);

        }

        producer.shutdown();
    }
}
