package app.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import app.model.Achievement;
import app.model.Comment;
import app.model.Content;
import app.model.ContentBuilder;
import app.model.EffectInstance;
import app.model.MyTable;
import app.model.Page;
import app.model.ScaleInstance;
import app.model.Site;
import app.model.Theme;
import app.model.ThemeBuilder;
import app.model.Url;
import app.service.IRequestHandlerService;
import app.service.IUserService;

@Controller
public class UserController {
	private static String REDIRECT_ERROR_URL = "redirect:/haha";
	private static String REDIRECT_CURRENT_HOME_URL = "redirect:home";
	private static String USER_SITE_HOME = "userSite_home";
	private static String NEW_PAGE = "newpage";
	private static String NEW_SITE = "newsite";
	private static String USER_PAGE = "user_page";
	private static String USER_PROFILE = "home_user";
	private static String USER_PAGE_EDIT = "edit";
	private static String USER_SITE_EDIT = "editsite";
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRequestHandlerService requestHandlerService;
	
	@RequestMapping(value = "{username}/{siteId}/edit", method = RequestMethod.GET)
	public String editSite(@PathVariable String username, @PathVariable long siteId, Model model){
		String result = REDIRECT_ERROR_URL;
		if (requestHandlerService.isValidUrl(username, siteId)){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (userService.permissions(principal, username) || userService.permissionsIsAdmin(principal)){
				Site site = userService.getSite(siteId);
				List<String> tags = userService.getTagsString(siteId);
				String tagsString = "";
				for (int i=0;i<tags.size();i++){
					tagsString+=tags.get(i);
					tagsString+=",";
				}
				if (site.getTypeTheme().equals("black")){
					model.addAttribute("blackTheme", true);
					model.addAttribute("standartTheme", false);
				} else {
					model.addAttribute("blackTheme", false);
					model.addAttribute("standartTheme", true);
				}
				model.addAttribute("tags", tagsString);
				model.addAttribute("site", site);
				result = USER_SITE_EDIT;
			}
		}
		return result;
	}
	
	@RequestMapping(value = "{username}/{siteId}/doEdit", method = RequestMethod.POST)
	public String doEditSite(@ModelAttribute Site siteDto, @RequestParam String tags, @PathVariable String username, @PathVariable long siteId){
		String result = REDIRECT_ERROR_URL;
		if (requestHandlerService.isValidUrl(username, siteId)){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (userService.permissions(principal, username) || userService.permissionsIsAdmin(principal)){
				Site site = userService.getSite(siteId);
				site.setDescription(siteDto.getDescription());
				site.setTypeTheme(siteDto.getTypeTheme());
				userService.saveSite(site);
				requestHandlerService.handlingEditTags(tags, site.getId());
				result = "redirect:/"+username+"/home";
			}
		}
		return result;
	}
	
	@RequestMapping(value = "{username}/{siteId}/home")
	public String homeSitePage(@PathVariable String username, @PathVariable long siteId,
			Model model){
		String result = REDIRECT_ERROR_URL;
		if (requestHandlerService.isValidUrl(username, siteId)){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			boolean auth = userService.permissions(principal, username);
			Site site = userService.getSite(siteId, username);
			List<Page> pages = userService.getPages(siteId,username);
			List<String> tags = userService.getTagsString(siteId);
			Theme theme = ThemeBuilder.getThemeStandart();
			if (site.getTypeTheme().equals("black")){
				theme = ThemeBuilder.getThemeBlack();
			}
			model.addAttribute("theme", theme);
			model.addAttribute("site",site);
			model.addAttribute("tags",tags);
			model.addAttribute("auth", auth);	
			model.addAttribute("username", username);
			model.addAttribute("siteName", site.getSiteName());
			model.addAttribute("pages", pages);
			model.addAttribute("idSite", siteId);
			result = USER_SITE_HOME;
		}
		return result;
	}
	
	@RequestMapping(value = "{username}/{siteId}/newpage")
	public String newPage(@PathVariable String username, @PathVariable long siteId, Model model){
		String result = REDIRECT_ERROR_URL;
		if (requestHandlerService.isValidUrl(username, siteId)){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (userService.permissions(principal, username)){
				Site site = userService.getSite(siteId);
				model.addAttribute("idSite", siteId);
				model.addAttribute("site",site);
				result = NEW_PAGE;
			}
		}
		return result;
	}
	
