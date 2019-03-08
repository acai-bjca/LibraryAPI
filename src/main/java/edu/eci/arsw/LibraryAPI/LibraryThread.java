package edu.eci.arsw.LibraryAPI;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
 
public class LibraryThread extends Thread {

    private final int milisegundos = 3000;
    private String correo;
    private String asunto = "Respuesta de solicitud";
    private String cuerpo = "El libro se ha creado correctamente";
    private String remitente = "amalialfonsoc@gmail.com";
    private String clave = "pruebaarsw";	

    public LibraryThread(String correo) {
        super();
        this.correo = correo;
    }

    @Override
    public void run() {
        try {
        	Thread.sleep(milisegundos);
            //this.wait(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
			sendMail();
		} catch (Exception e) {			
			e.printStackTrace();
		}
    }
    
	public void sendMail() throws Exception {
        Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitente);
	    props.put("mail.smtp.clave", clave);    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);

	    try {	    	
	        message.setFrom(new InternetAddress(remitente));
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo, false));  //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	        message.setText(cuerpo);
	        Transport.send(message);
	        //EmailUtil.sendEmail(session, emailID,"SimpleEmail Testing Subject", "SimpleEmail Testing Body");
	        /*
	        Transport transport = session.getTransport();
	        transport.connect(remitente, clave);
	        */
	        //Transport.sendMessage(message, message.getAllRecipients());
	        //transport.close();
	        //System.out.println("Se envio el correo");
	        
	    }
	    catch (MessagingException me) {
	        me.printStackTrace();   
	    }	    
	}
}
