package server.controller;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ServerConnection {
    private String method;
    private String path;
    private static final String SERVER_URL = "https://dry-brushlands-54922.herokuapp.com";
    private JSONObject response = new JSONObject();
    public static final String RESPONSE_STATUS = "status";

    // connect to the API server
    public void connect() {
        try {
            URL url = new URL(SERVER_URL + this.path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(this.method);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                if (content.length() > 0) {
                    this.response = new JSONObject(content.toString());
                }
                else this.response = new JSONObject();
                this.response.put("status", 200);
            } else {
                this.response.put("status", connection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postRequestWithBody(JSONObject body) {
        try {
            URL url = new URL(SERVER_URL + this.path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            System.out.println(body.toString());
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(body.toString());
            wr.flush();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                if (content.length() > 0) {
                    this.response = new JSONObject(content.toString());
                }
                this.response.put("status", 200);
            } else {
                this.response.put("status", con.getResponseCode());
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
