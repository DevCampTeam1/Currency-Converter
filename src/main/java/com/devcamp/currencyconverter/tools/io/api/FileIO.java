package com.devcamp.currencyconverter.tools.io.api;

import java.io.IOException;

public interface FileIO {
    String read(String file) throws IOException;
}