package exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;

public class BaseException extends RuntimeException {
    private final Enum<?> code;
    private final Object[] args;
    private String message;

    private static final String DEFAULT_PREFIX = "error.";

    public BaseException(@NonNull Enum<?> code, Object... args) {
        this(null, code, args);
    }

    public BaseException(Throwable ex, Enum<?> code, Object... args) {
        this(Messages.defaultFormat(code, args), ex, code, args);
    }

    private BaseException(String message, Throwable ex, @NonNull Enum<?> code, Object... args) {
        super(message, ex);
        this.code = code;
        this.args = args;
    }

    public void convertMessage(MessageSource messageSource) {
        this.message = messageSource.getMessage(DEFAULT_PREFIX  +code.name(), args, null, LocaleContextHolder.getLocale());
    }

    @Override
    public String getMessage() {
        if (message != null) {
            return message;
        }
        return super.getMessage();
    }

    public String geCodeName() {
        return code.name();
    }
}
