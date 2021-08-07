package priv.chunyu.moto.VERINFO;

import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.XCMP.XCMPsocket;

import java.io.IOException;
import java.util.logging.Logger;

public class RFBand extends XCMPsocket {

    public static byte id[] = {(byte) 0x00, (byte) 0x04};
    static String strClassName = RFBand.class.getName();
    static Logger logger = Logger.getLogger(strClassName);
    static String powerlevel;
    public RFBand() throws IOException, InterruptedException {
        super();
    }

    public static String request() throws IOException, InterruptedException {
        synchronized (lock) {
            checkflag();
            byte data[] = {(byte) 0x00, (byte) 0x0F, (byte) 0X00, (byte) 0x0B, (byte) 0X01, (byte) flag,
                    (byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1],
                    (byte) id[0], (byte) id[1], (byte) 0X00, (byte) 0x03, (byte) 0X00, (byte) 0x0F, (byte) 0X63};
            output.write(data);
            logger.info("Sending Power Level Request");
            lock.wait(500);
        }
        return powerlevel;
    }

    public static void reply(byte[] data) throws IOException, InterruptedException {
        synchronized (lock) {
            HexicmalData = DataProcess.ReadingData(data);
            powerlevel = DataProcess.hexToAscii(26, HexicmalData.length(), HexicmalData.toString());
            logger.info("Power Level Request: " + powerlevel);
            lock.notify();
        }
    }
}

