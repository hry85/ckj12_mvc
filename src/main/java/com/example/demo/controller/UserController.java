package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.demo.model.User;

@Controller
@RequestMapping("/users")
public class UserController {
	private List<User> users = new ArrayList<>();

	@GetMapping("/info/{id}")
	public String getInfo(@PathVariable("id") int id, Model model) {
		User user = users.stream().filter(u -> u.getId() == id).findFirst().get();
		model.addAttribute("user", user);
		return "info";
	}

	@GetMapping("/create")
	public String createUser() {
		return "user_form";
	}

	@PostMapping("/add")
	public String addUser(@ModelAttribute(name = "user") User user, Model model) {
		// вычислить ид для пользователя
		int id = users.stream().map(usr -> usr.getId()).max((a, b) -> a - b).orElse(0);
		user.setId(id + 1);
		users.add(user);
		return "redirect:/users/all";
	}

	@GetMapping("/all")
	public String users(Model model) {
		model.addAttribute("users", users);
		return "users";
	}
}
