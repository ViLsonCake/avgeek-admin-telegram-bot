package project.vilsoncake.avgeekadmintelegrambot.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.avgeekadmintelegrambot.dto.ReferrerDto;
import project.vilsoncake.avgeekadmintelegrambot.dto.WeeklyReportDto;
import project.vilsoncake.avgeekadmintelegrambot.entity.UserEntity;

import java.util.List;

import static project.vilsoncake.avgeekadmintelegrambot.constant.BotMessageConst.*;

@Component
public class MessageUtils {

    public String createUserTable(List<UserEntity> users) {
        StringBuilder table = new StringBuilder("<pre>\n");

        int maxUsernameLength = users.stream()
                .mapToInt(user -> user.getUsername().length())
                .max()
                .orElse(0);

        int usersCount = users.size();
        int indexLength = String.valueOf(usersCount).length() + 3;
        int index = 0;

        String tableColumnTemplate = String.format(TABLE_COLUMN_TEMPLATE, String.valueOf(usersCount).length() + 1, maxUsernameLength + 1);
        String outline = "|" + "-".repeat(indexLength) + "|" + "-".repeat(maxUsernameLength + 3) + "|" + "-".repeat(5) + "|" + "\n";

        table.append(outline);

        for (UserEntity user : users) {
            index++;
            table.append(String.format(tableColumnTemplate, index + ".", user.getUsername(), user.getAirport().toUpperCase()));
        }

        table.append(outline);
        table.append("</pre>\n");

        return table.toString();
    }

    public String createTrafficTable(WeeklyReportDto weeklyReportDto) {
        StringBuilder text = new StringBuilder("<pre>\n");

        int maxLength = weeklyReportDto.getReferrers().stream()
                .mapToInt(referrer -> (referrer.getReferrer() + ": " + referrer.getUniques()).length() + 1)
                .max()
                .orElse(0);

        if (TRAFFIC_TABLE_TITLE.length() > maxLength) {
            maxLength = TRAFFIC_TABLE_TITLE.length() + 1;
        }

        if (maxLength % 2 != 0) {
            maxLength++;
        }

        String outline = "|" + "-".repeat(maxLength + 3) + "|" + "\n";

        String tableColumnTemplate = String.format(TRAFFIC_COLUMN_TEMPLATE, maxLength + 1);

        int padding = (maxLength - TRAFFIC_TABLE_TITLE.length()) / 2 + 1;
        String centeredTitle = String.format("%" + padding + "s%s%" + padding + "s", "", TRAFFIC_TABLE_TITLE, "");

        text.append(outline);
        text.append(String.format(tableColumnTemplate, centeredTitle));
        text.append(outline);

        String views = String.format(TRAFFIC_VIEWS_TEMPLATE, weeklyReportDto.getViews());
        String uniqueViews = String.format(TRAFFIC_UNIQUE_VIEWS_TEMPLATE, weeklyReportDto.getUniqueViews());
        String todayUniqueViews = String.format(TRAFFIC_TODAY_UNIQUE_VIEWS_TEMPLATE, weeklyReportDto.getTodayUniqueViews());


        text.append(String.format(tableColumnTemplate, views));
        text.append(String.format(tableColumnTemplate, uniqueViews));
        text.append(String.format(tableColumnTemplate, todayUniqueViews));

        text.append(outline);

        String clones = String.format(TRAFFIC_CLONES_TEMPLATE, weeklyReportDto.getClones());
        String uniqueCloners = String.format(TRAFFIC_UNIQUE_CLONERS_TEMPLATE, weeklyReportDto.getUniqueCloners());
        String todayUniqueCloners = String.format(TRAFFIC_TODAY_UNIQUE_CLONERS_TEMPLATE, weeklyReportDto.getTodayUniqueCloners());

        text.append(String.format(tableColumnTemplate, clones));
        text.append(String.format(tableColumnTemplate, uniqueCloners));
        text.append(String.format(tableColumnTemplate, todayUniqueCloners));

        text.append(outline);

        text.append(String.format(tableColumnTemplate, TRAFFIC_REFERRAL_SOURCES_TITLE));

        for (ReferrerDto referrer : weeklyReportDto.getReferrers()) {
            String referrerData = String.format(TRAFFIC_REFERRER_TEMPLATE, referrer.getReferrer(), referrer.getUniques());
            text.append(String.format(tableColumnTemplate, referrerData));
        }

        text.append(outline);

        text.append("</pre>");

        return text.toString();
    }
}
