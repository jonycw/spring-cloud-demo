package com.mint.other.aliyun;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：cw
 * @date ：Created in 2020/8/27 上午11:17
 * @description：
 * @modified By：
 * @version: $
 */
@Component
@Slf4j
public class AliCloudTools {

    private final DefaultMQProducer defaultMQProducer;
    private final IAcsClient iAcsClient;

    @Autowired
    public AliCloudTools(DefaultMQProducer defaultMQProducer, IAcsClient iAcsClient) {
        this.defaultMQProducer = defaultMQProducer;
        this.iAcsClient = iAcsClient;
    }

    /**
     * 生产短信消息
     * @param phone
     * @param type
     * @return
     */
    public boolean producerPhoneSms(String code,String phone,String type) throws MQClientException, UnsupportedEncodingException {
        // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
        defaultMQProducer.start();
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);
        Message msg = new Message( "phone_message", "TagA", (code+","+phone+","+type).getBytes(RemotingHelper.DEFAULT_CHARSET));

        msg.setKeys("phone_"+phone);
        try {
//            defaultMQProducer.send(msg, new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    log.info(new Date() + " Send mq message success. Topic is:",sendResult);
//                }
//                @Override
//                public void onException(Throwable e) {
//                    System.out.println("send message failed.");
//                    log.info(new Date() + " Send mq message failed. Topic is:");
//                    e.printStackTrace();
//                }
//            });
             defaultMQProducer.send(msg);
             log.info("mq发送成功");
        }
        catch (Exception e) {
            log.info(new Date() + " Send mq message failed. Topic is:" + msg.getTopic());
            e.printStackTrace();
            return false;
        }
        // 在应用退出前，销毁 Producer 对象
        // 注意：如果不销毁也没有问题
        defaultMQProducer.shutdown();
        return true;
    }

    /**
     * 发送短信
     * @param code
     * @param phoneNumber
     * @param TemplateCode
     * @return
     */
    public SendSmsResponse sendSms(String code, String phoneNumber, String TemplateCode){

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();

        // 拼接接收方手机号码
//        String recNum = "";
//        for (String phoneNumber : phoneNumbers) {
//            recNum += phoneNumber + ",";
//        }
//        recNum = recNum.substring(0, recNum.length() - 1);

        //必填:待发送手机号
        request.setPhoneNumbers(phoneNumber);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("xxxxxxxxx");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(TemplateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"codes\":\""+code+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("xxxxx");

        try{
            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = iAcsClient.getAcsResponse(request);
            return sendSmsResponse;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param bizId
     * @return
     * @throws ClientException
     */
    public QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = iAcsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
}
