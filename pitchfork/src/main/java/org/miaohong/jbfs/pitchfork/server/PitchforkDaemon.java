package org.miaohong.jbfs.pitchfork.server;

/**
 * Created by miaohong on 16/2/10.
 */
public class PitchforkDaemon {

    public static void main(String[] args) {
        Pitchfork pitchfork = Pitchfork.getInstance();

        pitchfork.run();
    }
}
