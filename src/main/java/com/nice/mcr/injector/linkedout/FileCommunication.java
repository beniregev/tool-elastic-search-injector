package com.nice.mcr.injector.linkedout;

import java.io.*;

public class FileCommunication implements DataListener {

    public static String fileName = "0";
    private String json;

    @Override
    public void update(String json) {
        this.json = json;
        writeJSONToFile();
    }

    private void writeJSONToFile() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("..\\tool-elastic-search-injector\\input\\" + fileName + ".txt"));
            writer.write(this.json);
            writer.flush();
            writer.close();
            fileName = String.valueOf(Integer.parseInt(fileName) + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void closeConnection(){
//        try {
//            if (this.writer != null) {
//                this.writer.close();
//            }
//        }
//        catch (IOException e) { e.printStackTrace(); }
//    }
}