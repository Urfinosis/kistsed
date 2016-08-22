package app.model;

import java.util.ArrayList;
import java.util.List;

public class ContentBuilder {
	private static List<ContentTemp> contentTemp = new ArrayList<ContentTemp>();
	
	public static void addContent(String content, String type, int numContainer, int position){
		contentTemp.add(new ContentTemp(content, type, numContainer, position));
	}
	
	public static List<ContentTemp> getContentTemp(){
		return contentTemp;
	}
	
	public static void clear(){
		contentTemp.clear();
	}
}


