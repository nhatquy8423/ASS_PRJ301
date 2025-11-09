/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Trien
 */
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailUtil {

    public static boolean sendOTP(String toEmail, String otp) {
        final String SMTP_HOST = "smtp.gmail.com";
        final String SMTP_USER = "nguyentientrien11t2@gmail.com";     // email gửi
        final String SMTP_PASS = "gbzohtervoheiokz";        // app password nếu Gmail + 2FA

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASS);
            }
        });
        session.setDebug(true); // <-- xem log chi tiết trên console

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Your OTP code");
            String content = "Mã OTP của bạn là: " + otp + 
                    "\nMã OTP của bạn sẽ hết hạn trong 5 phút.\n"
                    +"Vui lòng bỏ qua nếu không phải do bạn yêu cầu.";
            message.setText(content);

            Transport.send(message); // <-- gửi
            return true;
        } catch (MessagingException e) {
            e.printStackTrace(); // in stacktrace ra console để debug
            return false;
        }
    }
    public static void main(String[] args) {
    boolean sent = sendOTP("email_nhan_test@gmail.com", "123456");
    System.out.println("Gửi mail thành công? " + sent);
}
}
