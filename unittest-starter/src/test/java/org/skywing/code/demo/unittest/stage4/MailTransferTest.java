package org.skywing.code.demo.unittest.stage4;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skywing.code.demo.unittest.mail.Email;
import org.skywing.code.demo.unittest.mail.ExternalMailSystem;
import org.skywing.code.demo.unittest.mail.MailTransfer;
import org.skywing.code.demo.unittest.mail.Utils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Utils.class})
public class MailTransferTest {
    private static final String TEST_MAIL_USER = "user";
    private static final String TEST_MAIL_DOMAIN = "yahoo.com";
    private static final String TEST_MAIL_BODY = "body";
    private static final Long TEST_MAIL_TIMESTAMP = 1L;
    
    private ExternalMailSystem mockMailSystem;
    
    @Before
    public void setUp() {
        mockMailSystem = PowerMockito.mock(ExternalMailSystem.class);
    }
    
    @Test
    public void testTransfer() {
        PowerMockito.mockStatic(System.class);
        PowerMockito.when(Utils.timestamp()).thenReturn(TEST_MAIL_TIMESTAMP);
        
        Email email = new Email.EmailBuilder().body(TEST_MAIL_BODY)
                .user(TEST_MAIL_USER).domain(TEST_MAIL_DOMAIN)
                .timestamp(TEST_MAIL_TIMESTAMP).build();        
        PowerMockito.doNothing().when(mockMailSystem).send(email);
        
        MailTransfer mailTransfer = new MailTransfer(mockMailSystem);
        mailTransfer.transfer(TEST_MAIL_USER + "@" + TEST_MAIL_DOMAIN, TEST_MAIL_BODY);
        
        Mockito.verify(mockMailSystem).send(email);
        PowerMockito.verifyStatic();
        Utils.timestamp();
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
            mailTransfer.transfer(null, TEST_MAIL_BODY);
            fail("Excected exception not threw out!");
        } catch (IllegalArgumentException e) {
        }
        
        try {
            mailTransfer.transfer("", TEST_MAIL_BODY);
            fail("Excected exception not threw out!");
        } catch (IllegalArgumentException e) {
        }
        
        try {
            mailTransfer.transfer(" user@ ", TEST_MAIL_BODY);
            fail("Excected exception not threw out!");
        } catch (IllegalArgumentException e) {
        }
        
        try {
            mailTransfer.transfer(" @yahoo.com ", TEST_MAIL_BODY);
            fail("Excected exception not threw out!");
        } catch (IllegalArgumentException e) {
        }
    }
}
