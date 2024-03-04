package com.praba.onlinetodoapp.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.praba.onlinetodoapp.entity.Todo;
import com.praba.onlinetodoapp.entity.User;
import com.praba.onlinetodoapp.repository.TodoRepository;
import com.praba.onlinetodoapp.repository.UserRepository;
import com.praba.onlinetodoapp.service.TodoServiceImpl;
import com.praba.onlinetodoapp.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserApiControllerTest {

	@Autowired
	UserApiController userApiController;

    @Autowired
    private MockMvc mockMvc;

	@MockBean
	UserRepository userRepository;

	@MockBean
	TodoRepository todoRepository;

	@MockBean
	UserServiceImpl userServiceImpl;

	@MockBean
	TodoServiceImpl todoServiceImpl;

	User user;
	Todo todo;
	MultiValueMap<String, String> body;
	Long USER_ID = (long) 1234;
	Long TODO_ID = (long) 1234;

	private static ObjectMapper MAPPER ;

	@BeforeEach
	void setUp() throws Exception {

		todo = new Todo();
		todo.setId(TODO_ID);
		todo.setDescription("Test");
		todo.setTitle("titleTEST");
		todo.setTargetDate("2022-01-01");
		todo.setStatus("Completed");
		todo.setLastUpdatedDate("2022-01-01");

		List<Todo> todoList = new ArrayList<>();
		todoList.add(todo);

		user = new User();
		user.setId(USER_ID);
		user.setFirstName("TestFN");
		user.setLastName("TestLN");
		user.setEmail("test@test.com");
		user.setPassword("password");
		user.setTodoList(todoList);

		body  =new LinkedMultiValueMap<>();
		body.put("title",Collections.singletonList("titleTEST"));
		body.put("description",Collections.singletonList("Test"));
		body.put("status",Collections.singletonList("Completed"));
		body.put("targetDate",Collections.singletonList("2022-01-01"));
		body.put("lastUpdatedDate",Collections.singletonList("2022-01-01"));


		MAPPER = new ObjectMapper()
			    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			    .registerModule(new JavaTimeModule());

	}

	@DisplayName("Test GET /userId Api ")
	@Test
	public void getUserByIdApiTest() throws Exception {

		when(userServiceImpl.findById(anyLong())).thenReturn(user);
		MvcResult result  =mockMvc.perform(get("/users/{userId}",1))
        .andExpect(status().isOk())
        .andReturn();
		User resultUser=parseResponse(result,User.class);
		assertEquals(user,resultUser);
	}

	@DisplayName("Test DELETE /userId Api ")
	@Test
	public void deleteUserApiTest() throws Exception {

		when(userServiceImpl.findById(anyLong())).thenReturn(user);
		mockMvc.perform(delete("/users/{userId}",1))
        .andExpect(status().isOk());
		verify(userServiceImpl, times(1)).delete(user);
	}


	@DisplayName("Test POST /userId/addtodos  Api")
	@Test
	public void addTodoTest() throws Exception {

		when(userServiceImpl.findById(anyLong())).thenReturn(user);

		mockMvc.perform(post("/users/{userId}/addtodos",1).params(body))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/users/1/listtodos"));

		verify(userServiceImpl, times(1)).save(user);
	}

	@DisplayName("Test GET /userId/listtodos  Api")
	@Test
	public void listTodosTest() throws Exception {

		when(userServiceImpl.findById(anyLong())).thenReturn(user);

		mockMvc.perform(get("/users/{userId}/listtodos",1))
        .andExpect(status().isOk())
        .andExpect(view().name("todo-list.jsp"))
        .andExpect(model().attributeExists("listTodo"))
		.andExpect(model().attributeExists("userId"));
	}

	@DisplayName("Test GET /userId/edit/todoId  Api")
	@Test
	public void editTodoTest() throws Exception {

		when(userServiceImpl.findById(anyLong())).thenReturn(user);
		when(todoServiceImpl.findById(anyLong())).thenReturn(todo);

		mockMvc.perform(get("/users/{userId}/edit/{todoId}",1,2))
        .andExpect(status().isOk())
        .andExpect(view().name("todo-form.jsp"))
        .andExpect(model().attributeExists("todo"))
		.andExpect(model().attributeExists("userId"));
	}

	@DisplayName("Test POST /userId/updatetodos/todoId  Api")
	@Test
	public void updateTodoTest() throws Exception {

		when(userServiceImpl.findById(anyLong())).thenReturn(user);
		when(todoServiceImpl.getOne(anyLong())).thenReturn(todo);

		mockMvc.perform(post("/users/{userId}/updatetodos/{todoId}",1,2).params(body))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/users/1/listtodos"));

		verify(todoServiceImpl, times(1)).save(todo);
	}


	@DisplayName("Test DELETE /userId/deletetodos/todoId  Api")
	@Test
	public void deleteTodoTest() throws Exception {

		when(userServiceImpl.findById(anyLong())).thenReturn(user);
		when(todoServiceImpl.findById(anyLong())).thenReturn(todo);

		mockMvc.perform(get("/users/{userId}/deletetodos/{todoId}",1,2))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/users/1/listtodos"));

		verify(todoServiceImpl, times(1)).delete(todo);
	}


	@DisplayName("Test POST getUserDetailsUsingUserId Api")
	@Test
	public void getUserDetailsByUserId() {

		when(userServiceImpl.findById(anyLong())).thenReturn(user);
		User resultUser = (userApiController.getUserById(anyLong()));
		assertNotNull(user);
		assertEquals("TestFN", resultUser.getFirstName());
		assertEquals("TestLN", resultUser.getLastName());
		assertEquals("test@test.com", resultUser.getEmail());
	}

	@DisplayName("Test getUserDetailsUsingUserId Api null check")
	@Test
	public void getUserDetailsByUserIdNullCheck() {

		when(userServiceImpl.findById(anyLong())).thenReturn(null);

		User result = userApiController.getUserById(anyLong());

		assertNull(result);
	}

	public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
		try {
			String contentAsString = result.getResponse().getContentAsString();
			return MAPPER.readValue(contentAsString, responseClass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
