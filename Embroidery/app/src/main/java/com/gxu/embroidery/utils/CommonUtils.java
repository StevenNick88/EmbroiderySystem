package com.gxu.embroidery.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Administrator on 2015/5/15.
 */
public class CommonUtils {

    public static String saveFileToSdcard(String fileName, byte[] data) {
        String path="";
        boolean flag = false;
        // 先判断sdcard的状态
        String state = Environment.getExternalStorageState();
        // 表示sdcard卡挂载在手机上，并且可以读写
        FileOutputStream outputStream = null;
        // 获得srcard的根目录 /mnt/sdcard/....
        File root = Environment.getExternalStorageDirectory();

        if (state.equals(Environment.MEDIA_MOUNTED)) {

            File file = new File(root.getAbsolutePath() + "/Embroidery");
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                outputStream = new FileOutputStream(new File(file, fileName));
                outputStream.write(data, 0, data.length);
                flag = true;
                path = file.getAbsolutePath();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Exception e2) {
                        // TODO: handle exception
                        e2.printStackTrace();
                    }

                }
            }

        }
        return path;
    }

    public static byte[]  ChangeListToByte(List<String> list) {
        String listStr="";
        byte[] bytes=new byte[]{};
        for (String str: list) {
            listStr=listStr+str;

        }
        bytes= listStr.getBytes();
        return bytes;
    }

    /**
     * 创建指定数量的随机字符串
     * @param numberFlag 是否是数字
     * @param length
     * @return
     */
    public static String createRandom(boolean numberFlag, int length){
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }
}
