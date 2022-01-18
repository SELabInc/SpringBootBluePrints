package com.selab.springbootblueprints.p6spy;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.env.spi.AnsiSqlKeywords;
import org.hibernate.engine.jdbc.internal.Formatter;
import org.hibernate.internal.util.StringHelper;
import org.springframework.boot.ansi.AnsiColor;

import java.util.*;

@Slf4j
@Builder(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class CustomMultiLineFormatter implements Formatter {

    private static final Set<String> BEGIN_CLAUSES = new HashSet<>();
    private static final Set<String> END_CLAUSES = new HashSet<>();
    private static final Set<String> LOGICAL = new HashSet<>();
    private static final Set<String> QUANTIFIERS = new HashSet<>();
    private static final Set<String> DML = new HashSet<>();
    private static final Set<String> MISC = new HashSet<>();
    private static final Set<String> HIGHLIGHTING_KEYWORDS = new HashSet<>(AnsiSqlKeywords.INSTANCE.sql2003());

    static {
        BEGIN_CLAUSES.add("left");
        BEGIN_CLAUSES.add("right");
        BEGIN_CLAUSES.add("inner");
        BEGIN_CLAUSES.add("outer");
        BEGIN_CLAUSES.add("group");
        BEGIN_CLAUSES.add("order");

        END_CLAUSES.add("where");
        END_CLAUSES.add("set");
        END_CLAUSES.add("having");
        END_CLAUSES.add("from");
        END_CLAUSES.add("by");
        END_CLAUSES.add("join");
        END_CLAUSES.add("into");
        END_CLAUSES.add("union");

        LOGICAL.add("and");
        LOGICAL.add("or");
        LOGICAL.add("when");
        LOGICAL.add("else");
        LOGICAL.add("end");

        QUANTIFIERS.add("in");
        QUANTIFIERS.add("all");
        QUANTIFIERS.add("exists");
        QUANTIFIERS.add("some");
        QUANTIFIERS.add("any");

        DML.add("insert");
        DML.add("update");
        DML.add("delete");

        MISC.add("select");
        MISC.add("on");

        HIGHLIGHTING_KEYWORDS.addAll(Arrays.asList("KEY", "SEQUENCE", "CASCADE", "INCREMENT"));
    }

    private static final String INDENT_STRING = "    ";
    private static final String INITIAL = System.lineSeparator() + INDENT_STRING;
    private static final String SYMBOLS_AND_WS = "=><!+-*/()',.|&`\"?" + StringHelper.WHITESPACE;

    private final AnsiColor keywordColor;
    private final AnsiColor quotedColor;
    private final AnsiColor stringColor;

    @Override
    public String format(String source) {
        FormatProcess formatProcess = new CustomMultiLineFormatter.FormatProcess(source, keywordColor, quotedColor, stringColor);

        return formatProcess.perform();
    }

    private static class FormatProcess {
        private final String keywordColorEscape;
        private final String quotedColorEscape;
        private final String stringColorEscape;
        private final String colorResetEscape = "\u001b[0m";

        private boolean quoted;
        private boolean string;

        private boolean beginLine = true;
        private boolean afterBeginBeforeEnd;
        private boolean afterByOrSetOrFromOrSelect;
        private boolean afterOn;
        private boolean afterBetween;
        private boolean afterInsert;
        private boolean afterSelect;
        private int inFunction;
        private int parensSinceSelect;
        private final LinkedList<Integer> parenCounts = new LinkedList<>();
        private final LinkedList<Boolean> afterByOrFromOrSelects = new LinkedList<>();

        private int indent = 1;

        private final StringBuilder result = new StringBuilder();
        private final StringTokenizer tokenizer;

        private String lastLowCaseToken;
        private String token;
        private String lowCaseToken;


        public FormatProcess(String sql, AnsiColor keywordColor, AnsiColor quotedColor, AnsiColor stringColor) {
            this.tokenizer = new StringTokenizer(sql, SYMBOLS_AND_WS, true);
            this.keywordColorEscape = generateEscape(keywordColor);
            this.quotedColorEscape = generateEscape(quotedColor);
            this.stringColorEscape = generateEscape(stringColor);
        }

        public static String generateEscape(AnsiColor color) {
            return String.format("\u001b[%sm", color.toString());
        }

        public String perform() {

            result.append(INITIAL);

            while (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken();


                if (token.equals("`") || token.equals("'") || token.equals("\"") || token.equals("[")) {

                    switch (token) {
                        case "[":
                        case "'":
                        case "\"":
                            string = true;
                            break;
                        case "`":
                            quoted = true;
                            break;
                        default:
                            log.error(String.format("token is in quotation marks but not flagged (quote: %s )", token));
                    }

                    StringBuilder tokenBuilder = new StringBuilder(token);
                    String breakToken = token.equals("[") ? "]" : token;
                    while (tokenizer.hasMoreTokens()) {
                        String tmpToken = tokenizer.nextToken();
                        tokenBuilder.append(tmpToken);

                        if (breakToken.equals(tmpToken)) {
                            break;
                        }
                    }

                    token = tokenBuilder.toString();
                }

                lowCaseToken = token.toLowerCase(Locale.ROOT);

                if (afterByOrSetOrFromOrSelect && ",".equals(this.token)) {
                    commaAfterByOrFromOrSelect();
                } else if (afterOn && ",".equals(this.token)) {
                    commaAfterOn();
                } else if ("(".equals(this.token)) {
                    openParen();
                } else if (")".equals(this.token)) {
                    closeParen();
                } else if (BEGIN_CLAUSES.contains(lowCaseToken)) {
                    beginNewClause();
                } else if (END_CLAUSES.contains(lowCaseToken)) {
                    endNewClause();
                } else if ("select".equals(lowCaseToken)) {
                    select();
                } else if (DML.contains(lowCaseToken)) {
                    updateOrInsertOrDelete();
                } else if ("values".equals(lowCaseToken)) {
                    values();
                } else if ("on".equals(lowCaseToken)) {
                    on();
                } else if (afterBetween && lowCaseToken.equals("and")) {
                    misc();
                    afterBetween = false;
                } else if (LOGICAL.contains(lowCaseToken)) {
                    logical();
                } else if (isWhitespace(this.token)) {
                    white();
                } else {
                    misc();
                }

                if (!isWhitespace(this.token)) {
                    lastLowCaseToken = lowCaseToken;
                }

            }

            return result.toString();
        }

        private void commaAfterOn() {
            out();
            indent--;
            newline();
            afterOn = false;
            afterByOrSetOrFromOrSelect = true;
        }

        private void commaAfterByOrFromOrSelect() {
            out();
            if (!afterSelect) {
                newline();
            }
        }

        private void logical() {
            if ("end".equals(lowCaseToken)) {
                indent--;
            }
            newline();
            out();
            beginLine = false;
        }

        private void on() {
            indent++;
            afterOn = true;
            newline();
            out();
            beginLine = false;
        }

        private void misc() {
            out();
            if ("between".equals(lowCaseToken)) {
                afterBetween = true;
            }
            if (afterInsert) {
                newline();
                afterInsert = false;
            } else {
                beginLine = false;
                if ("case".equals(lowCaseToken)) {
                    indent++;
                }
            }
        }

        private void white() {
            if (!beginLine) {
                result.append(" ");
            }
        }

        private void updateOrInsertOrDelete() {
            out();
            indent++;
            beginLine = false;
            if ("update".equals(lowCaseToken)) {
                newline();
            }
            if ("insert".equals(lowCaseToken)) {
                afterInsert = true;
            }
        }

        private void select() {
            out();
            indent++;
            newline();
            parenCounts.addLast(parensSinceSelect);
            afterByOrFromOrSelects.addLast(afterByOrSetOrFromOrSelect);
            parensSinceSelect = 0;
            afterByOrSetOrFromOrSelect = true;
            afterSelect = true;
        }

        private void out() {
            String highlightedToken = highlighting(token);
            result.append(highlightedToken);
        }

        private String highlighting(String token) {
            String result = token;
            if (string) {
                string = false;
                result = stringColorEscape + token + colorResetEscape;
            } else if (quoted) {
                quoted = false;
                result = quotedColorEscape + token + colorResetEscape;
            } else if (HIGHLIGHTING_KEYWORDS.contains(token.toUpperCase())) {
                result = keywordColorEscape + token.toUpperCase() + colorResetEscape;
            }

            return result;
        }

        private void endNewClause() {
            if (!afterBeginBeforeEnd) {
                indent--;
                if (afterOn) {
                    indent--;
                    afterOn = false;
                }
                newline();
            }
            out();
            if (!"union".equals(lowCaseToken)) {
                indent++;
            }
            newline();
            afterBeginBeforeEnd = false;
            afterByOrSetOrFromOrSelect = "by".equals(lowCaseToken)
                    || "set".equals(lowCaseToken)
                    || "from".equals(lowCaseToken);
            afterSelect = !afterSelect || afterByOrSetOrFromOrSelect;
        }

        private void beginNewClause() {
            if (!afterBeginBeforeEnd) {
                if (afterOn) {
                    indent--;
                    afterOn = false;
                }
                indent--;
                newline();
            }
            out();
            beginLine = false;
            afterBeginBeforeEnd = true;
        }

        private void values() {
            indent--;
            newline();
            out();
            indent++;
            newline();
        }

        private void closeParen() {
            parensSinceSelect--;
            if (parensSinceSelect < 0) {
                indent--;
                parensSinceSelect = parenCounts.removeLast();
                afterByOrSetOrFromOrSelect = afterByOrFromOrSelects.removeLast();
                afterSelect = !afterSelect || afterByOrSetOrFromOrSelect;
            }
            if (inFunction > 0) {
                inFunction--;
            } else {
                if (!afterByOrSetOrFromOrSelect) {
                    indent--;
                    newline();
                }
            }
            out();
            beginLine = false;
        }

        private void openParen() {
            if (isFunctionName(lastLowCaseToken) || inFunction > 0) {
                inFunction++;
            }
            beginLine = false;
            if (inFunction > 0) {
                out();
            } else {
                out();
                if (!afterByOrSetOrFromOrSelect) {
                    indent++;
                    newline();
                    beginLine = true;
                }
            }
            parensSinceSelect++;
        }

        private static boolean isFunctionName(String tok) {
            if (tok == null || tok.length() == 0) {
                return false;
            }

            final char begin = tok.charAt(0);
            final boolean isIdentifier = Character.isJavaIdentifierStart(begin) || '"' == begin;
            return isIdentifier &&
                    !LOGICAL.contains(tok) &&
                    !END_CLAUSES.contains(tok) &&
                    !QUANTIFIERS.contains(tok) &&
                    !DML.contains(tok) &&
                    !MISC.contains(tok);
        }

        private static boolean isWhitespace(String token) {
            return StringHelper.WHITESPACE.contains(token);
        }

        private void newline() {
            result.append(System.lineSeparator());
            result.append(INDENT_STRING.repeat(Math.max(0, indent)));
            beginLine = true;
        }
    }

}
