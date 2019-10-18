package ru.itis.longpollingtokens.security.filters;

import jdk.nashorn.internal.parser.Token;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.itis.longpollingtokens.dto.TokenDto;
import ru.itis.longpollingtokens.security.authentication.JwtAuthentication;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

public class JwtAuthenticationFilter implements Filter {
    // константа, содержит название токена
    private final static String AUTH_HEADER = "AUTH";



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // вытаскиваем запрос
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // вытаскиваем заголовок с токеном
        String tokenJSON = request.getHeader(AUTH_HEADER);
        // если заголовок содержит что-либо
        if (tokenJSON != null) {
            // создаем объект токен-аутентификации
            JwtAuthentication authentication = new JwtAuthentication();

            JSONObject jsonTokenObject = new JSONObject(tokenJSON);

            TokenDto token = TokenDto.builder()
                    .accessToken(jsonTokenObject.getString("accessToken"))
                    .accessToken(jsonTokenObject.getString("refreshToken"))
                    .accessExpiresIn(Long.parseLong(jsonTokenObject.getString("accessExpiresIn")))
                    .build();

            // в него кладем токен
            authentication.setToken(token);

            // отдаем контексту
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // отдаем запрос дальше (его встретит либо другой фильтр, либо что-то еще)
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
