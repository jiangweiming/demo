package org.skywing.code.demo.unittest.stage4;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skywing.code.demo.unittest.mail.Email;
import org.skywing.code.demo.unittest.mail.ExternalMailSystem;
import org.skywing.code.demo.unittest.mail.MailTransfer;

@RunWith(PowerMockRunner.class)
public class MailTransferTest {
    private static final String TEST_DATA_USER = "user";
    private static final String TEST_DATA_DOMAIN = "yahoo.com";
    private static final String TEST_DATA_BODY = "body";
    
    private ExternalMailSystem mockMailSystem;
    
    @Before
    public void setUpClass() {
        mockMailSystem = PowerMockito.mock(ExternalMailSystem.class);
    }
    
    @Test
    public void testTransfer() {
        PowerMockito.doNothing().when(mockMailSystem).send(Mockito.any(Email.class));
        
        MailTransfer mailTransfer = new MailTransfer(mockMailSystem);
        mailTransfer.transfer("user@yahoo.com", "body");
        
//        Email email = new Email.EmailBuilder().body("body")
//        Mockito.verify(mockMailSystem).send(email);
    }
    
    @Test(expected=RuntimeException.class)
    public void testTransferSendEx() {
        PowerMockito.doThrow(new RuntimeException("Error occured when sending Email"))
                .when(mockMailSystem).send(Mockito.any(Email.class));
        MailTransfer mailTransfer = new MailTransfer(mockMailSystem);
        mailTransfer.transfer("user@yahoo.com", "body");
    }
    
    @Test
    public void testTransferArgsEx() {
        MailTransfer mailTransfer = new MailTransfer(mockMailSystem);
        try {
            mailTransfer.transfer(null, "body");
            fail("Excected exception not threw out!");
        } catch (IllegalArgumentException e) {
        }
        
        try {
            mailTransfer.transfer("", "body");
            fail("Excected exception not threw out!");
        } catch (IllegalArgumentException e) {
        }
        
        try {
            mailTransfer.transfer(" user@ ", "body");
            fail("Excected exception not threw out!");
        } catch (IllegalArgumentException e) {
        }
        
        try {
            mailTransfer.transfer(" @yahoo.com ", "body");
            fail("Excected exception not threw out!");
        } catch (IllegalArgumentException e) {
        }
    }
}
