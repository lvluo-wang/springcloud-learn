package exception;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

public class Messages {
    private static final String NULL_CODE = "__";
    private static final Pattern PATTERN = Pattern.compile("_");

    public static String defaultFormat(Enum<?> code, Object... args) {
        return MessageFormat.format(defaultMessage(code), args);
    }

    private static String defaultMessage(Enum<?> code) {
        if (NULL_CODE.equals(code.name())) {
            return "{0}";
        }
        AtomicInteger ai = new AtomicInteger();
        return PATTERN
          .splitAsStream(code.name())
          .map(a -> "".equals(a) ? "{" + ai.getAndIncrement() + "}" : a.toLowerCase())
          .collect(joining(" "));
    }
}