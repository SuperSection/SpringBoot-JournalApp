package com.supersection.journalApp.service;

import com.supersection.journalApp.enitity.UserEntity;
import com.supersection.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplementationTests {

    @InjectMocks
    private UserDetailsServiceImplementation userDetailsService;

    @Mock
    private UserRepository userRepository;

//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    void loadUserByUsernameTest() {
        when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(UserEntity.builder()
                            .username("Pagol")
                            .password("password")
                            .roles(new ArrayList<>())
                            .build());
        UserDetails user = userDetailsService.loadUserByUsername("Pagol");
        Assertions.assertNotNull(user);
    }
}
