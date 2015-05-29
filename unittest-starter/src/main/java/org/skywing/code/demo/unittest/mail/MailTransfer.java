package org.skywing.code.demo.unittest.mail;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.skywing.code.demo.unittest.mail.Email.EmailBuilder;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

/**
 * A mail transfer class which responsible for sending email to remote smtp server.
 * @author Jwm
 * @since 1.7
 */
public class MailTransfer {
    public static final char EMAIL_ADDRESS_DELIMITER = '@';
    
    private ExternalMailSystem externalMailSystem;
    
    public MailTransfer(ExternalMailSystem externalMailSystem) {
        this.externalMailSystem = externalMailSystem;
    }

    public void transfer(String address, String body) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(address), "Parameter 'address' cannot be null or empty.");
        
        List<String> addrComponents = Splitter.on(EMAIL_ADDRESS_DELIMITER).omitEmptyStrings().trimResults().splitToList(address);        
        Preconditions.checkArgument(2 == addrComponents.size(), "Parameter 'address' is invalid, a format liked 'user@domain.com' is needed.");
        
        EmailBuilder builder = new Email.EmailBuilder();
        Email email = builder.user(addrComponents.get(0)).domain(addrComponents.get(1))
                .body(body).timestamp(Calendar.getInstance(Locale.CHINA).getTime()).build();
        
        externalMailSystem.send(email);
    }
}