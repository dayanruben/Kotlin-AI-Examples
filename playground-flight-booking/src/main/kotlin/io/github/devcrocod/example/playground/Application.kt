package io.github.devcrocod.example.playground

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.reader.TextReader
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.SimpleVectorStore
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.Resource

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.theme.Theme
import org.springframework.boot.runApplication

@SpringBootApplication
@Theme(value = "customer-support-agent")
class Application : AppShellConfigurator {
    private val logger: Logger = LoggerFactory.getLogger(Application::class.java)

    // In the real world, ingesting documents would often happen separately, on a CI
    // server or similar.
    @Bean
    fun ingestTermOfServiceToVectorStore(
        embeddingModel: EmbeddingModel,
        vectorStore: VectorStore,
        @Value("classpath:rag/terms-of-service.txt") termsOfServiceDocs: Resource
    ): CommandLineRunner {
        return CommandLineRunner {
            // Ingest the document into the vector store
            vectorStore.write(TokenTextSplitter().transform(TextReader(termsOfServiceDocs).read()))

            vectorStore.similaritySearch("Cancelling Bookings").forEach { doc ->
                logger.info("Similar Document: ${doc.content}")
            }
        }
    }

    @Bean
    fun vectorStore(embeddingModel: EmbeddingModel): VectorStore =
        SimpleVectorStore(embeddingModel)

    @Bean
    fun chatMemory(): ChatMemory =
        InMemoryChatMemory()
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

