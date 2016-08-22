package app.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public Cloudinary createCloudinary(){
		Map<String, String> config = new HashMap<String, String>();
		config.put("cloud_name", env.getProperty("cloudinary_name"));
		config.put("api_key", env.getProperty("cloudinary_api_key"));
		config.put("api_secret", env.getProperty("cloudinary_api_secret"));
		Cloudinary cloudinary = new Cloudinary(config);
		return cloudinary;
	}
	
}
