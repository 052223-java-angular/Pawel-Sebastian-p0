package com.revature.ecommerce_cli.services;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

import com.revature.ecommerce_cli.DAO.UserDAO;
import com.revature.ecommerce_cli.models.User;

public class UserServiceTest {
    
    @Mock 
    private UserDAO userDAO;

    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userService = new UserService(userDAO);
    }
    
    @Test
    public void testRegister() {
        String username = "testUser";
        String password = "testPassword";

        doNothing().when(userDAO).save(any(User.class));

        User result = userService.register(username, password);

        verify(userDAO, times(1)).save(any(User.class));

        assertEquals(username, result.getUsername());
    }

    @Test
    public void testIsValidUsername() {
        assertTrue(userService.isValidUsername("83920930"));
        assertFalse(userService.isValidUsername(".bobbbbbbb"));
        assertFalse(userService.isValidUsername("bobbbbbbb."));
        assertFalse(userService.isValidUsername("bobbb._bbbb"));
        assertFalse(userService.isValidUsername("bob"));
        assertFalse(userService.isValidUsername("timmm?timmmmm"));
    }
    
    @Test
    public void testIsUniqueUsername() {
        String existingUsername = "existingUser";
        String newUsername = "newUser";

        when(userDAO.findByUsername(existingUsername)).thenReturn(Optional.of(new User()));
        when(userDAO.findByUsername(newUsername)).thenReturn(Optional.empty());

        assertFalse(userService.isUniqueUsername(existingUsername));
        assertTrue(userService.isUniqueUsername(newUsername));
    }
    @Test
    public void testIsValidPassword() {
        assertTrue(userService.isValidPassword("Valid123"));
        assertFalse(userService.isValidPassword("invalid"));
        assertTrue(userService.isValidPassword("83920930"));
        assertFalse(userService.isValidPassword(".bobbbbbbb"));
        assertFalse(userService.isValidPassword("bobbbbbbb."));
        assertFalse(userService.isValidPassword("bobbb._bbbb"));
        assertFalse(userService.isValidPassword("bob"));
        assertFalse(userService.isValidPassword("timmm?timmmmm"));
    }
    
    @Test
    public void testIsSamePassword() {
        String password = "password123";
        String confirmPassword = "password123";
        String differentPassword = "differentPassword123";
    
        assertTrue(userService.isSamePassword(password, confirmPassword));
        assertFalse(userService.isSamePassword(password, differentPassword));
    }
}
