package app.model;

import java.util.HashMap;
import java.util.Map;

public class EffectInstance {
	private static Map<String, String> imageEffect = new HashMap<String, String>();
	
	public static void addEffectForImage(String public_id, String effect){
		imageEffect.put(public_id, effect);
	}
	
	public static void changeEffectForImage(String public_id, String effect){
		imageEffect.replace(public_id, effect);
	}
	
	public static String getEffect(String public_id){
		return imageEffect.get(public_id);
	}
}
