package com.technosdev.config;

import com.technosdev.entities.CompanyEntity;
import com.technosdev.entities.CurrentUserEntity;
import com.technosdev.entities.UserEntity;
import com.technosdev.services.CompanyService;
import com.technosdev.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompanyService companyService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserEntity userEntity = userService.getUserPermission(login);
        
        if (userEntity != null){
            boolean isPasswordValid = passwordEncoder.matches(password, userEntity.getPassword());

            CompanyEntity companyEntity = companyService.findCompanyByUserId(userEntity.getId());

            if (isPasswordValid){
                ArrayList<String> roles = new ArrayList<String>();

                if(!userEntity.getRoles().isEmpty()) {
                    for (int i = 0; i <= userEntity.getRoles().size() - 1 ; i++){
                        roles.add(userEntity.getRoles().get(i).getName());
                    }
                }

                CurrentUserEntity currentUserEntityAuthentication = new CurrentUserEntity(
                    userEntity.getId(),
                    userEntity.getName(),
                    userEntity.getEmail(),
                    roles, companyEntity
                );

                return new CurrentUserAuthentication(currentUserEntityAuthentication);
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
