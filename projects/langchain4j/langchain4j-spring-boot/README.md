# LangChain4j Spring Boot Example in Kotlin

This project is a rewritten Kotlin example from
the [langchain4j-examples](https://github.com/langchain4j/langchain4j-examples/tree/main/spring-boot-example)
repository. It demonstrates how to use `langchain4j` with Spring Boot in a Kotlin application.

## Requirements

- Java 17 or higher
- Kotlin 2.0.x or higher
- Gradle 8.x or higher
- OpenAI API key (set as environment variable OPENAI_API_KEY)
- IDE with Kotlin support (IntelliJ IDEA recommended)
- Git for version control

## Technologies Used

- Spring Boot 3.2.4
  - spring-boot-starter-web
  - spring-boot-starter-webflux
- Kotlin 2.0.x
  - kotlinx-coroutines-reactor
  - kotlin-reflect
- Langchain4j 0.35.0
  - langchain4j-spring-boot-starter
  - langchain4j-open-ai-spring-boot-starter
- OpenAI API integration
- WebFlux for reactive programming
- Kotlin Coroutines for asynchronous programming

## Running the Project

1. Clone the repository:
    ```bash
    git clone https://github.com/devcrocod/kotlin-ai-examples.git
    ```

2. Navigate to the project directory:
    ```bash
    cd kotlin-ai-examples/projects/langchain4j/langchain4j-spring-boot
    ```

3. Build and run the project using Gradle:

   [MacOS/Linux]
    ```bash
    ./gradlew bootRun
    ```
   [Windows]
    ```shell
   gradlew.bat bootRun
    ```

4. The project will start by default at `http://localhost:8080`.

## Project Structure

- **`build.gradle.kts`**: The project configuration, including all necessary dependencies and plugins for Kotlin, Spring
  Boot, and langchain4j.
- **`src/main/kotlin`**: The source code of the project written in Kotlin.
- **`src/main/resources`**: The project's resources, such as configuration files.

## Main Dependencies

- `langchain4j-spring-boot-starter`: For integrating LangChain into Spring Boot.
- `langchain4j-open-ai-spring-boot-starter`: For working with the OpenAI API.
- `spring-boot-starter-web` and `spring-boot-starter-webflux`: For creating REST APIs and supporting asynchronous
  programming.

## How to Use

1. Set up the OpenAI API by adding the required parameters in `application.properties`:
    ```properties
    langchain4j.open-ai.chat-model.api-key=${OPENAI_API_KEY}
    ```

2. The project supports asynchronous operations using `WebFlux` and `Reactor`.

## Additional Information

- The project utilizes Kotlin Coroutines and integrates with Spring Boot for asynchronous interaction with the OpenAI
  API.
- This example demonstrates how to use the `langchain4j` and `langchain4j-open-ai` libraries in a Kotlin project with
  Spring Boot.

## Links

- Original Java
  example: [langchain4j-examples/spring-boot-example](https://github.com/langchain4j/langchain4j-examples/tree/main/spring-boot-example)
- [Langchain4j Documentation](https://github.com/langchain4j/langchain4j)
