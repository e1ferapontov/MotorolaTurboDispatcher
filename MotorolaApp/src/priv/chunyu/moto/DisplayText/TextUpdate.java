package priv.chunyu.moto.DisplayText;

import priv.chunyu.moto.XCMP.XCMPsocket;

import java.io.IOException;
import java.util.logging.Logger;

public class TextUpdate extends XCMPsocket {
    public static byte id[] = {(byte) 0x00, (byte) 0x14};
    static String strClassName = TextUpdate.class.getName();
    static Logger logger = Logger.getLogger(strClassName);
    static String text;
    public TextUpdate() throws IOException, InterruptedException {
        super();
    }

    public static String request() throws IOException, InterruptedException {
        synchronized (lock) {

        }
        return text;
    }

    public static void reply(byte[] data) throws IOException, InterruptedException {
        synchronized (lock) {
            //esn = DataProcess.ReadingData(14, data.length, data);
            //logger.info("Electric Serial Number: " + esn);
            lock.notify();
            ;
        }
    }
}