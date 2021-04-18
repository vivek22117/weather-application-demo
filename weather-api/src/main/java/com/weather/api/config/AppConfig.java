package com.weather.api.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean("weather-client")
    public RestTemplate weatherRestTemplate() {
        PoolingHttpClientConnectionManager connectionManager = poolingHttpClientCM(20, 20);
        RequestConfig requestConfig = requestConfig(1000, 2000, 1400);
        CloseableHttpClient httpClient = httpClient(connectionManager, requestConfig);

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }

    private PoolingHttpClientConnectionManager poolingHttpClientCM(int maxTotal, int maxPerRoute) {
        PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
        result.setMaxTotal(maxTotal);
        result.setDefaultMaxPerRoute(maxPerRoute);
        return result;
    }

    private RequestConfig requestConfig(int connectionReqTimeout, int connectionTimeout, int socketTimeout) {
        RequestConfig result = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionReqTimeout) //how long wait for a connection from connection manager
                .setConnectTimeout(connectionTimeout) //how long wait for establishing connection
                .setSocketTimeout(socketTimeout) //how long wait between packets
                .build();
        return result;
    }
    private CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager
            , RequestConfig requestConfig) {
        CloseableHttpClient result = HttpClientBuilder
                .create()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
        return result;
    }
}
