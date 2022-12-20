package meta.metarch.infra.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meta.metarch.infra.helper.I18NHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingDebugAspect {

    private final I18NHelper i18NHelper;

    @Before("execution(* meta.smart.application.usecase..*(..)))")
    public void beforeMethod(JoinPoint joinPoint) {
        log.error(i18NHelper.getMessage("smart.message.initmethod", joinPoint.getSignature().toShortString()));
    }

    @AfterReturning("execution(* meta.smart.application.usecase..*(..)))")
    public void afterMethod(JoinPoint joinPoint) {
        log.error(i18NHelper.getMessage("smart.message.finishmethod", joinPoint.getSignature().toShortString()));
    }
}
