package edu.cmu.cs.cloud.samples.aws.launcher;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Tag;
import edu.cmu.cs.cloud.samples.aws.http.HttpCaller;
import edu.cmu.cs.cloud.samples.aws.pojos.LoadGenerator;
import edu.cmu.cs.cloud.samples.aws.html.TestIDParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadGeneratorLauncher {
    private static final String AMI_ID         = "ami-2575315a";
    private static final String INSTANCE_TYPE  = "m3.medium";
    private static final String KEY_NAME       = "15319-demo";
    private static final String SECURITY_GROUP = "sg-0915e3b86fdf658ec";
    private static final List<Tag> tags = new ArrayList<>();
    public static Instance lginstance;

    public static String launchLoadGenerator(){
        tags.add(new Tag("project","2"));
        tags.add(new Tag("type","LoadGenerator"));
        lginstance = LaunchEC2Instance.launchInstance(AMI_ID,INSTANCE_TYPE,KEY_NAME,SECURITY_GROUP,tags);
//        LaunchEC2Instance.describeInstance(lginstance);
        LoadGenerator.getInstance();
        LoadGenerator.init(lginstance);
        return lginstance.getInstanceId();
    }


    public static boolean authenticate(String url) throws IOException, InterruptedException {

        Map<String, String> creds = new HashMap<>();
        creds.put("username",System.getProperty("TPZUserName"));
        creds.put("passwd",System.getProperty("TPZPassword"));

        HttpCaller httprequest = new HttpCaller();
        String responseCode="0";
        for (int i = 0; i < 5; i++) {
            Thread.sleep(10000);
            try {
                responseCode = httprequest.doGet(createAuthenticationURL(url), creds).get("httpCode");
                if(responseCode.equals("200")){
                    System.out.println("Successfully authenticated to load balancer." );
                    return true;
                }
            } catch(IOException e) {
                e.printStackTrace();
                continue;
            }

        }

        System.out.println("Authentication to load generator failed with response code "+responseCode);
        return false;
    }

    private static String createAuthenticationURL(String dnsName){
        System.out.println("Created URL is "+"http://"+dnsName+"/password?");
        return "http://"+dnsName+"/password?";
    }


    public static String submitFirstService(String dnsWebService, String dnsloadGenerator) throws InterruptedException, IOException {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("dns", dnsWebService);
        HttpCaller httpCaller = new HttpCaller();
        Map<String, String> responseMap = new HashMap<>();
        String testID = "SUBMISSIONFAILED";
        for (int i = 0; i < 5; i++) {
            Thread.sleep(5000);
            try {
                responseMap = httpCaller.doGet(createFirstSubmitURL(dnsWebService, dnsloadGenerator), requestParams);
                if(responseMap.get("httpCode").equals("200")){
                    testID = TestIDParser.getTestID(responseMap.get("content"));
                    if (testID.matches("/log\\?name=test\\.\\d{13}\\.log")) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

        }
        return testID;
    }

    private static String createFirstSubmitURL(String dnsWebService, String loadGeneratorURL){
        System.out.println("Created URL : "+"http://"+loadGeneratorURL+"/test/horizontal?");
        return "http://"+loadGeneratorURL+"/test/horizontal?";

    }


    public static String submitHorizontalWebService(String dnsWebService, String dnsloadGenerator) throws IOException, InterruptedException {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("dns",dnsWebService);
        Map<String, String> responseMap = new HashMap<>();
//        requestParams.put("dns", dnsWebService);
        HttpCaller httpCaller = new HttpCaller();
        for (int i = 0; i < 5; i++) { //Try making request 5 times
            try {
                Thread.sleep(10000);
                responseMap = httpCaller.doGet(createHorizontalSubmitURL(dnsWebService, dnsloadGenerator), requestParams);
                if (responseMap.get("httpCode").equals("200")) {
                    System.out.println("Success on submitting the web service instance.");
                    break;
                }
            }catch (IOException e){
                e.printStackTrace();
                continue;
            }

        }

        System.out.println(responseMap.get("content"));
        return responseMap.get("httpCode");
    }

    private static String createHorizontalSubmitURL(String dnsWebService, String loadGeneratorURL){
        System.out.println("Created URL : "+"http://"+loadGeneratorURL+"/test/horizontal/add?");
        return "http://"+loadGeneratorURL+"/test/horizontal/add?";

    }


}




