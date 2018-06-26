package edu.cmu.cs.cloud.samples.aws.launcher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlarmLauncherTest {

    @Test
    void getLBName() {
        AlarmLauncher.getLBName("arn:aws:elasticloadbalancing:us-east-1:111851906400:loadbalancer/app/project2-elb/542e7c91c3a85835");
    }
}