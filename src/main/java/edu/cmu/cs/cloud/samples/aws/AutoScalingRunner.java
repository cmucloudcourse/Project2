package edu.cmu.cs.cloud.samples.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.autoscaling.model.Alarm;
import com.amazonaws.services.autoscaling.model.AmazonAutoScalingException;
import com.amazonaws.services.cloudwatch.model.ComparisonOperator;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsRequest;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsResult;
import com.amazonaws.services.ec2.model.Instance;
import edu.cmu.cs.cloud.samples.aws.http.HttpCaller;
import edu.cmu.cs.cloud.samples.aws.launcher.*;
import edu.cmu.cs.cloud.samples.aws.pojos.ELB;
import edu.cmu.cs.cloud.samples.aws.pojos.LoadGenerator;
import edu.cmu.cs.cloud.samples.aws.pojos.TargetGroup;

import java.io.IOException;
import java.util.*;

public class AutoScalingRunner {

    private static final Map<String, String> sgMap = new HashMap<>();
    private static final String LAUNCHCONFIG = "project2-lc";
    private static final String TARGET_GROUP = "project2-tg";
    private static final Map<String,TargetGroup> targetGroups = new HashMap<>();
    private static final String elbName = "project2-elb";
    private static final String asgName = "project2-asg";
    private static  ELB elb ;
    private static final List<String> alarmNames = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        sgMap.put("load-generator","load-generator");
        sgMap.put("web-service","web-service");


        setup();

        String loadGeneratorDNS = LoadGenerator.getInstance().getPublicDNS();
        boolean authenticated = LoadGeneratorLauncher.authenticate(loadGeneratorDNS);
        if(authenticated){
            try {
                startLoadTest(loadGeneratorDNS);
            }catch (Exception e){
                tearDown(sgMap.keySet(), Arrays.asList(LoadGenerator.getInstance().getInstanceID()), LAUNCHCONFIG, elb.getElnARN());
            }
        }else {
        tearDown(sgMap.keySet(), Arrays.asList(LoadGenerator.getInstance().getInstanceID()), LAUNCHCONFIG, elb.getElnARN());
        }
    }



    private static void setup() {

        //Create security groups
        setupSecurityGroups(sgMap.keySet());

        //Launch Load Generator
        LoadGeneratorLauncher.launchLoadGenerator(sgMap.get("load-generator"));

        //Create Launch Config
        LaunchConfigLauncher.createLaunchConfig(LAUNCHCONFIG,sgMap.get("web-service"));

        //Create target group
        TargetGroup targetGroup = TargetGroupLauncher.launchTargetGroup("project2-tgt");

        elb = ELBLauncher.launchELB(elbName,sgMap.get("web-service"),targetGroup);



        AutoScalingLauncher.createASG(asgName,LAUNCHCONFIG,targetGroup.getArn(),elb.getAvailabilityZones());

        String lowCPUARN = AutoScalingLauncher.createScalingInPolicy(asgName);
        String highCPUARN = AutoScalingLauncher.createScalingOutPolicy(asgName);


        alarmNames.add(AlarmLauncher.createAlarm("highCPUAlarm", 60d, 60, ComparisonOperator.GreaterThanThreshold, highCPUARN));
        alarmNames.add(AlarmLauncher.createAlarm("lowCPUAlarm", 30d, 180, ComparisonOperator.LessThanThreshold, lowCPUARN));



    }


    private static void tearDown(Set<String> securityGroups, List<String> instances, String launchConfigName, String elbARN) {


        //Terminating instances
//        teardownInstances(instances);

        //Delete load balancer
        deleteLoadBalancer(elbARN);

        //Delete Target Group
        teardownTargetGroup();

        //Deleting Launch config
        teardownLaunchConfig(launchConfigName);

        //Delete Alarms
        teardownAlarms(alarmNames);

        //Delete AutoScalingGroup
        AutoScalingLauncher.deleteASG(asgName);

        //Deleting security groups
        teardownSecurityGroups(securityGroups);


    }

    private static void teardownAlarms(List<String> alarmNames) {
        alarmNames.parallelStream().forEach(i -> AlarmLauncher.deleteAlarm(i));
    }

    private static void deleteLoadBalancer(String elbARN) {
        try {
            ELBLauncher.deleteLoadBalancer(elbARN);
        }catch (Exception e ){
            System.out.println("Problem while terminating the load balancer.Delete them manually");
        }
    }

    private static void teardownTargetGroup() {
        try {
            TargetGroupLauncher.deleteTargetGroup(targetGroups.get(TARGET_GROUP).getArn());
        } catch (Exception e ){
        System.out.println("Problem while terminating the load balancer.Delete them manually");
    }
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


    private static void startLoadTest(String loadGenDNSName) throws InterruptedException {
        System.out.println("Starting load test @ " + loadGenDNSName);
        Map<String, String> requestParams = new HashMap<>();
        HttpCaller httpCaller = new HttpCaller();
        requestParams.put("dns", elb.getDnsName());
        Map<String, String> responseMap = new HashMap<>();

        for (int i = 0; i < 5; i++) { //Try making request 5 times
            try {
                Thread.sleep(10000);
                responseMap = httpCaller.doGet(LoadGeneratorLauncher.createAutoScalingURl(loadGenDNSName), requestParams);
                if (responseMap.get("httpCode").equals("200")) {
                    System.out.println("Success on submitting the web service instance.");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}
