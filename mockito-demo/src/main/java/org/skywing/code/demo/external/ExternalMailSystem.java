package org.skywing.code.demo.external;

import org.skywing.code.demo.domain.Email;

public interface ExternalMailSystem {

    public void send(String domain, String user, String body);
    
    public void send(Email email);
}
