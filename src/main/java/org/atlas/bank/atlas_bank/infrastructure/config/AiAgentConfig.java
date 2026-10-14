package org.atlas.bank.atlas_bank.infrastructure.config;

import org.atlas.bank.atlas_bank.infrastructure.adapter.in.ai.AtlasBankTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiAgentConfig {

    @Bean
    public ChatClient chatClient(
            ChatClient.Builder builder,
            AtlasBankTools tools
    ) {
        return builder
                .defaultSystem("You're an AI agent that helps users with their banking needs. ")
                .defaultTools(tools)
                .build();
    }
}
