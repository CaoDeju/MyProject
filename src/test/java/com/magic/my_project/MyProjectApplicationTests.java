package com.magic.my_project;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import java.io.*;

public class MyProjectApplicationTests {

    @Test
    public void readWordFromFile() {
        BufferedReader br = null;
        Writer fileWriter = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("./src/main/resources/word.txt"));
            br = new BufferedReader(new InputStreamReader(fileInputStream));
            String readLine;
            File file = new File("./src/main/resources/words.txt");
            fileWriter = new FileWriter(file);
            while ((readLine = br.readLine()) != null) {
                if (StringUtils.isNotBlank(readLine)) {
                    String[] split = readLine.split("\",\"");
                    for (String s : split) {
                        fileWriter.write(s+"\r\n");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }

            } catch (IOException e) {
            }
        }
    }

}
