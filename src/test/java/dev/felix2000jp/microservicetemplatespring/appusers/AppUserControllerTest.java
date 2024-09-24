package dev.felix2000jp.microservicetemplatespring.appusers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppUserController.class)
class AppUserControllerTest {

    @MockBean
    private AppUserService appUserService;

    @SpyBean(AppUserMapperImpl.class)
    private AppUserMapper appUserMapper;

    @Autowired
    private MockMvc mockMvc;

    private AppUser appUser;
    private String appUserDtoJson;

    @BeforeEach
    void setUp() {
        appUser = new AppUser();
        appUser.setId(UUID.randomUUID());
        appUser.setUsername("username");
        appUser.setPassword("password");

        appUserDtoJson = String.format("""
                {
                    "id": "%s",
                    "username": "%s"
                }
                """, appUser.getId(), appUser.getUsername());
    }

    @Test
    void getById_should_return_200_and_user_when_user_exists() throws Exception {
        when(appUserService.findById(appUser.getId())).thenReturn(appUser);

        mockMvc
                .perform(get("/api/users/" + appUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(appUserDtoJson));
    }

    @Test
    void getById_should_return_404_when_user_not_exists() throws Exception {
        when(appUserService.findById(appUser.getId())).thenThrow(new AppUserNotFoundException());

        mockMvc
                .perform(get("/api/users/" + appUser.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Not Found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("User was not found"));

    }

    @Test
    void getByUsername_should_return_200_and_user_when_user_exists() throws Exception {
        when(appUserService.findByUsername(appUser.getUsername())).thenReturn(appUser);

        mockMvc
                .perform(get("/api/users/username/" + appUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().json(appUserDtoJson));
    }

    @Test
    void getByUsername_should_return_404_when_user_not_exists() throws Exception {
        when(appUserService.findByUsername(appUser.getUsername())).thenThrow(new AppUserNotFoundException());

        mockMvc
                .perform(get("/api/users/username/" + appUser.getUsername()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Not Found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("User was not found"));
    }

    @Test
    void create_should_return_201_and_location_when_user_created() throws Exception {
        when(appUserService.create(any(AppUser.class))).thenReturn(appUser);

        var bodyJson = String.format("""
                {
                    "username": "%s",
                    "password": "%s"
                }
                """, appUser.getUsername(), appUser.getPassword());

        mockMvc
                .perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(bodyJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/users/" + appUser.getId()));
    }

    @Test
    void create_should_return_409_when_user_not_unique() throws Exception {
        when(appUserService.create(any(AppUser.class))).thenThrow(new AppUserConflictException());

        var bodyJson = String.format("""
                {
                    "username": "%s",
                    "password": "%s"
                }
                """, appUser.getUsername(), appUser.getPassword());

        mockMvc
                .perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(bodyJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Conflict"))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.detail").value("User already exists"));
    }

    @Test
    void update_should_return_204_when_user_updated() throws Exception {
        when(appUserService.update(any(AppUser.class))).thenReturn(appUser);

        var bodyJson = String.format("""
                {
                    "username": "%s",
                    "password": "%s"
                }
                """, appUser.getUsername(), appUser.getPassword());

        mockMvc
                .perform(put("/api/users/" + appUser.getId()).contentType(MediaType.APPLICATION_JSON).content(bodyJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void update_should_return_404_when_user_not_exists() throws Exception {
        when(appUserService.update(any(AppUser.class))).thenThrow(new AppUserNotFoundException());

        var bodyJson = String.format("""
                {
                    "username": "%s",
                    "password": "%s"
                }
                """, appUser.getUsername(), appUser.getPassword());

        mockMvc
                .perform(put("/api/users/" + appUser.getId()).contentType(MediaType.APPLICATION_JSON).content(bodyJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Not Found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("User was not found"));
    }

    @Test
    void update_should_return_409_when_user_not_unique() throws Exception {
        when(appUserService.update(any(AppUser.class))).thenThrow(new AppUserConflictException());

        var bodyJson = String.format("""
                {
                    "username": "%s",
                    "password": "%s"
                }
                """, appUser.getUsername(), appUser.getPassword());

        mockMvc
                .perform(put("/api/users/" + appUser.getId()).contentType(MediaType.APPLICATION_JSON).content(bodyJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Conflict"))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.detail").value("User already exists"));
    }


    @Test
    void delete_should_return_204_when_user_deleted() throws Exception {
        mockMvc
                .perform(delete("/api/users/" + appUser.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_should_return_404_when_user_not_exists() throws Exception {
        when(appUserService.delete(appUser.getId())).thenThrow(new AppUserNotFoundException());

        mockMvc
                .perform(delete("/api/users/" + appUser.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Not Found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("User was not found"));
    }

}
