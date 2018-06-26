package edu.cmu.cs.cloud.samples.aws.launcher;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClientBuilder;
import com.amazonaws.services.autoscaling.model.*;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.elasticmapreduce.model.SimpleScalingPolicyConfiguration;
import com.amazonaws.waiters.Waiter;
import com.amazonaws.waiters.WaiterParameters;

import java.util.List;

public class AutoScalingLauncher {
    private static final String name = "project2-asg";

    private static final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();
    private static final AmazonAutoScaling asgClient = AmazonAutoScalingClientBuilder
            .standard()
            .withCredentials(credentialsProvider)
            .withRegion(Regions.US_EAST_1)
            .build();


    public static void createASG(String name, String launchConfigName, String targetGroupARN, List<String> availabilityZoneList){
        CreateAutoScalingGroupRequest request = new CreateAutoScalingGroupRequest()
                .withAutoScalingGroupName(name)
                .withLaunchConfigurationName(launchConfigName)
                .withMinSize(1)
                .withMaxSize(15)
                .withDesiredCapacity(1)
                .withHealthCheckGracePeriod(60)
                .withHealthCheckType("ELB")
                .withTargetGroupARNs(targetGroupARN)
                .withTags(new Tag().withKey("project").withValue("2"), new Tag().withKey("type").withValue("asg"))
                .withAvailabilityZones(availabilityZoneList);

        CreateAutoScalingGroupResult result = asgClient.createAutoScalingGroup(request);

        waitUntilProvisioned(name);
    }

    private static void waitUntilProvisioned(String groupName){
        Waiter waiter =  asgClient.waiters().groupInService();
        System.out.println("Waiting for ASG to start running");
        waiter.run(new WaiterParameters<>(
                new DescribeAutoScalingGroupsRequest().withAutoScalingGroupNames(groupName)));
        System.out.println("ASG is running");
    }

    private static void waitUntilDeleted(String groupName){
        Waiter waiter =  asgClient.waiters().groupNotExists();
        System.out.println("Waiting for ASG to start terminating");
        waiter.run(new WaiterParameters<>(
                new DescribeAutoScalingGroupsRequest().withAutoScalingGroupNames(groupName)));
        System.out.println("ASG is terminated");
    }


    public static void deleteASG(String name){
        DeleteAutoScalingGroupRequest request = new DeleteAutoScalingGroupRequest()
                .withAutoScalingGroupName(name);
        DeleteAutoScalingGroupResult result = asgClient.deleteAutoScalingGroup(request);

        waitUntilDeleted(name);
    }


    public static String createIncreaseByOneScalingOutPolicy(String asgName){


        PutScalingPolicyRequest request = new PutScalingPolicyRequest()
                .withAutoScalingGroupName(asgName)
                .withPolicyType("SimpleScaling")
                .withPolicyName("IncreaseByOneScaleOut")
                .withAdjustmentType("ChangeInCapacity").withScalingAdjustment(1)
                .withCooldown(60);
        PutScalingPolicyResult response = asgClient.putScalingPolicy(request);

        return response.getPolicyARN();

    }

    public static String createDecreaseByOneScalingOutPolicy(String asgName){


        PutScalingPolicyRequest request = new PutScalingPolicyRequest()
                .withAutoScalingGroupName(asgName)
                .withPolicyType("SimpleScaling")
                .withPolicyName("DecreaseByOneScaleOut")
                .withAdjustmentType("ChangeInCapacity").withScalingAdjustment(-1)
                .withCooldown(60);
        PutScalingPolicyResult response = asgClient.putScalingPolicy(request);

        return response.getPolicyARN();

    }


    public static String createNormalScalingOutPolicy(String asgName){


        PutScalingPolicyRequest request = new PutScalingPolicyRequest()
                .withAutoScalingGroupName(asgName)
                .withPolicyType("SimpleScaling")
                .withPolicyName("NormalScaleOut")
                .withAdjustmentType("ChangeInCapacity").withScalingAdjustment(2)
                .withCooldown(60);
        PutScalingPolicyResult response = asgClient.putScalingPolicy(request);

        return response.getPolicyARN();

    }

    public static String creatNormaleScalingInPolicy( String asgName){

        PutScalingPolicyRequest request = new PutScalingPolicyRequest()
                .withAutoScalingGroupName(asgName)
                .withPolicyType("SimpleScaling")
                .withPolicyName("NormalScaleIn")
                .withAdjustmentType("ChangeInCapacity").withScalingAdjustment(-2)
                .withCooldown(60);
        PutScalingPolicyResult response = asgClient.putScalingPolicy(request);

        return response.getPolicyARN();

    }

    public static String createMaxScalingOutPolicyLB(String asgName){


        PutScalingPolicyRequest request = new PutScalingPolicyRequest()
                .withAutoScalingGroupName(asgName)
                .withPolicyType("SimpleScaling")
                .withPolicyName("MaxScaleOut")
                .withAdjustmentType("ChangeInCapacity").withScalingAdjustment(3)
                .withCooldown(60);
        PutScalingPolicyResult response = asgClient.putScalingPolicy(request);

        return response.getPolicyARN();

    }

    public static String createMaxScalingInPolicy( String asgName){


        PutScalingPolicyRequest request = new PutScalingPolicyRequest()
                .withAutoScalingGroupName(asgName)
                .withPolicyType("SimpleScaling")
                .withPolicyName("MaxScaleIn")
                .withAdjustmentType("ChangeInCapacity").withScalingAdjustment(-3)
                .withCooldown(60);
        PutScalingPolicyResult response = asgClient.putScalingPolicy(request);

        return response.getPolicyARN();

    }
}
