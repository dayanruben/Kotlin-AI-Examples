package io.github.devcrocod.example.playground.client

import com.vaadin.flow.server.auth.AnonymousAllowed
import com.vaadin.hilla.BrowserCallable
import io.github.devcrocod.example.playground.services.CustomerSupportAssistant
import reactor.core.publisher.Flux

@BrowserCallable
@AnonymousAllowed
class AssistantService(private val agent: CustomerSupportAssistant) {

    fun chat(chatId: String, userMessage: String): Flux<String> =
        agent.chat(chatId, userMessage)
}