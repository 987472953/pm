package com.pzhu.pm.student.config;

import com.pzhu.pm.student.common.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author QYstart
 * @date 2021/4/4
 */
@Configuration
public class Config {
    @Bean
    public Cache getCache(){
        return new Cache();
    }
}
