package io.github.devcrocod.example.mcpserver

import com.fasterxml.jackson.databind.ObjectMapper
import io.modelcontextprotocol.server.McpServer
import io.modelcontextprotocol.server.McpServerFeatures
import io.modelcontextprotocol.server.McpSyncServer
import io.modelcontextprotocol.server.transport.HttpServletSseServerTransport
import io.modelcontextprotocol.server.transport.StdioServerTransport
import io.modelcontextprotocol.spec.McpSchema


fun main(args: Array<String>) {
    val command = args.firstOrNull() ?: "--sse-server"
    val port = args.getOrNull(1)?.toIntOrNull() ?: 3001
    when (command) {
        "--stdio" -> `run mcp server using stdio`()
        "--sse-server" -> `run sse mcp server with plain configuration`(port)
        else -> {
            System.err.println("Unknown command: $command")
        }
    }
}

fun McpSyncServer.configureServer(): McpSyncServer {
    val kotlinPrompt = McpServerFeatures.SyncPromptRegistration(
        McpSchema.Prompt("Kotlin Developer", "Develop small kotlin applications", null)
    ) { request ->
        McpSchema.GetPromptResult(
            "Kotlin project development prompt",
            listOf(
                McpSchema.PromptMessage(
                    McpSchema.Role.ASSISTANT,
                    McpSchema.TextContent("I will help you develop a Kotlin project.")
                )
            )
        )
    }

    val calculatorTool = McpServerFeatures.SyncToolRegistration(
        McpSchema.Tool("testTool", "A test tool", """{"type":"string"}""")
    ) { _ ->
        McpSchema.CallToolResult(listOf(McpSchema.TextContent("Hello world!")), false)
    }

    val searchResource = McpServerFeatures.SyncResourceRegistration(
        McpSchema.Resource("https://search.com/", "Web Search", "Web search engine", "text/html", null)
    ) { request ->
        McpSchema.ReadResourceResult(
            listOf(McpSchema.TextResourceContents("Search results", request.uri, "text/html"))
        )
    }

    this.addPrompt(kotlinPrompt)
    this.addTool(calculatorTool)
    this.addResource(searchResource)

    return this
}

fun `run mcp server using stdio`() {
    val server = McpServer.sync(StdioServerTransport())
        .serverInfo("mcp test server", "0.1.0")
        .capabilities(
            McpSchema.ServerCapabilities.builder()
                .prompts(true)
                .resources(true, true)
                .tools(true)
                .build()
        )
        .build()

    server.configureServer()

    println("Server running on stdio")
}

fun `run sse mcp server with plain configuration`(port: Int) {
    println("Starting SSE server on port $port.")
    println("Use inspector to connect to the http://localhost:$port/sse")

    val transport = HttpServletSseServerTransport(ObjectMapper(), "/message", "/sse")
    val server = McpServer.sync(transport)
        .serverInfo("mcp test server", "0.1.0")
        .capabilities(
            McpSchema.ServerCapabilities.builder()
                .prompts(true)
                .resources(true, true)
                .tools(true)
                .build()
        )
        .build()

    server.configureServer()

    println("SSE server is running on http://localhost:$port/sse")
}
