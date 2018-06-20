package edu.cmu.cs.cloud.samples.aws.launcher;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.waiters.Waiter;
import com.amazonaws.waiters.WaiterParameters;
import com.amazonaws.waiters.WaiterTimedOutException;
import com.amazonaws.waiters.WaiterUnrecoverableException;
import edu.cmu.cs.cloud.samples.aws.exeptions.DryRunException;

import java.util.List;

public class LaunchEC2Instance {


    public static Instance launchInstance(String amiId, String instanceType, String keyName, String securityGroup, List<Tag> tags){

        AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

        // Create an Amazon EC2 Client
        AmazonEC2 ec2 = AmazonEC2ClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_EAST_1)
                .build();

        // Create a Run Instance Request
        RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                .withImageId(amiId)
                .withInstanceType(instanceType)
                .withMinCount(1)
                .withMaxCount(1)
                .withKeyName(keyName)
//                .withSecurityGroups(securityGroup)
                .withSecurityGroupIds(securityGroup)
                .withSubnetId("subnet-afc51381");


        // Execute the Run Instance Request
        RunInstancesResult runInstancesResult = ec2.runInstances(runInstancesRequest);

        // Return the Object Reference of the Instance just Launched
        Instance instance = runInstancesResult.getReservation()
                .getInstances()
                .get(0);

        waitUntilRunning(instance.getInstanceId());

        CreateTagsRequest createTagsRequest = new CreateTagsRequest();
        createTagsRequest.withResources(instance.getInstanceId()).withTags(tags);
        ec2.createTags(createTagsRequest);

        return instance;
    }

    public static void describeInstance(Instance instance){
            AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
            if(getDryRunStatus(instance.getInstanceId())){
                DescribeInstancesRequest request = new DescribeInstancesRequest().withInstanceIds(instance.getInstanceId());
                DescribeInstancesResult response = ec2.describeInstances(request);
                Instance instance1 = response.getReservations().get(0).getInstances().get(0);
                System.out.println("Describe instance result is "+
                                    instance1.getState()+"\t"+
                                    instance1.getPublicDnsName()+"\t"+
                                    instance1.getSubnetId()+"\t"+
                                    instance1.getLaunchTime()+"\t"+
                                    instance1.getInstanceLifecycle()
                        );
            }

    }

    public static Instance populateInstancePOJO(String instanceId) throws DryRunException {
        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        if(getDryRunStatus(instanceId)){
            DescribeInstancesRequest request = new DescribeInstancesRequest().withInstanceIds(instanceId);
            DescribeInstancesResult response = ec2.describeInstances(request);
            Instance instance = response.getReservations().get(0).getInstances().get(0);
            System.out.println("Returning Instance with instance id "+instance.getInstanceId()+
                    " with tag" + instance.getTags().get(1).getValue());
            return instance;
        }else{
            throw new DryRunException("Dry Run Failed. Will not proceed with sending the request.");
        }


    }

    private static boolean getDryRunStatus(String instanceId) {
        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        DryRunSupportedRequest<DescribeInstancesRequest> dryRequest = ()->{
            DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest().withInstanceIds(instanceId);
            return describeInstancesRequest.getDryRunRequest();
        };

        DryRunResult dryResponse = ec2.dryRun(dryRequest);

        if(dryResponse.isSuccessful()){
            System.out.println("Describe Request Dry Run successful. Proceeding with actual request");
            return true;
        }else{
            System.out.println("Describe Request Dry run request failed. Will not process the request further");
            return false;
        }
    }

    private static void waitUntilRunning(String instanceId){
        AmazonEC2 ec = AmazonEC2ClientBuilder.defaultClient();
        Waiter waiter =  ec.waiters().instanceRunning();
        System.out.println("Waiting for instance to start running");
        waiter.run(new WaiterParameters<>(
                    new DescribeInstancesRequest().withInstanceIds(instanceId)));
        System.out.println("Instance is running");
    }

    private static void waitUntilStop(String instanceId){
        AmazonEC2 ec = AmazonEC2ClientBuilder.defaultClient();
        Waiter waiter =  ec.waiters().instanceStopped();
        System.out.println("Waiting for instance to stop");
        waiter.run(new WaiterParameters<>(
                new DescribeInstancesRequest().withInstanceIds(instanceId)));
        System.out.println("Instance is stopped");
    }


    public static void shutDown(String instanceId){
        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        DryRunSupportedRequest<StopInstancesRequest> dryRequest = ()->{
            StopInstancesRequest request = new StopInstancesRequest().withInstanceIds(instanceId);
            return request.getDryRunRequest();
        };
        DryRunResult dryResponse = ec2.dryRun(dryRequest);

        if(dryResponse.isSuccessful()){
            System.out.println("Describe Request Dry Run successful. Proceeeding with actual request");
        }else{
            System.out.println("Describe Request Dry run request failed. Will not process the request further");
        }

        StopInstancesRequest request = new StopInstancesRequest().withInstanceIds(instanceId);
        StopInstancesResult response = ec2.stopInstances(request);
        waitUntilStop(instanceId);

    }

}
