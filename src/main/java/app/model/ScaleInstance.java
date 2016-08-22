package app.model;

import java.util.HashMap;
import java.util.Map;

public class ScaleInstance {
	private static Map<String, Integer> imageScale = new HashMap<String, Integer>();
	
	public static void addScaleForImage(String public_id, int scale){
		imageScale.put(public_id, scale);
	}
	
	public static void changeScaleForImage(String public_id, int scale){
		imageScale.replace(public_id, scale);
	}
	
	public static int getScale(String public_id){
		return imageScale.get(public_id);
	}
}
