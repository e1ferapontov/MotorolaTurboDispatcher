package priv.chunyu.moto.LocationRequestResponseProtocol;

import priv.chunyu.moto.ImmiediateLocationRequest.ImmediateLocation;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

public class LRRPmsg extends LRRP implements Runnable {
    protected static DatagramSocket LS_socket;  // Location Service
    protected static InetAddress LS_address;
    protected static InetAddress SU;// Subscriber Unit
    String strClassName = LRRPmsg.class.getName();
    Logger logger = Logger.getLogger(strClassName);

    public LRRPmsg() throws IOException, InterruptedException {
        LS_address = LRRP.LS_address;
        SU = LRRP.SU;
        LS_socket = LRRP.LS_socket;
    }

    public static void receive() throws IOException, InterruptedException {
        boolean x = true;
        while (x) {
            byte[] data = new byte[500];
            DatagramPacket receivePacket = new DatagramPacket(data, data.length, LS_address, 4001);
            LS_socket.receive(receivePacket);
            switch (data[0]) {
                case (byte) 0x07:
                    //immediate Location Request
                    if (data[3] == 0x04 && data[4] == 0x00 && data[5] == 0x00 && data[6] == 0x00 && data[7] == 0x01) {
                        ImmediateLocation.report(data);
                    }
//			case (byte) 0x10:
//				System.out.println("Havent written yet");
            }
        }
    }

    public void run() {
        try {
            receive();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
