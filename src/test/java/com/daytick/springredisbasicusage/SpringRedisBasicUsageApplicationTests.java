package com.daytick.springredisbasicusage;

import com.daytick.springredisbasicusage.pojos.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class SpringRedisBasicUsageApplicationTests {

    /**
     * 构造器注入报错：ParameterResolutionException: No ParameterResolver registered for parameter redistemplate
     * 暂时退而求其次使用@Autowired注入
     */
    @Autowired
    private RedisTemplate<String, Object> customRedisTemplate;

    @Test
    void contextLoads() {
        ValueOperations<String, Object> stringObjectValueOperations = customRedisTemplate.opsForValue();

        Person person1 = new Person("张三", 20);
        System.out.println("存入Redis：" + person1);
        stringObjectValueOperations.set("person", person1);

        Person person2 = (Person) stringObjectValueOperations.get("person");
        System.out.println("Redis取出：" + person2);

        customRedisTemplate.delete("person");
    }

}
