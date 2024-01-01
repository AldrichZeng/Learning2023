//package com.zy;
//
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.alibaba.datax.dataxservice.face.eventcenter.model.Consts;
//import com.alibaba.di.utils.AcsRequestUtil;
//import com.alibaba.fastjson.JSON;
//
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.gpdb.model.v20160503.DescribeDBInstanceAttributeRequest;
//import com.aliyuncs.gpdb.model.v20160503.DescribeDBInstanceAttributeResponse;
//import com.aliyuncs.http.ProtocolType;
//import com.aliyuncs.profile.DefaultProfile;
//import edu.emory.mathcs.backport.java.util.Arrays;
//import org.apache.commons.collections.map.HashedMap;
//import org.junit.Test;
//
///**
// * @author 匠承
// * @Date: 2023/11/9 16:18
// */
//public class ADBPGTest {
//    String regionId = "cn-shanghai";
//    List<String> regionList = new ArrayList<>(Arrays.asList(new String[]{"cn-shanghai", "cn-hangzhou", "cn-beijing", "cn-zhangjiakou", "ap-southeast-5"}));
//    // 全息账号
//    String instanceId = "gp-uf63b8re28i926zqf"; // 上海
//    //String instanceId = "gp-k1ag80yez5h9xty88"; // 印尼
//    // String instanceId = "gp-8vb243if4kew6alv8"; // 张家口
//    List<String> instanceList = new ArrayList<>(Arrays.asList(new String[]{"gp-uf63b8re28i926zqf", "gp-k1ag80yez5h9xty88", "gp-8vb243if4kew6alv8"}));
//    String accessId = "*";
//    String accessKey = "*";
//    String domain = "gpdb.cn-shanghai.aliyuncs.com";
//
//    // di账号
//    //String instanceId = "gp-k1avj2bqf19aawz4n"; // 印尼
//    //String accessId = "*";
//    //String accessKey = "*";
//
//    @Test
//    public void test1() throws Exception {
//        needEndPoint(regionId, accessId, accessKey, instanceId, domain);
//    }
//
//    @Test
//    public void test2() throws Exception {
//        noEndpoint(regionId, accessId, accessKey, instanceId);
//    }
//
//    @Test
//    public void test3() throws Exception {
//        for (String region : regionList) {
//            for (String instance : instanceList) {
//                noEndpoint(region, accessId, accessKey, instance);
//            }
//        }
//    }
//
//    @Test
//    public void test4() throws Exception {
//        for (String region : regionList) {
//            for (String instance : instanceList) {
//                needEndPoint(region, accessId, accessKey, instance, "gpdb." + region + ".aliyuncs.com");
//            }
//            System.out.println("============");
//        }
//    }
//
//    Map<String, String> map = new HashMap<>();
//
//    @Test
//    public void test5() throws Exception {
//        map.put("cn-shanghai", "gpdb.aliyuncs.com");
//        map.put("cn-hangzhou", "gpdb.aliyuncs.com");
//        map.put("cn-beijing", "gpdb.aliyuncs.com");
//        map.put("cn-zhangjiakou", "gpdb.cn-zhangjiakou.aliyuncs.com");
//        map.put("ap-southeast-5", "gpdb.ap-southeast-5.aliyuncs.com");
//
//        for (String region : regionList) {
//            String domain = map.get(region);
//            for (String instance : instanceList) {
//                needEndPoint(region, accessId, accessKey, instance, domain);
//            }
//            System.out.println("======");
//        }
//    }
//
//    private void noEndpoint(String regionId, String accessId, String accessKey, String instanceId) throws Exception {
//        DefaultProfile profileGetRegion = DefaultProfile.getProfile(regionId, accessId, accessKey);
//        //DefaultProfile.addEndpoint(endpointName, regionId, product, domain);
//        IAcsClient client = new DefaultAcsClient(profileGetRegion);
//        DescribeDBInstanceAttributeRequest request = new DescribeDBInstanceAttributeRequest();
//        request.setDBInstanceId(instanceId);
//        request.setProtocol(ProtocolType.HTTP);
//        request.setSecurityToken(null);
//        try {
//            DescribeDBInstanceAttributeResponse response = (DescribeDBInstanceAttributeResponse) AcsRequestUtil.getAcsResponse(client, request, Consts.ActionTarget.HYBRIDDB4PG.toString(), "describeGpdbInfo");
//            System.out.println("success:\t" + regionId + "," + instanceId + "\tresult: " + JSON.toJSONString(response));
//        } catch (Exception e) {
//            System.out.println("failed:\t" + regionId + "," + instanceId + "\texception: " + e);
//        }
//    }
//
//    private void needEndPoint(String regionId, String accessId, String accessKey, String instanceId, String domain) throws Exception {
//        DefaultProfile profileGetRegion = DefaultProfile.getProfile(regionId, accessId, accessKey);
//        DefaultProfile.addEndpoint("gpdb", regionId, "gpdb", domain);
//        IAcsClient client = new DefaultAcsClient(profileGetRegion);
//        DescribeDBInstanceAttributeRequest request = new DescribeDBInstanceAttributeRequest();
//        request.setDBInstanceId(instanceId);
//        request.setProtocol(ProtocolType.HTTP);
//        request.setSecurityToken(null);
//        try {
//            DescribeDBInstanceAttributeResponse response = (DescribeDBInstanceAttributeResponse) AcsRequestUtil.getAcsResponse(client, request, Consts.ActionTarget.HYBRIDDB4PG.toString(), "describeGpdbInfo");
//            System.out.println("success:\t" + regionId + "," + instanceId + "," + domain + "\tresult: " + JSON.toJSONString(response));
//        } catch (Exception e) {
//            System.out.println("failed:\t" + regionId + "," + instanceId + "," + domain + "\texception: " + e);
//        }
//    }
//}
