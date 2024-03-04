package com.praba.onlinetodoapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.praba.onlinetodoapp.controller.dto.TodoDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.praba.onlinetodoapp.entity.Todo;
import com.praba.onlinetodoapp.entity.User;
import com.praba.onlinetodoapp.service.TodoService;
import com.praba.onlinetodoapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/users")
public class UserApiController {

	
    private static final Logger logger = LogManager.getLogger(UserApiController.class);
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");

    @Autowired
    private UserService userService;
    
    @Autowired
    private TodoService todoService;
    
//	@GetMapping("/{userId}")
//	public User getUserById(@PathVariable Long userId) {
//
//		logger.info("");
//		logger.info("*******************************************");
//		logger.info("GET /userId");
////		logger.info("\tuserId: " + userId);
//		logger.info("******************************************");
//		logger.info("");
//
//		return userService.findById(userId);
//	}
	@GetMapping("/{userId}")
	public User getUserById(@PathVariable Long userId) {

		User user = userService.findById(userId);
		logger.info("");
		logger.info("*******************************************");
		logger.info("GET /userId");
//		logger.info("\tuserId: " + userId);
		logger.info("******************************************");
		logger.info("");

		if (user != null) {
			// Remove sensitive data before returning the response
			user.setPassword(null);
		}
		return user;
	}

	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Long userId) {
		
		logger.info("");
		logger.info("*******************************************");
		logger.info("DELETE /userId");
//		logger.info("\tuserId: " + userId);
		logger.info("******************************************");
		logger.info("");
		
		User user = userService.findById(userId);
		userService.delete(user);
	}
	
	@PostMapping("/{userId}/addtodos")
	public ModelAndView addTodo(@PathVariable Long userId,  @RequestParam Map<String, String> body  ) {
		
		final ObjectMapper mapper = new ObjectMapper(); 
		final TodoDto todoDto = mapper.convertValue(body, TodoDto.class);
		Date date = new Date();
		
		logger.info("");
		logger.info("*******************************************");
		logger.info("POST /userId/addtodos");
//		logger.info("\tuserId: " + userId);
//		logger.info("\ttodo: " + todoDto.toString());
		logger.info("******************************************");
		logger.info("");

		User user = userService.findById(userId);
		Todo todo = new Todo();
		todo.setTitle(todoDto.getTitle());
		todo.setDescription(todoDto.getDescription());
		todo.setTargetDate(todoDto.getTargetDate());
		todo.setStatus(todoDto.getStatus());
		todo.setLastUpdatedDate(sdf.format(date));
		user.getTodoList().add(todo);
		userService.save(user);
	    return new ModelAndView("redirect:/users/"+userId+"/listtodos");
	}
	
	@GetMapping("/{userId}/listtodos")
    public ModelAndView todoList(@PathVariable Long userId) {
    	
		logger.info("");
		logger.info("*******************************************");
		logger.info("GET /userId/listtodos");
//		logger.info("\tuserId: " + userId);
		logger.info("******************************************");
		logger.info("");
		
    	User user = userService.findById(userId);
    	List<Todo> listTodo =user.getTodoList();
        ModelAndView model = new ModelAndView("todo-list.jsp");
        model.addObject("listTodo", listTodo);
        model.addObject("userId",userId);
        return model;
    }
	
	@GetMapping("{userId}/edit/{todoId}")
	public ModelAndView editTodo(@PathVariable Long userId, @PathVariable Long todoId) {
		
		logger.info("");
		logger.info("*******************************************");
		logger.info("GET /userId/edit/todoId");
//		logger.info("\tuserId: " + userId);
		logger.info("******************************************");
		logger.info("");
		
		User user = userService.findById(userId);
		Todo todo = todoService.findById(todoId);
        ModelAndView model = new ModelAndView("todo-form.jsp");
        model.addObject("todo", todo);
        model.addObject("userId",userId);
        return model;
	}
	
	@PostMapping("/{userId}/updatetodos/{todoId}")
	public ModelAndView updateTodo(@PathVariable Long userId,@PathVariable Long todoId, @RequestParam Map<String, String> body ) {
		body.remove("id");
		final ObjectMapper mapper = new ObjectMapper(); 
		final TodoDto todoDto = mapper.convertValue(body, TodoDto.class);
		Date date = new Date();
		
		logger.info("");
		logger.info("*******************************************");
		logger.info("POST /userId/updatetodos/todoId");
//		logger.info("\tuserId: " + userId);
//		logger.info("\ttodo: " + todoDto);
		logger.info("******************************************");
		logger.info("");
		
		User user = userService.findById(userId);
		Todo todo = todoService.getOne(todoId);
		todo.setTitle(todoDto.getTitle());
		todo.setDescription(todoDto.getDescription());
		todo.setTargetDate(todoDto.getTargetDate());
		todo.setStatus(todoDto.getStatus());
		todo.setLastUpdatedDate(sdf.format(date));
		todoService.save(todo);
	    return new ModelAndView("redirect:/users/"+userId+"/listtodos");
	}

	@GetMapping("{userId}/deletetodos/{todoId}")
	public ModelAndView deleteTodo(@PathVariable Long userId, @PathVariable Long todoId) {
		
		logger.info("");
		logger.info("*******************************************");
		logger.info("GET /userId/deletetodos/todoId");
//		logger.info("\tuserId: " + userId);
		logger.info("******************************************");
		logger.info("");
		
		User user = userService.findById(userId);
		Todo todo = todoService.findById(todoId);
		user.getTodoList().remove(todo);
		todoService.delete(todo);
		return new ModelAndView("redirect:/users/"+userId+"/listtodos");
	}

}
