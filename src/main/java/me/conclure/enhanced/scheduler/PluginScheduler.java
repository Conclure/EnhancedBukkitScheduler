package me.conclure.enhanced.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Function;

public final class PluginScheduler implements EnhancedScheduler {

    final Plugin plugin;
    final BukkitScheduler scheduler;

    public PluginScheduler(Plugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin cannot be null");
        this.scheduler = plugin.getServer().getScheduler();
    }

    @Override
    public Plugin getOwner() {
        return plugin;
    }

    @Override
    public void cancelAllTasks() {
        scheduler.cancelTasks(plugin);
    }

    @Override
    public void cancelTask(int rawId) {
        scheduler.cancelTask(rawId);
    }

    @Override
    public void cancelTask(ScheduledTaskId id) {
        Objects.requireNonNull(id,"id cannot be null");
        scheduler.cancelTask(id.getRawId());
    }

    @Override
    public void cancelTask(ScheduledTask task) {
        Objects.requireNonNull(task,"task cannot be null");
        scheduler.cancelTask(task.getTaskId().getRawId());
    }

    @Override
    public boolean isCurrentlyRunning(int taskId) {
        return scheduler.isCurrentlyRunning(taskId);
    }

    @Override
    public boolean isCurrentlyRunning(ScheduledTaskId id) {
        Objects.requireNonNull(id,"id cannot be null");
        return scheduler.isCurrentlyRunning(id.getRawId());
    }

    @Override
    public boolean isCurrentlyRunning(ScheduledTask task) {
        Objects.requireNonNull(task,"task cannot be null");
        return scheduler.isCurrentlyRunning(task.getTaskId().getRawId());
    }

    @Override
    public boolean isQueued(int taskId) {
        return scheduler.isQueued(taskId);
    }

    @Override
    public boolean isQueued(ScheduledTaskId id) {
        Objects.requireNonNull(id,"id cannot be null");
        return scheduler.isQueued(id.getRawId());
    }

    @Override
    public boolean isQueued(ScheduledTask task) {
        Objects.requireNonNull(task,"task cannot be null");
        return scheduler.isQueued(task.getTaskId().getRawId());
    }

    @Override
    public Task runTask(ScheduledConsumer consumer) {
        Objects.requireNonNull(consumer,"consumer cannot be null");
        return new Task(plugin,consumer,r -> r.runTask(plugin));
    }

    @Override
    public Task runTask(ScheduledRunnable runnable) {
        Objects.requireNonNull(runnable,"runnable cannot be null");
        return new Task(plugin,runnable,r -> r.runTask(plugin));
    }

    @Override
    public Task runTaskAsync(ScheduledConsumer consumer) {
        Objects.requireNonNull(consumer,"consumer cannot be null");
        return new Task(plugin,consumer,r -> r.runTaskAsynchronously(plugin));
    }

    @Override
    public Task runTaskAsync(ScheduledRunnable runnable) {
        Objects.requireNonNull(runnable,"runnable cannot be null");
        return new Task(plugin,runnable,r -> r.runTaskAsynchronously(plugin));
    }

    @Override
    public Task runTaskLater(ScheduledConsumer consumer, long delay) {
        Objects.requireNonNull(consumer,"consumer cannot be null");
        return new Task(plugin,consumer,r -> r.runTaskLater(plugin,delay));
    }

    @Override
    public Task runTaskLater(ScheduledRunnable runnable, long delay) {
        Objects.requireNonNull(runnable,"runnable cannot be null");
        return new Task(plugin,runnable,r -> r.runTaskLater(plugin,delay));
    }

    @Override
    public Task runTaskLaterAsync(ScheduledConsumer consumer, long delay) {
        Objects.requireNonNull(consumer,"consumer cannot be null");
        return new Task(plugin,consumer,r -> r.runTaskLaterAsynchronously(plugin,delay));
    }

    @Override
    public Task runTaskLaterAsync(ScheduledRunnable runnable, long delay) {
        Objects.requireNonNull(runnable,"runnable cannot be null");
        return new Task(plugin,runnable,r -> r.runTaskLaterAsynchronously(plugin,delay));
    }

