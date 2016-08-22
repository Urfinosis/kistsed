package app.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Content;
import app.model.ContentBuilder;
import app.model.ContentTemp;
import app.model.MyTable;
import app.model.Page;
import app.model.Tag;

@Service
@Transactional
public class RequestHandlerService implements IRequestHandlerService {

	@Autowired
	private IUserService userService;
	
	@Override
	public void handlingSaveContent(List<String> contents, Page page) {
		List<ContentTemp> contentTemp = ContentBuilder.getContentTemp();
		System.out.println("sss"+contentTemp.size());
		for (int i=0;i<contentTemp.size();i++){
			ContentTemp temp = contentTemp.get(i);
			Content content = new Content();
			content.setContent(temp.getContent());
			content.setTypeContent(temp.getType());
			content.setPosition(temp.getPosition());
			content.setNumContainer(temp.getNumContainer());
			content.setIdPage(page.getId());
			content.setIdSite(page.getIdSite());
			userService.saveContent(content);
		}
		ContentBuilder.clear();
		List<ContentTemp> test = ContentBuilder.getContentTemp();
		System.out.println("test:"+test.size());
		int createTable = 0;
		int positionTable = 0;
		int numContainer = 1;
		for (int i=0; i<contents.size()-3;i+=4){
			String typeContent = contents.get(i+1);
			numContainer = Integer.parseInt(contents.get(i+2));
			System.out.println(numContainer);
			if (typeContent.equals("5")){
				if (createTable==0){
					createTable = 1;
					positionTable = Integer.parseInt(contents.get(i+3));
				}
				int x = Integer.parseInt(contents.get(i));
				int y = Integer.parseInt(contents.get(i+4));		
				MyTable myTable = new  MyTable();
				myTable.setX(x);
				myTable.setY(y);
				myTable.setIdPage(page.getId());
				myTable.setIdSite(page.getIdSite());
				userService.saveMyTable(myTable);
				i+=4;
			} else {
				Content content = new Content();
				String contentString = contents.get(i);
				System.out.println(contents.get(i));
				System.out.println(Integer.parseInt(contents.get(i+3)));
				System.out.println(typeContent);
				System.out.println(numContainer);
				if (typeContent.equals("6")){
					contentString = contentString.replaceAll("#", ",");
				}
				content.setContent(contentString);
				content.setTypeContent(typeContent);
				content.setPosition(Integer.parseInt(contents.get(i+3)));
				content.setNumContainer(numContainer);
				content.setIdPage(page.getId());
				content.setIdSite(page.getIdSite());
				userService.saveContent(content);
			}
		}
		if (createTable == 1){
			Content content = new Content();
			content.setContent("0");
			content.setTypeContent("5");
			content.setPosition(positionTable);
			content.setNumContainer(numContainer);
			content.setIdPage(page.getId());
			content.setIdSite(page.getIdSite());
			userService.saveContent(content);
		}
	}

	@Override
	public boolean isValidUrl(String username) {
		boolean result = true;
		if (userService.getUser(username) == null){
			result = false;
		} 
		return result;
	}

	@Override
	public boolean isValidUrl(String username, long siteId) {
		boolean result = true;
		if (userService.getUser(username) == null){
			result = false;
		} else
		if (userService.getSite(siteId, username) == null){
			result = false;
		}
		return result;
	}

	@Override
	public boolean isValidUrl(String username, long siteId, long pageId) {
		boolean result = true;
		if (userService.getUser(username) == null){
			result = false;
		} else
		if (userService.getSite(siteId, username) == null){
			result = false;
		} else
		if (userService.getPage(pageId, username) == null){
			result = false;
		}
		return result;
	}

