package dev.felix2000jp.microservicetemplatespring.appusers;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class AppUserService {

    private final AppUserRepository appUserRepository;

    AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    AppUser findById(UUID id) {
        return appUserRepository
                .findById(id)
                .orElseThrow(AppUserNotFoundException::new);
    }

    AppUser findByUsername(String username) {
        return appUserRepository
                .findByUsername(username)
                .orElseThrow(AppUserNotFoundException::new);
    }

    AppUser create(AppUser appUser) {
        var username = appUser.getUsername();
        var doesUsernameExist = appUserRepository.existsByUsername(username);

        if (doesUsernameExist) {
            throw new AppUserConflictException();
        }

        return appUserRepository.save(appUser);
    }

    AppUser update(AppUser appUser) {
        var appUserToUpdate = appUserRepository
                .findById(appUser.getId())
                .orElseThrow(AppUserNotFoundException::new);

        var newUsername = appUser.getUsername();
        var oldUsername = appUserToUpdate.getUsername();

        var isUsernameNew = !newUsername.equals(oldUsername);
        var doesUsernameExist = appUserRepository.existsByUsername(newUsername);

        if (isUsernameNew && doesUsernameExist) {
            throw new AppUserConflictException();
        }

        appUserToUpdate.setUsername(appUser.getUsername());
        appUserToUpdate.setPassword(appUser.getPassword());
        return appUserRepository.save(appUserToUpdate);
    }

    AppUser delete(UUID id) {
        var appUserToDelete = appUserRepository
                .findById(id)
                .orElseThrow(AppUserNotFoundException::new);

        appUserRepository.delete(appUserToDelete);
        return appUserToDelete;
    }

}
