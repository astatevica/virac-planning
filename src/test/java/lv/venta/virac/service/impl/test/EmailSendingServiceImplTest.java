package lv.venta.virac.service.impl.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import lv.venta.virac.email.EmailSendingServiceImpl;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.mail.javamail.JavaMailSender;

//This connects Mockito to the JUnit test
@ExtendWith(MockitoExtension.class)
public class EmailSendingServiceImplTest {
	
	//Mocks FAKE email sender object
	@Mock
    private JavaMailSender mailSender;

	//Creates an EmailSendingServiceImpl and automatically puts a mock mailSender in it.
    @InjectMocks
    private EmailSendingServiceImpl emailService;

    //Mimics e-mail message
    private MimeMessage mimeMessage;

    @BeforeEach
    void setup() {

    	//Creates mimic message
        mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()));
    }

    @Test
    void testSendDocxEmailNotification() {

    	//Defines Mock behaviour
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        //Attaches fake file
        byte[] attachment = "Test DOCX".getBytes();

        //Calls function
        emailService.sendDocxEmailNotification(
                "to@test.lv",
                "from@test.lv",
                "Test Subject",
                "Hello",
                attachment
        );

        //Verifies if email has been sent
        verify(mailSender).send(any(MimeMessage.class));
    }

}
