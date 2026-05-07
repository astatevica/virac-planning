package lv.venta.virac.email;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSendingServiceImpl implements EmailSendingService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendDocxEmailNotification(String toEmail, String fromEmail, String subject, String text, byte[] attachment) {
		
		MimeMessage message = mailSender.createMimeMessage();
		try {
			//Create new Mime type e-mail object
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(fromEmail);
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(text);

	        // Create temporary file
		    File tempFile = File.createTempFile("VIRAC_plan_", ".docx");

		    // Write byte[] into file
		    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
		        fos.write(attachment);
		    }
		    
			//File transformation to resource
			FileSystemResource file = new FileSystemResource(tempFile);
			//Add file to e-mail
			helper.addAttachment(file.getFilename(), file);
			//Send e-mail
			mailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
