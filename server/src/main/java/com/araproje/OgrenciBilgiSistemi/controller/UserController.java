package com.araproje.OgrenciBilgiSistemi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.araproje.OgrenciBilgiSistemi.model.User;
import com.araproje.OgrenciBilgiSistemi.service.UserService;

@RestController
@RequestMapping("/api/rest/user")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/add-user")
	public ResponseEntity<String> bindLdapPerson(@RequestBody User person) {
		String result = userService.create(person);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/update-user")
	public ResponseEntity<String> rebindLdapPerson(@RequestBody User person) {
		String result = userService.update(person);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/retrieve-users")
	public ResponseEntity<List<User>> retrieve() {
		return new ResponseEntity<List<User>>(userService.retrieve(), HttpStatus.OK);
	}

	@GetMapping("/remove-user")
	public ResponseEntity<String> unbindLdapPerson(@RequestParam(name = "userId") String userId) {
		String result = userService.remove(userId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
