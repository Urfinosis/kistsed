package app.service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import app.dao.AchievementRepository;
import app.dao.CommentRepository;
import app.dao.ContentRepository;
import app.dao.MyTableRepository;
import app.dao.NewSiteRepository;
import app.dao.PageRepository;
import app.dao.SearchRepository;
import app.dao.SiteRepository;
import app.dao.TagRepository;
import app.dao.TokenRepository;
import app.dao.UserRepository;
import app.model.Achievement;
import app.model.Comment;
import app.model.Content;
import app.model.MyTable;
import app.model.NewSite;
import app.model.Page;
import app.model.Site;
import app.model.Tag;
import app.model.Url;
import app.model.User;
import app.model.VerificationToken;

@Service
@Transactional
public class UserService implements IUserService{
	
	@Autowired
	private NewSiteRepository newSiteRepository;
	
	@Autowired
	private AchievementRepository achievementRepository;
	
	@Autowired
	private TagRepository tagDao;
	
	@Autowired
	private MyTableRepository myTableDao; 
	
	@Autowired
	private CommentRepository commentDao;
	
	@Autowired
	private ContentRepository contentDao;
	
	@Autowired
	private SearchRepository searchDao;
	
	@Autowired
	private SiteRepository siteDao;
	
	@Autowired
	private PageRepository pageDao;
	
	@Autowired
	private TokenRepository tokenRepository;
	
    private UserRepository userDao;

