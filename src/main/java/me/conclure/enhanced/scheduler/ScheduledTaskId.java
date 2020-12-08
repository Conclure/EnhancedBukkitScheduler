package me.conclure.enhanced.scheduler;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface ScheduledTaskId {

    @NotNull
    ScheduledTask getTask();

    int getRawId();

    @NotNull
    Plugin getOwner();

    @NotNull
    String getId();

}
