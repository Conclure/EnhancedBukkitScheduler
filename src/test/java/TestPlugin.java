import me.conclure.enhanced.scheduler.EnhancedScheduler;
import me.conclure.enhanced.scheduler.PluginScheduler;
import me.conclure.enhanced.scheduler.ScheduledTaskContext;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        EnhancedScheduler scheduler = new PluginScheduler(this);
        scheduler.runTask(ScheduledTaskContext::cancel).cancel();
    }
}
