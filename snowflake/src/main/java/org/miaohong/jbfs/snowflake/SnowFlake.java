package org.miaohong.jbfs.snowflake;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RAtomicLong;

/**
 * Created by miaohong on 16/2/4.
 */
public class SnowFlake {
    private String redisAddr;
    private RAtomicLong atomicLong;
    public SnowFlake(String redisAddr) {
        this.redisAddr = redisAddr;


        Config config = new Config();
        config.useSingleServer().setAddress(redisAddr).setConnectionPoolSize(5);

        RedissonClient redissonClient = Redisson.create(config);
        atomicLong = redissonClient.getAtomicLong("anyAtomicLong");
        atomicLong.incrementAndGet();
    }


    public long getLongUUID() {
        return atomicLong.incrementAndGet();
    }

}
