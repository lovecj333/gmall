package com.atguigu.gmall.redisson.controller;

import com.atguigu.gmall.util.RedisUtil;
import com.atguigu.gmall.util.RedissonUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Controller
public class RedissonController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedissonUtil redissonUtil;

    private Lock reentrantLock = new ReentrantLock();

    @RequestMapping("testRedisson")
    @ResponseBody
    public String testRedisson(){
        RedissonClient redissonClient = redissonUtil.getRedissonClient();
        RLock lock = redissonClient.getLock("lock");
        lock.lock();
        //reentrantLock.lock();
        Jedis jedis = redisUtil.getJedis();
        try {
            String v = jedis.get("k");
            if(StringUtils.isBlank(v)){
                v = "0";
            }
            v = (Integer.parseInt(v)+1)+"";
            System.out.println("->" + v);
            jedis.set("k", v);
        } finally {
            jedis.close();
            lock.unlock();
            //reentrantLock.unlock();
        }
        return "success";
    }
}
