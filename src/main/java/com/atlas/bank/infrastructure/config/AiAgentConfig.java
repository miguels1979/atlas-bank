package com.atlas.bank.infrastructure.config;


import com.atlas.bank.infrastructure.adapter.in.ai.AtlasBankTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiAgentConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, AtlasBankTools atlasBankTools) {
        return builder
                .defaultSystem("Eres un agente bancario del banco Atlas Bank. Ayuda a los clientes a operar con sus cuentas. Si te preguntan cualquier otra cosa que no sea relacionada a atlas-bank , responde con cortesía que solo respondes cosas relacionadas con las cuentas o transferencias." +
                        " Responde siempre en español." )
                .defaultTools(atlasBankTools)
                .build();


    }
}
