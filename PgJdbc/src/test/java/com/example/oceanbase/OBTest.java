package com.example.oceanbase;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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

    String accessId = "*";
    String accessKey = "*";

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

    @Test
    public void test7() {
        String x = "{\n"
                + "    \"errno\": 0,\n"
                + "    \"msg\": \"success\",\n"
                + "    \"data\":\n"
                + "    {\n"
                + "        \"totalSize\": 275,\n"
                + "        \"pageNumber\": 1,\n"
                + "        \"pageSize\": 20,\n"
                + "        \"list\":\n"
                + "        [\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-03-13%2F73ac6a7a556c63dabaff6c6c1b029dbc.pdf\",\n"
                + "                \"fileName\": \"枣强二次图.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 29,\n"
                + "                \"size\": 13499173,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=393\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-03-13%2F73ac6a7a556c63dabaff6c6c1b029dbc.pdf\",\n"
                + "                \"@id\": \"5_51bc1847afdc2165d7806831d18ddbb9\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"29\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 5,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675669855000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"51bc1847afdc2165d7806831d18ddbb9\",\n"
                + "                \"oriId\": 393,\n"
                + "                \"updateTime\": 1678711190000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-07%2F201f7de561ddefdb6602a4ac483e97ab.pdf\",\n"
                + "                \"fileName\": \"阜城二次图.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 29,\n"
                + "                \"size\": 26582349,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=371\",\n"
                + "                \"errMsg\": \"解析失败\",\n"
                + "                \"convertFailed\": 3,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-07%2F201f7de561ddefdb6602a4ac483e97ab.pdf\",\n"
                + "                \"@id\": \"5_eb4e9854478616956b5574d3d32e00a7\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"29\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 5,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675669855000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"eb4e9854478616956b5574d3d32e00a7\",\n"
                + "                \"oriId\": 371,\n"
                + "                \"updateTime\": 1675751693000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2Faec5a040828fa68a3e931a1784c7af60.pdf\",\n"
                + "                \"fileName\": \"电力安全工作规程电力线路部分（GB26859-2011）.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 1142549,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=551\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2Faec5a040828fa68a3e931a1784c7af60.pdf\",\n"
                + "                \"@id\": \"7_86f6b78da38c8477c5175f0b9f15494b\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"86f6b78da38c8477c5175f0b9f15494b\",\n"
                + "                \"oriId\": 551,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2F48aba7231ec77ef5d47b5f921471eee9.doc\",\n"
                + "                \"fileName\": \"Q／GDW175-2008《变压器、高压并联电抗器和母线保护及辅助装置标准化设计规范》.doc\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 1702400,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=543\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2F48aba7231ec77ef5d47b5f921471eee9.docx\",\n"
                + "                \"@id\": \"7_e3fef627a0ed9819dee53cefb6d4dcbc\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"word\",\n"
                + "                \"id\": \"e3fef627a0ed9819dee53cefb6d4dcbc\",\n"
                + "                \"oriId\": 543,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2Fcf75f3816888f536ca929b1132b09ea9.pdf\",\n"
                + "                \"fileName\": \"Q_GDW393-2009110（66）kV〜220kV智能变电站设计规范.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 6899102,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=559\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2Fcf75f3816888f536ca929b1132b09ea9.pdf\",\n"
                + "                \"@id\": \"7_c845cdc9b72fa502626222f1565769c1\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"c845cdc9b72fa502626222f1565769c1\",\n"
                + "                \"oriId\": 559,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2Fad1133116f956d9a0417e1ba347b906b.pdf\",\n"
                + "                \"fileName\": \"架空输电线路运行规程（DLT-741-2010）.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 356176,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=555\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2Fad1133116f956d9a0417e1ba347b906b.pdf\",\n"
                + "                \"@id\": \"7_c6196c8f59ecf98efc44325a9fa4b9c0\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"c6196c8f59ecf98efc44325a9fa4b9c0\",\n"
                + "                \"oriId\": 555,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2Fabd6d300ef8f0b221ee7f46864b444c5.pdf\",\n"
                + "                \"fileName\": \"国能安全2014161号防止电力生产事故的二十五项重点要求.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 70910831,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=567\",\n"
                + "                \"errMsg\": \"文档转换过大，只支持标题检索\",\n"
                + "                \"convertFailed\": 2,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2Fabd6d300ef8f0b221ee7f46864b444c5.pdf\",\n"
                + "                \"@id\": \"7_0478106413438b21a9b56c8113821327\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"0478106413438b21a9b56c8113821327\",\n"
                + "                \"oriId\": 567,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2F3acacc68919629d60124cd5117e57b71.pdf\",\n"
                + "                \"fileName\": \"GBㄍT19963.1-2021风电场接入电力系统技术规定第1部分：陆上风电.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 1887118,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=541\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2F3acacc68919629d60124cd5117e57b71.pdf\",\n"
                + "                \"@id\": \"7_f27026ce3dbfc77221d63d4ffbb636e3\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"f27026ce3dbfc77221d63d4ffbb636e3\",\n"
                + "                \"oriId\": 541,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2Fdc981a8a0f16dde96e28af6dd69c26ef.pdf\",\n"
                + "                \"fileName\": \"GBㄍT25385-2019风力发电机组运行及维护要求.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 1268826,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=545\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2Fdc981a8a0f16dde96e28af6dd69c26ef.pdf\",\n"
                + "                \"@id\": \"7_53e11825ff14e15f9d5a6f7f7eacee69\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"53e11825ff14e15f9d5a6f7f7eacee69\",\n"
                + "                \"oriId\": 545,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2Fe528029086ce4aec3b91b24de2eb6fa8.pdf\",\n"
                + "                \"fileName\": \"NBT32004-2018光伏并网逆变器技术规范.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 12747414,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=561\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2Fe528029086ce4aec3b91b24de2eb6fa8.pdf\",\n"
                + "                \"@id\": \"7_d7520744acf2e3f0b71ab934f659fe34\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"d7520744acf2e3f0b71ab934f659fe34\",\n"
                + "                \"oriId\": 561,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2Fc10026990cfd786003f1d29755137a62.pdf\",\n"
                + "                \"fileName\": \"风力发电场设计规范.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 20720552,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=563\",\n"
                + "                \"errMsg\": \"文档转换过大，只支持标题检索\",\n"
                + "                \"convertFailed\": 2,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2Fc10026990cfd786003f1d29755137a62.pdf\",\n"
                + "                \"@id\": \"7_85df0bd4df8deb05edfadb36c4c989cf\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"85df0bd4df8deb05edfadb36c4c989cf\",\n"
                + "                \"oriId\": 563,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2F0854a693480712693ecd1ba36070e5ed.pdf\",\n"
                + "                \"fileName\": \"NBT32006-2013光伏发电站电能质量检测技术规程.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 2608044,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=549\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2F0854a693480712693ecd1ba36070e5ed.pdf\",\n"
                + "                \"@id\": \"7_8e1ab799a7f39c17065bfa9d887f47e2\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"8e1ab799a7f39c17065bfa9d887f47e2\",\n"
                + "                \"oriId\": 549,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2F13cf681015e4efa6cfc7b3e97d6bd49d.pdf\",\n"
                + "                \"fileName\": \"十八项反措实施细则.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 793331,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=557\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2F13cf681015e4efa6cfc7b3e97d6bd49d.pdf\",\n"
                + "                \"@id\": \"7_9cf2dcbef2b4336dea8e4138d9573433\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"9cf2dcbef2b4336dea8e4138d9573433\",\n"
                + "                \"oriId\": 557,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2F1186ae33b09f4968f2ad2552dfb05674.pdf\",\n"
                + "                \"fileName\": \"南方电网《电力设备检修试验规程》2017.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 8006124,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=565\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2F1186ae33b09f4968f2ad2552dfb05674.pdf\",\n"
                + "                \"@id\": \"7_9b51115cc0186ae7ef4328ac29558c3b\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"9b51115cc0186ae7ef4328ac29558c3b\",\n"
                + "                \"oriId\": 565,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2F18e99a5ff0a0e3efd248b1d20a99b136.pdf\",\n"
                + "                \"fileName\": \"GWO-BSTv14.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 3225152,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=553\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2F18e99a5ff0a0e3efd248b1d20a99b136.pdf\",\n"
                + "                \"@id\": \"7_8ec8e18614aade7fa53faad5cd297998\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"8ec8e18614aade7fa53faad5cd297998\",\n"
                + "                \"oriId\": 553,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2Ffebf2044735cb0f101e9647ec882a794.pdf\",\n"
                + "                \"fileName\": \"Q_GDW422-2010国家电网继电保护整定计算技术规范.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 276185,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=547\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2Ffebf2044735cb0f101e9647ec882a794.pdf\",\n"
                + "                \"@id\": \"7_d44bc4222fababd5e6c2b7f82e240924\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670622000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"d44bc4222fababd5e6c2b7f82e240924\",\n"
                + "                \"oriId\": 547,\n"
                + "                \"updateTime\": 1675670622000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2Fafb1b10e6e2e00baa1c2fe270438faa4.pdf\",\n"
                + "                \"fileName\": \"GBㄍT19568-2004风力发电机组装配和安装规范.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 776795,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=531\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2Fafb1b10e6e2e00baa1c2fe270438faa4.pdf\",\n"
                + "                \"@id\": \"7_b73f296ecddc6a26c7af762e754c6d3b\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670541000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"b73f296ecddc6a26c7af762e754c6d3b\",\n"
                + "                \"oriId\": 531,\n"
                + "                \"updateTime\": 1675670542000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2Fa71b8fdf8378397c1d489bf29bc3ee6b.pdf\",\n"
                + "                \"fileName\": \"GBT11022-2011高压开关设备和控制设备标准的共用技术要求.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 5723105,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=535\",\n"
                + "                \"errMsg\": \"文档转换过大，只支持标题检索\",\n"
                + "                \"convertFailed\": 2,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2Fa71b8fdf8378397c1d489bf29bc3ee6b.pdf\",\n"
                + "                \"@id\": \"7_3136768a150918fe846207eba3b02455\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670541000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"3136768a150918fe846207eba3b02455\",\n"
                + "                \"oriId\": 535,\n"
                + "                \"updateTime\": 1675670542000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2Fc9ddc9d78543b1eacb0207c9a225b125.pdf\",\n"
                + "                \"fileName\": \"GBZ34161-2017智能微电网保护设备技术导则.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 4136803,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=533\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2Fc9ddc9d78543b1eacb0207c9a225b125.pdf\",\n"
                + "                \"@id\": \"7_e035391e043a0f634c5f47ea2e1b8cbc\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670541000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"e035391e043a0f634c5f47ea2e1b8cbc\",\n"
                + "                \"oriId\": 533,\n"
                + "                \"updateTime\": 1675670542000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            },\n"
                + "            {\n"
                + "                \"attachments\":\n"
                + "                [],\n"
                + "                \"createBy\": \"001\",\n"
                + "                \"downloadUrl\": \"/data/file/download?path=2023-02-06%2F141929003bf8f638694f6fba0c1d0129.pdf\",\n"
                + "                \"fileName\": \"GBZ19963-2005风电场接入电力系统技术规定.pdf\",\n"
                + "                \"inputType\": \"upload\",\n"
                + "                \"nodeId\": 31,\n"
                + "                \"size\": 235121,\n"
                + "                \"sourceName\": \"手动上传\",\n"
                + "                \"status\": 50,\n"
                + "                \"trueName\": \"超级管理员\",\n"
                + "                \"updateBy\": \"001\",\n"
                + "                \"url\": \"./#/document/preview?fileId=525\",\n"
                + "                \"errMsg\": null,\n"
                + "                \"convertFailed\": 0,\n"
                + "                \"@contentType\": \"file\",\n"
                + "                \"@downloadUrl\": \"/data/file/download?path=2023-02-06%2F141929003bf8f638694f6fba0c1d0129.pdf\",\n"
                + "                \"@id\": \"7_1daec1d98f9c291d2a5712a4a5c795cc\",\n"
                + "                \"@markdel\": 0,\n"
                + "                \"@inputType\": \"upload\",\n"
                + "                \"@nodeid\":\n"
                + "                [\n"
                + "                    \"31\"\n"
                + "                ],\n"
                + "                \"@sourceId\": 7,\n"
                + "                \"@type\": \"文档\",\n"
                + "                \"createTime\": 1675670541000,\n"
                + "                \"docType\": \"pdf\",\n"
                + "                \"id\": \"1daec1d98f9c291d2a5712a4a5c795cc\",\n"
                + "                \"oriId\": 525,\n"
                + "                \"updateTime\": 1675670542000,\n"
                + "                \"@extFields\": null,\n"
                + "                \"@user_classes_path\": null\n"
                + "            }\n"
                + "        ]\n"
                + "    }\n"
                + "}";
        JSON.parse(x);
    }

    @Test
    public void test100() {
        List<String> inputs = new ArrayList<>();
        inputs.add("\"public\".\"tableName\"");
        inputs.add("\"public\".\"table.Name\"");
        inputs.add("\"sche.ma\".\"tableName\"");
        inputs.add("\"hello\".\"tableName\"");
        Pattern pattern = Pattern.compile("\"([^\"]+)\"\\.\"([^\"]+)\"");
        for (int i = 0; i < inputs.size(); i++) {
            String input = inputs.get(i);
            Matcher matcher = pattern.matcher(input);

            if (matcher.matches()) {
                System.out.println("Match found: " + input);
                System.out.println(matcher.group(1));
                System.out.println(matcher.group(2));
            } else {
                System.out.println("No match.");
            }
        }
    }

    @Test
    public void test101(){
        Timestamp timestamp = null;
        Instant instant = timestamp.toInstant();
    }

    @Test
    public void test02(){
        Long rawData = null;
        Instant instant = Instant.ofEpochMilli(rawData);
    }

    @Test
    public void test03(){
        Object data = null;
        Long raw = (Long) data;
        System.out.println(raw==null);
    }
}
