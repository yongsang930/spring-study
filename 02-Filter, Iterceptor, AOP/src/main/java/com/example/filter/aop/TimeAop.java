package com.example.filter.aop;

import com.example.filter.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class TimeAop {

    /*
    * @target(com.example.annotation.PhoneNumber) - 런타임에 PhoneNumber 어노테이션이 붙은 클래스
    * @args(com.example.annotation.Entity) - 메서드 인자 타입에 Entity 어노테이션이 붙은 클래스
    * @within(com.example.annotation.Controller) - Controller 어노테이션이 붙은 클래스
    * @annotation(com.example.annotation.Encrypt) - Encrypt 어노테이션이 붙은 메서드
    * @annotation(Encrypt) - Encrypt 어노테이션이 붙은 메서드
    * within(com.example.dto.*) com.example.dto 패키지의 모든 클래스의 모든 메서드
    * within(com.example.dto..*) com.example.dto 하위 모든 패키지 포함
    * within(com.example.dto.UserService) com.example.dto.UserService 클래스의 모든 메서드
    */ 

    // spring bean에 등록 되어야만 동작함 그게 아니라면 AspectJ를 사용해야 함
    @Pointcut(value = "within(com.example.filter.controller.UserApiController)")
    public void timerPointCut() {}

    // 실행 흐름 around(전) > before > controller > afterReturning or afterThrowing > after > around(후)

    @Before(value = "timerPointCut()")
    public void before(JoinPoint joinPoint){
        log.info("before");
    }

    @After(value = "timerPointCut()")
    public void after(JoinPoint joinPoint){
        log.info("after");
    }

    @AfterReturning(value = "timerPointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        log.info("afterReturning");
    }

    @AfterThrowing(value = "timerPointCut()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint,Throwable throwable){
        log.info("afterThrowing");
    }

    // 전과 후를 모두 봄
    @Around(value = "timerPointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("메서드 실행 전");

        Arrays.stream(joinPoint.getArgs()).forEach(
                it -> {
                    if(it instanceof UserRequest){
                        var tempUser = (UserRequest) it;
                        var phoneNumber = tempUser.getPhoneNumber().replace("-", "");
                        tempUser.setPhoneNumber(phoneNumber);
                        //log.info("it: {} ", it);
                    }
                }
        );

        // 암복호화, 로깅, 모니터링 등
        var newObjs = Arrays.asList(
                new UserRequest()
        );

        /*

        // 암복호화
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof EncryptedRequest encReq) {

                String decryptedJson = AESUtil.decrypt(encReq.getData());
                UserRequest newReq = objectMapper.readValue(decryptedJson, UserRequest.class);

                args[i] = newReq;  // 복호화된 객체로 교체
            }
        }

        return joinPoint.proceed(args);  // 변경된 파라미터 전달

        // 값 세팅
        Object[] args = jp.getArgs();

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof UserRequest req) {

                req.setUserId(SecurityUtil.getUserId());
                req.setRequestTime(LocalDateTime.now());
            }
        }

        // 개인정보 마스킹
        for (Object arg : args) {
            if (arg instanceof UserRequest req) {
                UserRequest safe = new UserRequest();
                safe.setName(req.getName());
                safe.setEmail(mask(req.getEmail()));
                log.info("Request Safe Log = {}", safe);
            }
        }
        */

        var stopWatch = new StopWatch();

        stopWatch.start();

        joinPoint.proceed(newObjs.toArray());

        stopWatch.stop();

        log.info("메서드 실행 후 ::: " + stopWatch.getTotalTimeMillis());
    }
}
