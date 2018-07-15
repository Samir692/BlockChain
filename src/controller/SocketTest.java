package controller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;

public class SocketTest {
  private boolean run = true;


  public static void startSender(String param_data) throws UnknownHostException{
    InetAddress aHost = InetAddress.getLocalHost();
    (new Thread() {
        @Override
        public void run() {
            byte data[] = param_data.getBytes();
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setBroadcast(true);
            } catch (SocketException ex) {
                ex.printStackTrace();
                //parent.quit();
            }

            DatagramPacket packet = new DatagramPacket(
                    data,
                    data.length,
                    aHost,
                    9090);
            int i=0;
            while (true) {
                try {

                    System.out.println("Sending.." + new String(packet.getData()));
                    socket.send(packet);
                    Thread.sleep(50);
                    i++;
                    System.out.println(i);
                    
                    socket.receive(packet);
                    String temp=new String(packet.getData());
                    System.out.println("Sender is okay = "+temp);
                    break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                   // parent.quit();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                   // parent.quit();
                }
            }
        }}).start();
    }


  public static void startServer() {
	  ArrayList clients = new ArrayList<>();
	  
    (new Thread() {
        @Override
        public void run() {

                //byte data[] = new byte[0];
                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket(9090);
                    //socket.setBroadcast(true);;

                } catch (SocketException ex) {
                    ex.printStackTrace();
                    //parent.quit();
                }
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                //System.out.println("this is what has been received111"+packet.getData());
                String temp;
                while (true) {
                try {
                    socket.receive(packet);
                    temp=new String(packet.getData());
                    System.out.println("this is what has been received = "+temp);
                    
                    InetAddress aHost = InetAddress.getLocalHost();
                    byte data[] = "OK".getBytes();
                    packet = new DatagramPacket(
                            data,
                            data.length,
                            aHost,
                            9090);
                    socket.send(packet);
                    break;


                    //System.out.println("Message received ..."+ temp);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                 }
            }

    }).start();
 }
}