	@Override
	public void handlingTags(String tags, long idSite) {
		String tagString = "";
		for (int i=0;i<tags.length();i++){
			char temp = tags.charAt(i);
			if (temp == ','){
				Tag tag = new Tag();
				tag.setIdSite(idSite);
				tag.setTag(tagString);
				tagString= "";
				userService.saveTag(tag);
			} 
			else if (i+1==tags.length()){
				tagString+=temp;
				Tag tag = new Tag();
				tag.setIdSite(idSite);
				tag.setTag(tagString);
				tagString= "";
				userService.saveTag(tag);
			}
			else {
				tagString+=temp;
			}
		}
	}

	@Override
	public void handlingEditContent(long pageId, String username, int numContainers, List<String> list) {
		Page page = userService.getPage(pageId, username);
		page.setNumContainers(numContainers);
		userService.savePage(page);
		userService.deleteContentFromPage(page.getId());
		List<ContentTemp> contentTemp = ContentBuilder.getContentTemp();
		for (int i=0;i<contentTemp.size();i++){
			ContentTemp temp = contentTemp.get(i);
			Content content = new Content();
			content.setContent(temp.getContent());
			content.setTypeContent(temp.getType());
			content.setPosition(temp.getPosition());
			content.setNumContainer(temp.getNumContainer());
			content.setIdPage(page.getId());
			content.setIdSite(page.getIdSite());
			userService.saveContent(content);
		}
		ContentBuilder.clear();
		int createTable = 0;
		int positionTable = 0;
		int numContainer = 1;
		for (int i=0; i<list.size()-3;i+=4){
			String typeContent = list.get(i+1);
			numContainer = Integer.parseInt(list.get(i+2));
			System.out.println(numContainer);
			if (typeContent.equals("5")){
				if (createTable==0){
					createTable = 1;
					positionTable = Integer.parseInt(list.get(i+3));
				}
			} else {
				Content content = new Content();
				String contentString = list.get(i);
				if (typeContent.equals("6")){
					contentString = contentString.replaceAll("#", ",");
				}
				content.setContent(contentString);
				content.setTypeContent(typeContent);
				content.setPosition(Integer.parseInt(list.get(i+3)));
				content.setNumContainer(numContainer);
				content.setIdPage(page.getId());
				content.setIdSite(page.getIdSite());
				userService.saveContent(content);
			}
		}
		if (createTable == 1){
			Content content = new Content();
			content.setContent("0");
			content.setTypeContent("5");
			content.setPosition(positionTable);
			content.setNumContainer(numContainer);
			content.setIdPage(page.getId());
			content.setIdSite(page.getIdSite());
			userService.saveContent(content);
		}
	}

	@Override
	public List<List<Content>> handlingSortContent(long pageId, long siteId, int numContainers) {
		List<Content> contents = userService.getContents(pageId, siteId);
		contents.sort(new Comparator<Content>() {

			@Override
			public int compare(Content o1, Content o2) {
				if (o1.getPosition() < o2.getPosition()){
					return -1;
				} else if(o1.getPosition() > o2.getPosition()) {
					return 1;
				} 
				return 0;
			}
			
		});
		List<List<Content>> sortedContentList = new ArrayList<>();
		for(int i=0;i<numContainers;i++){
			sortedContentList.add(new ArrayList<Content>());
		}
		for (int i = 0; i < contents.size(); i++) {
			sortedContentList.get(contents.get(i).getNumContainer()-1).add(contents.get(i));
			System.out.println(contents.get(i).getPosition());
		}
		return sortedContentList;
	}

	@Override
	public void handlingEditTags(String tags, long idSite) {
		userService.deleteTags(idSite);
		String tagString = "";
		for (int i=0;i<tags.length();i++){
			char temp = tags.charAt(i);
			if (temp == ','){
				Tag tag = new Tag();
				tag.setIdSite(idSite);
				tag.setTag(tagString);
				tagString= "";
				userService.saveTag(tag);
			} 
			else if (i+1==tags.length()){
				tagString+=temp;
				Tag tag = new Tag();
				tag.setIdSite(idSite);
				tag.setTag(tagString);
				tagString= "";
				userService.saveTag(tag);
			}
			else {
				tagString+=temp;
			}
		}
	}

}
