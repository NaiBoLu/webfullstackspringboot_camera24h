package webcamera.com.vn.webapp.service.aouth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/* class nay phuc vu cong tac xac thuc qua gmail */
@Service
@Async
public class EmailService {
    //class javaMailSender cuar spring  ho tro xac thuc qua mail
    @Autowired
    private JavaMailSender mailSender;

    /*methodd sendEmail de gui email xac thuc toi mot user cu ther *
     + to: dia chi email nguoi nhan
     + subject: tieu de mail 
     + content: noi dung gui cua mail do
    */
    public void sendEmail(String to, String subject, String content){
        /*1. khoi tao bien cua class simplemailmessage(class mau mail chuan) */
        SimpleMailMessage message = new SimpleMailMessage();

        //2. xac nhan gui email voi thiet lap: to - subject - content
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        //3. tien hanh gui send message di len gmail
        mailSender.send(message);

    }
}

