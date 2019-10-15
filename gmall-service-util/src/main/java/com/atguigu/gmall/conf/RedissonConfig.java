package com.atguigu.gmall.conf;

import com.atguigu.gmall.util.RedissonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host:disabled}")
    private String host;
    @Value("${spring.redis.port:0}")
    private int port;
    @Value("${spring.redis.password:disabled}")
    private String password;
    @Value("${spring.redis.database:0}")
    private int database;

    @Bean
    public RedissonUtil getRedissonUtil(){
        RedissonUtil redissonUtil = new RedissonUtil();
        redissonUtil.init(host, port, password, database);
        return redissonUtil;
    }
}
