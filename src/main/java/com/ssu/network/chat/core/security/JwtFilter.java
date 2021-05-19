package com.ssu.network.chat.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.network.chat.core.response.error.ErrorResponse;
import com.ssu.network.chat.core.response.error.ErrorSummary;
import com.ssu.network.chat.core.response.error.ExceptionCode;
import com.ssu.network.chat.core.security.exception.NotBearerTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtProvider.checkingAndParsingBearer(request.getHeader("Authorization"));
            if (token != null) {
                Claims claims = jwtProvider.getClaimsFromToken(token);
                if (claims != null)
                    SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(claims));
            }
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            sendErrorMessage(response, ExceptionCode.MALFORMED_TOKEN);
        } catch (SignatureException e) {
            sendErrorMessage(response, ExceptionCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            sendErrorMessage(response, ExceptionCode.EXPIRE_TOKEN);
        } catch (NotBearerTokenException e) {
            sendErrorMessage(response, ExceptionCode.NOT_BEARER_FORMAT);
        }
    }

    private void sendErrorMessage(HttpServletResponse res, ExceptionCode code) throws IOException {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.FORBIDDEN.value(), new ErrorSummary(code))));

    }
}
