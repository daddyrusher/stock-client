package com.daddyrusher.stockclient

import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.io.IOException
import java.time.Duration

class WebClientStockClient(private val webClient: WebClient): StockClient {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun pricesFor(symbol: String): Flux<StockPrice> {
        return webClient.get()
                        .uri("http://localhost:9090/stocks/$symbol")
                        .retrieve()
                        .bodyToFlux(StockPrice::class.java)
                        .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(20))
                        .doOnError(IOException::class.java) { logger.error(it.message)}
    }
}