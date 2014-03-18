package com.anuragkapur.heroku.keepalive;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * @author: anuragkapur
 * @since: 18/03/2014
 */

public class HttpRequestJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestJob.class);
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("app");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        String urlsToPost = resourceBundle.getString("urls.post");
        StringTokenizer tokenizer = new StringTokenizer(urlsToPost, ",");
        while (tokenizer.hasMoreTokens()) {
            String urlToPost = tokenizer.nextToken();
            LOGGER.info("Will execute HTTP POST on url :: {}", urlToPost);
            executePost(urlToPost);
        }

        String urlsToGet = resourceBundle.getString("urls.get");
        tokenizer = new StringTokenizer(urlsToGet, ",");
        while (tokenizer.hasMoreTokens()) {
            String urlToGet = tokenizer.nextToken();
            LOGGER.info("Will execute HTTP GET on url :: {}", urlToGet);
            executeGet(urlToGet);
        }
    }

    private void executeGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            LOGGER.info("Response Status Line :: {} for url {}", response.getStatusLine(), url);
        } catch (IOException e) {
            LOGGER.error("Exception occurred {}", e);
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                LOGGER.error("Exception occurred {}", e);
            }
        }
    }

    private void executePost(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            LOGGER.info("Response Status Line :: {} for url {}", response.getStatusLine(), url);
        } catch (IOException e) {
            LOGGER.error("Exception occurred {}", e);
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                LOGGER.error("Exception occurred {}", e);
            }
        }
    }
}
