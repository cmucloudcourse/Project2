package edu.cmu.cs.cloud.samples.aws;

import edu.cmu.cs.cloud.samples.aws.http.HttpCaller;
import edu.cmu.cs.cloud.samples.aws.launcher.LaunchEC2Instance;
import edu.cmu.cs.cloud.samples.aws.launcher.LoadGeneratorLauncher;
import edu.cmu.cs.cloud.samples.aws.launcher.WebServiceLauncher;
import edu.cmu.cs.cloud.samples.aws.pojos.LoadGenerator;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Project2Runner {

    public static void main(String[] args) throws IOException {
//        String loadGeneratorInstanceID = LoadGeneratorLauncher.launchLoadGenerator();
//        System.out.println("Public DNS of Load Generator is "+ LoadGenerator.getInstance().getPublicDNS());
//        System.out.println("Subnet of Load Generator is "+ LoadGenerator.getInstance().getSubnet());
//        LoadGenerator.getInstance().getTags().stream().forEach(i -> System.out.println("Load Generator tag"+i));
//        String webserviceInstanceID = WebServiceLauncher.launchWebService();
//        System.out.println("Public DNS of Webservice is "+ WebServiceLauncher.webServiceMap.get(webserviceInstanceID).getPublicDNS());
//        System.out.println("Subnet of Webservice is "+ WebServiceLauncher.webServiceMap.get(webserviceInstanceID).getSubnet());
//        WebServiceLauncher.webServiceMap.get(webserviceInstanceID).getTags().stream().forEach(i -> System.out.println("Load Generator tag"+i));
          HttpCaller caller = new HttpCaller();
          Map<String,String> userParams = new HashMap<>();
          userParams.put("passwd","EI5bw1SiJhNFxU4gF7P1gQ");
          userParams.put("username","manasshukla.official@gmail.com");
          caller.doGet("http://ec2-54-89-141-3.compute-1.amazonaws.com/password",userParams);
    }

}
