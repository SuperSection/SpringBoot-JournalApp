package com.supersection.journalApp.service;

import com.supersection.journalApp.enitity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void testFindJournalsByUsername() {
        UserEntity user = userService.findByUsername("Pagol");
        assertFalse(user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(UserEntity user) {
        assertTrue(userService.saveNewUser(user));
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "3,4,7"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }
}
