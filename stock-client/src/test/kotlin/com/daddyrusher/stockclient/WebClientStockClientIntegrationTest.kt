package com.daddyrusher.stockclient

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient

class WebClientStockClientIntegrationTest {
    var webClient: WebClient = WebClient.create()

    @Test
    fun shouldRetrieveStockPricesFromTheService() {
        val client = WebClientStockClient(webClient)
        val prices = client.pricesFor("SYMBOL")

        Assertions.assertNotNull(prices)
        val fivePrices = prices.take(5)
        Assertions.assertEquals(5,fivePrices.count().block())
        Assertions.assertEquals("SYMBOL", fivePrices.blockFirst()?.symbol)
    }
}