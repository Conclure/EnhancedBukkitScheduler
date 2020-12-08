package me.conclure.enhanced.scheduler;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@FunctionalInterface
public interface ScheduledConsumer extends Taskable {

    void accept(
            @NotNull ScheduledTaskContext context
    );

    @NotNull
    static ScheduledConsumer of(
            @NotNull Consumer<ScheduledTaskContext> consumer
    ) {
        return consumer::accept;
    }

}
