package dev.felix2000jp.microservicetemplatespring.appusers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
class AppUserController {

    private final AppUserMapper appUserMapper;
    private final AppUserService appUserService;

    AppUserController(AppUserMapper appUserMapper, AppUserService appUserService) {
        this.appUserMapper = appUserMapper;
        this.appUserService = appUserService;
    }

    @GetMapping("/{id}")
    ResponseEntity<AppUserDto> getById(@PathVariable UUID id) {
        var appUser = appUserService.findById(id);
        var body = appUserMapper.toDto(appUser);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/username/{username}")
    ResponseEntity<AppUserDto> getByUsername(@PathVariable String username) {
        var appUser = appUserService.findByUsername(username);
        var body = appUserMapper.toDto(appUser);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    ResponseEntity<Void> create(@Valid @RequestBody CreateAppUserDto createAppUserDto) {
        var appUserToCreate = appUserMapper.toEntity(createAppUserDto);
        var appUser = appUserService.create(appUserToCreate);
        var location = URI.create("/api/users/" + appUser.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateAppUserDto updateAppUserDto) {
        var appUserToUpdate = appUserMapper.toEntity(id, updateAppUserDto);
        appUserService.update(appUserToUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        appUserService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
