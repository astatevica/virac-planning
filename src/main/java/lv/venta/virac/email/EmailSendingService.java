package lv.venta.virac.email;

public interface EmailSendingService {
	
	//R - retrieve all
	public abstract  void sendDocxEmailNotification(String toEmail, String fromEmail, String subject, String text, byte[] attachment);

}
