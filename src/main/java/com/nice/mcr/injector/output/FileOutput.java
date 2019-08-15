package com.nice.mcr.injector.output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutput implements OutputHandler {

    public static final String CLI_OPTION = "file";

    public static int fileIndex = 0;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
