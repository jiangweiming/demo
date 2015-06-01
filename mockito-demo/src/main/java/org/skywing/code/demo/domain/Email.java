package org.skywing.code.demo.domain;

import java.util.Date;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Email {
    private String domain;
    private String user;
    private String body;
    private Date timestamp;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("user", this.user).add("domain", this.domain)
                .add("body", this.body).add("timestamp", this.timestamp)
                .toString();
    }
}
