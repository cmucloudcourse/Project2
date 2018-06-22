package edu.cmu.cs.cloud.samples.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.waiters.WaiterUnrecoverableException;
import edu.cmu.cs.cloud.samples.aws.launcher.LaunchEC2Instance;
import edu.cmu.cs.cloud.samples.aws.launcher.LoadGeneratorLauncher;
import edu.cmu.cs.cloud.samples.aws.pojos.LoadGenerator;

import java.lang.reflect.Executable;
import java.util.*;

public class AutoScalingRunner {

    public static Map<String, String> sgMap = new HashMap<>();

    public static void main(String[] args) {
        setup("loadbalancer", "web-service");
        tearDown(sgMap.keySet(), Arrays.asList(LoadGenerator.getInstance().getInstanceID()));
    }

    public static void setup(String... securityGroups) {

        //Create security groups
        for (String securityGroup : securityGroups) {
            try {
                sgMap.put(securityGroup, LaunchEC2Instance.createSecurityGroup(securityGroup));
            } catch (AmazonClientException e) {
                //Check if the sec group already exists. If yes just populate the map with its name
                if (e.getMessage().contains("The security group '" + securityGroup + "' already exists")) {
                    sgMap.put(securityGroup, securityGroup);
                }
                e.printStackTrace();
            }
        }
        LoadGeneratorLauncher.launchLoadGenerator(sgMap.get("loadbalancer"));
    }

    public static void tearDown(Set<String> securityGroups, List<String> instances) {


        //Terminating instances
        for (String instance : instances) {
            try {
                LaunchEC2Instance.terminate(instance);
            } catch (AmazonClientException ae) {
                System.out.println("Problem while terminating the instance " + instance);
                ae.printStackTrace();
            }
        }

        //Deleting security groups
        try {
            for (String securityGroup : securityGroups) {
                LaunchEC2Instance.deleteSecurityGroup(securityGroup);
                sgMap.entrySet().remove(securityGroup); //to avoid concurrent modification exception
            }
        } catch (AmazonClientException e) {
            System.out.println("Exception occured while deleting security groups. Please delete them manually");
            e.printStackTrace();
        }


    }
}
