package project.vilsoncake.avgeekadmintelegrambot.constant;

public class BotMessageConst {
    public static final String TABLE_COLUMN_TEMPLATE = "| %%-%ss | %%-%ss | %%3s |\n";
    public static final String TRAFFIC_COLUMN_TEMPLATE = "| %%-%ss |\n";
    public static final String TRAFFIC_REFERRER_TEMPLATE = "• %s: %s";
    public static final String DELAY_TEMPLATE = "0 00 19 * * *";
    public static final String MARKDOWN_PARSE_MODE = "Markdown";
    public static final String HTML_PARSE_MODE = "html";
    public static final String START_COMMAND_TEXT = """
            Hey, %s. You are in the admin panel of the *Avgeek telegram bot*.
                        
            Here you can see the bot's users and also check the github repository traffic.
            """;
    public static final String LINK_COMMAND_TEXT = "https://github.com/ViLsonCake/avgeek-telegram-bot";
    public static final String PING_COMMAND_TEXT = "pong";
    public static final String TRAFFIC_TABLE_TITLE = "Weekly Repository Traffic";
    public static final String TODAY_TRAFFIC_TABLE_TITLE = "Today Repository Traffic";
    public static final String TRAFFIC_VIEWS_TEMPLATE = "Views: %s";
    public static final String TRAFFIC_UNIQUE_VIEWS_TEMPLATE = "Unique visitors: %s";
    public static final String TRAFFIC_TODAY_UNIQUE_VIEWS_TEMPLATE = "Today unique visitors: %s";
    public static final String TRAFFIC_CLONES_TEMPLATE = "Clones: %s";
    public static final String TRAFFIC_UNIQUE_CLONERS_TEMPLATE = "Unique cloners: %s";
    public static final String TRAFFIC_TODAY_UNIQUE_CLONERS_TEMPLATE = "Today unique cloners: %s";
    public static final String TRAFFIC_REFERRAL_SOURCES_TITLE = "Referral sources";
    public static final String NEW_UNIQUE_VISITOR_TEXT = """
            *New Unique Visitor*
            
            Today views: *%s*
            Today unique visitors: *%s*
            """;
}
