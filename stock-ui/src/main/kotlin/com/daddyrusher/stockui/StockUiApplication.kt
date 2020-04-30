package com.daddyrusher.stockui

import javafx.application.Application
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockUiApplication

fun main(args: Array<String>) {
    Application.launch(ChartApplication::class.java, *args)
}