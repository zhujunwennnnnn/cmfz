package com.cmfz.interceptor;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Interceptor {

    @Pointcut(value = ("execution(* com.cmfz.service.*.*(..))"))
    public void pc(){}

    @Pointcut(value = ("execution(* com.cmfz.service.BannerService.insert(..))"))
    public void pc1(){}

    @Before("pc()")
    public void before(){

    }

    @After("pc1()")
    public void after(){

    }

}
