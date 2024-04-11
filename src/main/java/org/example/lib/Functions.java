package org.example.lib;

import java.util.HashMap;
import java.util.Map;
public class Functions {
    private static Map<String, Function> functions;

    static {
        functions = new HashMap<>();
        functions.put("синус", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("Ожидался один аргумент функции синус");
                return new NumberValue(Math.sin(args[0].asNumber()));
            }
        });
        functions.put("косинус", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("Ожидался один аргумент функции косинус");
                return new NumberValue(Math.cos(args[0].asNumber()));
            }
        });
        functions.put("длина", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("Ожидался один аргумент функции длина");
                return new NumberValue(args[0].asString().length());
            }
        });
    }
    public static boolean isExists(String key) {
        return functions.containsKey(key);
    }
    public static Function get(String key) {
        if (!isExists(key)) throw new RuntimeException("Неизвестная функция" + key);
        return functions.get(key);

    }
    public static void set(String key, Function function) {
        functions.put(key, function);
    }
}
