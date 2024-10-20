package dev.felix2000jp.microservicetemplatespring.appusers;

import dev.felix2000jp.microservicetemplatespring.config.auth.AppuserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/appusers")
class AppuserController {

    private final AppuserService appuserService;

    AppuserController(AppuserService appuserService) {
        this.appuserService = appuserService;
    }

    @GetMapping
    ResponseEntity<AppuserDto> find(@AuthenticationPrincipal AppuserPrincipal principal) {
        var body = appuserService.find(principal);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    ResponseEntity<Void> create(@Valid @RequestBody CreateAppuserDto createAppuserDto) {
        appuserService.create(createAppuserDto);
        var location = URI.create("/api/appusers");
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping
    ResponseEntity<Void> delete(@AuthenticationPrincipal AppuserPrincipal principal) {
        appuserService.delete(principal);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/token")
    ResponseEntity<String> token(Authentication authentication) {
        var token = appuserService.generateToken(authentication);
        return ResponseEntity.ok(token);
    }

}
