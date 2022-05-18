package io.github.nosequel.tab.shared.skin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkinUtil {

    private static Map<UUID, String[]> cache = new HashMap<>();

    public static String[] getSkinData(UUID uuid) {
        if(uuid == null) return SkinType.DARK_GRAY.getSkinData();
        try {
            if (cache.containsKey(uuid)) {
                return cache.get(uuid);
            }

            URL url = new URL(
                    "https://sessionserver.mojang.com/session/minecraft/profile/" +
                            uuid.toString().replace("-", "") +
                            "?unsigned=false"
            );
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.setConnectTimeout(10 * 1000);
            connection.setReadTimeout(10 * 1000);
            JsonObject json = new JsonParser()
                    .parse(
                            new InputStreamReader(connection.getInputStream())
                    )
                    .getAsJsonObject()
                    .get("properties")
                    .getAsJsonArray()
                    .get(0)
                    .getAsJsonObject();

            return cache.put(uuid, new String[]{
                    json.get("value").getAsString(),
                    json.get("signature").getAsString()
            });
        } catch (IOException e) {
            return null;
        }
    }
}