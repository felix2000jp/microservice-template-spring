package dev.felix2000jp.microservicetemplatespring.appusers;

import dev.felix2000jp.microservicetemplatespring.config.auth.Scope;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
public class Appuser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = 3, max = 20)
    @NotBlank
    private String username;

    @Size(min = 5, max = 150)
    @NotBlank
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Scope scope;

    public Appuser() {
    }

    Appuser(String username, String password, Scope scope) {
        this.username = username;
        this.password = password;
        this.scope = scope;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Scope getScope() {
        return scope;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(scope);
    }

}
