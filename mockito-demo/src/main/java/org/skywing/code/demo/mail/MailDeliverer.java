package org.skywing.code.demo.mail;

import org.skywing.code.demo.domain.Email;
import org.skywing.code.demo.external.ExternalMailSystem;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class MailDeliverer {
    private ExternalMailSystem externalMailSystem;
    private Timestamper timestamper;
    
    public MailDeliverer(ExternalMailSystem externalMailSystem,
            Timestamper timestamper) {
        super();
        this.externalMailSystem = externalMailSystem;
        this.timestamper = timestamper;
    }

    public void deliver(String address, String body) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(address), "Parameter 'address' cannot be null or empty.");
        
        Email email = new Email();
        email.setBody(Strings.nullToEmpty(body));
        applyUserAndDomainUsingProvidedAddress(address, email);
        email.setTimestamp(timestamper.stamp());
        externalMailSystem.send(email);
    }

    private void applyUserAndDomainUsingProvidedAddress(String address, Email email) {
        Preconditions.checkArgument(address.contains("@"), "Parameter 'address' is invalid.");
        
        String[] addressComponents = address.split("@");
        email.setUser(addressComponents[0]);
        email.setDomain(addressComponents[1]);
    }
}