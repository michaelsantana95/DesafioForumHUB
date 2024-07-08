package com.example.challenge_forum_hub.infra.security;

import com.example.challenge_forum_hub.infra.exception.Erro400Exception;
import com.example.challenge_forum_hub.infra.exception.Erro401Exception;
import com.example.challenge_forum_hub.infra.exception.Erro404Exception;
import com.example.challenge_forum_hub.infra.exception.Erro500Exception;
import com.example.challenge_forum_hub.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/usuarios/login") || requestURI.equals("/usuarios/novo") ||
                requestURI.startsWith("/v3/api-docs") || requestURI.equals("/swagger-ui.html") ||
                requestURI.startsWith("/swagger-ui")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            var tokenJWT = recuperarToken(request);
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = usuarioRepository.findByEmail(subject);
            if (usuario == null) throw new Erro401Exception("Token inválido.");
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Erro400Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(e.getMessage());
        } catch (Erro401Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(e.getMessage());
        } catch (Erro500Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(e.getMessage());
        }
    }

    private String recuperarToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new Erro400Exception("Header 'Authorization' ausente ou inválido.");
        }
        return authorizationHeader.replace("Bearer ", "");
    }

}
