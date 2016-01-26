package org.miaohong.jbfs.store.server.controller;

import org.miaohong.jbfs.store.block.SuperBlockConst;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * Created by baidu on 16/1/25.
 */
public class Test {
    private static Test instance = new Test();

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    private int i = 0;

    private Test() {
        i = 10;
    }

    private static String filePath = "/tmp/a";

    public static Test getInstance() {
        return instance;
    }


    public static void main(String[] args) {
         FileChannel fc = null;
        Test t = Test.getInstance();
        System.out.println(t.getI());

        Test t2 = Test.getInstance();
        System.out.println(t2.getI());

         byte[] magic = {(byte)0xab, (byte)0xcd, (byte)0xef, (byte)0x00};
         byte ver = (byte)0x01;
         byte[] padding = new byte[SuperBlockConst.SUPER_BLOCK_PADDING_SIZE];

        try {
            fc = new FileOutputStream(filePath, true).getChannel();

            FileOutputStream ff = new FileOutputStream(filePath, true);

            ByteBuffer bb = ByteBuffer.allocate(SuperBlockConst.SUPER_BLOCK_HEADER_SIZE);
            bb.order(ByteOrder.BIG_ENDIAN);

            bb.put(magic);
            bb.put(ver);
            bb.put(padding);

            System.out.println(bb.toString());

            ff.write(magic);
            ff.write(ver);
            ff.write(padding);

            //fc.write(bb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
