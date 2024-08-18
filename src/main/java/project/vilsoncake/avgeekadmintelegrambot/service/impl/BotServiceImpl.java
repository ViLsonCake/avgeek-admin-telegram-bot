package project.vilsoncake.avgeekadmintelegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import project.vilsoncake.avgeekadmintelegrambot.dto.WeeklyReportDto;
import project.vilsoncake.avgeekadmintelegrambot.entity.jpa.UserEntity;
import project.vilsoncake.avgeekadmintelegrambot.repository.UserReadOnlyRepository;
import project.vilsoncake.avgeekadmintelegrambot.service.BotService;
import project.vilsoncake.avgeekadmintelegrambot.service.TrafficService;
import project.vilsoncake.avgeekadmintelegrambot.utils.MessageUtils;

import java.util.List;

import static project.vilsoncake.avgeekadmintelegrambot.constant.BotMessageConst.*;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final UserReadOnlyRepository userReadOnlyRepository;
    private final TrafficService trafficService;
    private final MessageUtils messageUtils;

    @Override
    public SendMessage startBotCommand(String username, String id) {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setParseMode(MARKDOWN_PARSE_MODE);
        message.setText(String.format(START_COMMAND_TEXT, username));

        return message;
    }

    @Override
    public SendMessage usersBotCommand(String username, String id) {
        List<UserEntity> users = userReadOnlyRepository.findAll();

        String table = messageUtils.createUserTable(users);

        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setParseMode(HTML_PARSE_MODE);
        message.setText(table);

        return message;
    }

    @Override
    public SendMessage trafficBotCommand(String username, String id) {
        WeeklyReportDto weeklyReportDto = trafficService.getWeeklyReport();

        String messageText = messageUtils.createTrafficTable(weeklyReportDto);

        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setParseMode(HTML_PARSE_MODE);
        message.setText(messageText);

        return message;
    }

    @Override
    public SendMessage linkBotCommand(String id) {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(LINK_COMMAND_TEXT);

        return message;
    }

    @Override
    public SendMessage pingBotCommand(String id) {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(PING_COMMAND_TEXT);

        return message;
    }
}
