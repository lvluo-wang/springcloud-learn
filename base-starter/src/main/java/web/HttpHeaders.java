package web;

public class HttpHeaders {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_CAPTCHA = "X-CAPTCHA";
    public static final String HEADER_SMS = "X-SMS";
    public static final String HEADER_USER = "X-USER";
    public static final String HEADER_USER_BETA = "X-Use-Beta";
    public static final String HEADER_PASSWORD = "X-PASSWORD";
    public static final String FORWARDED_FOR_HEADER = "X-Forwarded-For";
    public static final String HEADER_CLIENT = "X-CLIENT";

    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_INTERNAL_SERVER_ERROR = 500;

}
