package security.jwt;

import io.jsonwebtoken.*;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import security.SecurityConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@Component
public class JWTProvider {
    public static final Logger LOG = LoggerFactory.getLogger(JWTProvider.class);

    // Нам надо сделать 3 метода:
    // Для создания токена
    // Для валидации токена
    // Для получения id пользователя из токена
    public String generateToken(Authentication authentication) {
        //https://russianblogs.com/article/7437378565/
        //https://habr.com/ru/post/482552/
        User user = (User) authentication.getPrincipal(); //возвращает информацию об идентификаторе объекта. Приводим ее к User, UserDetails

        Date now = new Date(System.currentTimeMillis());  //текущее время
        Date expirationTime = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME); //через 15 минут токен умрет

        String userId = Long.toString(user.getId()); //не забываем у нас возвращается Long а нужен String

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", userId);
        claimsMap.put("username", user.getEmail());
        claimsMap.put("phone", user.getPhone());
        claimsMap.put("role", user.getRole());

        //строим токен
        String token = Jwts.builder()
                .setSubject(userId) //для кого токен?)
                .addClaims(claimsMap) //добавим мапу с claims
                .setIssuedAt(now) // время начала жизни токена т.е. когда он был сгенерирован - сейчас
                .setExpiration(expirationTime) // а когда он закончится? через expirationTime
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY) //добавим шифрование по секретному слову
                .compact();

        return token;
    }

    public boolean validateToken(String token) {
        //ALt + CTRL + T - поместить блок кода в блок if, try и т.д.
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET_KEY) //ключ для расшифровки токена
                    .parseClaimsJws(token); //вытаскиваем клеймсы т.е. вернет нам настоящие данные которые мы полжили в мапу
            return true;
        } catch (SignatureException | //если ошибка в сигнатуре токена (мб ключ не подходит)
                MalformedJwtException | //не хвататет каких-то компонентов
                ExpiredJwtException | //если он протух
                UnsupportedJwtException | //неподдерживаемый тип токена
                IllegalArgumentException exception) {

            LOG.error(exception.getMessage());
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        String userId = (String) claims.get("id");
        return Long.parseLong(userId);
    }
}

