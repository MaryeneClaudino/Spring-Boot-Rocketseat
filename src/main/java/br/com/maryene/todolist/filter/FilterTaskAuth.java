package br.com.maryene.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.maryene.todolist.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var serveletPath = request.getServletPath();

        if (serveletPath.startsWith("/tasks/")) {
            // Pegando a autenticação(usuário e senha):
            var authorization = request.getHeader("Authorization");
            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);
            var authString = new String(authDecode);

            String[] credentials = authString.split(":");
            String userName = credentials[0];
            String password = credentials[1];

            // Validando usuário:
            var user = this.userRepository.findByUserName(userName);
            if (user == null) {
                response.sendError(401);
            } else {

                // Validando senha:
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(),
                        user.getPassword());
                if (passwordVerify.verified) {

                    request.setAttribute("idUser", user.getId());

                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}