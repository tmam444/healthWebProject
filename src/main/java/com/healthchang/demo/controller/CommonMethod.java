package com.healthchang.demo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommonMethod {

    public static StringBuffer apiURLimport(String urlstr, String requestMethod){
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL url = new URL(urlstr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(requestMethod);
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;
            while((returnLine = br.readLine()) != null){
                stringBuffer.append(returnLine + "\n");
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

}
