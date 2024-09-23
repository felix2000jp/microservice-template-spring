package dev.felix2000jp.microservicetemplatespring.appusers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppUserControllerIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    TestRestTemplate restTemplate;

    private AppUser appUser;
    private AppUserDto appUserDto;

    @BeforeEach
    void setUp() {
        appUser = new AppUser();
        appUser.setUsername("username");
        appUser.setPassword("password");
        appUser = appUserRepository.save(appUser);

        appUserDto = new AppUserDto(appUser.getId(), appUser.getUsername());
    }

    @AfterEach
    void tearDown() {
        appUserRepository.deleteAll();
    }

    @Test
    void getById_should_return_user_when_user_exists() {
        var actual = restTemplate.getForObject("/api/users/{id}", AppUserDto.class, appUser.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(appUserDto);
    }

    @Test
    void getByUsername_should_return_user_when_user_exists() {
        var actual = restTemplate.getForObject("/api/users/username/{username}", AppUserDto.class, appUser.getUsername());
        assertThat(actual).usingRecursiveComparison().isEqualTo(appUserDto);
    }

    @Test
    void create_should_save_user_when_user_not_exists() {
        var createAppUserDto = new CreateAppUserDto("new username", "new password");

        var location = restTemplate.postForLocation("/api/users", createAppUserDto, AppUserDto.class);

        var actual = restTemplate.getForObject(location, AppUserDto.class);
        var actualInDatabase = appUserRepository.findById(actual.id()).orElse(null);
        assertThat(actualInDatabase).isNotNull();
        assertThat(actualInDatabase.getUsername()).isEqualTo(createAppUserDto.username());
        assertThat(actualInDatabase.getPassword()).isEqualTo(createAppUserDto.password());
    }

    @Test
    void update_should_update_user_when_user_exists() {
        var updateAppUserDto = new UpdateAppUserDto("new username", "new password");

        restTemplate.put("/api/users/" + appUser.getId(), updateAppUserDto);

        var actualInDatabase = appUserRepository.findById(appUser.getId()).orElse(null);
        assertThat(actualInDatabase).isNotNull();
        assertThat(actualInDatabase.getUsername()).isEqualTo(updateAppUserDto.username());
        assertThat(actualInDatabase.getPassword()).isEqualTo(updateAppUserDto.password());
    }

    @Test
    void delete_should_delete_user_when_user_exists() {
        restTemplate.delete("/api/users/" + appUser.getId());

        var actualInDatabase = appUserRepository.findById(appUser.getId()).orElse(null);
        assertThat(actualInDatabase).isNull();
    }

}
