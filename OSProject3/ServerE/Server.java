//Jay Challangi, JXC180095
//Jashan Shah, JMS180016
package ServerE;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Iterator;

//Will be run on dc25 whose ip address is 10.176.69.56
public class Server {
    private ServerSocket server;
    private final int port = 5050; // port is 5050 for all
    private int state = -1;
    private int writes = -1;
    private String ipofA = "10.176.69.52";
    private String ipofB = "10.176.69.53";
    private String ipofC = "10.176.69.54";
    private String ipofD = "10.176.69.55";
    private String ipofE = "10.176.69.56";
    private String ipofF = "10.176.69.57";
    private String ipofG = "10.176.69.58";
    private String ipofH = "10.176.69.59";
    private final String[] transitions = { "ABCDEFGH", "EFGH", "EFG", "BCDEFG" };
    private boolean merged = false;
    private File x;
    private FileOutputStream xw;
    private FileInputStream xr;
    private int vn = 1;
    private int ru = 8;
    private String ds = "A";
    public final String fileString = "E wrote to file";
    private ArrayList<ClientConnection> ClientConnections = new ArrayList<>();
    private Queue<String> messageWaitList = new LinkedList<String>();

    public Server() {
        try {
            server = new ServerSocket(port);
            System.out.println("Server E Started");
            Socket AsServer;
            Socket AsClient;
            ServerConnection handler;
            Thread thread;

            // connections
            AsClient = new Socket(ipofA, 5050);
            System.out.println("Connected to A");
            ClientConnections.add(new ClientConnection("A", AsClient));
            AsServer = server.accept();
            System.out.println("A Connected");
            handler = new ServerConnection("A", AsServer);
            thread = new Thread(handler);
            thread.start();

            AsClient = new Socket(ipofB, 5050);
            System.out.println("Connected to B");
            ClientConnections.add(new ClientConnection("B", AsClient));
            AsServer = server.accept();
            System.out.println("B Connected");
            handler = new ServerConnection("B", AsServer);
            thread = new Thread(handler);
            thread.start();

            AsClient = new Socket(ipofC, 5050);
            System.out.println("Connected to C");
            ClientConnections.add(new ClientConnection("C", AsClient));
            AsServer = server.accept();
            System.out.println("C Connected");
            handler = new ServerConnection("C", AsServer);
            thread = new Thread(handler);
            thread.start();

            AsClient = new Socket(ipofD, 5050);
            System.out.println("Connected to D");
            ClientConnections.add(new ClientConnection("D", AsClient));
            AsServer = server.accept();
            System.out.println("D Connected");
            handler = new ServerConnection("D", AsServer);
            thread = new Thread(handler);
            thread.start();

            AsServer = server.accept();
            System.out.println("F Connected");
            handler = new ServerConnection("F", AsServer);
            thread = new Thread(handler);
            thread.start();
            AsClient = new Socket(ipofF, 5050);
            System.out.println("Connected to F");
            ClientConnections.add(new ClientConnection("F", AsClient));

            AsServer = server.accept();
            System.out.println("G Connected");
            handler = new ServerConnection("G", AsServer);
            thread = new Thread(handler);
            thread.start();
            AsClient = new Socket(ipofG, 5050);
            System.out.println("Connected to G");
            ClientConnections.add(new ClientConnection("G", AsClient));

            AsServer = server.accept();
            System.out.println("H Connected");
            handler = new ServerConnection("H", AsServer);
            thread = new Thread(handler);
            thread.start();
            AsClient = new Socket(ipofH, 5050);
            System.out.println("Connected to H");
            ClientConnections.add(new ClientConnection("H", AsClient));

            // file handling
            x = new File("ServerE/X.txt");
            if (x.createNewFile()) {
                System.out.println("X.txt created");
            } else {
                if (x.delete()) {
                    x = new File("ServerE/X.txt");
                    System.out.println("Existing X deleted, new one created");
                } else {
                    System.out.println("Unable to delete existing X");
                }
            }
            xw = new FileOutputStream(x);

            // start
            state++;// state=0
            writes++;// 0 writes
            printStats();
            // write 1
            RecieveWrite();
            // write 2
            RecieveWrite();

            // conections change
            state++;// state 1
            updateConnections();
            // write 3
            WritetoFile();
            // write 4
            WritetoFile();

            // conections change
            state++;// state 2
            updateConnections();
            // write 5
            WritetoFile();
            // write 6
            WritetoFile();

            state++;// state3
            updateConnections();
            // write 7
            RecieveWrite();
            // write 8
            RecieveWrite();

        } catch (

        IOException e) {
            System.out.println("ERROR setting up");
            e.printStackTrace();
        }

    }

