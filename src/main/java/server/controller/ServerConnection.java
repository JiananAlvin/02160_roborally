package utils.server.controller;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ServerConnection {
    private String method;
    private String path;
    private final String url = "https://dry-brushlands-54922.herokuapp.com";

    private JSONObject response = new JSONObject();

    // connect to the API server
    public void connect(){
        try {
            URL url = new URL(this.url + this.path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(this.method);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                if (content.length() > 0) {
                    this.response = new JSONObject(content.toString());
                }
                this.response.put("status",200);
            } else {
                this.response.put("status",connection.getResponseCode());
            }
        } catch (Exception e) {
//            this.response.put("status",500);
            e.printStackTrace();
        }

    }

    // get the response from the API server
    public JSONObject getResponse(){
        return this.response;
    }

    protected void setPath(String path) {
        this.path = path;
    }

    protected void setMethod(String method) {
        this.method = method;
    }
}
