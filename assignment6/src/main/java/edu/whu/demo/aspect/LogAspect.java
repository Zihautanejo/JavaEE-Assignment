package edu.whu.demo.aspect;

import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Data
@Component
public class LogAspect {

    //要统计每一次的使用时间，调用次数，异常次数
    static final Map<String,Long> calltimes = Collections.synchronizedMap(new HashMap<>());
    static final Map<String,Long> excepttimes = Collections.synchronizedMap(new HashMap<>());
    static final Map<String,List<Long> > time = Collections.synchronizedMap(new HashMap<>());

    @Pointcut("execution(* edu.whu.demo.controller.*.*(..))")
    //这里限定在controller层  --表达式还需要理解一下
    public void ControllerPointCut(){}

    @Around("ControllerPointCut()")
    public Object TimePointCut(ProceedingJoinPoint joinPoint) throws Throwable{

        //计入调用次数
        String ContSign = joinPoint.getSignature().toString();
        Long m = calltimes.containsKey(ContSign) ? calltimes.get(ContSign) : 0;
        calltimes.put(ContSign, m + 1);

        Object refVal = null;
        try {
            long t1 = Calendar.getInstance().getTimeInMillis();
            refVal = joinPoint.proceed();
            long t2 = Calendar.getInstance().getTimeInMillis();

            //计入时间
            List<Long> n = time.containsKey(ContSign) ? time.get(ContSign) : new ArrayList<Long>();
            n.add(t2 - t1);
            time.put(ContSign, n);

            //返回函数结果 --必要的
            return refVal;

        } catch (Throwable e) {
            Long p = excepttimes.containsKey(ContSign) ? excepttimes.get(ContSign) : 0;
            excepttimes.put(ContSign, p + 1);
            throw e;
        }
    }

    public static Map<String, Long> getCalltimes() {
        return calltimes;
    }

    public static Map<String,Long> getExcepttimes(){
        return excepttimes;
    }
    public static Map<String,List<Long> > getTime() {
        return time;
    }

    /*@AfterThrowing(pointcut="ControllerPointCut()",throwing = "ex")
    public void ExceptPointCut(JoinPoint jp, Throwable ex){
        String ContSign = jp.getSignature().toString();
        Long m = excepttimes.containsKey(ContSign)?excepttimes.get(ContSign):0;
        excepttimes.put(ContSign,m+1);
    }*/

}
