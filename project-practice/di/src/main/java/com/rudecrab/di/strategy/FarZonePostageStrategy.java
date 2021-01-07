package com.rudecrab.di.strategy;

import org.springframework.stereotype.Service;

/**
 * @author RudeCrab
 */
@Service("farZone")
public class FarZonePostageStrategy implements PostageStrategy{
    @Override
    public long calPostage(int weight) {
        if (weight <= 10) {
            return 16;
        } else if (weight <= 15) {
            return 24;
        } else {
            return 32;
        }
    }
}
