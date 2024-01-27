package com.zy.metaq;

import java.io.UnsupportedEncodingException;

import com.alibaba.rocketmq.client.impl.MQClientAPIImpl;
import com.alibaba.rocketmq.client.impl.producer.DefaultMQProducerImpl;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

import com.taobao.metaq.client.MetaProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author 匠承
 * @Date: 2023/10/18 16:25
 */
public class MetaqMain {
    public static void main(String[] args) {
        MetaqMain ins = new MetaqMain();
        ins.metaqSend();
    }

    public void metaqSend() {
        SendResult sendResult = null;

        String producerGroup = "datax-metaq-autotest";
        MetaProducer producer = new MetaProducer(producerGroup);
        producer.setRetryAnotherBrokerWhenNotStoreOK(true);
        producer.setUnitName("daily");

        try {
            producer.start();
            metaqInitCheck(producer);
            System.out.println(producer.getInstanceName());
            System.out.println("start");
            String key = "1";
            String[] data = new String[]{"hello", "world", "xyz"};
            String topicVal = "datax-metaq-autotest";
            String tagVal = "tag";

            byte[] message = getMsgBytes(data, ",", -1);

            if (message != null) {
                Message msg = new Message(topicVal, tagVal, key, message);
                System.out.println("msg: " + msg);
                try {
                    sendResult = producer.send(msg);
                    System.out.println("sendResult: " + sendResult);
                } catch (Exception e) {
                    System.out.println("exception 1, " + e);
                }
            }
            producer.shutdown();
        } catch (Exception e) {
            System.out.println("exception 2, " + e);
        }
    }

    public static byte[] getMsgBytes(String[] data, String fieldDelimiter, int messageIndex) throws UnsupportedEncodingException {
        if (messageIndex >= 0) {
            String message = data[messageIndex];
            if (StringUtils.isEmpty(message)) {
                return null;
            }
            return message.getBytes("utf-8");
        }
        int filedNum = data.length;
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < filedNum; i++) {
            String obj = data[i];
            if (obj == null) {
                obj = null;
            }
            if (i != 0) {
                sb.append(fieldDelimiter);
            }
            sb.append(obj);
        }
        return sb.toString().getBytes("utf-8");
    }

    public void metaqInitCheck(MetaProducer producer) {
        DefaultMQProducerImpl producerImpl = producer.getMetaProducerImpl().getDefaultMQProducerImpl();
        MQClientAPIImpl clientImpl = producerImpl.getmQClientFactory().getMQClientAPIImpl();
        if (CollectionUtils.isEmpty(clientImpl.getNameServerAddressList())) {
            // 为获取到，则触发一次
            String res = clientImpl.fetchNameServerAddr();
            System.out.println("check: " + res);
        }
    }
}