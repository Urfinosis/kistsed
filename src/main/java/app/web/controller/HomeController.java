package app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.model.Site;
import app.service.IUserService;

@Controller
public class HomeController {
	private static String REDIRECT_HOME_URL = "redirect:/home";
	private static String HOME_PAGE = "home";
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirect_home(){
		String result = REDIRECT_HOME_URL;
		return result;
	}

	@RequestMapping(value="/home",method=RequestMethod.GET)
	public String home(Model model){
		String result = HOME_PAGE;
		List<Site> sites = userService.getPopularSites();
		model.addAttribute("newsites", userService.getNewsSites());
		model.addAttribute("sites", sites);
		return result;
	}
}
