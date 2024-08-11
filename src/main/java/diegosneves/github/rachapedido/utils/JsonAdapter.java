package diegosneves.github.rachapedido.utils;

public interface JsonAdapter {

    String toJson(Object obj);

    <T> T fromJson(String json, Class<T> classOfT);

}
