package com.rudecrab.di.strategy;

import org.springframework.stereotype.Service;

/**
 * @author RudeCrab
 */
@Service("nearZone")
public class NearZonePostageStrategy implements PostageStrategy{
    @Override
    public long calPostage(int weight) {
        if (weight <= 10) {
            return 8;
        } else {
            return 16;
        }
    }
}
