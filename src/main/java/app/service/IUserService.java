package app.service;

import java.util.List;

import app.model.Achievement;
import app.model.Comment;
import app.model.Content;
import app.model.MyTable;
import app.model.Page;
import app.model.Site;
import app.model.Tag;
import app.model.Url;
import app.model.User;
import app.model.VerificationToken;

public interface IUserService {
	public boolean emailExist(String email);
	public boolean usernameExist(String username);
	public boolean isAuth(Object principal);
	public boolean permissions(Object principal, String username);
	public boolean permissionsIsAdmin(Object principal);
	public void createVerificationToken(User user,String token);
	public VerificationToken getVerificationToken(String verificationToken);
	public void saveUser(User user);
	public void deleteUser(String username);
	public void deleteSite(long idSite);
	public void deleteSites(String username);
	public void deletePage(long idPage);
	public void deletePages(String username);
	public List<User> getUsers();
	public User getUser(String username);
	public void saveSite(Site site);
	public void savePage(Page page);
	public void saveContent(Content content);
	public Site getSite(long idSite);
	public Site getSite(long idSite, String username);
	public List<Site> getSites(String username);
	public Page getPage(long idPage, String username);
	public List<Page> getPages(long idSite, String username);
	public List<Page> findPages(String search);
	public List<Site> findSites(String search);
	public List<User> findUser(String search);
	public List<Content> findContents(String search);
	public List<Content> getContents(long idPage,long idSite);
	public void saveComment(Comment comment);
	public List<Comment> getComments(long idContent);
	public void saveMyTable(MyTable myTable);
	public List<MyTable> getMyTables(long idPage,long idSite);
	public List<Url> findAll(String search);
	public void saveTag(Tag tag);
	public List<String> getTagsString(long idSite);
	public List<String> getTagsString();
	public List<Tag> findTags(String search);
	public void deleteContentFromPage(long pageId);
	public void deleteCommentFromPage(long pageId);
	public void deleteMyTableFromPage(long pageId);
	public Page getPage(long id);
	public void deleteTags(long idSite);
	public List<Site> getPopularSites();
	public int getCountOfSites(String username);
	public int getCountOfLikes(String username);
	public void saveAchievement(Achievement achievement);
	public Achievement getAchievement(String username, String type);
	public List<Achievement> getAchievements(String username);
	public void deleteAchievement(String username, String type);
	public Content getLikefromPage(long idPage, String typeContent);
	public List<Site> getNewsSites();
	public void addNewSite(Site site);
	public void deleteNewSiteLast();
}
