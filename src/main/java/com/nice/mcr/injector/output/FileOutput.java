package com.nice.mcr.injector.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutput implements OutputHandler {

    private static final Logger log = LoggerFactory.getLogger(FileOutput.class);

    public static final String CLI_OPTION = "file";

    public int fileIndex = 0;

    @Override
    public boolean open() {
        return true;
    }

    @Override
    public void output(String data) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("..\\tool-elastic-search-injector\\output\\" + fileIndex + ".json"));
            writer.write(data);
            writer.flush();
            writer.close();
            fileIndex++;
        } catch (IOException ioe) {
            log.error("", ioe);
        }
        System.out.println("Wrote file " + fileIndex);
    }
}
