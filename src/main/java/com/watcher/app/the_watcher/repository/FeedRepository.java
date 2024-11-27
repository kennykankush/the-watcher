package com.watcher.app.the_watcher.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.watcher.app.the_watcher.dto.Post;

@Repository
public class FeedRepository {

    @Autowired
    @Qualifier("PostTemplate")
    private RedisTemplate<String, Object> template;

    String postKey = "post:";

    public void savePost(String username, Post post){
        String key = postKey + username;
        template.opsForList().rightPush(key, post);
    }

    public List<Post> getAllPosts() {
        List<String> keys = new ArrayList<>(template.keys("post:*"));
    
        return keys.stream()
                .flatMap(key -> Optional.ofNullable(template.opsForList().range(key, 0, -1))
                        .orElse(List.of()) // Default to an empty list if null
                        .stream()
                        .map(obj -> (Post) obj))
                .collect(Collectors.toList());
    }

   
}
