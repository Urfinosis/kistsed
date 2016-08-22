package app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mytables")
public class MyTable {

  public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdPage() {
		return idPage;
	}

	public void setIdPage(long idPage) {
		this.idPage = idPage;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  private long idPage;
  
  private long idSite;
  
  public long getIdSite() {
	return idSite;
}

public void setIdSite(long idSite) {
	this.idSite = idSite;
}

private int x;
  
  private int y;
  
  public MyTable() {}
}