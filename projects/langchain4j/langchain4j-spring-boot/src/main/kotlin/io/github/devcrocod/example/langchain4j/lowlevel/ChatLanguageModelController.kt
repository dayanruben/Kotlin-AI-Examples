package io.github.devcrocod.example.langchain4j.lowlevel

import dev.langchain4j.model.chat.ChatLanguageModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * This is an example of using a [dev.langchain4j.model.chat.ChatLanguageModel], a low-level LangChain4j API
 */
@RestController
class ChatLanguageModelController(private val chatLanguageModel: ChatLanguageModel) {

    @GetMapping("/model")
    fun model(
        @RequestParam(value = "message", defaultValue = "Hello") message: String
    ): String = chatLanguageModel.generate(message)
}