package app.model;

public class Theme {
	private String backgroundColor;
	private String fontColor;
	private String comentColor;
	
	public Theme(String backgroundColor, String fontColor, String comentColor) {
		this.backgroundColor = backgroundColor;
		this.fontColor = fontColor;
		this.comentColor = comentColor;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public String getComentColor() {
		return comentColor;
	}
	public void setComentColor(String comentColor) {
		this.comentColor = comentColor;
	}
}
