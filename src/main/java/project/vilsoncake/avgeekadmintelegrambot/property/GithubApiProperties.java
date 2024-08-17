package project.vilsoncake.avgeekadmintelegrambot.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "github")
public class GithubApiProperties {
    private String baseUrl;
    private String accessToken;
    private String username;
    private String repositoryName;
}
