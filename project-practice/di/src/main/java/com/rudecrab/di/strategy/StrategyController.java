package com.rudecrab.di.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author RudeCrab
 */
@RestController
@RequestMapping("/strategy")
public class StrategyController {
    @Autowired
    private Map<String, PostageStrategy> map;

    @GetMapping("/price")
    public long calPrice(String zone, int weight) {
        return map.get(zone).calPostage(weight);
    }
}
