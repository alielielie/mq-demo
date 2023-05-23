package cn.itcast.mq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: mq-demo
 * @BelongsPackage: cn.zt.mq
 * @Author: zt
 * @CreateTime: 2023-05-23  14:00
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue(){
        String queueName = "simple.queue";
        String message = "hello, spring amqp";
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Test
    public void testSendMessage2WorkQueue() throws InterruptedException {
        String queueName = "simple.queue";
        String message = "hello, message_";
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }
    }

    @Test
    public void testSendFanoutExchange(){
        //交换机名称
        String exchangeName = "itcast.fanout";
        //消息
        String message = "hello, every one";
        //发送消息
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    @Test
    public void testSendDirectExchange(){
        //交换机名称
        String exchangeName = "itcast.direct";
        //消息
        String message = "hello, red";
        //发送消息
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
    }

    @Test
    public void testSendTopicExchange(){
        //交换机名称
        String exchangeName = "itcast.topic";
        //消息
        String message = "天气不行";
        //发送消息
        rabbitTemplate.convertAndSend(exchangeName, "china.weather", message);
    }

    @Test
    public void testSendObjectQueue(){
        Map<String, Object> msg = new HashMap<>();
        msg.put("name", "柳岩");
        msg.put("age", 21);
        rabbitTemplate.convertAndSend("object.queue", msg);
    }

}
