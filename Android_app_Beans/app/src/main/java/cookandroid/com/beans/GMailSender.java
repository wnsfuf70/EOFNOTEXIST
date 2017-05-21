package cookandroid.com.beans;

import com.sun.mail.smtp.SMTPMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

public class GMailSender extends Authenticator {
	    private String server = "smtp.gmail.com";
   	    private String user;
	    private String password;
	    private Session session;

	    public GMailSender(String user, String password) {
	        this.user = user;
	        this.password = password;
	        Properties props = new Properties();
	        props.setProperty("mail.transport.protocol","smtp");
	        props.setProperty("mail.host", server);
	        props.setProperty("mail.smtp.auth","true");
	        props.setProperty("mail.smtp.port","465");
	        props.setProperty("mail.smtp.socketFactory.port", "465");
	        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.setProperty("mail.smtp.socketFactory.fallback", "false");
	        props.setProperty("mail.smtp.quitwait", "false"); //전송후 응답메세지를 받지않고 종료

	        session = Session.getDefaultInstance(props,this);
	    }
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, password);
		}

	    public void sendMail(String subject, String body, String sender, String recipients) throws Exception{

			SMTPMessage message = new SMTPMessage(session);
	        message.setSender(new InternetAddress(sender));
			//setDataHandler() 메소드는 DataHandler 객체를 파라미터로 전달받으며,
			//DataHalder 클래스는 DataSource로부터 데이터를 읽어올 수 있도록 해 주는 핸들러 역할을 한다
	        message.setSubject(subject);  
	        message.setDataHandler(new DataHandler(new ByteArrayDataSource( body.getBytes(), "text/plain")));

			if (recipients.indexOf(',') > 0)
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));  
	        else  
	            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));  

			Transport.send(message);
	    }  

	    public class ByteArrayDataSource implements DataSource{
	        private byte[] data;  
	        private String type;  

	        public ByteArrayDataSource(byte[] data, String type) {
	            this.data = data;  
	            this.type = type;  
	        }
			@Override
	        public String getContentType() {
	            if (type == null)
	                return "application/octet-stream";
	            else
	                return type;
	        }
			@Override
			public String getName() { return "ByteArrayDataSource"; }
			@Override
			public InputStream getInputStream() throws IOException { return new ByteArrayInputStream(data); }
			@Override
	        public OutputStream getOutputStream() throws IOException { throw new IOException("Not Supported"); }
	    }
	}
