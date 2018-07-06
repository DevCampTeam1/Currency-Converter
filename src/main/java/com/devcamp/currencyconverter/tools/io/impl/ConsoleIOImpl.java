package com.devcamp.currencyconverter.tools.io.impl;

import com.devcamp.currencyconverter.tools.io.api.ConsoleIO;
import org.springframework.stereotype.Component;

@Component
public class ConsoleIOImpl implements ConsoleIO {
    @Override
    public void write(Object line) {
        System.out.println(line);
    }
}
