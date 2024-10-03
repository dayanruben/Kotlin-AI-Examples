package io.github.devcrocod.example.playground.services

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.LocalDate


@Service
class CustomerSupportAssistant(
    modelBuilder: ChatClient.Builder,
    vectorStore: VectorStore,
    chatMemory: ChatMemory
) {
    private val chatClient: ChatClient = modelBuilder
        .defaultSystem(
            """
                You are a customer chat support agent of an airline named "Funnair".
                Respond in a friendly, helpful, and joyful manner.
                You are interacting with customers through an online chat system.
                Before providing information about a booking or cancelling a booking, you MUST always
                get the following information from the user: booking number, customer first name, and last name.
                Check the message history for this information before asking the user.
                Before changing a booking you MUST ensure it is permitted by the terms.
                If there is a charge for the change, you MUST ask the user to consent before proceeding.
                Use the provided functions to fetch booking details, change bookings, and cancel bookings.
                Use parallel function calling if required.
                Today is {current_date}.
                """.trimIndent()
        )
        .defaultAdvisors(
            PromptChatMemoryAdvisor(chatMemory), // Chat Memory
            QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()), // RAG
            LoggingAdvisor()
        )
        .defaultFunctions("getBookingDetails", "changeBooking", "cancelBooking") // FUNCTION CALLING
        .build()

    fun chat(chatId: String, userMessageContent: String): Flux<String> {
        return chatClient.prompt()
            .system { it.param("current_date", LocalDate.now().toString()) }
            .user(userMessageContent)
            .advisors {
                it.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                    .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
            }
            .stream()
            .content()
//            .asFlow() // Check flow
    }
}