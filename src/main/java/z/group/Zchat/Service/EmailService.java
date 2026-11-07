package z.group.Zchat.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String to,String subject,String body){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(subject);
        mail.setTo(to);
        mail.setText(body);

        javaMailSender.send(mail);
    }
}
