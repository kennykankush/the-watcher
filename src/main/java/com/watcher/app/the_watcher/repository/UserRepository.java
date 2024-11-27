package com.watcher.app.the_watcher.repository;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.watcher.app.the_watcher.dto.User;

@Repository
public class UserRepository {

    @Autowired
    private RedisTemplate<String, Object> template;

    String hash = "UserDatabase";

    public void savePerson(User user){
        template.opsForHash().put(hash, user.getUsername(), user.getPassword());
    }

    public boolean userExist(String username){
        return template.opsForHash().hasKey(hash, username);
    }

    // public User getUserByUsername(String username){
    //     return (User) template.opsForHash().get(hash, username);
    // }

    public String getPasswordByUsername(String username){
        return (String) template.opsForHash().get(hash, username);
    }

    public void deleteUserByUsername(String username){
        template.opsForHash().get(hash, username);
    }

    public List<User> getAllUsers () {
        return template.opsForHash()
        .values(hash)
        .stream()
        .map(user -> (User) user)
        .collect(Collectors.toList());
        
    }
}

//https://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx
//https://medium.com/@patrickkoss/unveiling-the-magic-of-java-spring-repositories-writing-our-own-repository-layer-37797fa6f94d
//https://dotnettutorials.net/lesson/repository-design-pattern-in-java/