package babyhere.server.gpt.service;

import babyhere.server.gpt.dto.CompletionChatResponse;
import babyhere.server.gpt.dto.GPTCompletionChatRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GPTChatRestService {

    private final OpenAiService openAiService;

    public CompletionChatResponse completionChat(GPTCompletionChatRequest gptCompletionChatRequest) {
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
                GPTCompletionChatRequest.of(gptCompletionChatRequest));

        CompletionChatResponse response = CompletionChatResponse.of(chatCompletion);

//        List<String> messages = response.getMessages().stream()
//                .map(CompletionChatResponse.Message::getMessage)
//                .collect(Collectors.toList());

//        GPTAnswer gptAnswer = saveAnswer(messages);
//
//        saveQuestion(gptCompletionChatRequest.getMessage(), gptAnswer);

        return response;
    }
}
