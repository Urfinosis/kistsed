package app.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import app.model.Achievement;
import app.model.User;
import app.model.VerificationToken;
import app.registration.OnRegistrationCompleteEvent;
import app.service.IUserService;
import app.web.dto.UserDto;

@Controller
public class RegistrationController {
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
    private IUserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public String getUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult results, WebRequest request) {
    	if (userService.emailExist(userDto.getEmail())) {
    		results.addError(new ObjectError("user", "email exist error"));
    	}
    	if (userService.usernameExist(userDto.getUsername())) {
    		results.addError(new ObjectError("user", "username exist error"));
    	}
    	if (results.hasErrors()){
    		return "registration";
    	}
    	User user = new User();
    	user.setEmail(userDto.getEmail());
    	user.setFirstName(userDto.getFirstName());
    	user.setLastName(userDto.getLastName());
    	user.setUsername(userDto.getUsername());
    	user.setRole("ROLE_USER");
    	user.setEnabled(false);
    	user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    	userService.saveUser(user);
    	Achievement achievement1 = new Achievement();
    	Achievement achievement2 = new Achievement();
    	achievement1.setActive(false);
    	achievement1.setType("profi");
    	achievement1.setUsername(user.getUsername());
    	achievement2.setActive(false);
    	achievement2.setType("popular");
    	achievement2.setUsername(user.getUsername());
    	userService.saveAchievement(achievement1);
    	userService.saveAchievement(achievement2);
    	String appUrl = request.getContextPath();
    	eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));	 
    	return "redirect:/login";    
    }
    @RequestMapping(value = "/regitrationConfirm.html", method = RequestMethod.GET)
    public String confirmRegistration
          (WebRequest request, @RequestParam("token") String token) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
//        if (verificationToken == null) {
//            String message = messages.getMessage("auth.message.invalidToken", null, locale);
//            model.addAttribute("message", message);
//            return "redirect:/badUser.html?lang=" + locale.getLanguage();
//        } 
        User user = verificationToken.getUser();
        user.setEnabled(true); 
        userService.saveUser(user); 
        return "redirect:/login"; 
    }
    
    @RequestMapping(value = "/resend_confirmation_message")
    public String resendConfirm(HttpServletRequest request){
    	HttpSession session = request.getSession();
    	String username = (String) session.getAttribute("username");
    	System.out.println("+"+username);
    	User user = userService.getUser(username);
    	String appUrl = request.getContextPath();
    	eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
    	return "/login";
    }
  
}