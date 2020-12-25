package com.daytick.springredisbasicusage.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis 配置类
 *
 * @author ly
 * @since 2020/12/23 4:29 PM
 */
@Configuration
public class RedisConfig {


    /**
     * 自定义的 RedisTemplate<String, Object>
     * - Spring 默认注入了 redisTemplate 和 stringRedisTemplate
     * - redisTemplate 默认的key，value，hashKey，hashValue 序列化方式都是 JdkSerializationRedisSerializer（二进制形式）
     * - stringRedisTemplate 所有的序列化方式都是 StringRedisSerializer
     * - 很多时候（如使用HASH类型），我们希望value的序列化方式为JSON，这就需要自定义一个 RedisTemplate
     *
     * @param lettuceConnectionFactory 使用 Spring 默认的 Redis 客户端 Lettuce（自动装配）
     * @return 将名为 customRedisTemplate 的 RedisTemplate 交给 Spring 管理
     */
    @Bean
    public RedisTemplate<String, Object> customRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        // 设置 Jackson2JsonRedisSerializer
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        // 序列化方式：String
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // 序列化方式：JSON
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        return redisTemplate;
    }

}
