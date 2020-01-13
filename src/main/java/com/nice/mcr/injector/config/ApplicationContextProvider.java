package com.nice.mcr.injector.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     When Spring instantiates beans, it looks for ApplicationContextAware implementations, If they are found, the setApplicationContext() methods will be invoked.
 * </p>
 * <p>
 *     In this way, Spring is setting current {@link ApplicationContext}.
 * </p>
 *
 * @author Binyamin Regev
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext = appContext;
    }
}
