package com.praba.onlinetodoapp.service;

import com.praba.onlinetodoapp.entity.Todo;

public interface TodoService {

	Todo findById(Long todoId);

	void delete(Todo todo);

	Todo save(Todo todo);

	Todo getOne(Long todoId);

}
