package com.mint.other.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cw
 * @date 2020/8/9 18:54
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleMessage {

    /**
     * 每30分模板消息发送
     */
    @Scheduled(cron = "* */30 * * * ?")
    public void sendMessage() {
        List<Object> vipCodeList = new ArrayList<>();
        if (vipCodeList.size() > 0) {

        } else {
            // 休息60s
            log.error("所有视频会员的模板消息已经发送完成");
            int milliSecond = 60000;
            sleep(milliSecond);
        }
    }

    private void sleep(Integer milliSecond) {
        try {
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
