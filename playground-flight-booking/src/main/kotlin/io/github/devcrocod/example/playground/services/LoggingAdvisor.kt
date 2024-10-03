package io.github.devcrocod.example.playground.services

import org.springframework.ai.chat.client.AdvisedRequest
import org.springframework.ai.chat.client.RequestResponseAdvisor

class LoggingAdvisor : RequestResponseAdvisor {

    override fun adviseRequest(request: AdvisedRequest, adviseContext: MutableMap<String, Any>): AdvisedRequest {
        println("Request: $request")
        return request
    }
}