package edu.cmu.cs.cloud.samples.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.autoscaling.model.AmazonAutoScalingException;
import edu.cmu.cs.cloud.samples.aws.launcher.LaunchConfigLauncher;
import edu.cmu.cs.cloud.samples.aws.launcher.LaunchEC2Instance;
import edu.cmu.cs.cloud.samples.aws.launcher.LoadGeneratorLauncher;
import edu.cmu.cs.cloud.samples.aws.pojos.LoadGenerator;

import java.security.Security;
import java.util.*;

public class AutoScalingRunner {

    public static final Map<String, String> sgMap = new HashMap<>();
    public static final String launchconfig = "project2-lc";





    public static void main(String[] args) {
        sgMap.put("load-generator","load-generator");
        sgMap.put("web-service","web-service");


        setup(sgMap.keySet());


        tearDown(sgMap.keySet(), Arrays.asList(LoadGenerator.getInstance().getInstanceID()), launchconfig);
    }



    private static void setup(Set<String> securityGroups) {

        //Create security groups
        setupSecurityGroups(securityGroups);

        //Launch Load Generator
//        LoadGeneratorLauncher.launchLoadGenerator(sgMap.get("loadbalancer"));

        //Create Launch Config
        LaunchConfigLauncher.createLaunchConfig(launchconfig,securityGroups);
    }


    private static void tearDown(Set<String> securityGroups, List<String> instances, String launchConfigName) {


        //Terminating instances
//        teardownInstances(instances);

        //Deleting security groups
        teardownSecurityGroups(securityGroups);


        //Deleting Launch config
        teardownLaunchConfig(launchConfigName);

    }


    private static void setupSecurityGroups(Set<String> securityGroups) {
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
    }

    private static void teardownInstances(List<String> instances) {
        for (String instance : instances) {
            try {
                LaunchEC2Instance.terminate(instance);
            } catch (AmazonClientException ae) {
                System.out.println("Problem while terminating the instance " + instance);
                ae.printStackTrace();
            }
        }
    }

    private static void teardownSecurityGroups(Set<String> securityGroups) {
        try {
            for (String securityGroup : securityGroups) {
                LaunchEC2Instance.deleteSecurityGroup(securityGroup);
                sgMap.entrySet().remove(securityGroup); //to avoid concurrent modification exception
            }
        } catch (AmazonClientException e) {
            System.out.println("Exception occurred while deleting security groups. Please delete them manually");
            e.printStackTrace();
        }
    }

    private static void teardownLaunchConfig(String launchConfigName) {
        try {
            LaunchConfigLauncher.deleteLaunchConfig(launchConfigName);
        }catch (AmazonAutoScalingException e){
            System.out.println("LaunchConfig with name "+launchConfigName+" does not exists.\nProbably it was deleted in an earlier request");
            e.printStackTrace();
        }
    }
}
