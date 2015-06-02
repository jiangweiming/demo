package org.skywing.code.demo.saver;

import java.io.IOException;

import org.skywing.code.demo.domain.Email;

public interface IEmailSaver {
    public void save(String directory, String fileName, Email email) throws IOException;
}