    @Override
    public Task runTaskTimer(
            ScheduledConsumer consumer,
            long delay,
            long period
    ) {
        Objects.requireNonNull(consumer,"consumer cannot be null");
        return new Task(plugin,consumer,r -> r.runTaskTimer(plugin,delay,period));
    }

    @Override
    public Task runTaskTimer(
            ScheduledRunnable runnable,
            long delay,
            long period
    ) {
        Objects.requireNonNull(runnable,"runnable cannot be null");
        return new Task(plugin,runnable,r -> r.runTaskTimer(plugin,delay,period));
    }

    @Override
    public Task runTaskTimerAsync(
            ScheduledConsumer consumer,
            long delay,
            long period
    ) {
        Objects.requireNonNull(consumer,"consumer cannot be null");
        return new Task(plugin,consumer,r -> r.runTaskTimerAsynchronously(plugin,delay,period));
    }

    @Override
    public Task runTaskTimerAsync(
            ScheduledRunnable runnable,
            long delay,
            long period
    ) {
        Objects.requireNonNull(runnable,"runnable cannot be null");
        return new Task(plugin,runnable,r -> r.runTaskTimerAsynchronously(plugin,delay,period));
    }

    static final class Task implements ScheduledTask {

        final Plugin plugin;
        final Id id;
        final BukkitTask task;
        final Taskable taskable;

        Task(
                Plugin plugin,
                Taskable taskable,
                Function<BukkitRunnable,BukkitTask> function
        ) {
            this.plugin = plugin;
            this.taskable = taskable;
            if (taskable instanceof ScheduledConsumer) {
                task = function.apply(new BukkitRunnable() {
                    @Override
                    public void run() {
                        ((ScheduledConsumer) taskable).accept(Task.this);
                    }
                });
            } else if (taskable instanceof ScheduledRunnable){
                task = function.apply(new BukkitRunnable() {
                    @Override
                    public void run() {
                        ((ScheduledRunnable) taskable).run();
                    }
                });
            } else {
                throw new IllegalArgumentException("taskable does not instanceof ScheduledConsumer or ScheduledRunnable");
            }
            id = new Id(plugin,task.getTaskId(),this);
        }

        @Override
        public boolean isCancelled() {
            return Reflection.isCancelled(task);
        }

        @Override
        public boolean isSync() {
            return task.isSync();
        }

        @Override
        public void cancel() {
            task.cancel();
        }

        @Override
        public ScheduledTaskId getTaskId() {
            return id;
        }
    }

    static final class Id implements ScheduledTaskId {

        final Plugin plugin;
        final int rawId;
        final String id;
        final Task task;

        Id(
                Plugin plugin,
                int rawId,
                Task task) {
            this.plugin = plugin;
            this.rawId = rawId;
            this.task = task;
            this.id = plugin.getName() + ":" + rawId;
        }

        @Override
        public ScheduledTask getTask() {
            return task;
        }

        @Override
        public int getRawId() {
            return rawId;
        }

        @Override
        public Plugin getOwner() {
            return plugin;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return rawId == id.rawId && Objects.equals(plugin, id.plugin);
        }

        @Override
        public int hashCode() {
            return Objects.hash(plugin, rawId);
        }
    }

    static final class Reflection {

        static final Class<?> CLASS_CRAFTTASK;
        static final Field FIELD_PERIOD;

        static {
            String classCraftTask = new StringBuilder()
                    .append("org.bukkit.craftbukkit.")
                    .append(Bukkit.getServer()
                            .getClass()
                            .getPackage()
                            .getName()
                            .replace('.', ',')
                            .split(",")[3])
                    .append(".scheduler.CraftTask")
                    .toString();

            try {
                CLASS_CRAFTTASK = Class.forName(classCraftTask);
                (FIELD_PERIOD = CLASS_CRAFTTASK.getDeclaredField("period")).setAccessible(true);
            } catch (ReflectiveOperationException e) {
                throw new Error(e);
            }
        }

        static boolean isCancelled(BukkitTask task) {
            try {
                return FIELD_PERIOD.getLong(task) == -2L;
            } catch (ReflectiveOperationException e) {
                return false;
            }
        }
    }
}
