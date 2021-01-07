package com.rudecrab.di.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author RudeCrab
 */
@RestController
@RequestMapping("/chain")
public class ChainController {
    @Autowired
    private List<Handler> handlers;

    @PostMapping
    public void process(@RequestBody MyRequest request) {
        System.out.println("---");
        // 依次调用Handler
        for (Handler handler : handlers) {
            // 如果返回为false,中止调用
            if (!handler.process(request)) {
                break;
            }
        }
    }
}
