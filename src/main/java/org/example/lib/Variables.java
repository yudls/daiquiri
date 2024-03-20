package org.example.lib;
import java.util.HashMap;
import java.util.Map;
public final class Variables {
    public static final NumberValue ZERO = new NumberValue(0);
    private static Map<String, Value> variables;
    static {
        variables = new HashMap<>();
        variables.put("PI", new NumberValue(Math.PI));
        variables.put("ПИ", new NumberValue(Math.PI));
        variables.put("E", new NumberValue(Math.E));
        variables.put("Е", new NumberValue(Math.E));
    }
    public static boolean isExists(String key) {
        return variables.containsKey(key);
    }
    public static Value get(String key) {
        if (!isExists(key)) return ZERO;
        return variables.get(key);

    }
    public static void set(String key, Value value) {
        variables.put(key, value);
    }
}
