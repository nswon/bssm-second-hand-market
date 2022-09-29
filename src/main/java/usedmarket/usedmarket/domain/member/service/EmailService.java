package usedmarket.usedmarket.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private static String ePw;

    public static void createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            key.append((rnd.nextInt(10)));
        }

        ePw = String.valueOf(key);
    }

    private MimeMessage createMessage(String email) throws Exception {

        createKey();
        log.info("보내는 대상 : " + email);
        log.info("인증 번호 : " + ePw);

        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("[부산소마고 중고 마켓] 회원가입 이메일 인증");

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1><span style='color:orange;'>메일인증</span> 안내입니다.</h1>";
        msgg+= "<br/>";
        msgg+= "<p>안녕하세요.</p>";
        msgg+= "부산소마고 중고 마켓을 이용해 주셔서 감사드립니다.";
        msgg+= "<p>아래 <span style='color:orange;'>'회원가입 인증 코드'</span> 를 복사 후 회원가입 입력을 완료해주세요.</p>";
        msgg+= "감사합니다.";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "<div align='center'>";
        msgg+= "<div style='font-size:130%;'>";
        msgg+= "<h1>";
        msgg+= ePw+"</h1><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("javaprojectnamse@gmail.com", "부산소마고 중고 마켓"));

        return message;
    }

    public void sendSimpleMessage(String email) throws Exception {
        MimeMessage message = createMessage(email);
        try{//예외처리
            javaMailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public boolean verityCode(String code) {
        return !ePw.equals(code);
    }
}

