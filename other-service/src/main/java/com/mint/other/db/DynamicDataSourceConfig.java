package com.mint.other.db;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：cw
 * @date ：Created in 2020/6/10 上午11:56
 * @description：
 * @modified By：
 * @version: $
 */

@Configuration
public class DynamicDataSourceConfig {

    @Bean(name = "sourceOne")
    @ConfigurationProperties(prefix = "spring.datasource.druid.source-one" )
    public DataSource sourceOneDb () {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "sourceTwo")
    @ConfigurationProperties(prefix = "spring.datasource.druid.source-two" )
    public DataSource sourceTwoDb () {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源配置
     * @return
     */
    @Bean
    @Primary
    public DataSource multipleDataSource (@Qualifier("sourceOne") DataSource one,
                                          @Qualifier("sourceTwo") DataSource two) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map< Object, Object > targetDataSources = new HashMap<>(2);
        targetDataSources.put(DBTypeEnum.DATASOURCE_TWO.Value(), two);
        targetDataSources.put(DBTypeEnum.DATASOURCE_ONE.Value(), one);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(one);
        return dynamicDataSource;
    }

}
