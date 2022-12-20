package org.example;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.print.attribute.standard.JobKOctets;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        CloseableHttpResponse response = httpClient.execute(request);
//        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
        String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
        httpClient.close();
//        System.out.println(body);
        upvotes(body);
    }

    static void upvotes(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Post> posts = mapper.readValue(body, new TypeReference<List<Post>>() {
        });
        List<Post> posts1 = posts.stream().filter(post -> post.getUpvotes() != null).collect(Collectors.toList());
        posts1.forEach(System.out::println);

    }

}