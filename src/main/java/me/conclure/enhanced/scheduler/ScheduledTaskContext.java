package me.conclure.enhanced.scheduler;

import org.jetbrains.annotations.NotNull;

public interface ScheduledTaskContext {

    void cancel();

    @NotNull
    ScheduledTaskId getTaskId();

}
