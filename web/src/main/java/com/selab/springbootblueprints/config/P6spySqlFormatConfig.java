package com.selab.springbootblueprints.config;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.hibernate.engine.jdbc.internal.FormatStyle;


public class P6spySqlFormatConfig implements MessageFormattingStrategy {

    private final String STATEMENT_SQL_MESSAGE_FORMAT = "#%s | url: %s | connectionID: %s | category: %s | elapsed: %sms\n\t%s\n";
    private final String EITHER_CATEGORY_MESSAGE_FORMAT = "#%s | url: %s | connectionID: %s | category: %s | prepared: %s | sql: %s | elapsed: %sms";

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
            if (trimLowCaseSql.startsWith("create") || trimLowCaseSql.startsWith("alter") || trimLowCaseSql.startsWith("comment")) {
                result = FormatStyle.DDL
                        .getFormatter()
                        .format(sql);
            } else {
                result = FormatStyle.BASIC
                        .getFormatter()
                        .format(sql);
            }
        }

        return result;
    }
}