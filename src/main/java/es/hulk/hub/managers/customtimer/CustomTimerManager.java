package es.hulk.hub.managers.customtimer;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CustomTimerManager {

    private List<CustomTimer> customTimers = Lists.newArrayList();

    public void addTimer(CustomTimer timer){
        customTimers.add(timer);
    }

    public void deleteTimer(CustomTimer timer){
        customTimers.remove(timer);
    }

    public CustomTimer getCustomTimer(String name){
        for (CustomTimer timer : customTimers) {
            if (timer.getName().equalsIgnoreCase(name)) {
                return timer;
            }
        }
        return null;
    }
}
