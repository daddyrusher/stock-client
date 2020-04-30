package com.daddyrusher.stockui

import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationEvent
import org.springframework.context.ConfigurableApplicationContext

class ChartApplication: Application() {
    lateinit var applicationContext: ConfigurableApplicationContext

    override fun init() {
        applicationContext = SpringApplicationBuilder(StockUiApplication::class.java).run()
    }

    override fun start(stage: Stage) {
        applicationContext.publishEvent(StageReadyEvent(stage))
    }

    override fun stop() {
        applicationContext.close()
        Platform.exit()
    }

    class StageReadyEvent(stage: Stage): ApplicationEvent(stage) {
        fun getStage(): Stage {
            return getSource() as Stage
        }
    }
}