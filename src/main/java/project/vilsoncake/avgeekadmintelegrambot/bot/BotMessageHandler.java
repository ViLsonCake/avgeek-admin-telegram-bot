package project.vilsoncake.avgeekadmintelegrambot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.vilsoncake.avgeekadmintelegrambot.service.BotService;

import static project.vilsoncake.avgeekadmintelegrambot.constant.CommandNamesConst.*;

@Component
@RequiredArgsConstructor
public class BotMessageHandler {

    private final BotService botService;

    public void handleMessage(Update update, AvgeekAdminTelegramBot bot) {
        if (!bot.isCreator(update.getMessage().getFrom().getId())) {
            return;
        }

        String username = update.getMessage().getFrom().getUserName();
        String id = update.getMessage().getFrom().getId().toString();

        try {
            switch (update.getMessage().getText()) {
                case START_COMMAND_NAME:
                    bot.execute(botService.startBotCommand(username, id));
                    break;
                case USERS_COMMAND_NAME:
                    bot.execute(botService.usersBotCommand(username, id));
                    break;
                case LINK_COMMAND_NAME:
                    bot.execute(botService.linkBotCommand(id));
                    break;
                case PING_COMMAND_NAME:
                    bot.execute(botService.pingBotCommand(id));
                    break;
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
