package server.controller;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class ServerConnection {
    private String method;
    private String path;
    private static final String SERVER_URL = "https://dry-brushlands-54922.herokuapp.com";
    private JSONObject response = new JSONObject();
    public static final String RESPONSE_STATUS = "status";

    // connect to the API server
    public void connect() {
        try {
            URL url = new URL(this.SERVER_URL + this.path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(this.method);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                if (content.length() > 0) {
                    this.response = new JSONObject(content.toString());
                }
                this.response.put("status", 200);
            } else {
                this.response.put("status", connection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // get the response from the API server
    public JSONObject getResponse() {
        return this.response;
    }

    protected void setPath(String path) {
        this.path = path;
    }

    protected void setMethod(String method) {
        this.method = method;
    }
}
