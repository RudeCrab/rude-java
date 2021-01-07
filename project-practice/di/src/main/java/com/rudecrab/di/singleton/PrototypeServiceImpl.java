package com.rudecrab.di.singleton;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author RudeCrab
 */
@Service
@Scope("prototype")
public class PrototypeServiceImpl implements UserService{
}
