package com.devcamp.currencyconverter.tools.io.impl;

import com.devcamp.currencyconverter.tools.io.api.FileIO;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FileIOImpl implements FileIO {
    @Override
    public String read(String file) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (
                InputStream inputStream = getClass().getResourceAsStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line).append(System.lineSeparator());
            }
        }
        return fileContent.toString();
    }
}