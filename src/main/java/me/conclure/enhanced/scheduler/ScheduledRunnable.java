package me.conclure.enhanced.scheduler;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ScheduledRunnable extends Taskable {

    void run();

    @NotNull
    static ScheduledRunnable of(
            @NotNull Runnable runnable
    ) {
        return runnable::run;
    }

}
