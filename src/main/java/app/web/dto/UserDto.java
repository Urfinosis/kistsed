package app.web.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import app.validation.ValidEmail;
import app.validation.ValidName;
import app.validation.ValidPassword;

public class UserDto {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
	  
	  @NotNull
	  @Size(min = 1)
	  private String username;
	  
	  @ValidName(message="Invalid First")
	  private String firstName;
	  
	  @ValidName
	  private String lastName;
	  
	  @ValidEmail
	  private String email;
	  
	  @ValidPassword
	  private String password;
	
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