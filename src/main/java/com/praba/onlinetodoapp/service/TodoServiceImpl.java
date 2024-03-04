package com.praba.onlinetodoapp.service;

import java.util.NoSuchElementException;

import com.praba.onlinetodoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praba.onlinetodoapp.entity.Todo;

@Service
public class TodoServiceImpl implements TodoService  {
	
	@Autowired
    TodoRepository todoRepository;

	@Override
	public Todo findById(Long todoId) {
		return todoRepository.findById(todoId).orElseThrow(() -> new NoSuchElementException());
	}

	@Override
	public void delete(Todo todo) {
		todoRepository.delete(todo);
	}

	@Override
	public Todo save(Todo todo) {
		return todoRepository.save(todo);
	}

	@Override
	public Todo getOne(Long todoId) {
		return todoRepository.getOne(todoId);
	}

}
