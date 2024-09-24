package dev.felix2000jp.microservicetemplatespring.appusers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppUserService appUserService;

    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser();
        appUser.setId(UUID.randomUUID());
        appUser.setUsername("username");
        appUser.setPassword("password");
    }

    @Test
    void findById_should_return_user_when_user_exists() {
        when(appUserRepository.findById(appUser.getId())).thenReturn(Optional.of(appUser));

        var actual = appUserService.findById(appUser.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(appUser);
    }

    @Test
    void findById_should_throw_when_user_not_exists() {
        when(appUserRepository.findById(appUser.getId())).thenReturn(Optional.empty());

        var actual = catchThrowable(() -> appUserService.findById(appUser.getId()));

        assertThat(actual).isInstanceOf(AppUserNotFoundException.class);
    }

    @Test
    void findByUsername_should_return_user_when_user_exists() {
        when(appUserRepository.findByUsername(appUser.getUsername())).thenReturn(Optional.of(appUser));

        var actual = appUserService.findByUsername(appUser.getUsername());

        assertThat(actual).usingRecursiveComparison().isEqualTo(appUser);
    }

    @Test
    void findByUsername_should_throw_when_user_not_exists() {
        when(appUserRepository.findByUsername(appUser.getUsername())).thenReturn(Optional.empty());

        var actual = catchThrowable(() -> appUserService.findByUsername(appUser.getUsername()));

        assertThat(actual).isInstanceOf(AppUserNotFoundException.class);
    }

    @Test
    void create_should_return_user_when_username_unique() {
        var appUserToCreate = new AppUser();
        appUserToCreate.setUsername(appUser.getUsername());
        appUserToCreate.setPassword(appUser.getPassword());

        when(appUserRepository.existsByUsername(appUserToCreate.getUsername())).thenReturn(false);
        when(appUserRepository.save(appUserToCreate)).thenReturn(appUser);

        var actual = appUserService.create(appUserToCreate);

        assertThat(actual).usingRecursiveComparison().isEqualTo(appUser);
    }

    @Test
    void create_should_throw_when_username_not_unique() {
        var appUserToCreate = new AppUser();
        appUserToCreate.setUsername(appUser.getUsername());
        appUserToCreate.setPassword(appUser.getUsername());

        when(appUserRepository.existsByUsername(appUserToCreate.getUsername())).thenReturn(true);

        var actual = catchThrowable(() -> appUserService.create(appUserToCreate));

        assertThat(actual).isInstanceOf(AppUserConflictException.class);
    }

    @Test
    void update_should_return_user_when_user_exists_and_username_not_new() {
        var updatedAppUser = new AppUser();
        updatedAppUser.setId(appUser.getId());
        updatedAppUser.setUsername(appUser.getUsername());
        updatedAppUser.setPassword("new password");

        when(appUserRepository.findById(updatedAppUser.getId())).thenReturn(Optional.of(appUser));
        when(appUserRepository.existsByUsername(updatedAppUser.getUsername())).thenReturn(true);
        when(appUserRepository.save(any(AppUser.class))).thenReturn(updatedAppUser);

        var actual = appUserService.update(updatedAppUser);

        assertThat(actual).usingRecursiveComparison().isEqualTo(updatedAppUser);
    }

    @Test
    void update_should_return_user_when_user_exists_and_username_new_and_unique() {
        var updatedAppUser = new AppUser();
        updatedAppUser.setId(appUser.getId());
        updatedAppUser.setUsername("new username");
        updatedAppUser.setPassword("new password");

        when(appUserRepository.findById(updatedAppUser.getId())).thenReturn(Optional.of(appUser));
        when(appUserRepository.existsByUsername(updatedAppUser.getUsername())).thenReturn(false);
        when(appUserRepository.save(any(AppUser.class))).thenReturn(updatedAppUser);

        var actual = appUserService.update(updatedAppUser);

        assertThat(actual).usingRecursiveComparison().isEqualTo(updatedAppUser);
    }

    @Test
    void update_should_throw_when_user_not_exists() {
        var updatedAppUser = new AppUser();
        updatedAppUser.setId(appUser.getId());
        updatedAppUser.setUsername("new username");
        updatedAppUser.setPassword("new password");

        when(appUserRepository.findById(updatedAppUser.getId())).thenReturn(Optional.empty());

        var actual = catchThrowable(() -> appUserService.update(appUser));

        assertThat(actual).isInstanceOf(AppUserNotFoundException.class);
    }

    @Test
    void update_Should_throw_when_username_new_and_not_unique() {
        var updatedAppUser = new AppUser();
        updatedAppUser.setId(appUser.getId());
        updatedAppUser.setUsername("new username");
        updatedAppUser.setPassword("new password");

        when(appUserRepository.findById(updatedAppUser.getId())).thenReturn(Optional.of(appUser));
        when(appUserRepository.existsByUsername(updatedAppUser.getUsername())).thenReturn(true);

        var actual = catchThrowable(() -> appUserService.update(updatedAppUser));

        assertThat(actual).isInstanceOf(AppUserConflictException.class);
    }

    @Test
    void delete_should_return_user_when_user_exists() {
        when(appUserRepository.findById(appUser.getId())).thenReturn(Optional.of(appUser));

        var actual = appUserService.delete(appUser.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(appUser);
    }

    @Test
    void delete_should_throw_when_user_not_exists() {
        when(appUserRepository.findById(appUser.getId())).thenReturn(Optional.empty());

        var actual = catchThrowable(() -> appUserService.delete(appUser.getId()));

        assertThat(actual).isInstanceOf(AppUserNotFoundException.class);
    }

}