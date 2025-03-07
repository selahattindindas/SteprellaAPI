package com.Steprella.Steprella.entities.concretes;

import com.Steprella.Steprella.entities.abstracts.BaseEntity;
import com.Steprella.Steprella.services.enums.Gender;
import com.Steprella.Steprella.services.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "email" , unique = true)
    private String email;

    @Column(name = "role" , nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "gender" , nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Customer customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