	@RequestMapping(value = "{username}/{siteId}/createnewpage", method = RequestMethod.POST)
	public String createNewPage(@ModelAttribute("page") Page page, @RequestParam("content") List<String> list,
			@PathVariable String username, @PathVariable long siteId){
		String result = REDIRECT_ERROR_URL;
		if (requestHandlerService.isValidUrl(username, siteId)){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (userService.permissions(principal, username)){
				userService.savePage(page);
				requestHandlerService.handlingSaveContent(list, page);
				result = REDIRECT_CURRENT_HOME_URL;
			}
		}
		return result;
	}
	
	@RequestMapping(value = "{username}/createnewsite", method = RequestMethod.POST)
	public String createNewSite(@ModelAttribute("site") Site site, @RequestParam("tags") String tags,
			@PathVariable String username, HttpServletRequest request){
		String result = REDIRECT_ERROR_URL;
		if (requestHandlerService.isValidUrl(username)){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (userService.permissions(principal, username)){
				userService.saveSite(site);
				if (userService.getNewsSites().size()==5){
					userService.deleteNewSiteLast();
				}
				requestHandlerService.handlingTags(tags, site.getId());
				if (userService.getCountOfSites(username) == 3){
					String type = "profi";
					HttpSession session = request.getSession();
					session.setAttribute("profi", false);
					if (!userService.getAchievement(username, type).isActive()) {
						userService.deleteAchievement(username, type);
						Achievement achievement = new Achievement();
						achievement.setType(type);
						achievement.setUsername(username);
						achievement.setActive(true);
						userService.saveAchievement(achievement);
						session.setAttribute("profi", true);
					}
				}
				result = REDIRECT_CURRENT_HOME_URL;
			}
		}
		return result;
	}
	
	@RequestMapping(value = "{username}/{siteId}/{pageId}/doEdit", method = RequestMethod.POST)
	public String doEditPage(@RequestParam("content") List<String> list, @RequestParam("numContainers") int numContainers,
			@PathVariable String username, @PathVariable long siteId, @PathVariable long pageId){
		String result = REDIRECT_ERROR_URL;
		if (requestHandlerService.isValidUrl(username, siteId, pageId)){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (userService.permissions(principal, username) || userService.permissionsIsAdmin(principal)){
				requestHandlerService.handlingEditContent(pageId, username, numContainers, list);
				result = "redirect:/"+username+"/"+siteId+"/home";
			}
		}
		return result;
	}
	
	@RequestMapping(value = "{username}/{siteId}/{pageId}/edit")
	public String editPage(Model model,@PathVariable String username, 
			@PathVariable long pageId, @PathVariable long siteId){
		String result = REDIRECT_ERROR_URL;
		if (requestHandlerService.isValidUrl(username, siteId, pageId)){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (userService.permissions(principal, username) || userService.permissionsIsAdmin(principal)){
				Page page = userService.getPage(pageId, username);
				Site site = userService.getSite(siteId);
				int numContainers = page.getNumContainers();
				List<List<Content>> listContents = requestHandlerService.handlingSortContent(pageId, siteId, numContainers);
				for (int i = 0; i < numContainers; i++) model.addAttribute("contents"+(i+1),listContents.get(i));
				model.addAttribute("idSite", siteId);
				model.addAttribute("page",page);
				model.addAttribute("site",site);
				model.addAttribute("idPage", pageId);
				model.addAttribute("numContainers", numContainers);
				model.addAttribute("pageName", page.getPageName());
				result = USER_PAGE_EDIT;
			}
		}
		return result;
	}
	
	@RequestMapping(value = "{username}/newsite", method = RequestMethod.GET)
	public String newSite(@PathVariable String username){
		String result = REDIRECT_ERROR_URL;
		if (requestHandlerService.isValidUrl(username)){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (userService.permissions(principal, username)){
				result = NEW_SITE;
			}
		}
		return result;
	}
	
	@RequestMapping(value = "{username}/home", method = RequestMethod.GET)
    public String home_user(@PathVariable String username, Model model, HttpServletRequest request){
		List<Site> newSites = userService.getNewsSites();
		System.out.println("size "+newSites.size());
		String result = REDIRECT_ERROR_URL;
		if (requestHandlerService.isValidUrl(username)){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			boolean auth = userService.permissions(principal, username);
			List<Site> sites = userService.getSites(username);
			List<Achievement> achievements = userService.getAchievements(username);
			boolean congratulation = false;
			if (userService.getCountOfSites(username) == 3 && userService.getCountOfLikes(username)>=10){
				HttpSession session = request.getSession();
				boolean check = false;
				try {
					check = (boolean) session.getAttribute("profi");
					session.removeAttribute("profi");
				} catch(Exception x) {
					
				}
				if (check){
					congratulation = true;
				}
			}
		    model.addAttribute("congratulation", congratulation);
			model.addAttribute("achievement1", achievements.get(0));
			model.addAttribute("achievement2", achievements.get(1));
			model.addAttribute("auth", auth);
			model.addAttribute("user", userService.getUser(username));
			model.addAttribute("sites", sites);
			result = USER_PROFILE;
		}
    	return result;
    }
	
