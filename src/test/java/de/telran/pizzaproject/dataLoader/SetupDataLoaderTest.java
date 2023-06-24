package de.telran.pizzaproject.dataLoader;

import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class SetupDataLoaderTest {

    @Mock
    private UserService userService;
    @Mock
    private ContextRefreshedEvent event;

    private SetupDataLoader setupDataLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setupDataLoader = new SetupDataLoader(userService);
    }

    @Test
    void onApplicationEvent_AlreadySetup_NoUserCreated() {
        setupDataLoader.alreadySetup = true;
        setupDataLoader.onApplicationEvent(event);

        verify(userService, never()).findUserByUsername(any());
        verify(userService, never()).addUser(any(User.class));
    }

    @Test
    void onApplicationEvent_FirstRun_CreateUsers() {
        setupDataLoader.alreadySetup = false;
        when(userService.findUserByUsername(anyString())).thenReturn(Optional.empty());

        setupDataLoader.onApplicationEvent(event);

        verify(userService, times(1)).findUserByUsername(anyString());
        verify(userService, times(1)).addUser(any(User.class));
    }

    @Test
    void createUserIfNotFound_UserAlreadyExists_NoUserCreated() {
        setupDataLoader.alreadySetup = false;
        String name = "admin";
        User userInDB = new User();
        when(userService.findUserByUsername(name)).thenReturn(Optional.of(userInDB));

        setupDataLoader.onApplicationEvent(event);

        verify(userService, never()).addUser(any(User.class));
    }

    @Test
    void createUserIfNotFound_UserDoesNotExist_UserCreated() {
        setupDataLoader.alreadySetup = false;
        when(userService.findUserByUsername("admin")).thenReturn(Optional.empty());

        setupDataLoader.onApplicationEvent(event);

        verify(userService).addUser(any(User.class));
    }
}