package app.model;

import javax.persistence.Column;
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
@Table(name = "user_content")
public class Content {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name="content", columnDefinition="TEXT")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String content;
	private String typeContent;
	private int position;
	private int numContainer;
	private long idSite;
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTypeContent() {
		return typeContent;
	}
	public void setTypeContent(String typeContent) {
		this.typeContent = typeContent;
	}
	public int getNumContainer() {
		return numContainer;
	}
	public void setNumContainer(int numContainer) {
		this.numContainer = numContainer;
	}
	public long getIdSite() {
		return idSite;
	}
	public void setIdSite(long idSite) {
		this.idSite = idSite;
	}
	public long getIdPage() {
		return idPage;
	}
	public void setIdPage(long idPage) {
		this.idPage = idPage;
	}
	private long idPage;
	
	@Override
	public String toString() {
		return ": "+idSite+"-"+idPage;
	}
	
}