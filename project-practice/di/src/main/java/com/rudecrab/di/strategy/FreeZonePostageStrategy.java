package com.rudecrab.di.strategy;

import org.springframework.stereotype.Service;

/**
 * @author RudeCrab
 */
@Service("freeZone")
public class FreeZonePostageStrategy implements PostageStrategy{
    @Override
    public long calPostage(int weight) {
        if (weight <= 10) {
            return 0;
        } else {
            return 8;
        }
    }
}
