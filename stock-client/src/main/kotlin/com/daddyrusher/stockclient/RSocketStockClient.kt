package com.daddyrusher.stockclient

import org.slf4j.LoggerFactory.getLogger
import org.springframework.messaging.rsocket.RSocketRequester
import reactor.core.publisher.Flux
import java.io.IOException
import java.time.Duration

class RSocketStockClient(private val rSocketRequester: RSocketRequester) : StockClient {
    companion object {
        private val logger = getLogger(this::class.java)
    }

    override fun pricesFor(symbol: String): Flux<StockPrice> {
        return rSocketRequester.route("stockPrices")
                .data(symbol)
                .retrieveFlux(StockPrice::class.java)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(20))
                .doOnError(IOException::class.java) { logger.error(it.message)}
    }

}
