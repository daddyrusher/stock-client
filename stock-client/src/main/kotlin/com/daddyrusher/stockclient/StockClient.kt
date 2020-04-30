package com.daddyrusher.stockclient

import reactor.core.publisher.Flux

interface StockClient {
    fun pricesFor(symbol: String): Flux<StockPrice>
}