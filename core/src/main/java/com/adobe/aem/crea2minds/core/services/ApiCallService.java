package com.adobe.aem.crea2minds.core.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;

import javax.jcr.Credentials;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.util.Base64;

public class ApiCallService {


    public String getJson(SlingHttpServletRequest request){

        String credentials ="admin : admin";

        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        String Url ="http://localhost:4502/bin/navigation.json?path=/content/crea2minds/us/en";
        Url +="&credentials="+ encodedCredentials;

        CloseableHttpClient conn = HttpClients.createDefault();
        HttpGet get = new HttpGet(Url);
        get.setHeader("Authorization","Basic "+encodedCredentials);
        HttpResponse response = null;
        String response1 =null;
        try {
            response = conn.execute(get);
            response1 = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response1;

    }
}
