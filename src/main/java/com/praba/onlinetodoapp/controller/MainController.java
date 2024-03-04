package com.praba.onlinetodoapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.praba.onlinetodoapp.service.UserService;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@Autowired
	private UserService userService;

	private static final Logger logger = LogManager.getLogger(MainController.class);

//	@RequestMapping("/health")
//	public ResponseEntity<String> healthRequest(@RequestBody String partners) {
//
//		logger.info("");
//		logger.info("***********************************************");
//		logger.info("POST /health                               ");
//		logger.info("***********************************************");
//		logger.info("");
//		return ResponseEntity.ok("health request received");
//	}

	@GetMapping ("/health")
	public ResponseEntity<String> healthRequest(@RequestBody String partners) {

		logger.info("");
		logger.info("***********************************************");
		logger.info("POST /health                               ");
		logger.info("***********************************************");
		logger.info("");
		return ResponseEntity.ok("health request received");
	}

//	@PostMapping("/login")
//	public ResponseEntity<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
//		if (username.equals("admin") && password.equals("password")) {
//			return ResponseEntity.ok("Login successful");
//		} else {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//		}
//	}


	@GetMapping("/")
	public String root() {
		return "index.html";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login.html";
	}

	@GetMapping("/new")
	public String todoNewForm(ModelMap model) {

		org.springframework.security.core.userdetails.User springUser = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		com.praba.onlinetodoapp.entity.User jpaUser = userService.findByEmail(springUser.getUsername());
		Long userId = jpaUser.getId(); // get logged in user id
		model.addAttribute("userId", userId);
		return "todo-form.jsp";
	}

}
