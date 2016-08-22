package app.model;

public class ContentTemp {
	private String content;
	private String type;
	private int numContainer;
	private int position;
	
	public ContentTemp(String content, String type, int numContainer, int position) {
		this.content=content;
		this.type=type;
		this.numContainer=numContainer;
		this.position=position;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumContainer() {
		return numContainer;
	}

	public void setNumContainer(int numContainer) {
		this.numContainer = numContainer;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}