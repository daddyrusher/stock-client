package com.daddyrusher.stockui

import com.daddyrusher.stockui.ChartApplication.StageReadyEvent
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class StageInitializer(@Value("\${spring.application.ui.title}") val applicationTitle: String,
                       private val applicationContext: ApplicationContext) : ApplicationListener<StageReadyEvent> {
    @Value("\${classpath:/chart.fxml}")
    val chartResource: Resource? = null

    override fun onApplicationEvent(event: StageReadyEvent) {
        val fxmlLoader = FXMLLoader(chartResource?.url)
        fxmlLoader.setControllerFactory { applicationContext.getBean(it) }
        val parent = fxmlLoader.load<Parent>()
        val stage = event.getStage()
        stage.scene = Scene(parent, 800.0, 600.0)
        stage.title = applicationTitle
        stage.show()
    }
}
