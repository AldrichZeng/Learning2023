package com.zy;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

import com.taobao.metaq.client.MetaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2023/11/28 16:41
 */
public class SendMetaqMessage {
    private static final Logger logger = LoggerFactory.getLogger(SendMetaqMessage.class);
    private static String topicVal;
    private static String tagVal = "tag";
    private static String key = "key";
    private static MetaProducer producer;

    public SendMetaqMessage() {
    }

    public static void init() throws MQClientException {
        producer = new MetaProducer("di-ide");
        producer.setRetryAnotherBrokerWhenNotStoreOK(true);
        producer.start();
    }

    public static void main(String[] args) throws UnsupportedEncodingException, MQBrokerException, RemotingException, InterruptedException, MQClientException, ParseException {
        init();
        // 消息体
        StringBuilder sb = new StringBuilder(args[0]);
        // 消息数量
        int recordNum = Integer.parseInt(args[1]);
        // 消息发送时间间隔
        int interval = Integer.parseInt(args[2]);
        // topic
        topicVal = args[3];

        for(int i = 0; i < recordNum; ++i) {
            MessageExt msg = new MessageExt();
            msg.setTopic(topicVal);
            msg.setTags(tagVal);
            msg.setKeys(key);
            byte[] bytes = sb.append("#").append(i).toString().getBytes("utf-8");
            msg.setBody(bytes);
            producer.send(msg);
            logger.info("send message #" + i);
            Thread.sleep((long)interval);
        }

        producer.shutdown();
    }
}
