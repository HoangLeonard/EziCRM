package com.ezicrm.eziCRM.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class User{

    private final String username = "chungminhhoa1998";

    public String getPassword() {
        String hashPass = this.password;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        hashPass = passwordEncoder.encode(hashPass);
        return hashPass;
    }

    @Setter
    private String password = "Cmh771998@";

}
