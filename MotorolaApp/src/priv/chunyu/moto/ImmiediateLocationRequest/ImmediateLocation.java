package priv.chunyu.moto.ImmiediateLocationRequest;

import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.LocationRequestResponseProtocol.LRRPsocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

public class ImmediateLocation extends LRRPsocket {

    static String strClassName = ImmediateLocation.class.getName();
    static Logger logger = Logger.getLogger(strClassName);
    static String latlng;
    public ImmediateLocation() throws IOException, InterruptedException {
        super();
    }

    public static String request(int RadioID) throws IOException, InterruptedException {
        synchronized (lock) {
            //latlng="NO LATLON";
            byte data[] = {(byte) 0x00, (byte) 0xce, (byte) 0xa0, (byte) 0x00, (byte) 0x9c, (byte) 0x04, (byte) 0x0d, (byte) 0x00, (byte) 0x0a};

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(data);
            outputStream.write("hello world".getBytes());

            byte outputData[] = outputStream.toByteArray();


            byte[] ipBytes = InetAddress.getByName("12.0.0.0").getAddress();
            byte[] resultAddressBytes = (ByteBuffer.allocate(ipBytes.length)).putInt(RadioID).array();
            for (int i = 0; i < ipBytes.length; i++) {
                if (ipBytes[i] != (byte) 0) {
                    resultAddressBytes[i] = ipBytes[i];
                }
            }

            SU = InetAddress.getByAddress(resultAddressBytes);
            DatagramPacket DpSend = new DatagramPacket(outputData, outputData.length, SU, 4007);
            LS_socket.send(DpSend);
            lock.wait(500);
        }
        return latlng;
    }

    public static void report(byte[] data) throws IOException, InterruptedException {
        synchronized (lock) {
            double Lat;
            double Lon;
            logger.info("ImmediateLocation Report");
            final long q = 4294967295L;// 0xfffffff
            Lat = DataProcess.ReadLatLongData(15, 18, data) * 180.0 / q;
            Lon = DataProcess.ReadLatLongData(19, 22, data) * 360.0 / q;
            latlng = Lat + "," + Lon;
            lock.notify();
        }
    }
}