package com.mint.other.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author ：cw
 * @date ：Created in 2020/8/27 上午11:10
 * @description：
 * @modified By：
 * @version: $
 */

@Configuration
public class AliCloudProducerConfig {

    @Value("${ali-cloud.accessKeyId}")
    private String accessKeyId;

    @Value("${ali-cloud.accessKeySecret}")
    private String accessKeySecret;

    @Value("${ali-cloud.autumnGroupId}")
    private String autumnGroupId;

    @Value("${ali-cloud.nameSrvAddr}")
    private String nameSrvAddr;

    @Value("${ali-cloud.product}")
    private String product;

    @Value("${ali-cloud.domain}")
    private String domain;

    @Bean
    public DefaultMQProducer defaultMQProducer(){
        RPCHook rpcHook = new AclClientRPCHook(new SessionCredentials(accessKeyId,
                accessKeySecret));
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(autumnGroupId, rpcHook);

        // DefaultMQProducer defaultMQProducer = new DefaultMQProducer(autumnGroupId,rpcHook, true, null);
        /**
         * 设置使用接入方式为阿里云，在使用云上消息轨迹的时候，需要设置此项，如果不开启消息轨迹功能，则运行不设置此项.
         */
        // defaultMQProducer.setAccessChannel(AccessChannel.CLOUD);
        defaultMQProducer.setNamesrvAddr(nameSrvAddr);
        defaultMQProducer.setSendMsgTimeout(5000);
        return defaultMQProducer;
    }

    @Bean
    public IAcsClient acsClient() throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",accessKeyId,accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou",product,domain);
        return new DefaultAcsClient(profile);
    }
}
