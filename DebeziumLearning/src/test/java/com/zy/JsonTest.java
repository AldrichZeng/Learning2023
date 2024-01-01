package com.zy;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/10/17 16:06
 */
public class JsonTest {

    @Test
    public void test01() {
        String positionInfo = "[\n"
                + "    {\n"
                + "        \"position\": \"{\\\"enginePostions\\\":{\\\"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_5149\\\":{\\\"sourceOffset\\\":{\\\"lsn_proc\\\":1625510908960,\\\"lsn_commit\\\":1625510908960,\\\"lsn\\\":1625510908960,\\\"txId\\\":6396377,\\\"ts_usec\\\":1697441060054907},\\\"sourcePartition\\\":{\\\"server\\\":\\\"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_5149\\\"}}},\\\"engineWindows\\\":{\\\"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_5149\\\":[{\\\"sourceOffset\\\":{\\\"lsn_proc\\\":1625494134000,\\\"txId\\\":6396334,\\\"ts_usec\\\":1697440883949020,\\\"lsn\\\":1625494134000},\\\"sourcePartition\\\":{\\\"server\\\":\\\"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_5149\\\"},\\\"timestampMills\\\":1697440883949}]}}\",\n"
                + "        \"stepName\": \"reader\"\n"
                + "    }\n"
                + "]";

        List<StepPositionInfo> res = JSON.parseArray(positionInfo, StepPositionInfo.class);
        System.out.println("size: " + res.size());
        for (int i = 0; i < res.size(); i++) {
            StepPositionInfo each = res.get(i);
            System.out.println("stepName: " + each.getStepName());
            System.out.println("position: " + each.getPosition());
            JSONObject position = JSON.parseObject(each.getPosition());
            System.out.println(position.keySet());
            if(position.containsKey("enginePostions")){
                System.out.println("包含enginePositions，并删除");
                position.remove("enginePostions");
            }
            each.setPosition(position.toJSONString());
        }
        System.out.println("position changed: "+ res.get(0).getPosition());


    }

    public static class StepPositionInfo {
        private String stepName;
        private String position;

        public StepPositionInfo() {
        }

        public String getStepName() {
            return this.stepName;
        }

        public void setStepName(String stepName) {
            this.stepName = stepName;
        }

        public String getPosition() {
            return this.position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
}