	@RequestMapping(value="{username}/{siteId}/{pageId}")
	public String userPage(@PathVariable String username, @PathVariable long siteId, @PathVariable long pageId, Model model){
		String result = REDIRECT_ERROR_URL;
		if (requestHandlerService.isValidUrl(username, siteId, pageId)){
			Site site = userService.getSite(siteId);
			Page page = userService.getPage(pageId, username);
			int numContainers = page.getNumContainers();
			List<List<Content>> listContents = requestHandlerService.handlingSortContent(pageId, siteId, numContainers);
			for (int i = 0; i < numContainers; i++) model.addAttribute("contents"+(i+1),listContents.get(i));
			List<Comment> comments = userService.getComments(pageId);
			Collections.reverse(comments);
			Theme theme = ThemeBuilder.getThemeStandart();
			if (site.getTypeTheme().equals("black")){
				theme = ThemeBuilder.getThemeBlack();
			}
			model.addAttribute("site", userService.getSite(siteId));
			model.addAttribute("pages", userService.getPages(siteId, username));
			model.addAttribute("numContainers", page.getNumContainers());
			model.addAttribute("idPage", page.getId());
			model.addAttribute("pageName", page.getPageName());
			model.addAttribute("comments", comments);
			model.addAttribute("theme", theme);
			result = USER_PAGE;
		}
		return result;
	}
	 
	 @RequestMapping(value = "/getTable", method = RequestMethod.POST)
	 public @ResponseBody List<List<Integer>> getTable(@RequestParam long idPage,@RequestParam long idSite) {
		 List<List<Integer>> reuslt = new ArrayList<List<Integer>>();
		 List<MyTable> myTables = userService.getMyTables(idPage,idSite);
		 for (int i=0;i<myTables.size();i++){
			 List<Integer> xy = new ArrayList<Integer>();
			 xy.add(myTables.get(i).getX());
			 xy.add(myTables.get(i).getY());
			 reuslt.add(xy);
		 }
		 return reuslt;
	 }
	 
	 @RequestMapping(value = "/search", method = RequestMethod.POST)
	 public String search(@RequestParam("search") String search, Model model){
		 List<Url> urls = userService.findAll(search);
		 model.addAttribute("urls", urls);
		 return "search";
	 }
	 
	 @RequestMapping(value = "/addLike", method = RequestMethod.POST)
	public @ResponseBody String addLike(@RequestParam long idPage, HttpServletRequest request) {
		Content like = userService.getLikefromPage(idPage, "3");
		String username = userService.getPage(like.getIdPage()).getUsername();
		int val = Integer.parseInt(like.getContent());
		like.setContent("" + (val + 1));
		userService.saveContent(like);
		if (Integer.parseInt(like.getContent()) >= 10){
			String type = "popular";
			if (!userService.getAchievement(username, type).isActive()) {
				HttpSession session = request.getSession();
				boolean prof = false;
				userService.deleteAchievement(username, type);
				Achievement achievement = new Achievement();
				achievement.setType(type);
				achievement.setUsername(username);
				achievement.setActive(true);
				userService.saveAchievement(achievement);
				prof = true;
				session.setAttribute("profi", prof);
			}
			
		}
		return like.getContent();
	}
	 
