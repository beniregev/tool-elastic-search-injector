package com.nice.mcr.injector.policies;

import org.springframework.boot.ApplicationArguments;

public interface Policy {
    public void run();
    default void run(ApplicationArguments args) {}
    default void setApplicationArguments(ApplicationArguments args) {}
}
