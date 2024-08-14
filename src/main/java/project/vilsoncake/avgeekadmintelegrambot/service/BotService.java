package project.vilsoncake.avgeekadmintelegrambot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface BotService {
    SendMessage startBotCommand(String username, String id);
    SendMessage usersBotCommand(String username, String id);
    SendMessage linkBotCommand(String id);
    SendMessage pingBotCommand(String id);
}
