package edu.cmu.cs.cloud.samples.aws.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TestIDParser {

    public static String getTestID(String html){
        Document doc = Jsoup.parse(html);
        Element link = doc.select("a").first();
        String linkHref = link.attr("href");
        System.out.println(linkHref);
        return linkHref;
    }
}
