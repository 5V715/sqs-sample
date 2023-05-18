package dev.silas.sqssample

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode
import io.awspring.cloud.sqs.listener.errorhandler.AsyncErrorHandler
import io.awspring.cloud.sqs.listener.errorhandler.ErrorHandler
import io.awspring.cloud.sqs.listener.interceptor.AsyncMessageInterceptor
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.sqs.SqsAsyncClient

@Configuration
class Config {

    @Bean
    fun kotlinObjectMapper() = ObjectMapper()
        .registerKotlinModule()

    @Bean
    fun defaultSqsListenerContainerFactory(
        sqsAsyncClient: ObjectProvider<SqsAsyncClient>,
        asyncErrorHandler: ObjectProvider<AsyncErrorHandler<Any>>,
        errorHandler: ObjectProvider<ErrorHandler<Any>>,
        asyncInterceptors: ObjectProvider<AsyncMessageInterceptor<Any>>,
        interceptors: ObjectProvider<MessageInterceptor<Any>>
    ) =
        SqsMessageListenerContainerFactory
            .builder<Any>()
            .configure { options ->
                options.acknowledgementMode(AcknowledgementMode.MANUAL)
            }.apply {
                sqsAsyncClient.ifAvailable(this::sqsAsyncClient)
                asyncErrorHandler.ifAvailable(this::errorHandler)
                errorHandler.ifAvailable(this::errorHandler)
                asyncInterceptors.forEach(this::messageInterceptor)
                interceptors.forEach(this::messageInterceptor)
            }
            .build()
}
