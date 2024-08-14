package project.vilsoncake.avgeekadmintelegrambot.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "bot")
public class BotProperties {
    private String name;
    private String token;
}
