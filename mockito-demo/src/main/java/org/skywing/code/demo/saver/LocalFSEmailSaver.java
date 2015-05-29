package org.skywing.code.demo.saver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.skywing.code.demo.domain.Email;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Closeables;

public class LocalFSEmailSaver implements IEmailSaver {
    
    @Override
    public void save(String directory, String fileName, Email email) throws IOException {
        Preconditions.checkNotNull(directory, "Parameter 'dest' cannot be null.");
        Preconditions.checkNotNull(email, "Parameter 'email' cannot be null.");
        
        if (!createDirectoryIfAbsent(directory)) {
            throw new RuntimeException(String.format("Fail to create directory %s.", directory));
        }
        
        Path path = Files.createFile(Paths.get(directory, fileName));
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(path, Charsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
            writer.write(email.toString());
        } catch (IOException e) {
            Files.deleteIfExists(path);
            throw e;
        } finally {
            Closeables.close(writer, true);
        }
    }

    /*
    private void createDirectoryIfAbsent(String directoryPath) throws IOException {
        Preconditions.checkNotNull(directoryPath, "Parameter 'directoryPath' cannot be null.");
        
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
             Files.createDirectories(path);
        }
    }
    */
    
    private boolean createDirectoryIfAbsent(String directoryPath) {
        File direcotry = new File(directoryPath);
        if (!direcotry.exists()) {
            return direcotry.mkdirs();
        }
        return true;
    }
}
