package io.github.tttol.mrls.config;

import io.micrometer.common.util.StringUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class RestClientConfig {

  @Value("${app.proxy.host}")
  private String proxyHost;

  @Value("${app.proxy.port}")
  private int proxyPort;

  @Bean
  public RestClient restClient() {
    if (StringUtils.isBlank(proxyHost)) {
        return RestClient.create();
    }

    var httpClient = HttpClients.custom()
            .setProxy(new HttpHost(proxyHost, proxyPort))
            .build();
    var httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

    return RestClient.builder()
            .requestFactory(httpComponentsClientHttpRequestFactory)
            .build();
  }
}
