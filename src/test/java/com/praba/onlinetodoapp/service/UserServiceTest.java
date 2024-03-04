package com.praba.onlinetodoapp.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.praba.onlinetodoapp.entity.User;
import com.praba.onlinetodoapp.repository.UserRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {
	
	@InjectMocks
	UserServiceImpl userService;
	
    @Mock
    private UserRepository userRepository;
    
    User user;
    User testUser;
	Long USER_ID= (long) 1234;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		user = new User();
		user.setId(USER_ID);
		user.setFirstName("TestFN");
		user.setLastName("TestLN");
		user.setEmail("test@test.com");
		user.setPassword("password");
		user.setTodoList(null);
		
		testUser = new User();
		testUser.setFirstName("TestFN-testUser");
		}

	@DisplayName("Test findByEmail method making call to Repository")
	@Test
	public void findByEmailTest() {

		when(userRepository.findByEmail(anyString())).thenReturn(user);
		User resultUser = (userService.findByEmail(anyString()));
		String resultEmailFromRepo = (userService.findByEmail(anyString())).getEmail();
		assertNotNull(resultUser);
		assertEquals("test@test.com", resultEmailFromRepo);
	}
	
	@DisplayName("Test findById method making call to Repository")
	@Test
	public void findById() {

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		User resultUser = (userService.findById(anyLong()));
		Long userIdfromRepo = (userService.findById(anyLong())).getId();
		assertNotNull(resultUser);
		assertEquals(USER_ID, userIdfromRepo);
	}
	
	@DisplayName("Test Save method making call to Repository")
	@Test
	public void saveMethodTest() {

		when(userRepository.save(testUser)).thenReturn(user);

		User resultUser = (userService.save(testUser));

		assertNotNull(user);
		assertEquals("TestFN", resultUser.getFirstName());
		assertEquals("TestLN", resultUser.getLastName());
		assertEquals("test@test.com", resultUser.getEmail());
		assertEquals("password", resultUser.getPassword());
	}
	
	@DisplayName("Test deleteMethodTest method making call to Repository")
	@Test
	public void deleteMethodTest() {

		doNothing().when(userRepository).delete(testUser);
		userService.delete(testUser);
		verify(userRepository, times(1)).delete(testUser);
	}

}