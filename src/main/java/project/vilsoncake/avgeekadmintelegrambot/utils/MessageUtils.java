package project.vilsoncake.avgeekadmintelegrambot.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.avgeekadmintelegrambot.entity.UserEntity;

import java.util.List;

import static project.vilsoncake.avgeekadmintelegrambot.constant.BotMessageConst.TABLE_COLUMN_TEMPLATE;

@Component
public class MessageUtils {

    public String createUserTable(List<UserEntity> users) {
        StringBuilder table = new StringBuilder();
        table.append("<pre>\n");

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
}
