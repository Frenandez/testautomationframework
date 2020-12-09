package net.timentraining.tests;

import org.testng.annotations.Test;

import net.timentraining.core.TestBase;

public class Login2 extends TestBase{

	
	
	@Test
	public void login_1() {
		webui.launch("http://www.set2login1.com");
	}
	

	@Test
	public void login_2() {
		webui.launch("http://www.set2login2.com");
	}

	@Test
	public void login_3() {
		webui.launch("http://www.set2login3.com");
	}

	@Test
	public void login_4() {
		webui.launch("http://www.set2login4.com");
	}
	
}
