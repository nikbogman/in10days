package com.eventhub

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcWebConfig: WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        // addPattern
        registry.addInterceptor(BearerTokenInterceptor())
    }
}