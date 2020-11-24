package com.example.client;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class RestTemplateConfig {

    @Value("${server.ssl.key-store}")
    private Resource keystore;

    @Value("${server.ssl.trust-store}")
    private Resource trustStore;

    @Value("${server.ssl.key-store-password}")
    private String keystorePassword;

    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;

    @Bean
    public RestTemplate configureRestTemplate() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
                .loadKeyMaterial(keystore.getURL(), keystorePassword.toCharArray(), keystorePassword.toCharArray())
                .build();

        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create()
                .setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext))
                .useSystemProperties()
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(closeableHttpClient);
        return new RestTemplate(requestFactory);
    }

    @Bean
    public CommandLineRunner run(final RestTemplate restTemplate) {
        return s -> restTemplate.getForEntity("https://localhost:8081/", String.class);
    }
}
