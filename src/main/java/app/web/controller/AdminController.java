package app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.model.User;
import app.service.IUserService;

@Controller
public class AdminController {
	private static String ADMIN_PAGE = "admin"; 
	private static String REDIRECT_ERROR_URL = "redirect:/haha";
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Model model){
		String result = REDIRECT_ERROR_URL;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userService.permissionsIsAdmin(principal)) {
			List<User> users = userService.getUsers();
			model.addAttribute("users", users);
			result = ADMIN_PAGE;
		}
		return result;
	}
	
	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	 public @ResponseBody String deleteUser(@RequestParam String username) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userService.permissionsIsAdmin(principal)) {
			userService.deleteUser(username);
		}
		return username;
	}
	
	@RequestMapping(value = "/edituser", method = RequestMethod.POST)
	 public @ResponseBody String editUser(@RequestParam long id, @RequestParam String username, @RequestParam String email,
			 @RequestParam String firstName, @RequestParam String lastName, @RequestParam String password) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userService.permissionsIsAdmin(principal)) {
			User user = userService.getUser(username);
			System.out.println(id);
			user.setId(id);
			user.setUsername(username);
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			if (!user.getPassword().equals(password)) {
				user.setPassword(passwordEncoder.encode(password));
			}
			userService.saveUser(user);
		}
		return username;
	}
	
}