	 @RequestMapping(value = "/deletesite", method = RequestMethod.POST)
	 public @ResponseBody long deleteSite(@RequestParam long idSite) {
		Site site = userService.getSite(idSite);
		String username = site.getUsername();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userService.permissions(principal, username) || userService.permissionsIsAdmin(principal)) {
			userService.deleteSite(idSite);
		}
		return idSite;
	}
	 
	 @RequestMapping(value = "/deletepage", method = RequestMethod.POST)
	 public @ResponseBody long deletePage(@RequestParam long idPage) {
		Page page = userService.getPage(idPage);
		String username = page.getUsername();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (userService.permissions(principal, username) || userService.permissionsIsAdmin(principal)){
	    	userService.deletePage(idPage);
	    }
		return idPage;
	}
	 
	@RequestMapping(value = "/getsitetags", method = RequestMethod.POST)
	public @ResponseBody List<String> getSiteTags(@RequestParam long idSite) {
		List<String> tagsString = userService.getTagsString(idSite);
		return tagsString;
	}
	 
	 @RequestMapping(value = "/gettags", method = RequestMethod.POST)
	 public @ResponseBody List<String> getTags() {
		 List<String> tagsString = userService.getTagsString();
		 return tagsString;
	 }	
	 
	 @RequestMapping(value = "/savecontent", method = RequestMethod.POST)
	 public @ResponseBody void saveContent(@RequestParam String content, @RequestParam String type,
			 @RequestParam int numContainer, @RequestParam int position) {
			 ContentBuilder.addContent(content, type, numContainer, position);
	 }	
	 
	 @RequestMapping(value = "/sendcomment", method = RequestMethod.POST)
	 public @ResponseBody String getCharNum(@RequestParam String message, @RequestParam String username, 
			 @RequestParam long idPage) {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userService.isAuth(principal)) {
			Comment comment = new Comment();
			comment.setIdPage(idPage);
			comment.setMessage(message);
			comment.setUsername(username);
			userService.saveComment(comment);
			return username + " say: " + message;
		}
		 return null;
	 }
	 
	 @RequestMapping(value = "/saveimage", method = RequestMethod.POST)
	 public @ResponseBody String saveImage(MultipartHttpServletRequest request) throws IOException {
		Iterator<String> itr = request.getFileNames();
		String url = "";
		while (itr.hasNext()) {
			String uploadedFile = itr.next();
			MultipartFile image = request.getFile(uploadedFile);
			File file = new File("temp.jpg");
			FileUtils.writeByteArrayToFile(file, image.getBytes());
			Map<String, String> imageMap = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
			file.delete();
			url = imageMap.get("secure_url");
			ScaleInstance.addScaleForImage(imageMap.get("public_id"), 0);
			EffectInstance.addEffectForImage(imageMap.get("public_id"), "");
			System.out.println(url);
		}
		return url;
	}
	 
	@RequestMapping(value = "/scaleimage", method = RequestMethod.POST)
	public @ResponseBody String scaleImage(String url, int scale) throws Exception {
		System.out.println("begin"+url);
		String newUrl = "";
		String public_id_temp = "";
		String temp = "";
		temp = url.replaceAll(".jpg", "");
		temp = temp.replaceAll(".png", "");
		System.out.println(temp);
		for (int i = temp.length() - 1; i > 0; i--) {
			char one = temp.charAt(i);
			if (one == '/') {
				i = 0;
			} else {
				public_id_temp += one;
			}
		}
		String public_id = new StringBuilder(public_id_temp).reverse().toString();
		Collection<String> values = cloudinary.api().resource(public_id, ObjectUtils.emptyMap()).values();
		int scaleTemp = ScaleInstance.getScale(public_id);
		scaleTemp+=scale;
		ScaleInstance.changeScaleForImage(public_id, scaleTemp);
		String effect = EffectInstance.getEffect(public_id);
		newUrl = cloudinary.url().format("jpg")
   			  .transformation(new Transformation().width((int)values.toArray()[10]+scaleTemp).height((int)values.toArray()[12]+scaleTemp).effect(effect))
   			  .generate(public_id);
		return newUrl;
	}
	
	@RequestMapping(value = "/sepiaimage", method = RequestMethod.POST)
	public @ResponseBody String sepiaImage(String url) throws Exception {
		String newUrl = "";
		String public_id_temp = "";
		String temp = "";
		temp = url.replaceAll(".jpg", "");
		temp = temp.replaceAll(".png", "");
		for (int i = temp.length() - 1; i > 0; i--) {
			char one = temp.charAt(i);
			if (one == '/') {
				i = 0;
			} else {
				public_id_temp += one;
			}
		}
		String public_id = new StringBuilder(public_id_temp).reverse().toString();
		Collection<String> values = cloudinary.api().resource(public_id, ObjectUtils.emptyMap()).values();
		String effect = EffectInstance.getEffect(public_id);
		int scale = ScaleInstance.getScale(public_id);
		if (effect.equals("")) {
			effect = "sepia";
			EffectInstance.changeEffectForImage(public_id, effect);
			newUrl = cloudinary.url().format("jpg").transformation(new Transformation()
					.width((int) values.toArray()[10]+scale).height((int) values.toArray()[12]+scale).effect(effect))
					.generate(public_id);
		} else {
			effect = "";
			newUrl = cloudinary.url().format("jpg").transformation(new Transformation()
					.width((int) values.toArray()[10]+scale).height((int) values.toArray()[12]+scale).effect(effect))
					.generate(public_id);
			EffectInstance.changeEffectForImage(public_id, effect);
		}
		return newUrl;
	}
}
