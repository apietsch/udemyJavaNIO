package de.utrl.development;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadedBlockingServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        while (true) {
            Socket s = ss.accept();
            System.out.println(("You are connected to socket: " + s + " bound on local port: " + s.getLocalPort() + " and connected to remote port: " + s.getPort()));
            try (s;
                 InputStream in = s.getInputStream();
                 OutputStream out = s.getOutputStream()
            ) {
                int data;
                while ((data = in.read()) != -1) {
                    out.write(transmogrify(data));
                }
            } finally {
                System.out.println("Disconnected. Socket is should be closed now: " + s.isClosed());
            }
        }
    }

    private static int transmogrify(int data) {
        return Character.isLetter(data) ? data ^ ' ' : data;
    }
}
