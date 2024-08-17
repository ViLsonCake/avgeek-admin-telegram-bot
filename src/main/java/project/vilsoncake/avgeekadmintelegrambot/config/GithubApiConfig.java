package project.vilsoncake.avgeekadmintelegrambot.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import project.vilsoncake.avgeekadmintelegrambot.property.GithubApiProperties;

@Configuration
@RequiredArgsConstructor
public class GithubApiConfig {

    private final GithubApiProperties githubApiProperties;

    @Bean
    public WebClient githubApiClient() {
        return WebClient.builder()
                .baseUrl(githubApiProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + githubApiProperties.getAccessToken())
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .build();
    }
}
