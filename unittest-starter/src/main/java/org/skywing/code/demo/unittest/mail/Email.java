package org.skywing.code.demo.unittest.mail;

import java.util.Date;

import com.google.common.base.Objects;

/**
 * A domain object class for email.
 * @author jwm
 *
 */
public class Email {
    private String domain;
    private String user;
    private String body;
    private Date timestamp;

    public Email(EmailBuilder builder) {
        this.domain = builder.domain;
        this.body = builder.body;
        this.user = builder.user;
        this.timestamp = builder.timestamp;
    }
    
    public String getDomain() {
        return domain;
    }

    public String getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.user, this.domain, this.body, this.timestamp);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equal(this, obj);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).omitNullValues()
                .add("user", this.user).add("domain", this.domain)
                .add("body", this.body).add("timestamp", this.timestamp)
                .toString();
    }
    
    public static class EmailBuilder {
        private String domain;
        private String user;
        private String body;
        private Date timestamp;
        
        public EmailBuilder domain(String domain) {
            this.domain = domain;
            return this;
        }
        
        public EmailBuilder user(String user) {
            this.user = user;
            return this;
        }
        
        public EmailBuilder body(String body) {
            this.body = body;
            return this;
        }
        
        public EmailBuilder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public Email build() {
            return new Email(this);
        }
    }
    
}
