package dev.silas.sqssample

import io.awspring.cloud.sqs.annotation.SqsListener
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement
import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationRegistry
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class SampleListener(
    private val registry: ObservationRegistry
) {

    val logger = KotlinLogging.logger { }

    @SqsListener("\${app.queue}")
    fun handleMessage(content: Content, ack: Acknowledgement) {
        Observation.start("handling message", registry)
            .observe {
                logger.info { "got message: $content" }
                Observation.start("acknowledge message", registry)
                    .observe {
                        ack.acknowledgeAsync().get(5, TimeUnit.SECONDS)
                    }
            }
    }
}
