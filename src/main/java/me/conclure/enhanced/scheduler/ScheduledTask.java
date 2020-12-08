package me.conclure.enhanced.scheduler;

import org.jetbrains.annotations.NotNull;

public interface ScheduledTask extends ScheduledTaskContext {

    boolean isCancelled();

    boolean isSync();

    @Override
    void cancel();

    @NotNull
    @Override
    ScheduledTaskId getTaskId();
}
