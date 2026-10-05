package com.pos_terminal_simulator.client.iso8583;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Iso8583TcpClient {

    private final String host;
    private final int port;

    private Socket socket;
    private BufferedReader reader;
    private OutputStream output;

    public Iso8583TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {

        if (isConnected()) {
            return;
        }

        System.out.println(
                "Connecting to " + host + ":" + port
        );

        socket = new Socket();

        socket.connect(
                new InetSocketAddress(host, port),
                5000
        );

        socket.setSoTimeout(10000);

        reader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(),
                        StandardCharsets.US_ASCII
                )
        );

        output = socket.getOutputStream();

        System.out.println(
                "Connected to ISO 8583 server."
        );
    }

    public void send(String isoMessage) throws IOException {

        if (!isConnected()) {
            throw new IllegalStateException(
                    "TCP connection is not open"
            );
        }

        if (isoMessage == null || isoMessage.isEmpty()) {
            throw new IllegalArgumentException(
                    "ISO message cannot be null or empty"
            );
        }

        byte[] messageBytes =
                isoMessage.getBytes(StandardCharsets.US_ASCII);

        /*
         * IMPORTANT:
         *
         * The mock switch uses BufferedReader.readLine().
         *
         * Therefore we must send a newline after the ISO message.
         *
         * Do NOT send a binary length prefix.
         */

        output.write(messageBytes);
        output.write('\n');
        output.flush();

        System.out.println("=================================");
        System.out.println("ISO 8583 REQUEST SENT");
        System.out.println("Length: " + messageBytes.length);
        System.out.println("Message:");
        System.out.println(isoMessage);
        System.out.println("=================================");
    }

    public String receive() throws IOException {

        if (!isConnected()) {
            throw new IllegalStateException(
                    "TCP connection is not open"
            );
        }

        System.out.println("=================================");
        System.out.println("WAITING FOR RESPONSE");
        System.out.println("=================================");

        try {

            /*
             * The mock switch uses PrintWriter.println(),
             * so the response is newline terminated.
             */
            String response = reader.readLine();

            if (response == null) {

                System.out.println(
                        "Server closed the connection."
                );

                return null;
            }

            System.out.println("=================================");
            System.out.println("ISO 8583 RESPONSE RECEIVED");
            System.out.println("Length: " + response.length());
            System.out.println("Response:");
            System.out.println(response);
            System.out.println("=================================");

            return response;

        } catch (java.net.SocketTimeoutException e) {

            System.out.println(
                    "TIMEOUT: No response received within "
                            + socket.getSoTimeout()
                            + " ms"
            );

            return null;
        }
    }

    public void sendOnly(String isoMessage) throws IOException {
        send(isoMessage);
    }

    public String sendAndReceive(String isoMessage)
            throws IOException {

        send(isoMessage);

        return receive();
    }

    public boolean isConnected() {

        return socket != null
                && socket.isConnected()
                && !socket.isClosed();
    }

    public void disconnect() {

        try {

            if (reader != null) {
                reader.close();
            }

            if (output != null) {
                output.close();
            }

            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {

            System.err.println(
                    "Error closing connection: "
                            + e.getMessage()
            );

        } finally {

            reader = null;
            output = null;
            socket = null;
        }

        System.out.println(
                "Disconnected from ISO 8583 server."
        );
    }
}
