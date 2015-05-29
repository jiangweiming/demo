package org.skywing.code.demo.unittest.stage3;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.skywing.code.demo.unittest.mail.ExternalMailSystem;
import org.skywing.code.demo.unittest.mail.MailTransfer;

public class MailTransferTest {
    private static ExternalMailSystem mailSystem;
    private static ExternalMailSystem exMailSystem;
    
    @BeforeClass
    public static void setUpClass() {
        mailSystem = new MockExternalMailSystem();
        exMailSystem = new MockExternalMailSystemEx();
    }
    
    @Test
    public void testTransfer() {
        MailTransfer mailTransfer = new MailTransfer(mailSystem);
        mailTransfer.transfer("user@yahoo.com", "body");
    }
    
    @Test(expected=RuntimeException.class)
    public void testTransferSendEx() {
        MailTransfer mailTransfer = new MailTransfer(exMailSystem);
        mailTransfer.transfer("user@yahoo.com", "body");
    }
    
    @Test
    public void testTransferArgsEx() {
        MailTransfer mailTransfer = new MailTransfer(mailSystem);
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
