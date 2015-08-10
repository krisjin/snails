package net.snails.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p/>
 * User : krisibm@163.com
 * Date: 2015/4/2
 * Time: 20:04
 */
public class Context {

    private static final Map<String, Object> map = new HashMap();

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public static void main(String[] args) {
        Context ctx = new Context();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        ctx.put("name", runnable);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ctx.put("name",executorService);

        Runnable run = (Runnable) ctx.get("name");
    }

}
