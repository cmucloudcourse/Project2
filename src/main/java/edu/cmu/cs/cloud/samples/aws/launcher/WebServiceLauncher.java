package edu.cmu.cs.cloud.samples.aws.launcher;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Tag;
import edu.cmu.cs.cloud.samples.aws.html.TestIDParser;
import edu.cmu.cs.cloud.samples.aws.http.HttpCaller;
import edu.cmu.cs.cloud.samples.aws.pojos.WebService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebServiceLauncher {
    private static int count=1;
    private static final String AMI_ID         = "ami-886226f7";
    private static final String INSTANCE_TYPE  = "m3.medium";
    private static final String KEY_NAME       = "15319-demo";
    private static final String SECURITY_GROUP = "sg-0915e3b86fdf658ec";
    private static final List<Tag> tags = new ArrayList<>();
    public static Instance wsintance;
    public static Map<String, WebService> webServiceMap = new HashMap<>();

    public static String launchWebService(){
        tags.add(new Tag("project","2"));
        tags.add(new Tag("type","WebService"));
        tags.add(new Tag("instance",String.valueOf(count++)));
        wsintance = LaunchEC2Instance.launchInstance(AMI_ID,INSTANCE_TYPE,KEY_NAME,SECURITY_GROUP,tags);
        WebService webService = new WebService(wsintance);
        webServiceMap.put(wsintance.getInstanceId(),webService);
        return wsintance.getInstanceId();
    }

    public static String launchWebService(String securityGroupId){
        tags.add(new Tag("project","2"));
        tags.add(new Tag("type","WebService"));
        tags.add(new Tag("instance",String.valueOf(count++)));
        wsintance = LaunchEC2Instance.launchInstance(AMI_ID,INSTANCE_TYPE,KEY_NAME,securityGroupId,tags);
        WebService webService = new WebService(wsintance);
        webServiceMap.put(wsintance.getInstanceId(),webService);
        return wsintance.getInstanceId();
    }

    public static boolean checkWebService(String dnsWebService) throws InterruptedException, IOException {
        Map<String, String> requestParams = new HashMap<>();
        HttpCaller httpCaller = new HttpCaller();
        boolean isServicePingable = false;
        Map<String, String> responseMap;
        for (int i = 0; i < 5; i++) {
            Thread.sleep(5000);
            try {
                responseMap = httpCaller.doGet(pingWebServiceURL(dnsWebService), requestParams);
                System.out.println("Response code from hitting /lookup/random" +responseMap.get("httpCode"));
                if (responseMap.get("httpCode").equals("200")) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
        return false;
    }

    public static String pingWebServiceURL(String dnsWebService){
        System.out.println("http://"+dnsWebService+"/lookup/random");
        return "http://"+dnsWebService+"/lookup/random";
    }
}