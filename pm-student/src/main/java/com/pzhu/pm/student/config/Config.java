package com.pzhu.pm.student.config;

import com.pzhu.pm.student.common.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author QYstart
 * @date 2021/4/4
 */
@Component
public class Config {
    @Bean(name = "uploadExecutorPool")
    public ThreadPoolExecutor crawlExecutorPool() {
        ThreadPoolExecutor uploadExecutorPool = new ThreadPoolExecutor(10, 20, 60,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(5));
        return uploadExecutorPool;
    }
}
