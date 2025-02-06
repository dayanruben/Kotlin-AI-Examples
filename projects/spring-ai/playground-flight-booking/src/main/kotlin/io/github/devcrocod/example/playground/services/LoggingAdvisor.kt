package io.github.devcrocod.example.playground.services

import org.springframework.ai.chat.client.RequestResponseAdvisor
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest

class LoggingAdvisor : RequestResponseAdvisor {
    override fun adviseRequest(request: AdvisedRequest, adviseContext: MutableMap<String, Any>): AdvisedRequest {
        println("Request: $request")
        return request
    }

    override fun getOrder(): Int {
        return 0
    }
}