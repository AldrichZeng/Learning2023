package com.example.oceanbase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.oceanbasepro.model.v20190901.DescribeInstancesRequest;
import com.aliyuncs.oceanbasepro.model.v20190901.DescribeInstancesResponse;
import com.aliyuncs.oceanbasepro.model.v20190901.DescribeTenantRequest;
import com.aliyuncs.oceanbasepro.model.v20190901.DescribeTenantResponse;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2024/3/7 20:28
 */
public class OBTest {

    String accessId = "LTAI5tDFScc4t2t1Vb2HoN7p";
    String accessKey = "***";

    @Test
    public void test1() throws ClientException {
        String regionId = "cn-beijing";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessId, accessKey);
        IAcsClient client = new DefaultAcsClient(profile);

        DescribeTenantRequest request = new DescribeTenantRequest();
        request.setSysRegionId(regionId);
        request.setInstanceId("ob5jdb8ka7sf28");
        request.setTenantId("t5jkdkr2mj2gw");
        DescribeTenantResponse response = client.getAcsResponse(request);
        System.out.println(response);
    }

    @Test
    public void test2() throws ClientException {
        String regionId = "cn-beijing";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessId, accessKey);
        IAcsClient client = new DefaultAcsClient(profile);

        DescribeInstancesRequest request = new DescribeInstancesRequest();
        request.setSysRegionId(regionId);
        DescribeInstancesResponse response = client.getAcsResponse(request);
        System.out.println(response);
    }

    @Test
    public void test3() throws ClientException {
        String regionId = "cn-beijing";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessId, accessKey);

        int pageNumber = 1;
        while (true) {
            final int pageNumberFinal = pageNumber;
            System.out.println("pageNumber: " + pageNumberFinal);
            CommonRequest commonRequest = new CommonRequest();
            IAcsClient client = new DefaultAcsClient(profile);
            commonRequest.setSysDomain("oceanbasepro.cn-beijing.aliyuncs.com");
            commonRequest.setSysAction("DescribeInstances");
            commonRequest.setSysVersion("2019-09-01");
            commonRequest.setSysRegionId(regionId);
            commonRequest.putQueryParameter("PageSize", "1");
            commonRequest.putBodyParameter("PageSize", "1");
            commonRequest.putQueryParameter("PageNumber", String.valueOf(pageNumberFinal));
            commonRequest.putBodyParameter("PageNumber", String.valueOf(pageNumberFinal));
            commonRequest.putQueryParameter("SecurityToken", null);
            commonRequest.putBodyParameter("SecurityToken", null);
            CommonResponse response = client.getCommonResponse(commonRequest);
            if (response != null && response.getData() != null) {
                System.out.println("response.data = " + JSON.toJSONString(response.getData()));
                JSONArray instances = JSONObject.parseObject(response.getData()).getJSONArray("Instances");
                if (instances == null || instances.isEmpty()) {
                    break;
                }
                for (Object instance : instances) {
                    JSONObject instanceJSON = (JSONObject) instance;
                    System.out.println("instanceId:" + instanceJSON.getString("InstanceId"));
                    System.out.println("instanceName:" + instanceJSON.getString("InstanceName"));
                }
                pageNumber++;
            } else {
                break;
            }
        }
    }

    @Test
    public void test4() throws ClientException {
        String regionId = "cn-beijing";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessId, accessKey);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setSysDomain("oceanbasepro.cn-beijing.aliyuncs.com");
        commonRequest.setSysAction("DescribeTenants");
        commonRequest.setSysVersion("2019-09-01");
        commonRequest.setSysRegionId(regionId);
        commonRequest.putQueryParameter("InstanceId", "ob5jdb8ka7sf28");
        commonRequest.putBodyParameter("InstanceId", "ob5jdb8ka7sf28");
        CommonResponse response = client.getCommonResponse(commonRequest);
        System.out.println(JSON.toJSONString(response.getData()));
    }

    @Test
    public void test5() throws ClientException {
        String regionId = "cn-beijing";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessId, accessKey);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setSysDomain("oceanbasepro.cn-beijing.aliyuncs.com");
        commonRequest.setSysAction("DescribeTenant");
        commonRequest.setSysVersion("2019-09-01");
        commonRequest.setSysRegionId(regionId);
        commonRequest.putQueryParameter("TenantId", "t5jkdkr2mj2gw");
        commonRequest.putBodyParameter("TenantId", "t5jkdkr2mj2gw");
        commonRequest.putQueryParameter("InstanceId", "ob5jdb8ka7sf28");
        commonRequest.putBodyParameter("InstanceId", "ob5jdb8ka7sf28");
        CommonResponse response = client.getCommonResponse(commonRequest);
        System.out.println(JSON.toJSONString(response.getData()));

        //DescribeTenantResponse describeTenantResponse = JSONObject.parseObject(JSON.toJSONString(response.getData())).toJavaObject(DescribeTenantResponse.class);
        //System.out.println(JSON.toJSONString(describeTenantResponse));
    }

    @Test
    public void test6() {
        String a = "jdbc:oceanbase:";
        System.out.println(a.substring("jdbc:".length(), a.length() - 1));
    }
}
