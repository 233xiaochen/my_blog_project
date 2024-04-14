package com.example.blog.config;

import org.apache.http.HttpHost;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestClientConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("124.71.99.191", Integer.parseInt("9200"), "http")
                ).setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(90000000)//25hours
                        .setSocketTimeout(90000000)).setHttpClientConfigCallback((httpAsyncClientBuilder -> {
                    httpAsyncClientBuilder.disableAuthCaching();//禁用身份验证缓存
                    //显式设置keepAliveStrategy
                    httpAsyncClientBuilder.setKeepAliveStrategy((httpResponse, httpContext) -> TimeUnit.MINUTES.toMillis(3));
                    //显式开启tcp keepalive
                    httpAsyncClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setSoKeepAlive(true).build());
                    return httpAsyncClientBuilder;
                }))
        );
        return client;
    }
}
