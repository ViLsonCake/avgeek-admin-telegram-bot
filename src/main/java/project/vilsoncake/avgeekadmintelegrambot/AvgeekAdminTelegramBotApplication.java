package project.vilsoncake.avgeekadmintelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class AvgeekAdminTelegramBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AvgeekAdminTelegramBotApplication.class, args);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(context.getBean("avgeekAdminTelegramBot", AbilityBot.class));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
