package contorApi.domUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by C311939 on 01.08.2016.
 */
public class JsonMessage {
    String message;

    public JsonMessage(String message) {
        this.message = message;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(this.message);
    }
}
