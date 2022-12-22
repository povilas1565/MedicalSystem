package security;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/api/auth/**";
    public static final String SECRET_KEY = "lifehacker"; //ключ шифрования/расшифрования токена
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 9000_000; //через 150 минут протухнет токен и сессия оборвется
}
