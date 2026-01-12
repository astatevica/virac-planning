package lv.venta.virac.dto;

public class JournalDTO {

	private int idJournal;
	private String name;
	
	public JournalDTO() {
		
	}
	
	public JournalDTO(int idJournal, String name) {
		this.idJournal = idJournal;
		this.name = name;
	}
	
	public int getIdJournal() {
		return idJournal;
	}
	public void setIdJournal(int idJournal) {
		this.idJournal = idJournal;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
