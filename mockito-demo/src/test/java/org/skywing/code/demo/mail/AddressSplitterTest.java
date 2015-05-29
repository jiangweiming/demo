package org.skywing.code.demo.mail;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

@RunWith(MockitoJUnitRunner.class)
public class AddressSplitterTest {
    @InjectMocks
    private AddressSplitter splitter = new AddressSplitter();
    
    @Mock
    private AddressInputQueue queue;
    
    @Test
    public void testSplitByComma() {
        String addresses = Joiner.on(',').join("one@gmail.com", "second@yahoo.com");
        
        when(queue.next()).thenReturn(addresses);
        List<String> results = splitter.split();
        assertEquals(Splitter.on(',').splitToList(addresses), results);
        verify(queue).next();
    }
}
