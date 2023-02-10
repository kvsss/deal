package com.deng.core.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@ConditionalOnProperty(prefix = "spring.elasticsearch", name = "enabled", havingValue = "true")
@Configuration
public class EsConfig {

/*    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }*/

    @Bean
    JacksonJsonpMapper jacksonJsonpMapper() {
        return new JacksonJsonpMapper();
    }

/*    @Configuration
    public class ElasticSearchConfig {
        @Bean
        public ElasticsearchClient elasticsearchClient() {
            RestClient client = RestClient.builder(new HttpHost("localhost", 9200, "http")).build();
            ElasticsearchTransport transport = new RestClientTransport(client, new JacksonJsonpMapper());
            return new ElasticsearchClient(transport);
        }
    }*/
}
