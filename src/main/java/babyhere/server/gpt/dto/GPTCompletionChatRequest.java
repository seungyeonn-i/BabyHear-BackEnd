package babyhere.server.gpt.dto;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GPTCompletionChatRequest {

    private String model = "gpt-3.5-turbo";

    private String role = "user";

    private String message;

    private Integer maxTokens = 1000;


    public static ChatCompletionRequest of(GPTCompletionChatRequest request) {
        return ChatCompletionRequest.builder()
                .model(request.getModel())
                .messages(convertChatMessage(request))
                .maxTokens(request.getMaxTokens())
                .build();
    }

    private static List<ChatMessage> convertChatMessage(GPTCompletionChatRequest request) {
        return List.of(new ChatMessage(request.getRole(), request.getMessage()));
    }
}
