package com.healthchang.demo.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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

    public static void downloadImage(String url, String fileName){
        try {
            URL imageUrl = new URL(url);
            InputStream in = new BufferedInputStream(imageUrl.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while(-1 != (n = in.read(buf))){
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();

            String path = System.getProperty("user.dir") + "/src/main/resources/static/images/user/";

            File fileData = new File(path, fileName);
            if(!fileData.exists()){
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(response);
                fos.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject inputListOutputJson(List list){
        JSONObject obj = new JSONObject();
        obj.put("list", list);

        JSONArray jsonArray = obj.getJSONArray("list");
        JSONArray returnJsonArray = new JSONArray();

        for(int i = 0; i<jsonArray.length(); i++){
            returnJsonArray.put(i, jsonArray.getJSONObject(i).get("target"));
        }

        JSONObject returnObj = new JSONObject();
        returnObj.put("list", returnJsonArray);
        return returnObj;
    }

}
