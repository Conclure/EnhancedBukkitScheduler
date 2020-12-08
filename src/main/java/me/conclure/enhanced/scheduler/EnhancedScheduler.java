package me.conclure.enhanced.scheduler;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface EnhancedScheduler {

    @NotNull
    Plugin getOwner();
    
    void cancelAllTasks();

    void cancelTask(
            int rawId
    );

    void cancelTask(
            @NotNull ScheduledTaskId id
    );
    
    void cancelTask(
            @NotNull ScheduledTask task
    );

    boolean isCurrentlyRunning(
            int taskId
    );

    boolean isCurrentlyRunning(
            @NotNull ScheduledTaskId id
    );
    
    boolean isCurrentlyRunning(
            @NotNull ScheduledTask task
    );

    boolean isQueued(
            int taskId
    );

    boolean isQueued(
            @NotNull ScheduledTaskId id
    );
    
    boolean isQueued(
            @NotNull ScheduledTask task
    );

    @NotNull
    ScheduledTask runTask(
            @NotNull ScheduledConsumer consumer
    );

    @NotNull
    ScheduledTask runTask(
            @NotNull ScheduledRunnable runnable
    );

    @NotNull
    ScheduledTask runTaskAsync(
            @NotNull ScheduledConsumer consumer
    );

    @NotNull
    ScheduledTask runTaskAsync(
            @NotNull ScheduledRunnable runnable
    );

    @NotNull
    ScheduledTask runTaskLater(
            @NotNull ScheduledConsumer consumer,
            long delay
    );

    @NotNull
    ScheduledTask runTaskLater(
            @NotNull ScheduledRunnable runnable,
            long delay
    );

    @NotNull
    ScheduledTask runTaskLaterAsync(
            @NotNull ScheduledConsumer consumer,
            long delay
    );

    @NotNull
    ScheduledTask runTaskLaterAsync(
            @NotNull ScheduledRunnable runnable,
            long delay
    );

    @NotNull
    ScheduledTask runTaskTimer(
            @NotNull ScheduledConsumer consumer,
            long delay,
            long period
    );

    @NotNull
    ScheduledTask runTaskTimer(
            @NotNull ScheduledRunnable runnable,
            long delay,
            long period
    );

    @NotNull
    ScheduledTask runTaskTimerAsync(
            @NotNull ScheduledConsumer consumer,
            long delay,
            long period
    );

    @NotNull
    ScheduledTask runTaskTimerAsync(
            @NotNull ScheduledRunnable runnable,
            long delay,
            long period
    );

}
