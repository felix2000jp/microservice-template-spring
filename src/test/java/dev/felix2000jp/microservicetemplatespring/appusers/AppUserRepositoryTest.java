package dev.felix2000jp.microservicetemplatespring.appusers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppUserRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

    @Autowired
    private AppUserRepository appUserRepository;

    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser();
        appUser.setUsername("username");
        appUser.setPassword("password");
        appUser = appUserRepository.save(appUser);
    }

    @AfterEach
    void tearDown() {
        appUserRepository.deleteAll();
    }

    @Test
    void existsByUsername_should_return_true_when_username_exists() {
        var actual = appUserRepository.existsByUsername(appUser.getUsername());

        assertThat(actual).isTrue();
    }

    @Test
    void existsByUsername_should_return_false_when_username_not_exists() {
        var actual = appUserRepository.existsByUsername("some other username that does not exist");

        assertThat(actual).isFalse();
    }

    @Test
    void findByUsername_should_return_user_when_username_exists() {
        var actual = appUserRepository.findByUsername(appUser.getUsername());

        assertThat(actual).usingRecursiveComparison().isEqualTo(Optional.of(appUser));
    }

    @Test
    void findByUsername_Should_return_empty_when_username_not_exists() {
        var actual = appUserRepository.findByUsername("some other username that does not exist");

        assertThat(actual).usingRecursiveComparison().isEqualTo(Optional.empty());
    }

}