    public void printStats() {
        System.out.println("VN=" + vn + " RU=" + ru + " DS=" + ds);
        System.out.flush();
    }

    public void sendMessages(String message) {
        for (ClientConnection x : ClientConnections) {
            if (transitions[state].contains(x.name)) {
                System.out.println("Sending Message to +" + x.name);
                System.out.flush();
                x.writeToConnection(message);
            }
        }
    }

    public void updateConnections() {
        //System.out.println(state + "+" + transitions[state]);
        //System.out.flush();
        if (transitions[state].length() % 2 == 0) {
            ds = transitions[state].substring(0, 1);
        } else {
            ds = "_";
        }
        Iterator<ClientConnection> iterator = ClientConnections.iterator();
        while (iterator.hasNext()) {
            ClientConnection x = iterator.next();
            if (!transitions[state].contains(x.name)) {
                x.closeEverything();
                iterator.remove();
            }
        }
        if (state > 0 && transitions[state - 1].length() < transitions[state].length()) {
            try {
                Socket AsServer;
                Socket AsClient;
                ServerConnection handler;
                Thread thread;

                // reconnect B
                AsClient = new Socket(ipofB, 5050);
                System.out.println("Connected to B");
                System.out.flush();
                ClientConnections.add(new ClientConnection("B", AsClient));
                AsServer = server.accept();
                System.out.println("B Connected");
                System.out.flush();
                handler = new ServerConnection("B", AsServer);
                thread = new Thread(handler);
                thread.start();

                // reconnect C
                AsClient = new Socket(ipofC, 5050);
                System.out.println("Connected to C");
                System.out.flush();
                ClientConnections.add(new ClientConnection("C", AsClient));
                AsServer = server.accept();
                System.out.println("C Connected");
                System.out.flush();
                handler = new ServerConnection("C", AsServer);
                thread = new Thread(handler);
                thread.start();

                // reconnect D
                AsClient = new Socket(ipofD, 5050);
                System.out.println("Connected to D");
                System.out.flush();
                ClientConnections.add(new ClientConnection("D", AsClient));
                AsServer = server.accept();
                System.out.println("D Connected");
                System.out.flush();
                handler = new ServerConnection("D", AsServer);
                thread = new Thread(handler);
                thread.start();
                merged = true;

            } catch (IOException e) {
                System.out.println("ERROR recreating connections");
                e.printStackTrace();
            }
        }

    }

    public void WritetoFile() {
        try {
            if (merged && CorrectPartition()) {
                ru = transitions[state].length();
                String messageToWrite = "";
                writes++;
                vn++;
                messageToWrite = vn+"";
                xw.write((fileString + "\n").getBytes());
                xw.close();
                xr=new FileInputStream(x);
                BufferedReader br= new BufferedReader(new InputStreamReader(xr));
                String line;
                while((line=br.readLine())!=null)
                {
                    messageToWrite+="#"+line;
                }
                sendMessages(messageToWrite);
                printStats();
                merged=false;
                xw=new FileOutputStream(x, true);

            } else if (CorrectPartition()) {
                ru = transitions[state].length();
                String messageToWrite = "";
                writes++;
                vn++;
                xw.write((fileString + "\n").getBytes());
                messageToWrite = vn + "#" + fileString;
                sendMessages(messageToWrite);
                printStats();
            } else {
                xw.write((fileString + "\n").getBytes());
                printStats();
            }
        } catch (IOException e) {
            System.out.println("ERROR writing to file");
            e.printStackTrace();
        }
    }

