package com.colak.springtutorial.aspect;

import com.colak.springtutorial.jpa.SampleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LocaleFilterAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..))")
    void isRepository() {
    }

    @Around("execution(public * *(..)) && isRepository()")
    public Object repositoryMethods(ProceedingJoinPoint pjp) throws Throwable {
        String language = LocaleContextHolder.getLocale().getLanguage();
        log.info("setting language to {}", language);

        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter(SampleEntity.FILTER_NAME);
        filter.setParameter(SampleEntity.PARAM_NAME, language);

        return pjp.proceed();
    }
}
