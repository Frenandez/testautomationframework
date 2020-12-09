package net.timentraining.core.mail;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.BodyTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.IMAPStore;

import net.timentraining.core.mail.HTMLLinkExtractor.HtmlLink;

public class EmailClient {

	private IMAPStore store;
	private Session session;
	private final String userId;
	private final String userEmailAddress;
	private final String userEmailPassword;
	
	/**
	 * @author rkarim
	 * Using this class, you can get email with various search criteria and you can compose an email too.
	 * Connect GMAIL IMAP with given emailId(email address) and password
	 * @param emailId
	 * @param password
	 */
;
	public EmailClient(String emailAddress,String uid, String password,String incomingServer, String outgoingServer, int outgoingPort){		
			
		this.userId=uid;
		this.userEmailAddress=emailAddress;
		this.userEmailPassword=password;
		setSession(incomingServer,outgoingServer,outgoingPort);
		
		
	}
	
	
	
	private void setSession(String incomingServer,String outgoingServer,int outgoingPort ){
		String smtpPort=Integer.toString(outgoingPort);
		
		Properties properties=new Properties();		
		//set smtp settings
		properties.put("mail.smtp.host", outgoingServer);
		properties.put("mail.smtp.socketFactory.port", smtpPort);
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", smtpPort);
		
		//set imap settings
		properties.put("mail.store.protocol", "imaps");
		
		

		Session session= Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userId, userEmailPassword);
			}
		  });
		
		//check connectivity
		try {
			this.store = (IMAPStore) session.getStore("imaps");
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
							}
		try {
			this.store.connect(incomingServer, this.userId, userEmailPassword);
			
			
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
		
		this.session=session;
	}
	
	public void ComposeEmail(String toEmailAddress, String Subject, String messageBody){
		
		try{
			MimeMessage message=new MimeMessage(this.session);
			message.setFrom(new InternetAddress(this.userEmailAddress));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailAddress));  
			message.setHeader("Subject",Subject);  
			message.setText(messageBody);  
			
			Transport.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}
	
		
	}
	
	
	private IMAPStore getStore(){
		return this.store;
	}
	
	private void mark_email_read(Message msg){
		try {
			msg.setFlag(Flag.SEEN,true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private IMAPFolder getFolder(String folderName, int openStatus){
		IMAPFolder folder = null;
		this.store= getStore();
		try {
			folder=(IMAPFolder) store.getFolder(folderName);
			folder.open(openStatus);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return folder;
		
	}
	
	/***
	*In your test if you ever want to check an email  that sent out by your AUT, you can use this method to test this email. 
	*This method will return the latest email body as a String.
	*
	***/
	public String get_most_recent_email(){
		return get_most_recent_email(this.userId);
	}
	
	private String getTextFromMimeMultipart(
	        MimeMultipart mimeMultipart)  throws MessagingException, IOException{
	    String result = "";
	    int count = mimeMultipart.getCount();
	    for (int i = 0; i < count; i++) {
	        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	        if (bodyPart.isMimeType("text/plain")) {
	            result = result + "\n" + bodyPart.getContent();
	            break; // without break same text appears twice in my tests
	        } else if (bodyPart.isMimeType("text/html")) {
	            String html = (String) bodyPart.getContent();
	            result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
	        } else if (bodyPart.getContent() instanceof MimeMultipart){
	            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	        }
	    }
	    return result;
	}
	
	/**
     * This method will download the attachments from today’s mail based on MailSubject and MailBody. 
      * Note : Attachments will be download for the mails which are received after particular timeframe.
     * @param downloadFilePath
     * @param textContainsInSubject
     * @param textContainsInBody
     * @param timebeforemailreceived
     */

     public void downloadMailAttachments(String downloadFilePath, String textContainsInSubject, String textContainsInBody, Date timebeforemailreceived) throws MessagingException, IOException
     {
                     Folder inbox=getFolder("INBOX", Folder.READ_WRITE);
                     ReceivedDateTerm rd = new ReceivedDateTerm(ReceivedDateTerm.EQ, Calendar.getInstance().getTime());
                     SubjectTerm st = new SubjectTerm(textContainsInSubject);
                     BodyTerm bt = new BodyTerm(textContainsInBody);       
                     SearchTerm s;
                     
                     if(textContainsInSubject != ""){
                                     s = new AndTerm(new SearchTerm[]{rd,st,bt});
                     }
                     else
                     {
                                     s = new AndTerm(new SearchTerm[]{rd,bt});
                     }
                     
                     Message[] m = null;
                     try {
                                     m = inbox.search(s);
                     } catch (MessagingException e) {
                                     e.printStackTrace();
                     }
                     
                     if (m.length !=0)
                     {
                                     for(int i=0;i<m.length;i++)
                                     {
                                                     Message message = m[i];
                                                     String contentType = message.getContentType();
                                                     @SuppressWarnings("unused")
													String messageContent = "";
                                                   @SuppressWarnings("unused")
												String attachFiles = "";
                                                 Date receivedDateTime = message.getReceivedDate();
                                                   if(receivedDateTime.after(timebeforemailreceived))
                                                   {
                                                                     if(contentType.contains("multipart"))
                                                                     {
                                                                                     Multipart multiPart = (Multipart) message.getContent();
                                                                                     int numberOfParts = multiPart.getCount();
                                                                                     for (int partCount = 0; partCount < numberOfParts; partCount++) {
                                                                                                     MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                                                                                                     if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                                                                                                     // this part is attachment
                                                                                                                     String fileName = part.getFileName();
                                                                                                                     attachFiles += fileName + ", ";
                                                                                                                     part.saveFile(downloadFilePath + fileName);
                                                                                                                     mark_email_read(message);
                                                                                                     } else {
                                                                                                                     // this part may be the message content
                                                                                                                     messageContent = part.getContent().toString();
                                                                                                                     mark_email_read(message);
                                                                                                     }
                                                                                     }
                                                                     }
                                                     }
                                     }
                     }
     }

	
	
	/***
	 * Retrieve most recent email for a given recipient email address
	 * @param recipientEmailAddress
	 * @return: null if no email found. email message content if found
	 * @throws MessagingException
	 */
	public String get_most_recent_email(String recipientEmailAddress){

		int num=-1;
		IMAPFolder inbox=getFolder("INBOX", Folder.READ_WRITE);
		RecipientStringTerm rs = new RecipientStringTerm(RecipientType.TO, recipientEmailAddress);
		
		SearchTerm s = new AndTerm(new SearchTerm[]{rs});
		
		
		
		String contentString=null;
		IMAPMessage[] m = null;
		try {			
			m = (IMAPMessage[]) inbox.search(s);
			
			if(m.length>0){
				num=m.length-1;
				
				try {
					
					
					
					if(m[num].getContent() instanceof String){
						contentString=(String) m[num].getContent();
						mark_email_read(m[num]);
					}else if(m[num].getContent() instanceof Multipart){

						contentString=getTextFromMimeMultipart((MimeMultipart) m[num].getContent());
						mark_email_read(m[num]);
					}
					
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}else{
				//throw exception or do nothing when no email found??
			}
			
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}
		
		
		
		

			
			
		
		
			
			return contentString;			
		} 
	


	/***
	 * This  method will fetch all email in inbox, consider performance if recipient mailbox has large number of email. 
	* Returns: This method returns Email body as an array
	 */
	public String[] get_email(){
		return get_email(this.userId);
	}
	
	/***
	 * This  method will fetch all email in inbox. It takes one String parameter for recipient email address. You may ask if I check emails on my inbox why should I need to specify recipient email? Well, you don't have to if you are expecting email in your inbox use get_email() instead. Which takes no parameter. This method has his own purpose lets talk abut it. 
	 * If you ever need to test many email to many different recipient, how will you manage creating that many account just to test? Simple solution is: you can impersonate your emailId for a new one by using '+' sign. here is an example : lets say your gmail account is: abc@gmail.com. and you want to send an email to different account, and you don't want to create new account for that. What you can do is: use 'abc+a@gmail.com' system will think it is a new email address, but its actually not, all email come to your inbox (abc@gmail.com). If you need another one use 'abc+whatever@gmail.com'. 
	 * So this is why you may need to use this method to test the recipient email address as well in same mailbox.

	* Returns: This method returns Email body as an array
	 */
	public String[] get_email(String recipientEmailAddress){
		
		ArrayList<String> list = new ArrayList<>();
		
		IMAPFolder inbox=getFolder("INBOX", Folder.READ_WRITE);
		RecipientStringTerm rs = new RecipientStringTerm(RecipientType.TO, recipientEmailAddress);
		//ReceivedDateTerm rd = new ReceivedDateTerm(ReceivedDateTerm.EQ, Calendar.getInstance().getTime());
		
		
		SearchTerm s = new AndTerm(new SearchTerm[]{rs});
		
		
		
		Multipart part;
		String contentString=null;
		
		try {
			Message[] m=inbox.search(s);
			for (int i = 0; i < m.length; i++) {
				try {
					
					if(m[i].getContent() instanceof String){
						contentString=(String) m[i].getContent();
						mark_email_read(m[i]);					
						
					}else if(m[i].getContent() instanceof Multipart){
						part=(Multipart) m[i].getContent();
						contentString=part.getBodyPart(0).getContent().toString();
						mark_email_read(m[i]);
					}
				
					
					list.add(contentString);
				} catch (IOException e) {
					e.printStackTrace();
				}
			
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		String[] newArray = new String[list.size()];
		list.toArray(newArray);
		return newArray;
		
		
	}
	
	public String[] get_email(String senderEmailAddress, String recipientEmailAddress, String subject){
		
		ArrayList<String> list = new ArrayList<>();
		
		Folder inbox=getFolder("INBOX", Folder.READ_WRITE);
		FromStringTerm fs = new FromStringTerm(senderEmailAddress);
		RecipientStringTerm rs = new RecipientStringTerm(RecipientType.TO, recipientEmailAddress);
		//ReceivedDateTerm rd = new ReceivedDateTerm(ReceivedDateTerm.EQ, Calendar.getInstance().getTime());
		SubjectTerm st = new SubjectTerm(subject);
		
		
		SearchTerm s = new AndTerm(new SearchTerm[]{fs,rs,st});
		
		
		
		Multipart part;
		String contentString=null;
		
		try {
			Message[] m=inbox.search(s);
			for (int i = 0; i < m.length; i++) {
				try {
					
					if(m[i].getContent() instanceof String){
						contentString=(String) m[i].getContent();
						mark_email_read(m[i]);
					}else if(m[i].getContent() instanceof Multipart){
						part=(Multipart) m[i].getContent();
						contentString=part.getBodyPart(0).getContent().toString();
						mark_email_read(m[i]);
					}
				
					
					list.add(contentString);
				} catch (IOException e) {
					e.printStackTrace();
				}
			
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		String[] newArray = new String[list.size()];
		list.toArray(newArray);
		return newArray;
		
		
	}
	public String[] get_email(String recipientEmailAddress, String subject){
		
		ArrayList<String> list = new ArrayList<>();
		
		Folder inbox=getFolder("INBOX", Folder.READ_WRITE);
		RecipientStringTerm rs = new RecipientStringTerm(RecipientType.TO, recipientEmailAddress);
		//ReceivedDateTerm rd = new ReceivedDateTerm(ReceivedDateTerm.EQ, Calendar.getInstance().getTime());
		SubjectTerm st = new SubjectTerm(subject);
		
		
		SearchTerm s = new AndTerm(new SearchTerm[]{rs,st});
		
		
		Multipart part;
		String contentString=null;
		try {
			Message[] m=inbox.search(s);
			for (int i = 0; i < m.length; i++) {
				try {
					
					if(m[i].getContent() instanceof String){
						contentString=(String) m[i].getContent();
					}else if(m[i].getContent() instanceof Multipart){
						part=(Multipart) m[i].getContent();
						contentString=part.getBodyPart(0).getContent().toString();
					}
				
					
					list.add(contentString);
				} catch (IOException e) {
					e.printStackTrace();
				}
			
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] newArray = new String[list.size()];
		list.toArray(newArray);
		return newArray;
	
		
		
	}
	

	
	
	/****
	 * This is very handy method to retrieve link from an email. Say, you are expecting an email with one or more link where you need to click. Use this method to achieve that.
	 * Note that: It will only look the most recent email and if multiple link found it will give you all in List.
	 * @param recipientAddress-Note that this method will only look for the last email receive for a given recipient.
	 *  @return - List of link
	 */
	public List<String> get_link_from_email(String recipientAddress){
		List<String> res = new ArrayList<>();
		String str = null;

		str = get_most_recent_email(recipientAddress);
		HTMLLinkExtractor htmlLinkExtractor = new HTMLLinkExtractor();
		
		for(HtmlLink h:htmlLinkExtractor.grabHTMLLinks(str)){
			res.add(h.link);
		}
		
	return res;


	}
		
	/**
	 * This method will let you know if given email received today. This method also will look for email that are "UNREAD EMAILS" only.
	 * Note that this method will not mark email as READ. 
	 * @param recipientEmailAddress
	 * @param textContainsInSubject
	 * @param textContainsInBody
	 * @return True : if email found, false if not
	 */
	public boolean check_todays_email_in_inbox(String recipientEmailAddress, String textContainsInSubject, String textContainsInBody){
		
		Folder inbox=getFolder("INBOX", Folder.READ_WRITE);
		RecipientStringTerm rs = new RecipientStringTerm(RecipientType.TO, recipientEmailAddress);
		ReceivedDateTerm rd = new ReceivedDateTerm(ReceivedDateTerm.EQ, Calendar.getInstance().getTime());
		SubjectTerm st = new SubjectTerm(textContainsInSubject);
		BodyTerm bt = new BodyTerm(textContainsInBody);
		Flags flags = new Flags(Flags.Flag.SEEN);
		FlagTerm ft = new FlagTerm(flags, false);	
		
		SearchTerm s = new AndTerm(new SearchTerm[]{rs,rd,st,bt,ft});
		
		
		
		Message[] m = null;
		try {
			m = inbox.search(s);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return m.length!=0;

	}
}