    public void RecieveWrite() {
        try {
            boolean inputRecieved = false;
            String toProcess = "";
            String[] processed;
            int recievedVn = 0;
            writes++;
            while (!inputRecieved) {
                if (messageWaitList.size() > 0) {
                    inputRecieved = true;
                    toProcess = messageWaitList.remove();
                    processed = toProcess.split("#");
                    recievedVn = Integer.parseInt(processed[0]);
                    if (recievedVn > vn && CorrectPartition())// If the recieved version number is greater
                    {
                        vn = recievedVn;
                        ru = transitions[state].length();
                        if (merged) {
                            xw.close();
                            xw=new FileOutputStream(x);
                            for(int i=1;i<processed.length;i++)
                            {
                                xw.write((processed[i] + "\n").getBytes());
                            }
                            merged=false;
                        } else {
                            xw.write((processed[1] + "\n").getBytes());
                        }
                    }
                    printStats();
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR writing to file");
            e.printStackTrace();
        }
    }

    public boolean CorrectPartition() {
        if (state == 0) {
            return true;
        }
        if (transitions[state].length() >= (float) transitions[state - 1].length() / 2
                && containsFirstHalf(transitions[0], transitions[state])) {
            return true;
        }
        return false;
    }

    public static boolean containsFirstHalf(String str1, String str2) {
        int halfLength = str1.length() / 2;
        for (int i = 0; i < halfLength; i++) {
            char c = str1.charAt(i);
            if (str2.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    // ServerConnection will handle reading from connections
    private class ServerConnection implements Runnable {

        private String name;
        private Socket AsServer;
        private BufferedReader bufferedReader;

        public ServerConnection(String name, Socket AsServer) {
            try {
                this.name = name;
                this.AsServer = AsServer;
                bufferedReader = new BufferedReader(new InputStreamReader(this.AsServer.getInputStream()));
            } catch (IOException e) {
                System.out.println("ERROR setting up connections " + name);
                e.printStackTrace();
                closeEverything();
            }
        }

        public void run() {
            boolean closed = false;
            while (AsServer.isConnected() && !closed) {
                try {
                    if (bufferedReader.ready()) {
                        String read = bufferedReader.readLine();
                        messageWaitList.add(read);
                    } else {
                        if (state > 0 && !transitions[state].contains(name)) {
                            closeEverything();
                            closed = true;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("ERROR reading from connection " + name);
                    e.printStackTrace();
                    closeEverything();
                }
            }
        }

        public void closeEverything() {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (AsServer != null) {
                    AsServer.close();
                }
            } catch (IOException e) {
                System.out.println("ERROR with ending connection");
                e.printStackTrace();
            }
        }

    }

    // ClientConnection will handle writing to the other servers
    private class ClientConnection {

        public String name;
        private Socket AsClient;
        private BufferedWriter bufferedWriter;

        public ClientConnection(String name, Socket AsClient) {
            try {
                this.name = name;
                this.AsClient = AsClient;
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.AsClient.getOutputStream()));
            } catch (IOException e) {
                System.out.println("ERROR setting up connections");
                e.printStackTrace();
                closeEverything();
            }
        }

        public void writeToConnection(String message) {
            try {
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                System.out.println("ERROR wrtiting to connection");
                e.printStackTrace();
                closeEverything();
            }
        }

        public void closeEverything() {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (AsClient != null) {
                    AsClient.close();
                }
            } catch (IOException e) {
                System.out.println("ERROR with ending connection");
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        Server one = new Server();
    }

}
