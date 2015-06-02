package org.skywing.code.demo.mail;

import java.util.Arrays;
import java.util.List;

public class AddressSplitter {
    private AddressInputQueue addressInputQueue;
    
    public List<String> split() {
        return Arrays.asList(addressInputQueue.next().split(","));
    }

}
