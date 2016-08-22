package app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String message;
  private String username;
  public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public long getIdPage() {
	return idPage;
}
public void setIdPage(long idPage) {
	this.idPage = idPage;
}
private long idPage;
  public Comment() {} 
  
} 