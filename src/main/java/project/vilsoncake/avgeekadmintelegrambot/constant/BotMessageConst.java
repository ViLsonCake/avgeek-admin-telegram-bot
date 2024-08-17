package project.vilsoncake.avgeekadmintelegrambot.constant;

public class BotMessageConst {
    public static final String TABLE_COLUMN_TEMPLATE = "| %%-%ss | %%-%ss | %%3s |\n";
    public static final String MARKDOWN_PARSE_MODE = "Markdown";
    public static final String HTML_PARSE_MODE = "html";
    public static final String START_COMMAND_TEXT = """
            Hey, %s. You are in the admin panel of the *Avgeek telegram bot*.
                        
            Here you can see the bot's users and also check the github repository traffic.
            """;
    public static final String LINK_COMMAND_TEXT = "https://github.com/ViLsonCake/avgeek-telegram-bot";
    public static final String PING_COMMAND_TEXT = "pong";
    public static final String TRAFFIC_COMMAND_TEXT = """
            ---------------------------------
            Weekly Repository Traffic
            ---------------------------------
            Views: %s
            Unique views: %s
            ---------------------------------
            Clones: %s
            Unique cloners: %s
            ---------------------------------
            Referral sources
            """;
}
