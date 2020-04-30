package com.daddyrusher.stockclient

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester
import reactor.test.StepVerifier

@SpringBootTest
class RSocketClientStockClientIntegrationTest {

    @Autowired
    lateinit var builder: RSocketRequester.Builder

    @Test
    fun shouldRetrieveStockPricesFromTheService() {
        val client = RSocketStockClient(createRSocketRequester())
        val symbol = "SYMBOL"

        val prices = client.pricesFor(symbol)

        StepVerifier.create(prices.take(5))
                .expectNextMatches { it.symbol == symbol }
                .expectNextMatches { it.symbol == symbol }
                .expectNextMatches { it.symbol == symbol }
                .expectNextMatches { it.symbol == symbol }
                .expectNextMatches { it.symbol == symbol }
                .verifyComplete()
    }

    private fun createRSocketRequester(): RSocketRequester {
        return builder.connectTcp("localhost", 7000).block()!!
    }
}