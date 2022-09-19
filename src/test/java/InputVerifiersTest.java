



import static org.junit.Assert.assertTrue;

import javax.swing.JFormattedTextField;

import org.junit.Test;

import utils.AlphabeticVerifier;
import utils.AlphanumericVerifier;
import utils.EmailVerifier;
import utils.MarkVerifier;
import utils.ModuleCodeVerifier;
import utils.StudentIDVerifier;

public class InputVerifiersTest {
	
	JFormattedTextField t = new JFormattedTextField();
	
	AlphabeticVerifier alpha = new AlphabeticVerifier();
	AlphanumericVerifier alpha_num = new AlphanumericVerifier();
	EmailVerifier email = new EmailVerifier();
	MarkVerifier mark = new MarkVerifier();
	ModuleCodeVerifier module = new ModuleCodeVerifier();
	StudentIDVerifier student = new StudentIDVerifier();
	
	@Test
	public void alphabetic_verifier() {
		t.setText("text");
		assertTrue("Correct input failed", alpha.verify(t));
		
		t.setText("text123");
		assertTrue("Incorrect input passed", !alpha.verify(t));
		
		t.setText("text!!*");
		assertTrue("Incorrect input passed", !alpha.verify(t));
		
		//No white spaces allowed
		t.setText("Test Testificate");
		assertTrue("Incorrect input passed", !alpha.verify(t));
	}
	
	@Test
	public void alphanumeric_verifier() {
		t.setText("text");
		assertTrue("Correct input failed", alpha_num.verify(t));
		
		t.setText("text123");
		assertTrue("Correct input failed", alpha_num.verify(t));
		
		t.setText("123");
		assertTrue("Correct input failed", alpha_num.verify(t));
		
		//No white spaces allowed
		t.setText("Test 123");
		assertTrue("Incorrect input passed", !alpha_num.verify(t));
		
		t.setText("Test123!;.@");
		assertTrue("Incorrect input passed", !alpha_num.verify(t));
	}
	
	@Test
	public void email() {
		t.setText("example@domain.com");
		assertTrue("Correct input failed", email.verify(t));
		
		t.setText("another_example@domain.co.uk");
		assertTrue("Correct input failed", email.verify(t));
		
		t.setText("test.testificate@the-domain.info");
		assertTrue("Correct input failed", email.verify(t));
		
		t.setText("Person.name97@the-domain.info");
		assertTrue("Correct input failed", email.verify(t));
		
		t.setText("regulartext");
		assertTrue("Incorrect input passed", !email.verify(t));
		
		t.setText("regular text with spaces");
		assertTrue("Incorrect input passed", !email.verify(t));
		
		t.setText("regulartext3");
		assertTrue("Incorrect input passed", !email.verify(t));
		
		t.setText("email@domain_field.com");
		assertTrue("Incorrect input passed", !email.verify(t));
		
		//Discovered an issue with this case (patched)
		t.setText(".@q.com");
		assertTrue("Incorrect input passed", !email.verify(t));
	}
	
	@Test
	public void mark() {
		t.setText("100");
		assertTrue("Correct input failed", mark.verify(t));
		
		t.setText("-1");
		assertTrue("Incorrect input passed", !mark.verify(t));
		
		t.setText("101");
		assertTrue("Incorrect input passed", !mark.verify(t));
		
		t.setText("abcd");
		assertTrue("Incorrect input passed", !mark.verify(t));
		
		t.setText("(*;");
		assertTrue("Incorrect input passed", !mark.verify(t));
		
		t.setText("1oo");
		assertTrue("Incorrect input passed", !mark.verify(t));
		
		t.setText("9 0");
		assertTrue("Incorrect input passed", !mark.verify(t));
	}
	
	@Test
	public void module() {
		t.setText("CS5000");
		assertTrue("Correct input failed", module.verify(t));
		
		t.setText("CS500o");
		assertTrue("Incorrect input passed", !module.verify(t));
		
		t.setText("CS500 ");
		assertTrue("Incorrect input passed", !module.verify(t));
		
		t.setText("C 5000");
		assertTrue("Incorrect input passed", !module.verify(t));
		
		t.setText("CSQWER");
		assertTrue("Incorrect input passed", !module.verify(t));
		
		t.setText("001234");
		assertTrue("Incorrect input passed", !module.verify(t));
	}
	
	@Test
	public void student() {
		t.setText("ABC1234");
		assertTrue("Correct input failed", student.verify(t));
		
		t.setText("ABC123A");
		assertTrue("Incorrect input passed", !student.verify(t));
		
		t.setText("ABC123");
		assertTrue("Incorrect input passed", !student.verify(t));
		
		t.setText("ABCQWER");
		assertTrue("Incorrect input passed", !student.verify(t));
		
		t.setText("0123456");
		assertTrue("Incorrect input passed", !student.verify(t));
		
		t.setText("AB!123!");
		assertTrue("Incorrect input passed", !student.verify(t));
		
		t.setText("AB 1234");
		assertTrue("Incorrect input passed", !student.verify(t));
		
	}

}
