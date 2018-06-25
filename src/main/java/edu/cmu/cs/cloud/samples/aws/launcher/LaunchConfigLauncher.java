package edu.cmu.cs.cloud.samples.aws.launcher;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClient;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClientBuilder;
import com.amazonaws.services.autoscaling.model.*;
import com.amazonaws.services.autoscaling.model.InstanceMonitoring;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.waiters.Waiter;
import com.amazonaws.waiters.WaiterParameters;
import edu.cmu.cs.cloud.samples.aws.pojos.WebService;

import java.util.*;

public class LaunchConfigLauncher {

    private static int count=1;
    private static final String AMI_ID         = "ami-886226f7";
    private static final String INSTANCE_TYPE  = "t2.micro";
    private static final String KEY_NAME       = "15319-demo";
    private static final List<Tag> tags = new ArrayList<>();
    public static Instance wsintance;
    public static Map<String, WebService> webServiceMap = new HashMap<>();

    private static AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    private static AmazonAutoScaling ec2 = AmazonAutoScalingClientBuilder
            .standard()
            .withCredentials(credentialsProvider)
            .withRegion(Regions.US_EAST_1)
            .build();


    public static boolean createLaunchConfig(String configName, String securityGroupId){

        //Enable Detailed Monitoring
        InstanceMonitoring detailMonitoring = new InstanceMonitoring();
        detailMonitoring.setEnabled(true);



        //Launch Configuration request
        System.out.println("Creating LaunchConfig request");
        CreateLaunchConfigurationRequest request = new CreateLaunchConfigurationRequest()
                .withLaunchConfigurationName(configName)
                .withImageId(AMI_ID)
                .withInstanceType(INSTANCE_TYPE)
                .withKeyName(KEY_NAME)
                .withSecurityGroups(securityGroupId)
                .withInstanceMonitoring(detailMonitoring);

        //Create launch configuration response.
        CreateLaunchConfigurationResult result = ec2.createLaunchConfiguration(request);

        //return true if there were no exceptions.
        System.out.println("Launch Config with "+configName+" created.");
        return true;
    }

    public static boolean deleteLaunchConfig(String configName){

        //Launch Configuration request
        System.out.println("Deleting LaunchConfig request");
        DeleteLaunchConfigurationRequest request = new DeleteLaunchConfigurationRequest()
                .withLaunchConfigurationName(configName);

        //Create launch configuration response.
        DeleteLaunchConfigurationResult result = ec2.deleteLaunchConfiguration(request);

        //return true if there were no exceptions.
        System.out.println("Launch Config with "+configName+" deleted.");
        return true;
    }

}
