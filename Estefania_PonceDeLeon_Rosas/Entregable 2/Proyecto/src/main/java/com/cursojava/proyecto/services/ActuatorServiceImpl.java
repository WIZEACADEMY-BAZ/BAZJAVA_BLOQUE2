package com.cursojava.proyecto.services;

import org.bson.json.JsonObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class ActuatorServiceImpl implements ActuatorService {

    private String localhost_port="http://localhost:8090";
    @Override
    public JsonObject getInfo() {
        return new JsonObject(this.getContentFromUrl(localhost_port+"/actuator/info"));
    }

    @Override
    public JsonObject getMetrics() {
        return new JsonObject(this.getContentFromUrl(localhost_port+"/actuator/metrics"));
    }

    @Override
    public JsonObject getMappings() {
        return new JsonObject(this.getContentFromUrl(localhost_port+"/actuator/mappings"));
    }

    @Override
    public JsonObject getLoggers() {
        return new JsonObject(this.getContentFromUrl(localhost_port+"/actuator/loggers"));
    }

    public String getContentFromUrl(String url){
        StringBuilder content = new StringBuilder();
        try {
            URL ruta = new URL(url);
            URLConnection urlConnection = ruta.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (
                MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }
}
