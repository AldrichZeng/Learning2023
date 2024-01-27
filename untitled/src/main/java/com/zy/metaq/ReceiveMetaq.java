package com.zy.metaq;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;

import com.taobao.metaq.client.MetaPushConsumer;

/**
 * @author 匠承
 * @Date: 2023/11/28 17:11
 */
public class ReceiveMetaq {

    public ReceiveMetaq() {
    }

    public static void main(String[] args) throws InterruptedException, MQClientException, IOException {
        final Object lock = new Object();
        if (args == null || args.length < 3) {
            System.out.println("usage: group  topic filepath  tag");
            System.exit(-1);
        }

        String group = args[0];
        String topic = args[1];
        String file = args[2];
        String tag = "*";
        if (args.length == 4) {
            tag = args[3];
        }

        final FileOutputStream fos = new FileOutputStream(file);
        MetaPushConsumer consumer = new MetaPushConsumer(group);
        // MetaPullConsumer
        consumer.subscribe(topic, tag);
        consumer.setConsumeMessageBatchMaxSize(1024);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                Iterator var4 = msgs.iterator();

                while(var4.hasNext()) {
                    MessageExt msg = (MessageExt)var4.next();

                    try {
                        synchronized(lock) {
                            fos.write((new String(msg.getBody(), "utf-8")).getBytes());
                            fos.write("\n".getBytes());
                        }
                    } catch (Exception var7) {
                        var7.printStackTrace();
                    }
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        TimeUnit.SECONDS.sleep(30L);
        if (fos != null) {
            fos.flush();
            fos.close();
        }

        System.exit(0);
    }
}
