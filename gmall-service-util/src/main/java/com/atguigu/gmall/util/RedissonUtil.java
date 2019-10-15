package com.atguigu.gmall.util;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonUtil {

    private RedissonClient redissonClient;

    public void init(String host, int port, String password, int database){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+host+":"+port).setPassword(password).setDatabase(database);
        redissonClient = Redisson.create(config);
    }

    public RedissonClient getRedissonClient(){
        return redissonClient;
    }
}
