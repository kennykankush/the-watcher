package com.watcher.app.the_watcher.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUser;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    //I'll need to find a way to communicate with Redis, like some sort of bridge. I could build this bridge 
    // brick by brick with my bare hands, or maybe i could just use redisConnectionFactory that basically
    // does the job for me. Now i've built a bridge, and also hired a bunch of messengers that specialise in
    // connection management. 

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setUsername(redisUser);
        config.setPassword(redisPassword);
        return new JedisConnectionFactory(config); // What the heck is JedisConnectionFactory // Hahaha... Java + Redis = Jedis....
        
    }

    //Think of Redis as some sort of planetary ancient machine ledger that takes in only Key and Value and operates in raw language.
    // and to talk to it or operate it, you need to be able to know the raw language of the universe. It only handles raw language, i could
    // travel through different planets and study the language BUT. i could just use a redisTemplate.
    //redisTemplate is the universal translator guy, literally.
    //When you hire him, he is your middleman and he'll setup your connection and then he'll ask you what planet am i from.
    //I'll say im from Earth and he takes out his EARTH module chip that has all the languages on EARTH and loads it up and it looks like this.
    //StringRedisSerializer earthLanguage = new StringRedisSerializer();
    //template.setkeySerializer(earthLanguage)
    //template.setValueSerializer(earthLanguage)
    //He'll ask ok, hit me
    // i said ok the key is GESETZ-polarité (LAW-POLARITY)
    // and the value is 주문하다. (ORDER)
    // He'll then input in that raw language.
    
    @Bean
    @Qualifier("userRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer serializer = new StringRedisSerializer();

        template.setKeySerializer(serializer);
        template.setHashKeySerializer(serializer);
        
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        return template;
        
    }

    @Bean
    @Qualifier("PostTemplate")
    public RedisTemplate<String, Object> postRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer serializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

        template.setKeySerializer(serializer);
        template.setHashKeySerializer(serializer);

        template.setValueSerializer(jsonSerializer);
        template.setHashKeySerializer(jsonSerializer);

        return template;

    }




    
}
