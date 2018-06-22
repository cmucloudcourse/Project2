package edu.cmu.cs.cloud.samples.aws.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class HttpCaller {

    public Map<String,String> doGet(String url, Map<String, String> requestParams) throws IOException {
        Map<String,String> responseMap = new HashMap<>();
        String queryString = createQueryString(requestParams);
        URL urlobj = new URL(url+queryString);
        System.out.println(urlobj.toString());
        HttpURLConnection con = (HttpURLConnection) urlobj.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setDoOutput(false);


        //Reading response
        InputStream response;
        if (con.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
            response = con.getInputStream();
        } else {
            /* error from server */
            response = con.getErrorStream();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(response));

        String buffer;
        StringBuilder sb = new StringBuilder();
        while ((buffer = reader.readLine()) != null) {
            sb.append(buffer);
            System.out.println(buffer);
        }
        String content = sb.toString();
        reader.close();

        responseMap.put("httpCode", String.valueOf(con.getResponseCode()));
        responseMap.put("content",content);

        return responseMap;
    }

    public String createQueryString(Map<String,String> queryParams) throws UnsupportedEncodingException {
        StringJoiner query = new StringJoiner("&");
        for (java.lang.String key : queryParams.keySet()) {
            query.add(URLEncoder.encode(key,"UTF-8")+"="+URLEncoder.encode(queryParams.get(key),"UTF-8"));

        }
        return query.toString();
    }
}
