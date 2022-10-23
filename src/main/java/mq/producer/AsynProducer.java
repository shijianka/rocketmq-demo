package mq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息
 */
public class AsynProducer {
    public static void main(String[] args) throws Exception{
        //1.创建消息生产者，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.254.128:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            //创建消息对象，指定主题Topic，tag，消息体
            Message msg = new Message("topic-base","tag3",("hello world ,异步消息"+i).getBytes());
            //发送异步消息
            producer.send(msg, new SendCallback() {
                /**
                 * 发送成功回调函数
                 * @param sendResult
                 */
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功"+sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送异常"+throwable);
                }
            });
            TimeUnit.SECONDS.sleep(1);

        }

        producer.shutdown();
    }
}
