package io.github.devcrocod.example.output

import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OutputParserController(private val chatClient: ChatClient) {
    @GetMapping("ai/output")
    fun generate(@RequestParam(value = "actor", defaultValue = "Jeff Bridges") actor: String): ActorsFilms {
        val userMessage = """
            Generate the filmography for the actor $actor.
        """.trimIndent()

        val promptTemplate = PromptTemplate(userMessage, mapOf("actor" to actor))
        val prompt = promptTemplate.create()
        val generation = chatClient.prompt(prompt).call().entity<ActorsFilms>(ActorsFilms::class.java)
        return generation!!
    }
}