    public UserRepository getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserRepository userDao) {
        this.userDao = userDao;
    }
    
	public boolean emailExist(String email) {
		final User user = userDao.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}
	
	public boolean usernameExist(String username) {
		final User user = userDao.findByUsername(username);
		if (user != null) {
			return true;
		}
		return false;
	}
    
    public boolean permissionsIsAdmin(Object principal) {
    	if (principal.equals("anonymousUser")){
			return false;
		} else {
			String username = ((UserDetails)principal).getUsername();
			User user = getUser(username);
			if (user == null){
				return false;
			}
			if (user.getRole().equals("ROLE_ADMIN")) {
				return true;
			}
		}
    	return false;
    }
    
    public boolean permissions(Object principal, String username){
		if (principal.equals("anonymousUser")){
			return false;
		}
		else {
			String authUsername = ((UserDetails)principal).getUsername();
			if (username.equals(authUsername)){
				return true;
			} else {
				return false;
			}
		}
	}
    
    public boolean isAuth(Object principal){
		if (principal.equals("anonymousUser")){
			return false;
		}
		return true;
	}

	
	public List<User> getUsers(){
		return userDao.findAll();
	}

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
    
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }
    
    public void saveUser(User user){
    	userDao.save(user);
    }
    
    public User getUser(String username){
    	return userDao.findByUsername(username);
    }
    
    public void saveSite(Site site){
    	siteDao.save(site);
    	addNewSite(site);
    }
    
    public void savePage(Page page){
    	pageDao.save(page);
    }

	@Override
	public List<Site> getSites(String username) {
		return siteDao.findByUsername(username);
	}	

	@Override
	public List<Page> getPages(long idSite, String username) {
		return pageDao.findByIdSiteAndUsername(idSite,username);
	}

	@Override
	public List<Page> findPages(String search) {
		return searchDao.findPages(search);
	}
	
	@Override
	public List<Tag> findTags(String search) {
		return searchDao.findTags(search);
	}
	
	@Override
	public List<User> findUser(String search) {
		return searchDao.findUser(search);
	}

	@Override
	public List<Site> findSites(String search) {
		return searchDao.findSites(search);
	}

	@Override
	public void saveContent(Content content) {
		contentDao.save(content);
	}

	@Override
	public List<Content> findContents(String search) {
		return searchDao.findContent(search);
	}

	@Override
	public List<Content> getContents(long idPage, long idSite) {
		return contentDao.findByIdPageAndIdSite(idPage, idSite);
	}

	@Override
	public void saveComment(Comment comment) {
		commentDao.save(comment);
	}

	@Override
	public List<Comment> getComments(long idPage) {
		return commentDao.findByIdPage(idPage);
	}

	@Override
	public void saveMyTable(MyTable myTable) {
		myTableDao.save(myTable);
	}

	@Override
	public List<MyTable> getMyTables(long idPage, long idSite) {
		return myTableDao.findByIdPageAndIdSite(idPage, idSite);
	}
	
	public List<Url> findAll(String search){
		List<Url> urls = new ArrayList<Url>();
		List<Site> sites = findSites(search);
		List<Page> pages = findPages(search);
		List<Content> contents = findContents(search);
		List<Tag> tags = findTags(search);
		List<User> users = findUser(search);
		User user = null;
		if (users.size() == 1){
			user = users.get(0);
		}
		List<Long> siteId = new ArrayList<Long>();
		for (int i=0;i<tags.size();i++){
			siteId.add(tags.get(i).getIdSite());
		}
		for (int i=0;i<siteId.size();i++){
			long temp = siteId.get(i);
			Site site = getSite(temp);
			String url = "http://localhost:8080/"+site.getUsername()+"/"+temp+"/home";
			urls.add(new Url("site",url,site.getSiteName()));
			for (int j=i+1;j<siteId.size();j++){
				if (temp == siteId.get(j)){
					siteId.remove(j);
				} 
			}
		}
		for (int i=0;i<sites.size();i++){
			String username = sites.get(i).getUsername();
			long id= sites.get(i).getId();
			String url = "http://localhost:8080/"+username+"/"+id+"/home";
			urls.add(new Url("site",url,sites.get(i).getSiteName()));
			for (int j=i+1;j<sites.size();j++){
				if (username == sites.get(j).getUsername() && id == sites.get(j).getId()){
					sites.remove(j);
				} 
			}
		}
		for (int i=0;i<pages.size();i++){
			String username = pages.get(i).getUsername();
			long id = pages.get(i).getIdSite();
			long idpage = pages.get(i).getId();
			String url = "http://localhost:8080/"+username+"/"+id+"/"+idpage;
			urls.add(new Url("page",url,pages.get(i).getPageName()));
			for (int j=i+1;j<pages.size();j++){
				if (username == pages.get(j).getUsername() && id == pages.get(j).getIdSite() && idpage == pages.get(j).getId()){
					pages.remove(j);
				} 
			}
		}
		for (int i=0;i<contents.size();i++){
			Site site = getSite(contents.get(i).getIdSite());
			String username = site.getUsername();
			long id = contents.get(i).getIdSite();
			long idpage = contents.get(i).getIdPage();
			Page page = getPage(idpage, username);
			String url = "http://localhost:8080/"+username+"/"+id+"/"+idpage;
			urls.add(new Url("page",url,page.getPageName()));
			for (int j=i+1;j<contents.size();j++){
				if (username == getSite(contents.get(j).getIdSite()).getUsername() && id == contents.get(j).getIdSite() && idpage == contents.get(j).getIdPage()){
					contents.remove(j);
				} 
			}
		}
		if (!(user==null)){
			String url = "http://localhost:8080/"+user.getUsername()+"/home";
			urls.add(new Url("user",url,user.getUsername()));
		}
		return urls;
	}

	@Override
	public Site getSite(long idSite) {
		return siteDao.findOne(idSite);
	}
	
	@Override
	public Site getSite(long idSite, String username) {
		return siteDao.findByIdAndUsername(idSite, username);
	}

	@Override
	public Page getPage(long idPage, String username) {
		return pageDao.findByIdAndUsername(idPage, username);
	}

	@Override
	public void deleteUser(String username) {
		userDao.deleteUserByUsername(username);
		List<Site> sites = getSites(username);
		for (int i=0;i<sites.size();i++){
			Site site = sites.get(i);
			List<Page> pages = getPages(site.getId(), username);
			for (int j=0;j<pages.size();j++){
				Page page = pages.get(j);
				deleteCommentFromPage(page.getId());
				deleteMyTableFromPage(page.getId());
				deleteContentFromPage(page.getId());
			}
		}
		deleteSites(username);
		deletePages(username);
	}

	@Override
	public void deleteSite(long id) {
		Site site = getSite(id);
		List<Page> pages = getPages(id, site.getUsername());
		for (int j=0;j<pages.size();j++){
			Page page = pages.get(j);
			deleteCommentFromPage(page.getId());
			deleteMyTableFromPage(page.getId());
			deleteContentFromPage(page.getId());
		}
		deleteTags(id);
		NewSite newSite = newSiteRepository.findBySiteId(id);
		if (newSite!=null){
		newSiteRepository.delete(newSite);
		}
		siteDao.delete(id);
		pageDao.deletePagesByIdSite(id);
	}

	@Override
	public void deleteSites(String username) {
		siteDao.deleteSitesByUsername(username);
	}

	@Override
	public void deletePage(long idPage) {
		deleteCommentFromPage(idPage);
		deleteMyTableFromPage(idPage);
		deleteContentFromPage(idPage);
		pageDao.delete(idPage);
	}

	@Override
	public void deletePages(String username) {
		pageDao.deletePagesByUsername(username);
	}

	@Override
	public void saveTag(Tag tag) {
		tagDao.save(tag);
	}

	@Override
	public List<String> getTagsString(long idSite) {
		List<Tag> tags = tagDao.findByIdSite(idSite);
		List<String> tagsString = new ArrayList<String>();
		for (int i=0;i<tags.size();i++){
			tagsString.add(tags.get(i).getTag());
		}
		return tagsString;
	}

	@Override
	public void deleteContentFromPage(long pageId) {
		contentDao.deleteByIdPage(pageId);
	}

	@Override
	public Page getPage(long id) {
		return pageDao.getOne(id);
	}

	@Override
	public void deleteCommentFromPage(long pageId) {
		commentDao.deleteByIdPage(pageId);
	}

	@Override
	public void deleteMyTableFromPage(long pageId) {
		myTableDao.deleteByIdPage(pageId);
	}

	@Override
	public List<String> getTagsString() {
		List<Tag> tags = tagDao.findAll();
		List<String> tagsString = new ArrayList<String>();
		for (int i=0;i<tags.size();i++){
			if (!tagsString.contains(tags.get(i).getTag())){
				tagsString.add(tags.get(i).getTag());
			}
		}
		return tagsString;
	}

	@Override
	public void deleteTags(long idSite) {
		tagDao.deleteByIdSite(idSite);
	}

	@Override
	public List<Site> getPopularSites() {
		List<Site> sites = siteDao.findAll();
		List<Site> popularSites = new ArrayList<Site>();
		List<TempSite> sortedSites = new ArrayList<TempSite>();
		for (int i=0;i<sites.size();i++){
			int tempLikes = calculateLikeForSite(sites.get(i).getId(), sites.get(i).getUsername());
			sortedSites.add(new TempSite(sites.get(i).getId(), tempLikes));
		}
		sortedSites.sort(new Comparator<TempSite>() {
			@Override
			public int compare(TempSite o1, TempSite o2) {
				if (o1.getLikes() < o2.getLikes()){
					return 1;
				} else if(o1.getLikes() > o2.getLikes()) {
					return -1;
				} 
				return 0;
			}
		});
		for (int i=0;i<sortedSites.size() && i<4;i++){
			popularSites.add(getSite(sortedSites.get(i).getSiteId()));
		}
		return popularSites;
	}
	
	public int calculateLikeForSite(long siteId, String username){
		int likes = 0;
		List<Page> pages = getPages(siteId, username);
		for (int i=0;i<pages.size();i++){
			Page page = pages.get(i);
			List<Content> contents = getContents(page.getId(), page.getIdSite());
			for (int j=0;j<contents.size();j++){
				Content content = contents.get(j);
				if (content.getTypeContent().equals("3")){
					int like = Integer.parseInt(content.getContent());
					likes+=like;
				}
			}
		}
		return likes;
	}
	
	public int calculateLikeForUser(String username){
		int likes = 0;
		List<Page> pages = getPages(username);
		for (int i=0;i<pages.size();i++){
			Page page = pages.get(i);
			List<Content> contents = getContents(page.getId(), page.getIdSite());
			for (int j=0;j<contents.size();j++){
				Content content = contents.get(j);
				if (content.getTypeContent().equals("3")){
					int like = Integer.parseInt(content.getContent());
					likes+=like;
				}
			}
		}
		return likes;
	}

	@Override
	public int getCountOfSites(String username) {
		return getSites(username).size();
	}
	
	public List<Page> getPages(String username){
		return pageDao.findByUsername(username);
	}

	@Override
	public void saveAchievement(Achievement achievement) {
		achievementRepository.save(achievement);
	}

	@Override
	public Achievement getAchievement(String username, String type) {
		return achievementRepository.findByUsernameAndType(username, type);
	}

	@Override
	public List<Achievement> getAchievements(String username) {
		return achievementRepository.findByUsername(username);
	}

	@Override
	public void deleteAchievement(String username, String type) {
		achievementRepository.deleteByUsernameAndType(username, type);
	}

	@Override
	public Content getLikefromPage(long idPage, String typeContent) {
		return contentDao.findByIdPageAndTypeContent(idPage, typeContent);
	}

	@Override
	public List<Site> getNewsSites() {
		List<NewSite> newSites = newSiteRepository.findAll();
		newSites.sort(new Comparator<NewSite>() {
			@Override
			public int compare(NewSite o1, NewSite o2) {
				if (o1.getPosition() < o2.getPosition()){
					return -1;
				} else if(o1.getPosition() > o2.getPosition()) {
					return 1;
				} 
				return 0;
			}
		});
		List<Site> sites = new ArrayList<Site>();
		for (int i=0;i<newSites.size();i++){
			sites.add(getSite(newSites.get(i).getSiteId()));
		}
		return sites;
	}

	@Override
	public void addNewSite(Site site) {
		List<NewSite> newSites = newSiteRepository.findAll();
		int position = 4;
		if (newSites.size()==0){
			NewSite newSite = new NewSite();
			newSite.setPosition(1);
			newSite.setSiteId(site.getId());
			newSiteRepository.save(newSite);
		} else if (newSites.size()==4){
			for (int j=0;j<newSites.size();j++){
				NewSite newSiteQ = newSites.get(j);
				newSiteQ.setPosition(newSiteQ.getPosition()+1);
				newSiteRepository.save(newSiteQ);
			}
			NewSite newSiteQ = new NewSite();
			newSiteQ.setPosition(1);
			newSiteQ.setSiteId(site.getId());
			newSiteRepository.save(newSiteQ);
		} else {
			for (int i=0;i<newSites.size();i++){
				NewSite newSite = newSites.get(i);
				if (newSite.getPosition()<position){
					position=newSite.getPosition();
				}
				newSite.setPosition(newSite.getPosition()+1);
				newSiteRepository.save(newSite);
			}
			NewSite newSite = new NewSite();
			newSite.setPosition(position);
			newSite.setSiteId(site.getId());
			newSiteRepository.save(newSite);
		}
	}
	
	public void deleteNewSiteLast(){
		NewSite newSite = newSiteRepository.findByPosition(5);
		newSiteRepository.delete(newSite);
	}

	@Override
	public int getCountOfLikes(String username) {
		return calculateLikeForUser(username);
	}
}

class TempSite{
	private long siteId;
	private int likes;
	
	public TempSite(long siteId, int likes) {
		this.siteId = siteId;
		this.likes = likes;
	}
	
	public int getLikes(){
		return likes;
	}
	
	public long getSiteId(){
		return siteId;
	}
}