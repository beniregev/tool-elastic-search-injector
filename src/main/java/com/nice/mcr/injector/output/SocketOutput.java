package com.nice.mcr.injector.output;

import org.springframework.beans.factory.annotation.Value;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketOutput implements OutputHandler {
    @Value("${socket.hostname}")
    private String hostname;
    @Value("${socket.port}")
    private int port;
    Socket clientSocket;

    public boolean open() {
        try {
            clientSocket = new Socket(hostname, port);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void output(String data) {

        DataOutputStream outToServer = null;
        try {
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes( data );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
