package com.selab.springbootblueprints.p6spy;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.Formatter;
import org.springframework.boot.ansi.AnsiColor;


public class CustomMessageFormattingStrategy implements MessageFormattingStrategy {

    private final String STATEMENT_SQL_MESSAGE_FORMAT = "#%s | url: %s | connectionID: %s | category: %s | elapsed: %sms\n\t%s\n";
    private final String EITHER_CATEGORY_MESSAGE_FORMAT = "#%s | url: %s | connectionID: %s | category: %s | prepared: %s | sql: %s | elapsed: %sms";

    private final Formatter formatter;

    public CustomMessageFormattingStrategy() {
        this.formatter = CustomMultiLineFormatter.builder()
                .keywordColor(AnsiColor.BLUE)
                .quotedColor(AnsiColor.CYAN)
                .stringColor(AnsiColor.GREEN)
                .build();
    }

    @Override
    public String formatMessage(final int connectionId, final String now, final long elapsed, final String category, final String prepared, String sql, final String url) {
        String result;
        if (category.equalsIgnoreCase(Category.STATEMENT.getName())) {
            String formattedSql = formatSql(sql);
            result = String.format(STATEMENT_SQL_MESSAGE_FORMAT, now, url, connectionId, category, elapsed, formattedSql);
        } else {
            result = String.format(EITHER_CATEGORY_MESSAGE_FORMAT, now, url, connectionId, category, prepared, sql, elapsed);
        }

        return result;
    }

    private String formatSql(String sql) {
        String trimLowCaseSql = sql.trim().toLowerCase();

        String result = "\n\tquery is empty";
        if (!trimLowCaseSql.isBlank()) {
            result = formatter.format(sql);
        }

        return result;
    }
}