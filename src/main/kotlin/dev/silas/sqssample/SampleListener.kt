package dev.silas.sqssample

import io.awspring.cloud.sqs.annotation.SqsListener
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class SampleListener {

    val logger = KotlinLogging.logger { }

    @SqsListener("\${app.queue}")
    fun handleMessage(content: Content, ack: Acknowledgement) {
        logger.info { "got message: $content" }
        ack.acknowledgeAsync().get(5, TimeUnit.SECONDS)
    }
}
