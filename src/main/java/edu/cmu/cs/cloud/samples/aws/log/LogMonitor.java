package edu.cmu.cs.cloud.samples.aws.log;

import com.amazonaws.services.dynamodbv2.xspec.M;
import edu.cmu.cs.cloud.samples.aws.Project2Runner;
import edu.cmu.cs.cloud.samples.aws.http.HttpCaller;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.Wini;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.Preferences;

public class LogMonitor implements Job{

    public static int MINUTES = 1;

    public static void monitorLog(String lgDNSName, String testID) throws IOException {
        System.out.println("Current Thread" + Thread.currentThread().getName());
        HttpCaller httpCaller = new HttpCaller();
        String urlString = "http://" + lgDNSName + testID;
        URL url = new URL(urlString);
//        Map<String, String> responseMap =responseMap httpCaller.doGet(url,new HashMap<>());
        Wini ini = new Wini(url);
        if(null != ini.get("Minute "+MINUTES)) {
            double totalRPS = ini.get("Minute " + MINUTES++).values().stream().mapToDouble(i -> Double.parseDouble(i)).sum();
            System.out.println("Minute : " + (MINUTES - 1) + " , Total RPS = " + totalRPS);
            Project2Runner.RPS = totalRPS;
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            monitorLog(jobExecutionContext.getJobDetail().getJobDataMap().get("lgDNSName").toString(),jobExecutionContext.getJobDetail().getJobDataMap().get("testID").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
