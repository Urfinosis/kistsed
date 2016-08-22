package app.model;

public class Url {
	private String type;
	private String url;
	private String name;
	
	public Url(String type, String url, String name) {
		this.type = type;
		this.url = url;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
