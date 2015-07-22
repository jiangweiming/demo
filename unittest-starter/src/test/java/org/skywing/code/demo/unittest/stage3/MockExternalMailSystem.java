package org.skywing.code.demo.unittest.stage3;

import java.util.ArrayList;
import java.util.List;

import org.skywing.code.demo.unittest.mail.Email;
import org.skywing.code.demo.unittest.mail.ExternalMailSystem;

public class MockExternalMailSystem implements ExternalMailSystem {
    public List<Email> emailsOut = new ArrayList<Email>();
    
    @Override
    public void send(String domain, String user, String body) {
        throw new UnsupportedOperationException("Unsupported operation called!");
    }

    @Override
    public void send(Email email) {
        System.out.println("Email: " + email + " has been send out!");
        this.emailsOut.add(email);
    }

}
