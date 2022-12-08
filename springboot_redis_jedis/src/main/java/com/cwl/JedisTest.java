package com.cwl;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","cwl");
        jsonObject.put("age",20);
        String s = jsonObject.toJSONString();
        Transaction multi = jedis.multi();
        try {
            multi.set("user1",s);
            multi.set("user2",s);
            multi.exec();
        } catch (Exception e) {
            multi.discard();
            e.printStackTrace();
        } finally {
            jedis.close();
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
        }
        System.out.println (jedis.exists("user3"));
        String user = jedis.get("user1");
        System.out.println(user);
    }
}
