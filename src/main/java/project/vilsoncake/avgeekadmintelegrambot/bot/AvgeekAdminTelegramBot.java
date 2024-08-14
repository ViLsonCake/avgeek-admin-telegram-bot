package project.vilsoncake.avgeekadmintelegrambot.bot;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.menubutton.SetChatMenuButton;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.vilsoncake.avgeekadmintelegrambot.property.BotProperties;

import java.util.ArrayList;
import java.util.List;

import static project.vilsoncake.avgeekadmintelegrambot.constant.CommandDescriptionsConst.*;
import static project.vilsoncake.avgeekadmintelegrambot.constant.CommandNamesConst.*;

@Component
public class AvgeekAdminTelegramBot extends AbilityBot {

    private final BotMessageHandler botMessageHandler;

    protected AvgeekAdminTelegramBot(BotProperties botProperties, BotMessageHandler botMessageHandler) {
        super(botProperties.getToken(), botProperties.getName());
        this.botMessageHandler = botMessageHandler;
    }

    @Override
    public long creatorId() {
        return 1L;
    }

    @Override
    public void onRegister() {
        List<BotCommand> botCommands = new ArrayList<>();
        botCommands.add(new BotCommand(START_COMMAND_NAME, START_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(USERS_COMMAND_NAME, USERS_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(TRAFFIC_COMMAND_NAME, TRAFFIC_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(LINK_COMMAND_NAME, LINK_COMMAND_DESCRIPTION));
        botCommands.add(new BotCommand(PING_COMMAND_NAME, PING_COMMAND_DESCRIPTION));

        try {
            execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
            execute(new SetChatMenuButton());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        botMessageHandler.handleMessage(update, this);
    }
}
