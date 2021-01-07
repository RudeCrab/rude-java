package com.rudecrab.di.singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RudeCrab
 */
@RestController
@RequestMapping("/singleton-second")
public class SecondController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService singleton;

    @Autowired
    @Qualifier("prototypeServiceImpl")
    private UserService prototype;

    @GetMapping("/singleton")
    public int getSingleton() {
        return singleton.hashCode();
    }

    @GetMapping("/prototype")
    public int getPrototype() {
        return prototype.hashCode();
    }
}
