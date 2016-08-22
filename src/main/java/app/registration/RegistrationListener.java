package app.registration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import app.model.User;
import app.service.IUserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	
    @Autowired
    private IUserService service;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Environment env;
 

    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(final OnRegistrationCompleteEvent event) { 
        Thread mail = new Thread(new Runnable() {			
			@Override
			public void run() {
				User user = event.getUser();
		        String token = UUID.randomUUID().toString();
		        service.createVerificationToken(user, token);      
		        String recipientAddress = user.getEmail();
		        String subject = "Registration Confirmation";
		        String confirmationUrl = event.getAppUrl() + "/regitrationConfirm.html?token=" + token;
				SimpleMailMessage email = new SimpleMailMessage();
		        email.setFrom(env.getProperty("smtp.username"));
		        email.setTo(recipientAddress);
		        email.setSubject(subject);
		        email.setText("Для активации аккаунта пройдите по ссылке " + "http://localhost:8080" + confirmationUrl);
				mailSender.send(email);	
			}
		});
        mail.start();
    }
}