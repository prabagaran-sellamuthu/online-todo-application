package com.praba.onlinetodoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.praba.onlinetodoapp.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo,Long> {
}
