package lv.venta.virac.dto;

public class ArticlePlanDTO {

	private int idArticlePlan;
	private int idPlan;
	private int idScientificArticles;
	private String articleComments;
	private String publicationLink;
	
	public ArticlePlanDTO() {
		
	}
	
	public ArticlePlanDTO(int idArticlePlan, int idPlan, int idScientificArticles, String articleComments, String publicationLink) {
		this.idArticlePlan = idArticlePlan;
		this.idPlan = idPlan;
		this.idScientificArticles = idScientificArticles;
		this.articleComments = articleComments;
		this.publicationLink = publicationLink;
	}
	
	public int getIdArticlePlan() {
		return idArticlePlan;
	}
	public void setIdArticlePlan(int idArticlePlan) {
		this.idArticlePlan = idArticlePlan;
	}
	public int getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(int idPlan) {
		this.idPlan = idPlan;
	}
	public int getIdScientificArticles() {
		return idScientificArticles;
	}
	public void setIdScientificArticles(int idScientificArticles) {
		this.idScientificArticles = idScientificArticles;
	}
	public String getArticleComments() {
		return articleComments;
	}
	public void setArticleComments(String articleComments) {
		this.articleComments = articleComments;
	}
	public String getPublicationLink() {
		return publicationLink;
	}
	public void setPublicationLink(String publicationLink) {
		this.publicationLink = publicationLink;
	}
	
}
