package edu.cmu.cs.cloud.samples.aws;

import edu.cmu.cs.cloud.samples.aws.jobs.LogMonitoringJob;
import edu.cmu.cs.cloud.samples.aws.launcher.LoadGeneratorLauncher;
import edu.cmu.cs.cloud.samples.aws.launcher.WebServiceLauncher;
import edu.cmu.cs.cloud.samples.aws.log.LogMonitor;
import edu.cmu.cs.cloud.samples.aws.pojos.LoadGenerator;
import edu.cmu.cs.cloud.samples.aws.pojos.WebService;
import org.quartz.SchedulerException;

import java.io.IOException;
import java.util.Stack;

public class Project2Runner {

    public static Stack<String> webserviceStack = new Stack();
    public static double RPS = 0;
    private static final int DELAY = 100;
    public static void main(String[] args) throws IOException, SchedulerException, InterruptedException {
        setup();
        String loadGeneratorDNS = LoadGenerator.getInstance().getPublicDNS();
        String firstWebserviceDNS = webserviceStack.peek();
        boolean authenticated = LoadGeneratorLauncher.authenticate(loadGeneratorDNS);
        if (authenticated) {
            startLoadTest(firstWebserviceDNS, loadGeneratorDNS);
        }
    }


    public static void setup() {


        System.out.println("launching load generator");
        String loadGeneratorInstanceID = LoadGeneratorLauncher.launchLoadGenerator();
        System.out.println("load generator launch sucessful");
        System.out.println("Subnet of Load Generator is " + LoadGenerator.getInstance().getSubnet());
        System.out.println("Public DNS of Load Generator is " + LoadGenerator.getInstance().getPublicDNS());

        System.out.println("lauching first webservice");
        String webserviceInstanceID = WebServiceLauncher.launchWebService();
        System.out.println("first webservice launched successfully");
        System.out.println("Public DNS of Webservice is " + WebServiceLauncher.webServiceMap.get(webserviceInstanceID).getPublicDNS());
        webserviceStack.push(WebServiceLauncher.webServiceMap.get(webserviceInstanceID).getPublicDNS());
        System.out.println("Subnet of Webservice is " + WebServiceLauncher.webServiceMap.get(webserviceInstanceID).getSubnet());

    }


    public static void startLoadTest(String firstWebserviceDNS, String loadGenerator) throws IOException, SchedulerException, InterruptedException {
        System.out.println("load test starting. ");
        String testID = LoadGeneratorLauncher.submitFirstService(firstWebserviceDNS, loadGenerator);
        if (!testID.equals("SUBMISSIONFAILED")) {
            monitorLogs(testID, loadGenerator);
        }
        Thread.sleep(DELAY * 1000);//Sleep for 100 secs to control the 100 sec delay for the  first instance.
        while (RPS < 60 && LogMonitor.MINUTES < 30) {
            System.out.println("Current Thread" + Thread.currentThread().getName() + "Cuurent RPS " + RPS);
            System.out.println("Going to launch one more instance and submit it ");
            String webserviceInstanceID = WebServiceLauncher.launchWebService();
            webserviceStack.push(WebServiceLauncher.webServiceMap.get(webserviceInstanceID).getPublicDNS());
            if(WebServiceLauncher.checkWebService(webserviceStack.peek())) {
                LoadGeneratorLauncher.submitHorizontalWebService(webserviceStack.peek(), loadGenerator);
            }
            Thread.sleep(DELAY * 1000);
        }
    }

    private static void monitorLogs(String testID, String loadGenerator) throws IOException, SchedulerException {
        System.out.println("Log Monitoring started.");
        LogMonitoringJob.startMonitoring(loadGenerator, testID);

    }








}
