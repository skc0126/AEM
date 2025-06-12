package com.testproject.core.utils;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;

public class HttpClientUtil {

    public static CloseableHttpClient createHttpClient(String username, String password) {
        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        return HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
    }

    public static HttpPost createHttpPost(String url) {
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        return post;
    }
}