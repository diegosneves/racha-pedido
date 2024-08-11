package diegosneves.github.rachapedido.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Builder
@AllArgsConstructor
public class GsonAdapter implements JsonAdapter {

    private Gson gson;

    public GsonAdapter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime());
        this.gson = gsonBuilder.setPrettyPrinting().create();
    }

    @Override
    public String toJson(Object obj) {
        return this.gson.toJson(obj);
    }

    @Override
    public <T> T fromJson(String json, Class<T> classOfT) {
        return this.gson.fromJson(json, classOfT);
    }
}
