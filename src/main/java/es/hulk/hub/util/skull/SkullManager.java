package es.hulk.hub.util.skull;

import es.hulk.hub.util.CC;
import es.hulk.hub.util.ServerUtil;
import es.hulk.hub.util.skull.impl.Skull_v1_7;
import es.hulk.hub.util.skull.impl.Skull_v1_8;
import lombok.Getter;

/**
 * Created by Risas
 * Project: PandaHub
 * Date: 14-04-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class SkullManager {

    private SkullVersion version;

    public SkullManager() {
        if (ServerUtil.SERVER_VERSION_INT == 7) {
            this.version = new Skull_v1_7();
        }
        else if (ServerUtil.SERVER_VERSION_INT >= 8) {
            this.version = new Skull_v1_8();
        }
        else {
            CC.sendConsole("&cSkull Version: Your server version is not supported!");
        }
    }
}
