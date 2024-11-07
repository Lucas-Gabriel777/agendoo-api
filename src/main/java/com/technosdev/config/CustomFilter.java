package com.technosdev.config;

import com.technosdev.entities.CompanyEntity;
import com.technosdev.entities.CurrentUserEntity;
import com.technosdev.entities.TokenService;
import com.technosdev.entities.UserEntity;
import com.technosdev.services.CompanyService;
import com.technosdev.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;


@Component
public class CustomFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        String tokenHeader = request.getHeader("token");

        if (tokenHeader != null) {
            var userToken = tokenService.validateToken(tokenHeader);

            UserEntity userEntity = userService.getUserPermission(userToken);
            ArrayList<String> roles = new ArrayList<String>();

            CompanyEntity companyEntity = companyService.findCompanyByUserId(userEntity.getId());

            if(!userEntity.getRoles().isEmpty()) {
                for (int i = 0; i <= userEntity.getRoles().size() - 1 ; i++){
                    roles.add(userEntity.getRoles().get(i).getName());
                }
            }

            CurrentUserEntity currentUserEntityAuthentication = new CurrentUserEntity(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                roles , companyEntity
            );

            var authentication = new CurrentUserAuthentication(currentUserEntityAuthentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request , response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;

        return authHeader.replace("Bearer " , "");
    }
}

/*
package com.technosdev.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class CustomFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String secretHeader = request.getHeader("x-secret");

        if (secretHeader != null){
            if (secretHeader.equals("secr3t")){
                Authentication authentication = new UsernamePasswordAuthenticationToken("Muito secreto" , null , List.of(new SimpleGrantedAuthority("ADMIN")));

                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request , response);

    }
}

 */
