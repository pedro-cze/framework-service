package com.etnetera.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class FileReader {

    private static final String BASE_PATH = "src/test/resources/";

    public static String readFile(final String path) throws IOException {
        return Files.readString(Paths.get(BASE_PATH, path));
    }

    private static String getPath(final String path) {
        return BASE_PATH + path;
    }
}
