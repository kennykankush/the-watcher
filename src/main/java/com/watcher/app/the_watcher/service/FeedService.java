package com.watcher.app.the_watcher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watcher.app.the_watcher.dto.Post;
import com.watcher.app.the_watcher.repository.FeedRepository;

@Service
public class FeedService {

    @Autowired
    private FeedRepository feedRepo;

    public void savePost(String username, Post post){
        feedRepo.savePost(username, post);
    }

    public List<Post> getAllPosts(){
        return feedRepo.getAllPosts();
    }

}
