package br.com.selectgearmotors.pay.util;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

    public static String getJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
