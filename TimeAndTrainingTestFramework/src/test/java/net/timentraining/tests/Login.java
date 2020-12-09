package net.timentraining.tests;

import org.testng.annotations.Test;

import net.timentraining.core.TestBase;
import net.timentraining.core.mail.Gmail;

public class Login extends TestBase{

	
	@Test
	public void login1() {
		Gmail gmail = new Gmail("timentrainingstudent@gmail.com", "time&training");
		String em = gmail.get_most_recent_email();
		System.out.println(em);
	}
	@Test
	public void login_1() {
		webui.launch("http://www.set1login1.com");
	}
	

	@Test
	public void login_2() {
		webui.launch("http://www.set1login2.com");
	}

	@Test
	public void login_3() {
		webui.launch("http://www.set1login3.com");
	}

	@Test
	public void login_4() {
		webui.launch("http://www.set1login4.com");
	}
	
}
