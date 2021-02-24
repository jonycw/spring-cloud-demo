package com.mint.other.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author cw
 * @Date 2020/6/8 15:11
 * @Description:
 */
@Slf4j
public class WriteLogsUtil {
    public static void write(String logPath, String message) {
        Charset charset = Charset.forName("UTF-8");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(logPath);

            // 构造错误信息 添加产生时间
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = simpleDateFormat.format(date);

            String messageAll = now + " " + message + "\r\n";
            fileWriter.write(messageAll);
            fileWriter.flush();
        } catch (IOException e) {
            log.error("写入日志文件失败：{}", e.getMessage());
        }finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    log.error("关闭日志文件失败：{}", e.getMessage());
                }
            }
        }

    }
}

