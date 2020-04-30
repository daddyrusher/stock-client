package com.daddyrusher.stockclient

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ClientConfig {
    @Bean
    @Profile("sse")
    fun webClientStockClient() = WebClientStockClient(webClient())

    @Bean
    @Profile("rsocket")
    fun rSocketStockClient() = RSocketStockClient(rSocketRequester())

    @Bean
    fun rSocketRequester() = RSocketRequester.builder().connectTcp("localhost", 7000).block()!!

    @Bean
    @ConditionalOnMissingBean
    fun webClient() = WebClient.builder().build()
}