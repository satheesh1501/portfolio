# Spring AI + Google Gemini — Complete Interview Study Guide

## 1. What is Spring AI?
Spring AI is the official Spring framework for AI integration, released in 2024. It provides abstractions that serve as the foundation for developing AI applications, similar to how Spring Data abstracts database interactions.

## 2. Why Spring AI vs Python LangChain?
- **Java Native**: Fits naturally into existing Java/Spring Boot enterprise ecosystems.
- **Spring DI**: Leverages Spring's dependency injection and modularity.
- **Production-grade**: Built with enterprise readiness, observability, and robust software engineering principles in mind.

## 3. Core Spring AI Concepts
- **ChatClient**: A fluent, builder-style API for interacting with LLMs.
- **ChatModel**: An abstraction interface over different AI providers (OpenAI, Gemini, Ollama, etc.).
- **Prompt and Message types**: 
  - `SystemMessage`: Sets behavior and context.
  - `UserMessage`: User's input.
  - `AssistantMessage`: AI's response.
- **System Prompt**: Instructions that set the behavior, role, and context for the AI model.
- **PromptTemplate**: Engine to create dynamic prompts using string interpolation.
- **Streaming responses (`Flux<ChatResponse>`)**: Reactor-based streaming for real-time token generation.
- **SSE (Server-Sent Events)**: Mechanism used to stream responses from the backend to the frontend continuously.
- **Function Calling**: Allowing the LLM to request the execution of predefined tools/functions on the server.
- **RAG (Retrieval-Augmented Generation)**: The concept of fetching external context (e.g., from a vector DB) and appending it to the prompt to ground the AI's response.

## 4. Google Gemini integration
- **Dependency**: `spring-ai-google-genai-spring-boot-starter`
- **Configuration**: Properties set in `application.yml` (e.g., `spring.ai.googlegenai.api-key`).
- **Gemini 1.5 Flash vs Gemini 1.5 Pro**: Flash is faster, more cost-effective, and highly suited for quick chat widgets; Pro is for complex reasoning tasks.
- **API key management**: Should be passed securely via environment variables, not hardcoded.

## 5. Spring Retry for resilience
- `@EnableRetry`: Activates Spring Retry in the application.
- `@Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))`: Automatically retries a method upon failure (e.g., rate limits or network issues).
- **Exponential backoff**: Increases the wait time between retries to prevent overwhelming the API.

## 6. Rate limiting with Redis
- Used to prevent abuse of the AI endpoint.
- **Mechanism**: Check message count for a specific IP/user in Redis before calling the Gemini API.
- Uses `INCR` (increment counter) and `EXPIRE` (set sliding window TTL) commands.

## 7. How implemented in Portfolio
- **System prompt**: Loaded with Satheesh's profile, resume, and skills so the AI answers as a personal assistant.
- **SSE streaming endpoint**: Delivers AI text to the chat widget word-by-word.
- **Rate limiting middleware**: Prevents spamming the portfolio chat.
- **Suggested starter questions**: Helps users know what to ask the AI.

## 8. Top 12 Interview Q&A
1. **What is Spring AI?** 
   - An abstraction framework by Spring for integrating AI models into enterprise Java applications.
2. **How is Spring AI different from LangChain?** 
   - It's Java-native and integrates seamlessly with the Spring ecosystem (DI, properties, Beans), whereas LangChain is Python/JS-centric.
3. **What is a system prompt and why is it important?** 
   - It defines the persona, rules, and context for the AI, guiding how it responds to the user.
4. **What is RAG (Retrieval-Augmented Generation)?** 
   - Fetching relevant data (documents, DB rows) and injecting it into the prompt so the AI can answer based on private or current data.
5. **How does SSE (Server-Sent Events) work?** 
   - A one-way HTTP connection where the server pushes updates (tokens) to the client continuously.
6. **What is the difference between streaming and non-streaming responses?** 
   - Streaming sends data chunk-by-chunk in real-time (better UX); non-streaming waits until the entire response is generated.
7. **How do you prevent abuse of the AI endpoint?** 
   - Implement rate limiting (e.g., using Redis) and require CAPTCHA or authentication.
8. **How do you handle API failures gracefully in Spring AI?** 
   - Using `@Retryable` with exponential backoff and circuit breakers (Resilience4j).
9. **What is function calling in AI models?** 
   - Giving the AI a list of available tools, allowing it to reply with a request to execute a specific tool with extracted arguments.
10. **Why did you choose Gemini 1.5 Flash over GPT-4?** 
    - Flash provides near-instant responses with high quality, which is crucial for a snappy portfolio chat widget.
11. **How do you pass context about yourself to the AI?** 
    - Through the SystemMessage by providing my resume and portfolio details.
12. **What are the token limits and how do you handle them?** 
    - Token limits restrict input/output size. Handle them by summarizing context, using RAG, and keeping chat history short.
