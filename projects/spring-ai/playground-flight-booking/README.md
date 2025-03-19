# AI-powered Expert System Demo (Kotlin Version)

This is a Kotlin reimplementation of the project: https://github.com/tzolov/playground-flight-booking

This project demonstrates how to use [Spring AI](https://github.com/spring-projects/spring-ai) to build an AI-powered system that:

- Has access to terms and conditions (retrieval augmented generation, RAG)
- Can call methods (functions) to perform actions (Function Calling)
- Uses an LLM to interact with the user

![alt text](diagram.jpg)

## Requirements

- Java 17+
- OpenAI API key in the `OPENAI_API_KEY` environment variable

## Running

Run the application by executing `Application.kt` in your IDE or by running `./gradlew bootRun`
(`gradlew.bat bootRun` for Windows) in the command line.

### With OpenAI Chat

Add the Spring AI OpenAI dependency to `build.gradle.kts`:

```kotlin
implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter:1.0.0-SNAPSHOT")
```

Add the OpenAI configuration to `application.properties`:
```
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-4o
```

### With VertexAI Gemini Chat
Add the Spring AI VertexAI Gemini and Onnx Transformer Embedding dependencies to `build.gradle.kts`:
```kotlin
implementation("org.springframework.ai:spring-ai-vertex-ai-gemini-spring-boot-starter:1.0.0-SNAPSHOT")
implementation("org.springframework.ai:spring-ai-transformers-spring-boot-starter:1.0.0-SNAPSHOT")
```

Add the VertexAI Gemini configuration to `application.properties`:
```
spring.ai.vertex.ai.gemini.project-id=${VERTEX_AI_GEMINI_PROJECT_ID}
spring.ai.vertex.ai.gemini.location=${VERTEX_AI_GEMINI_LOCATION}
spring.ai.vertex.ai.gemini.chat.options.model=gemini-1.5-pro-001
# spring.ai.vertex.ai.gemini.chat.options.model=gemini-1.5-flash-001
```

### With Azure OpenAI Chat
Add the Spring AI Azure OpenAI dependency to `build.gradle.kts`:
```kotlin
implementation("org.springframework.ai:spring-ai-azure-openai-spring-boot-starter:1.0.0-SNAPSHOT")
```

Add the Azure OpenAI configuration to `application.properties`:
```
spring.ai.azure.openai.api-key=${AZURE_OPENAI_API_KEY}
spring.ai.azure.openai.endpoint=${AZURE_OPENAI_ENDPOINT}
spring.ai.azure.openai.chat.options.deployment-name=gpt-4o
```

### With Groq Chat
Groq reuses the OpenAI Chat client but points to the Groq endpoint.

Add the Spring AI OpenAI dependency to `build.gradle.kts`:
```kotlin
implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter:1.0.0-SNAPSHOT")
implementation("org.springframework.ai:spring-ai-transformers-spring-boot-starter:1.0.0-SNAPSHOT")
```

Add the Groq configuration to `application.properties`:
```
spring.ai.openai.api-key=${GROQ_API_KEY}
spring.ai.openai.base-url=https://api.groq.com/openai
spring.ai.openai.chat.options.model=llama3-70b-8192
```

### With Anthropic Claude 3 Chat
Add the Spring AI Anthropic Claude and Onnx Transformer Embedding dependencies to `build.gradle.kts`:
```kotlin
implementation("org.springframework.ai:spring-ai-anthropic-spring-boot-starter:1.0.0-SNAPSHOT")
implementation("org.springframework.ai:spring-ai-transformers-spring-boot-starter:1.0.0-SNAPSHOT")
```

Add the Anthropic configuration to `application.properties`:
```
spring.ai.anthropic.api-key=${ANTHROPIC_API_KEY}
spring.ai.openai.chat.options.model=llama3-70b-8192
spring.ai.anthropic.chat.options.model=claude-3-5-sonnet-20240620
```

## Build Jar
[MacOS/Linux]
```shell
./gradlew clean build -Pproduction
```

[Windows]
```shell
gradlew.bat clean build -Pproduction
```

[MacOS/Linux]
```shell
java -jar ./build/libs/playground-flight-booking-0.0.1-SNAPSHOT.jar
```

[Windows]
```shell
java -jar .\build\libs\playground-flight-booking-0.0.1-SNAPSHOT.jar
```

## Using Docker
```shell
docker run -it --rm --name postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres ankane/pgvector
```