# Brave Search MCP Client

This project demonstrates how to integrate the [MCP Kotlin SDK](https://github.com/modelcontextprotocol/kotlin-sdk) with
a Brave Search server using [Ktor](https://ktor.io). The application launches the Brave Search server via `npx` (passing
the API key via an environment variable), connects to it using the MCP protocol over standard I/O streams, and exposes a
simple HTTP endpoint (`/chat`) to process natural language queries.

## Overview

When a POST request is made to the `/chat` endpoint with a text question in the body, the application:

1. Connects to the Brave Search MCP server.
2. Retrieves the list of available tools.
3. Calls the first available tool with the provided query.
4. Returns the tool's response as the HTTP response.

## Prerequisites

- **Java Development Kit (JDK) 11+** (or any version supported by your environment)
- **npx**: Make sure you have Node.js and npm installed. You can install npx via npm if needed:
  ```shell
  npm install -g npx
  ```
- **BRAVE_API_KEY**: Obtain your Brave Search API key and set it as an environment variable.

## Setup

1. Clone the repository

  ```shell
  git clone git@github.com:devcrocod/Kotlin-AI-Examples.git
  cd Kotlin-AI-Examples/projects/mcp/brave
  ```

2. Set the environment variable
   On Unix/Linux/macOS:

  ```shell
  export BRAVE_API_KEY="your-brave-api-key"
  ```

On Windows (Command Prompt):

  ```shell
set BRAVE_API_KEY=your-brave-api-key
  ```

3. Build the project

  ```shell
  ./gradlew build
  ```

## Running the application

Start the application using Gradle:

```shell
./gradlew run
```

The embedded Ktor server will start on port 8080.

## Usage

Send a POST request to the `/chat` endpoint with your query in the request body. For example, using `curl`:

```shell
curl -X POST -d "Does Kotlin support the Model Context Protocol? Please provide some references." http://localhost:8080/chat
```

The server will forward the query to the Brave Search MCP server, invoke the appropriate tool, and return the result as
plain text.

## How it works

1. Process Initialization
   The application starts the Brave Search MCP server using:
    ```kotlin
    ProcessBuilder("npx", "-y", "@modelcontextprotocol/server-brave-search")
    ```

   The API key is injected into the process environment via the `BRAVE_API_KEY` variable.

2. MCP Client Setup
   A `StdioClientTransport` is created from the process's input/output streams using the Kotlin I/O utilities.
   This transport is used to initialize the MCP client:
   ```kotlin
   val client = Client(clientInfo = Implementation(name = "brave-client", version = "1.0.0"))
   runBlocking { client.connect(transport) }
   ```
   The handshake with the server is performed during the connection.
3. Handling Chat Requests
   The /chat endpoint:
    - Reads the query from the request body.
    - Retrieves the list of available tools from the server.
    - Calls the first available tool with the query as an argument.
      Returns the result back to the caller.

## Dependencies

Key dependencies used in this project include:

- [Ktor](https://ktor.io/) for the HTTP server.
- [MCP Kotlin SDK](https://github.com/modelcontextprotocol/kotlin-sdk) for MCP client functionality.
- [kotlinx-io](https://github.com/Kotlin/kotlinx-io) for stream buffering.