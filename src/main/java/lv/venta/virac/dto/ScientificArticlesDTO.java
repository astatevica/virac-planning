package lv.venta.virac.dto;

public class ScientificArticlesDTO {
	
	private int idArticle;
	private String name;
	private String coAuthors;
	private int idJournal;
	
	public ScientificArticlesDTO() {
	}
	
	public ScientificArticlesDTO(int idArticle, String name, String coAuthors, int idJournal) {
		this.idArticle = idArticle;
		this.name = name;
		this.coAuthors = coAuthors;
		this.idJournal = idJournal;
	}
	
	public int getIdArticle() {
		return idArticle;
	}
	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCoAuthors() {
		return coAuthors;
	}
	public void setCoAuthors(String coAuthors) {
		this.coAuthors = coAuthors;
	}
	public int getIdJournal() {
		return idJournal;
	}
	public void setIdJournal(int idJournal) {
		this.idJournal = idJournal;
	}

}
