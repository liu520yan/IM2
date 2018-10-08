package com.im2.common;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * this.getClass().getClassLoader().getResource("文件名.文件类型")
 * <p>
 * Created by liuyan on 2018/10/8.
 */
public class FileUtil {
    /**
     * 将文件转换成map存储	 * 	 * @return
     */
    public static Map<String, String> readCsv(String filePath) {
        Map<String, String> map = new HashMap<>();
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                if (!tempString.startsWith("#")) {
                    String[] strArray = tempString.split(",");
                    map.put(strArray[0], strArray[1]);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return map;
    }

    public String readJson(String filePath) {
        Map<String, String> map = new HashMap<>();
        String encoding = "UTF-8";
        File file = new File(filePath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
