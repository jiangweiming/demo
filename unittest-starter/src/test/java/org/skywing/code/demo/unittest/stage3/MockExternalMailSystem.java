package org.skywing.code.demo.unittest.stage3;

import org.skywing.code.demo.unittest.mail.Email;
import org.skywing.code.demo.unittest.mail.ExternalMailSystem;

public class MockExternalMailSystem implements ExternalMailSystem {

    @Override
    public void send(String domain, String user, String body) {
        throw new UnsupportedOperationException("Unsupported operation called!");
    }

    @Override
    public void send(Email email) {
        System.out.println("Email: " + email + " has been send out!");
    }

}
