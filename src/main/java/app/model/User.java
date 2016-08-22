package app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  private String role;
  
  public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}
@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
private String username;
  
  private String firstName;
  
  private String lastName;
  
  private String email;
  
  private String password;
  
  private boolean enabled;

  public User() { }
  
  public User(String username, String firstName, String lastName, String email, String password){
	  this.username=username;
	  this.firstName=firstName;
	  this.lastName=lastName;
	  this.email=email;
	  this.password=password;
	  this.enabled=true;
  }

public boolean isEnabled() {
	return enabled;
}

public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}


  
} 