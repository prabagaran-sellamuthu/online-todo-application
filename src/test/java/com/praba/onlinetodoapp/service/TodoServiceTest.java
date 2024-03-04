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

import com.praba.onlinetodoapp.entity.Todo;
import com.praba.onlinetodoapp.repository.TodoRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoServiceTest {
	
	@InjectMocks
	TodoServiceImpl todoService;
	
    @Mock
    private TodoRepository todoRepository;
    
	Todo testTodo;
	Todo todo;
	Long USER_ID = (long) 1234;
	Long TODO_ID = (long) 1234;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		todo = new Todo();
		todo.setId(TODO_ID);
		todo.setDescription("Test");
		todo.setTitle("titleTEST");
		todo.setTargetDate("test");
		todo.setStatus("Completed");
		todo.setLastUpdatedDate("lastTEST");

		
		testTodo = new Todo();
		testTodo.setId(TODO_ID);
		testTodo.setDescription("Test");
		}
	
	@DisplayName("Test findById method making call to Repository")
	@Test
	public void findById() {

		when(todoRepository.findById(anyLong())).thenReturn(Optional.of(todo));
		Todo resultTodo = todoService.findById(anyLong());
		Long todoIdfromRepo = (todoService.findById(anyLong())).getId();
		assertNotNull(resultTodo);
		assertEquals(TODO_ID, todoIdfromRepo);
	}
	
	@DisplayName("Test Save method making call to Repository")
	@Test
	public void saveMethodTest() {

		when(todoRepository.save(testTodo)).thenReturn(todo);

		Todo resultTodo = (todoService.save(testTodo));

		assertNotNull(resultTodo);
		assertEquals(TODO_ID, resultTodo.getId());
		assertEquals("Test", resultTodo.getDescription());
		assertEquals("titleTEST", resultTodo.getTitle());
		assertEquals("test", resultTodo.getTargetDate());
		assertEquals("Completed", resultTodo.getStatus());
		assertEquals("lastTEST", resultTodo.getLastUpdatedDate());
	}
	
	@DisplayName("Test Update method making call to Repository")
	@Test
	public void updateMethodTest() {

		when(todoRepository.getOne(TODO_ID)).thenReturn(todo);

		Todo resultTodo = (todoService.getOne(TODO_ID));

		assertNotNull(resultTodo);
		assertEquals(TODO_ID, resultTodo.getId());
		assertEquals("Test", resultTodo.getDescription());
		assertEquals("titleTEST", resultTodo.getTitle());
		assertEquals("test", resultTodo.getTargetDate());
		assertEquals("Completed", resultTodo.getStatus());
		assertEquals("lastTEST", resultTodo.getLastUpdatedDate());
	}
	
	@DisplayName("Test deleteMethodTest method making call to Repository")
	@Test
	public void deleteMethodTest() {

		doNothing().when(todoRepository).delete(testTodo);
		todoService.delete(testTodo);
		verify(todoRepository, times(1)).delete(testTodo);
	}
	
}