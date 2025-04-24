package com.mtvs.quizlog.domain.auth.model;

import com.mtvs.quizlog.domain.user.dto.LogInDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class AuthDetails implements UserDetails {

    private LogInDTO logInDTO;

    public AuthDetails() {}

    public AuthDetails(LogInDTO logInDTO) {
        this.logInDTO = logInDTO;
    }

    public LogInDTO getLogInDTO() {
        return logInDTO;
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() { return true; }

    // 잠긴 계정 확인
    @Override
    public boolean isAccountNonLocked() { return true; }

    // 탈퇴 계정 여부 표현
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    // 계정 비활성화 여부
    @Override
    public boolean isEnabled() { return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        logInDTO.getRole().forEach(role -> authorities.add(()-> role));

        return authorities;
    }

    @Override
    public String getPassword() {
        return logInDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return logInDTO.getEmail();
    }
}
