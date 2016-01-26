package org.miaohong.jbfs.store.store;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaohong on 16/1/17.
 */
public class Utils {

    public static String[] splitStr(String buf, String separator) {
        if (buf == null) {
            return null;
        }

        return buf.split(separator);
    }


    public static boolean isFileExist(String filePath) {
        File f = new File(filePath);

        return f.exists();
    }

    public static boolean renameFile(String srcFile, String dstFile) {
        return new File(srcFile).renameTo(new File(dstFile));
    }

    public static String getFileDir(String filePath) {
        String dirStr = "";
        String[] pathList = filePath.split("/");

        for(int i = 0; i < pathList.length - 1; i++) {
            dirStr += pathList[i];

            dirStr += "/";
        }
        System.out.println(dirStr);

        return dirStr;
    }
}
