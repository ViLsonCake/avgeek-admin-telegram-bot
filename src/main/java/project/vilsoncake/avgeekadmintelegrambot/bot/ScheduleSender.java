package project.vilsoncake.avgeekadmintelegrambot.bot;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import project.vilsoncake.avgeekadmintelegrambot.dto.GithubApiViewsResponse;
import project.vilsoncake.avgeekadmintelegrambot.entity.document.DailyTrafficDocument;
import project.vilsoncake.avgeekadmintelegrambot.entity.document.UsersInfoDocument;
import project.vilsoncake.avgeekadmintelegrambot.entity.jpa.UserEntity;
import project.vilsoncake.avgeekadmintelegrambot.property.BotProperties;
import project.vilsoncake.avgeekadmintelegrambot.service.TrafficService;
import project.vilsoncake.avgeekadmintelegrambot.service.impl.UsersInfoService;
import project.vilsoncake.avgeekadmintelegrambot.utils.MessageUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static project.vilsoncake.avgeekadmintelegrambot.constant.BotMessageConst.*;
import static project.vilsoncake.avgeekadmintelegrambot.constant.NumberConst.CHECK_NEW_UNIQUE_VISITORS_DELAY_IN_MINUTES;
import static project.vilsoncake.avgeekadmintelegrambot.constant.NumberConst.CHECK_NEW_USERS_DELAY_IN_MINUTES;

@Component
@RequiredArgsConstructor
public class ScheduleSender {

    private final BotSender botSender;
    private final TrafficService trafficService;
    private final UsersInfoService usersInfoService;
    private final BotProperties botProperties;
    private final MessageUtils messageUtils;

    @Scheduled(fixedDelay = CHECK_NEW_USERS_DELAY_IN_MINUTES, timeUnit = TimeUnit.MINUTES)
    public void sendNewUsers() {
        List<UsersInfoDocument> usersInfo = usersInfoService.findAllUsersInfo();
        List<UserEntity> users = usersInfoService.findAllEntities();

        if (usersInfo.isEmpty()) {
            usersInfoService.addNewDailyUsersInfo();
        }

        if (!DateUtils.isSameDay(usersInfo.get(0).getDate(), new Date())) {
            usersInfoService.addNewDailyUsersInfo();
        }

        UsersInfoDocument todayUsersInfo = usersInfoService.getLastAddedUsersInfo();

        if (users.size() > todayUsersInfo.getUsersCount()) {
            UserEntity newUser = usersInfoService.getLastAddedUserEntity();

            SendMessage message = new SendMessage();
            message.setChatId(botProperties.getCreatorId());
            message.setParseMode(MARKDOWN_PARSE_MODE);
            message.setText(String.format(NEW_USER_TEXT, newUser.getUsername(), newUser.getAirport(), newUser.getBotLanguage(), newUser.getCreatedAt(), users.size()));

            botSender.sendMessage(message);
        }
    }

    @Scheduled(fixedDelay = CHECK_NEW_UNIQUE_VISITORS_DELAY_IN_MINUTES, timeUnit = TimeUnit.MINUTES)
    public void sendNewTodayUniqueVisitors() {
        List<DailyTrafficDocument> trafficDocuments = trafficService.getAllTrafficDocuments();

        if (trafficDocuments.isEmpty()) {
            trafficService.addNewDailyTraffic();
        }

        if (!DateUtils.isSameDay(trafficDocuments.get(0).getDate(), new Date())) {
            trafficService.addNewDailyTraffic();
        }

        DailyTrafficDocument todayTrafficDocument = trafficDocuments.get(0);

        GithubApiViewsResponse viewsResponse = trafficService.getWeeklyViews();

        int currentViews = trafficService.getTodayViews(viewsResponse.getViews());
        int currentUniqueVisitors = trafficService.getTodayUniqueVisitors(viewsResponse.getViews());

        if (currentUniqueVisitors > todayTrafficDocument.getUniqueVisitors()) {
            trafficService.changeViewsCount(todayTrafficDocument, currentViews);
            trafficService.changeUniqueVisitorsCount(todayTrafficDocument, currentUniqueVisitors);

            SendMessage message = new SendMessage();
            message.setChatId(botProperties.getCreatorId());
            message.setParseMode(MARKDOWN_PARSE_MODE);
            message.setText(String.format(NEW_UNIQUE_VISITOR_TEXT, currentViews, currentUniqueVisitors));

            botSender.sendMessage(message);
        }
    }

    @Scheduled(cron = DELAY_TEMPLATE, zone = "UTC")
    public void sendTodayTrafficReport() {
        List<DailyTrafficDocument> trafficDocuments = trafficService.getAllTrafficDocuments();

        if (trafficDocuments.isEmpty()) {
            trafficService.addNewDailyTraffic();
        }

        if (!DateUtils.isSameDay(trafficDocuments.get(0).getDate(), new Date())) {
            trafficService.addNewDailyTraffic();
        }

        DailyTrafficDocument todayTrafficDocument = trafficDocuments.get(0);

        String messageText = messageUtils.createDailyTrafficReport(todayTrafficDocument);

        SendMessage message = new SendMessage();
        message.setChatId(botProperties.getCreatorId());
        message.setParseMode(HTML_PARSE_MODE);
        message.setText(messageText);

        botSender.sendMessage(message);
    }
}
