# Spring AI with OpenAI (Kotlin Version)

This project is a Kotlin version of the original project: [ai-openai-helloworld](https://github.com/rd-1-2022/ai-openai-helloworld/blob/main/README.md).

It contains a web service that accepts HTTP GET requests at `http://localhost:8080/ai/simple`.

There is an optional `message` parameter whose default value is "Tell me a joke".

The response to the request is generated using the OpenAI ChatGPT Service.

## Prerequisites

Before using the AI commands, make sure you have a developer token from OpenAI.

1. Create an account at [OpenAI Signup](https://platform.openai.com/signup).
2. Generate the token at [API Keys](https://platform.openai.com/account/api-keys).

The Spring AI project defines a configuration property named `spring.ai.openai.api-key` that you should set to the value of the `API Key` obtained from `openai.com`.

Exporting an environment variable is one way to set that configuration property:

```shell
export OPENAI_API_KEY=<INSERT KEY HERE>
```

Setting the API key is all you need to run the application.
However, you can find more information on setting started in the [Spring AI reference documentation section on OpenAI Chat](https://docs.spring.io/spring-ai/reference/api/chat/openai-chat.html).

## Building and running

```shell
./gradlew bootRun
```

## Access the endpoint

To get a response to the default request of "Tell me a joke"

```shell 
curl localhost:8080/ai
```

A sample response is

```text
Sure, here's a classic one for you:

Why don't scientists trust atoms?

Because they make up everything!
```

Now using the `message` request parameter
```shell
curl --get  --data-urlencode 'message=Tell me a joke about a cow.' localhost:8080/ai 
```

A sample response is

```text
Why did the cow go to space?

Because it wanted to see the mooooon!
```

Alternatively use the [httpie](https://httpie.io/) client
```shell
http localhost:8080/ai message=='Tell me a joke about a cow.'
```