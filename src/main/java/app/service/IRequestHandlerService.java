package app.service;

import java.util.List;

import app.model.Content;
import app.model.Page;

public interface IRequestHandlerService {
	void handlingSaveContent(List<String> contents, Page page);
	void handlingEditContent(long pageId, String username, int numContainers, List<String> list);
	boolean isValidUrl(String username);
	boolean isValidUrl(String username, long siteId);
	boolean isValidUrl(String username, long siteId, long pageId);
	void handlingTags(String tags, long idSite);
	List<List<Content>> handlingSortContent(long pageId, long siteId, int numContainers);
	void handlingEditTags(String tags, long idSite);
}
