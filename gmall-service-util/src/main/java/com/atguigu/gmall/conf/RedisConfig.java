package com.atguigu.gmall.conf;

import com.atguigu.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host:disabled}")
    private String host;
    @Value("${spring.redis.port:0}")
    private int port;
    @Value("${spring.redis.password:disabled}")
    private String password;
    @Value("${spring.redis.database:0}")
    private int database;

    @Bean
    public RedisUtil getRedisUtil(){
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.initPool(host, port, password, database);
        return redisUtil;
    }
}
