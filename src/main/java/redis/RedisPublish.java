package redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Random;

public class RedisPublish {

    private static String key="channel1";

    @Test
    public void publish(){
        Jedis jedis=new Jedis();
        List<String> list = jedis.pubsubChannels("*");
        System.out.println(list);
        while (true){
            Random random=new Random();
            jedis.publish(key,key+random.nextInt()*100);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void sublish(){
        Jedis jedis=new Jedis();
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println(channel+"--------：get::"+message);
            }
        },key);
    }

}
