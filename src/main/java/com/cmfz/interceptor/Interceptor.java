package com.cmfz.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.RedisTemplate;

@Component
@Aspect
public class Interceptor {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Around("@annotation(com.cmfz.annotation.AddCache)")
    public Object before(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();//使用String序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);//设置序列化
        //namespace
        String nameSpace = proceedingJoinPoint.getTarget().getClass().getName();
        //方法名
        String name = proceedingJoinPoint.getSignature().getName();
        //获得所有的参数
        Object[] args = proceedingJoinPoint.getArgs();
        //获取一个Stringbudil
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        for (Object arg : args) {
            stringBuilder.append(arg.toString());
        }

        HashOperations hashOperations = redisTemplate.opsForHash();
        if(hashOperations.hasKey(nameSpace,stringBuilder)){
            System.out.println("获取缓存");
            Object o = hashOperations.get(nameSpace, stringBuilder);
            return o;
        }

        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("添加缓存");
        hashOperations.put(nameSpace,stringBuilder,proceed);
        return proceed;
    }


    @After("@annotation(com.cmfz.annotation.ClearCache)")
    public void after(JoinPoint joinPoint){
        String target = joinPoint.getTarget().getClass().getName();
        System.out.println("清除缓存");
        redisTemplate.delete(target);
    }

}
