package org.miaohong.jbfs.pitchfork.server;

import org.miaohong.jbfs.pitchfork.config.PitchforkConfig;

/**
 * Created by miaohong on 16/2/10.
 */
public class Pitchfork {

    private PitchforkConfig pitchforkConfig = PitchforkConfig.getInstance();

    private Pitchfork() {}


    private static class SingletonHolder {
        private static final Pitchfork INSTANCE = new Pitchfork();
    }

    public static final Pitchfork getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void run() {

        
    }

//    public static void main(String[] args) {
//
//    }
}
