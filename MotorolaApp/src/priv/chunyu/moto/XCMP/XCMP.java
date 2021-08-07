package priv.chunyu.moto.XCMP;

import priv.chunyu.moto.xnl.XNL;

import java.io.IOException;

public class XCMP extends XNL {

    public XCMP() throws IOException, InterruptedException {
        super();
    }

    public void run() {
        try {
            new XCMPsocket();
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}