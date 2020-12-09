package net.timentraining.core.mail;

public class Gmail extends EmailClient{

	public Gmail(String emailId, String password) {
		super(emailId,emailId, password, "smtp.gmail.com","imap.gmail.com",465);
	}

}
