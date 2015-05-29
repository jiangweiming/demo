package org.skywing.code.demo.mail;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skywing.code.demo.domain.Email;
import org.skywing.code.demo.saver.LocalFSEmailSaver;

import com.google.common.base.Charsets;
import com.google.common.io.Closeables;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocalFSEmailSaver.class, Files.class, Closeables.class})
public class LocalFSEmailSaverTest {
    private final String directoryPath = "/dir/emails";
    private final String fileName = "file.eml";
    private final String user = "noreply";
    private final String domain = "yahoo.com";
    private final String body = "I'm showering";

    private LocalFSEmailSaver saver;

    @Before
    public void setUp() {
        saver = new LocalFSEmailSaver();
    }
    
    private Email getEmail(String user, String domain, String body) {
        Email email = new Email();
        email.setUser(user);
        email.setDomain(domain);
        email.setBody(body);
        return email;
    }
    
    @Test
    public void testCreateDirectoryPresent() throws Exception {
        final String mockPath = "mock path";
        Method method = saver.getClass().getDeclaredMethod("createDirectoryIfAbsent", String.class);
        method.setAccessible(true);

        File mockDirectory = mock(File.class);
        // tell PowerMockito to mock construction of a new File
        whenNew(File.class).withArguments(mockPath).thenReturn(mockDirectory);
        when(mockDirectory.exists()).thenReturn(true);
        assertTrue((Boolean) method.invoke(saver, mockPath));
        verifyNew(File.class).withArguments(mockPath);
        Mockito.verify(mockDirectory).exists();
        Mockito.verify(mockDirectory, Mockito.never());
    }

    @Test
    public void testCreateDirectoryAbsent() throws Exception {
        final String mockPath = "mock path";
        Method method = saver.getClass().getDeclaredMethod("createDirectoryIfAbsent", String.class);
        method.setAccessible(true);
        
        File mockDirectory = mock(File.class);
        whenNew(File.class).withArguments(mockPath).thenReturn(mockDirectory);
        when(mockDirectory.exists()).thenReturn(false);
        when(mockDirectory.mkdirs()).thenReturn(true);
        assertTrue((Boolean) method.invoke(saver, mockPath));
        verifyNew(File.class).withArguments(mockPath);
        Mockito.verify(mockDirectory).exists();
        Mockito.verify(mockDirectory).mkdirs();
    }
    
    @Test
    public void testSaveEmailCreateDirFailed() throws Exception {
        final String privateMethodName = "createDirectoryIfAbsent";
        
        // mock private method
        LocalFSEmailSaver privatePartialMock = spy(new LocalFSEmailSaver());
        
        // when using spy we recommend stubbing in style as below.
        doReturn(false).when(privatePartialMock, privateMethodName, directoryPath);
        
        try {
            privatePartialMock.save(directoryPath, fileName, getEmail(user, domain, body));
            fail("Expected RuntimeException not threw out.");
        } catch (RuntimeException e) {
        }
        
        // verify private
        verifyPrivate(privatePartialMock).invoke(privateMethodName, directoryPath);
    }

    @Test
    public void testSaveEmailWriteFailed() throws Exception {
        final String privateMethodName = "createDirectoryIfAbsent";
        final Path filePath = Paths.get(directoryPath, fileName);
        
        // mock private method
        LocalFSEmailSaver privatePartialMock = spy(new LocalFSEmailSaver());
        doReturn(true).when(privatePartialMock, privateMethodName, directoryPath);

        // mock static method
        mockStatic(Files.class, Closeables.class);
        when(Files.createFile(filePath)).thenReturn(filePath);
        
        BufferedWriter mockWriter = mock(BufferedWriter.class);
        doThrow(new IOException()).when(mockWriter).write(Mockito.anyString());
        when(Files.newBufferedWriter(filePath, Charsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(mockWriter);
        
        try {
            privatePartialMock.save(directoryPath, fileName, getEmail(user, domain, body));
            fail("Expected IOException not threw out.");
        } catch (IOException e) {
        }
        
        verifyPrivate(privatePartialMock).invoke(privateMethodName, directoryPath);
        verifyStatic();
        Files.createFile(filePath);
        Files.deleteIfExists(filePath);
        Closeables.close(Mockito.any(Closeable.class), Mockito.eq(true));
        Mockito.verify(mockWriter).write(Mockito.anyString());
    }
    
    @Test
    public void testSaveEmailSuccess() throws Exception {
        final String privateMethodName = "createDirectoryIfAbsent";
        final Path filePath = Paths.get(directoryPath, fileName);
        
        // mock private method
        LocalFSEmailSaver privatePartialMock = spy(new LocalFSEmailSaver());
        doReturn(true).when(privatePartialMock, privateMethodName, directoryPath);

        // mock static method
        mockStatic(Files.class, Closeables.class);
        when(Files.createFile(filePath)).thenReturn(filePath);

        BufferedWriter mockWriter = mock(BufferedWriter.class);
        when(Files.newBufferedWriter(filePath, Charsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(mockWriter);

        privatePartialMock.save(directoryPath, fileName, getEmail(user, domain, body));
        
        verifyPrivate(privatePartialMock).invoke(privateMethodName, directoryPath);
        verifyStatic();
        Files.createFile(filePath);
        Files.deleteIfExists(filePath);
        Closeables.close(Mockito.any(Closeable.class), Mockito.eq(true));
        Mockito.verify(mockWriter).write(Mockito.anyString());
    }
    
}
