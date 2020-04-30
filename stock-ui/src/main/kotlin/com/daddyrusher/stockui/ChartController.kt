package com.daddyrusher.stockui

import com.daddyrusher.stockclient.StockClient
import com.daddyrusher.stockclient.StockPrice
import javafx.application.Platform
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.chart.LineChart
import org.springframework.stereotype.Component
import com.daddyrusher.stockclient.WebClientStockClient
import javafx.collections.FXCollections.observableArrayList
import javafx.scene.chart.XYChart.Data
import javafx.scene.chart.XYChart.Series
import java.util.function.Consumer

@Component
class ChartController(private val stockClient: StockClient) {

    @FXML
    lateinit var chart: LineChart<String, Double>

    @FXML
    fun initialize() {
        val symbol = "SYMBOL"
        val priceSubscriber = PriceSubscriber(symbol)
        val data: ObservableList<Series<String, Double>> = observableArrayList()
        data.add(priceSubscriber.series)
        chart.data = data

        stockClient.pricesFor(symbol).subscribe(priceSubscriber)
    }

    private class PriceSubscriber(symbol: String): Consumer<StockPrice> {
        private val seriesData: ObservableList<Data<String, Double>> = observableArrayList()
        var series = Series(symbol, seriesData)

        override fun accept(stockPrice: StockPrice) {
            Platform.runLater { seriesData.add(Data(stockPrice.time.second.toString(), stockPrice.price)) }
        }

    }
}

