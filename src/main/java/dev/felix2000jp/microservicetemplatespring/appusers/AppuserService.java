package dev.felix2000jp.microservicetemplatespring.appusers;

import dev.felix2000jp.microservicetemplatespring.config.auth.AppuserPrincipal;
import dev.felix2000jp.microservicetemplatespring.config.auth.Scope;
import dev.felix2000jp.microservicetemplatespring.exceptions.HttpConflictException;
import dev.felix2000jp.microservicetemplatespring.exceptions.HttpNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
class AppuserService implements UserDetailsService {

    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder passwordEncoder;
    private final AppuserMapper appuserMapper;
    private final AppuserRepository appuserRepository;

    AppuserService(
            JwtEncoder jwtEncoder,
            PasswordEncoder passwordEncoder,
            AppuserMapper appuserMapper,
            AppuserRepository appuserRepository
    ) {
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
        this.appuserMapper = appuserMapper;
        this.appuserRepository = appuserRepository;
    }

    AppuserDto find(AppuserPrincipal principal) {
        var appuser = appuserRepository
                .findById(principal.id())
                .orElseThrow(HttpNotFoundException::new);

        return appuserMapper.toDto(appuser);
    }

    AppuserDto create(CreateAppuserDto createAppuserDto) {
        var doesUsernameExist = appuserRepository.existsByUsername(createAppuserDto.username());

        if (doesUsernameExist) {
            throw new HttpConflictException();
        }

        var appuserCreated = new Appuser(
                createAppuserDto.username(),
                passwordEncoder.encode(createAppuserDto.password()),
                Scope.APPLICATION
        );
        var appuserSaved = appuserRepository.save(appuserCreated);
        return appuserMapper.toDto(appuserSaved);
    }

    AppuserDto delete(AppuserPrincipal principal) {
        var userToDelete = appuserRepository
                .findById(principal.id())
                .orElseThrow(HttpNotFoundException::new);

        appuserRepository.delete(userToDelete);
        return appuserMapper.toDto(userToDelete);
    }

    String generateToken(Authentication authentication) {
        var now = Instant.now();
        var expiration = now.plus(12, ChronoUnit.HOURS);
        var appuserAuthenticated = (Appuser) authentication.getPrincipal();

        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .claim("appuser_username", appuserAuthenticated.getUsername())
                .claim("appuser_scope", appuserAuthenticated.getScope())
                .subject(appuserAuthenticated.getId().toString())
                .issuedAt(now)
                .expiresAt(expiration)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public Appuser loadUserByUsername(String username) {
        return appuserRepository
                .findByUsername(username)
                .orElseThrow(HttpNotFoundException::new);
    }
}
