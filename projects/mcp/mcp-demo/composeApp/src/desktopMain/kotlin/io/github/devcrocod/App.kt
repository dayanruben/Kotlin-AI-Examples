package io.github.devcrocod

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

// Defines the role of messages
enum class MessageType {
    USER, ASSISTANT
}

// Data class for each chat message
data class ChatMessage(val type: MessageType, val content: String)

@Composable
@Preview
fun App() {
    // Input and message list states
    var query by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<ChatMessage>()) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    // Create an MCPClient instance
    val mcpClient = remember { MCPClient() }
    // Remember the scroll state for the list
    val listState = rememberLazyListState()

    // Connecting to the server on screen startup
    LaunchedEffect(Unit) {
        mcpClient.connectToServer("http://localhost:3001/sse")
        messages = messages + ChatMessage(
            MessageType.ASSISTANT, "Connected to the server: ${mcpClient.serverVersion}"
        )
    }

    suspend fun senQuery() {
        // Avoid sending or ongoing requests
        if (query.isBlank() || isLoading) return

        isLoading = true
        messages = messages + ChatMessage(MessageType.USER, query)
        val response = mcpClient.processQuery(query)
        messages = messages + ChatMessage(MessageType.ASSISTANT, response)
        query = ""
        isLoading = false
        listState.animateScrollToItem(messages.size - 1)
    }

    MaterialTheme {
        Scaffold(
            // App bar with a title
            topBar = { TopAppBar(title = { Text("MCP Client") }) },
            content = { padding ->
                // Main container
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
                ) {
                    // Displays messages in a lazy list
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        state = listState
                    ) {
                        items(messages) { message ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = when (message.type) {
                                    MessageType.USER -> Arrangement.End
                                    MessageType.ASSISTANT -> Arrangement.Start
                                }
                            ) {
                                // Use different maximum widths depending on the message type
                                val maxWidth = when (message.type) {
                                    MessageType.USER -> 300.dp
                                    MessageType.ASSISTANT -> 600.dp
                                }
                                // Card to contain each message text
                                Card(
                                    modifier = Modifier.widthIn(max = maxWidth).padding(vertical = 4.dp),
                                    elevation = 4.dp
                                ) {
                                    Text(
                                        text = message.content,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input field and send button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Text field for the user's query
                        TextField(
                            value = query,
                            onValueChange = { query = it },
                            modifier = Modifier
                                .weight(1f)
                                .onPreviewKeyEvent { keyEvent ->
                                    if (!isLoading && keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Enter) {
                                        // Send a message on the Enter key
                                        scope.launch { senQuery() }
                                        true
                                    } else {
                                        false
                                    }
                                },
                            placeholder = { Text("Enter your query...") },
                            enabled = !isLoading
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Button to send the query
                        Button(
                            onClick = {
                                if (!isLoading && query.isNotBlank()) {
                                    scope.launch { senQuery() }
                                }
                            },
                            enabled = !isLoading
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text("Send")
                            }
                        }
                    }
                }
            }
        )
    }
}