package edu.cmu.cs.cloud.samples.aws.launcher;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.autoscaling.model.Alarm;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.*;

import java.util.List;


public class AlarmLauncher {
    private static final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();
    private static final  AmazonCloudWatch awsCloud = AmazonCloudWatchClientBuilder
            .standard()
            .withRegion(Regions.US_EAST_1)
            .withCredentials(credentialsProvider)
            .build();


    public static String createCPUAlarm(String alarmName, String asgName, double threshold, int period, ComparisonOperator comparisonOperator, String policyARN){

        Dimension dimension = new Dimension()
                .withName("AutoScalingGroupName")
                .withValue(asgName);

        PutMetricAlarmRequest request = new PutMetricAlarmRequest()
                .withAlarmName(alarmName)
                .withAlarmActions(policyARN)
                .withComparisonOperator(
                        comparisonOperator)
                .withEvaluationPeriods(1)
                .withMetricName("CPUUtilization")
                .withNamespace("AWS/EC2")
                .withPeriod(period)
                .withStatistic(Statistic.Average)
                .withThreshold(threshold)
                .withAlarmDescription(
                        "Alarm when server CPU utilization exceeds 70%")
                .withUnit(StandardUnit.Seconds)
                .withDimensions(dimension);

        PutMetricAlarmResult response = awsCloud.putMetricAlarm(request);
        return alarmName;
    }


    public static String createLBAlarm(String alarmName, String elbARN, double threshold, int period, ComparisonOperator comparisonOperator, String policyARN, String desc){

        Dimension dimension = new Dimension()
                    .withName("LoadBalancer")
                .withValue(getLBName(elbARN));

        PutMetricAlarmRequest request = new PutMetricAlarmRequest()
                .withAlarmName(alarmName)
                .withAlarmActions(policyARN)
                .withComparisonOperator(
                        comparisonOperator)
                .withEvaluationPeriods(1)
                .withMetricName("RequestCount")
                .withNamespace("AWS/ApplicationELB")
                .withPeriod(period)
                .withStatistic(Statistic.Sum)
                .withThreshold(threshold)
                .withAlarmDescription(
                        desc)
                .withUnit(StandardUnit.Seconds)
                .withDimensions(dimension);

        PutMetricAlarmResult response = awsCloud.putMetricAlarm(request);
        return alarmName;
    }


    public static void deleteAlarm(String alarmName){
        DeleteAlarmsRequest request = new DeleteAlarmsRequest()
                .withAlarmNames(alarmName);

        DeleteAlarmsResult response = awsCloud.deleteAlarms(request);
    }

    public static void describeAlarms(String ... alarmNames){
//        DescribeAlarmsRequest  request = new DescribeAlarmsRequest()
//                .withAlarmNames(alarmNames);
//        DescribeAlarmsResult result = awsCloud.describeAlarms();
//
//        return result.getMetricAlarms().get(0);
    }


    public static String getLBName(String arn){
        String [] tokens = arn.split("/");
        System.out.println(tokens[tokens.length-3]+"/"+tokens[tokens.length-2]+"/"+tokens[tokens.length-1]);
        return tokens[tokens.length-3]+"/"+tokens[tokens.length-2]+"/"+tokens[tokens.length-1];
    }
}
