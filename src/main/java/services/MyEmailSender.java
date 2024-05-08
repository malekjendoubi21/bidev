package services;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class MyEmailSender {



    // send(String to,String sub,String msg, html, code)
    public static void send(String to,String sub, String content){
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("notreatment.noreply@gmail.com","vcyrlkkbopzrzjuu");
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress("redblazers007@gmail.com"));
            message.setSubject("Nouvelle Réclamation");
            // message.setText(msg);
            message.setContent("Vous avez recu une reclamation","text/html; charset=utf-8");  //mettre html file
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}
    }


}






class SendMailSSL{
    public static void main(String[] args) {
        int code= 12344;
        String mail= "Vous avez recu une nouvelle reclamation";
        MyEmailSender.send("malekeljendoubi@gmail.com","Reset your password ",mail);

    }
}
