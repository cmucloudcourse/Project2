package edu.cmu.cs.cloud.samples.aws.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.StringJoiner;

public class HttpCaller {

    public void doGet(String url, Map<String, String> requestParams) throws IOException {
        String queryString = createQueryString(requestParams);
        URL urlobj = new URL(url+"?"+queryString);
        HttpURLConnection con = (HttpURLConnection) urlobj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println(responseCode);

    }

    public String createQueryString(Map<String,String> queryParams) throws UnsupportedEncodingException {
        StringJoiner query = new StringJoiner("&");
        for (java.lang.String key : queryParams.keySet()) {
            query.add(URLEncoder.encode(key,"UTF-8")+"="+URLEncoder.encode(queryParams.get(key),"UTF-8"));

        }
        System.out.println("Query Params are : "+query);
        return query.toString();
    }
}
