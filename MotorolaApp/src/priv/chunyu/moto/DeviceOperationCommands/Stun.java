package priv.chunyu.moto.DeviceOperationCommands;

import priv.chunyu.moto.XCMP.XCMPsocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Logger;

public class Stun extends XCMPsocket {
    static byte id[] = {(byte) 0x00, (byte) 0xFA};
    static String strClassName = Stun.class.getName();
    static Logger logger = Logger.getLogger(strClassName);
    public Stun() throws IOException, InterruptedException {
        super();
    }

    public static void request(int RadioID) throws IOException, InterruptedException {
        synchronized (lock) {
            checkflag();
            byte[] hexedRadioId = Arrays.copyOfRange(ByteBuffer.allocate(6).putInt(RadioID).array(), 1, 6);

            byte data[] = {(byte) 0x00, (byte) 0x17, (byte) 0X00, (byte) 0x0B, (byte) 0X01, (byte) flag,
                    (byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1],
                    (byte) id[0], (byte) id[1], (byte) 0X00, (byte) 0X0B,
                    (byte) 0X04, (byte) 0X1C,
                    (byte) 0X01, //01 關閉 02 打開
                    (byte) 0X01,
                    (byte) 0x01, (byte) 0x03};

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(data);
            outputStream.write(hexedRadioId);

            byte outputData[] = outputStream.toByteArray();

            output.write(outputData);
            logger.info("Sending Stun Request ");
            lock.wait(500);
        }

    }

}
