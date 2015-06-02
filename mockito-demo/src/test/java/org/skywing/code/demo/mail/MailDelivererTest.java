package org.skywing.code.demo.mail;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.skywing.code.demo.domain.Email;
import org.skywing.code.demo.external.ExternalMailSystem;

public class MailDelivererTest {
    private MailDeliverer mailDeliver;
    private ExternalMailSystem externalSystem;
    private Timestamper timestamper;
    
    @Before
    public void setUp() {
        // mock: Mock dependencies of MailDeliver, cause sending email to a real SMTP server is unrepeatable 
        // when executing this unit test in different environment, expecially in a test environment without 
        // SMTP server. In one word, we should avoid our unit tests depend on external system or server.
        externalSystem = mock(ExternalMailSystem.class);
        timestamper = mock(Timestamper.class);
        mailDeliver = new MailDeliverer(externalSystem, timestamper);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testSendBadEmail() {
        mailDeliver.deliver(null, null);
    }
    
    @Test
    public void testSendEmailError() {
        String expectedUser = "noreply";
        String expectedDomain = "yahoo.com";
        String expectedBody = "email test? bingo!";
        
        // stubbing void method: make externalSystem throw a RuntimeException when sending email
        doThrow(RuntimeException.class).when(externalSystem).send(any(Email.class));
        try {
            mailDeliver.deliver(expectedUser + '@' + expectedDomain, expectedBody);
            fail("Expected RuntimeException not threw out.");
        } catch(RuntimeException e) {
        }
        
        // capture mock object invocation argument.
        ArgumentCaptor<Email> captor = ArgumentCaptor.forClass(Email.class);
        // verify: check object externalSystem has been call with argument that object of class Email.
        verify(externalSystem).send(captor.capture());
        Email argEmail = captor.getValue();
        assertEquals(expectedUser, argEmail.getUser());
        assertEquals(expectedDomain, argEmail.getDomain());
        assertEquals(expectedBody, argEmail.getBody());
        assertNull(argEmail.getTimestamp());
    }
    
    @Test
    public void testSendEmailWithTimestamp() {
        String expectedUser = "noreply";
        String expectedDomain = "yahoo.com";
        String expectedBody = "";
        String expectedDate = "2015-04-09";
        
        // stubbing timestamper.stamp() and return a date object
        when(timestamper.stamp()).thenReturn(Date.valueOf(expectedDate));
        
        mailDeliver.deliver(expectedUser + '@' + expectedDomain, null);

        // verify call order
        ArgumentCaptor<Email> captor = ArgumentCaptor.forClass(Email.class);
        InOrder order = inOrder(timestamper, externalSystem);
        order.verify(timestamper, times(1)).stamp();
        order.verify(externalSystem).send(captor.capture());
        
        Email argEmail = captor.getValue();
        assertEquals(expectedUser, argEmail.getUser());
        assertEquals(expectedDomain, argEmail.getDomain());
        assertEquals(expectedBody, argEmail.getBody());
        
        
        assertEquals(Date.valueOf(expectedDate), argEmail.getTimestamp());
    }
    
    @Test
    public void testSetUserAndDomainError() {
        String expectedUser = "noreply";
        String expectedDomain = "yahoo.com";
        
        try {
            mailDeliver.deliver(expectedUser + expectedDomain, null);
            fail("Expected IllegalArgumentException not threw out.");
        } catch (IllegalArgumentException e) {
        }
        
        verify(externalSystem, never()).send(any(Email.class));
        verify(timestamper, never()).stamp();
    }
}
