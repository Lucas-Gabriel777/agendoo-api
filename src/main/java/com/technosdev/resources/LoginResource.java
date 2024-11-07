package com.technosdev.resources;

import com.technosdev.config.CurrentUserAuthentication;
import com.technosdev.entities.CompanyEntity;
import com.technosdev.entities.CurrentUserEntity;
import com.technosdev.entities.TokenService;
import com.technosdev.entities.UserEntity;
import com.technosdev.entities.body.AuthenticationDTD;
import com.technosdev.entities.response.LoginResponse;
import com.technosdev.services.CompanyService;
import com.technosdev.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("auth")
public class LoginResource {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CompanyService companyService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTD data){

        var userAuthenticate = ValidLogin(data.login() , data.password());
        if (userAuthenticate.isAuthenticated()){
            var token = tokenService.generateToken((CurrentUserEntity) userAuthenticate.getPrincipal());
            return ResponseEntity.ok(new LoginResponse(token , (CurrentUserEntity) userAuthenticate.getPrincipal()));
        }

        return ResponseEntity.badRequest().build();
    }

    public Authentication ValidLogin(String login , String password){

        UserEntity userEntity = userService.getUserPermission(login);

        if (userEntity != null){
            boolean isPasswordValid = passwordEncoder.matches(password, userEntity.getPassword());

            if (isPasswordValid){

                ArrayList<String> roles = new ArrayList<String>();

                if(!userEntity.getRoles().isEmpty()) {
                    for (int i = 0; i <= userEntity.getRoles().size() - 1 ; i++){
                        roles.add(userEntity.getRoles().get(i).getName());
                    }
                }

                CompanyEntity companyEntity = companyService.findCompanyByUserId(userEntity.getId());

                CurrentUserEntity currentUserEntityAuthentication = new CurrentUserEntity(
                        userEntity.getId(),
                        userEntity.getName(),
                        userEntity.getEmail(),
                        roles , companyEntity
                );

                return new CurrentUserAuthentication(currentUserEntityAuthentication);

            }
        }

        return null;
    }

}
