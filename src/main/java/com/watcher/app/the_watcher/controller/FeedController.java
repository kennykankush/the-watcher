package com.watcher.app.the_watcher.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.watcher.app.the_watcher.dto.Post;
import com.watcher.app.the_watcher.repository.FeedRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class FeedController {

    @Autowired
    private FeedRepository feedRepo;

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {

        model.addAttribute("contentObject", new Post());
        
        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/";
        }

        List<Post> allUserPost = feedRepo.getAllPosts();
        model.addAttribute("allPost", allUserPost);

        return "HomePage";
    }
    
    @PostMapping("/createPost")
    public String postContent(HttpSession session, @ModelAttribute Post post){

        String currentUser = (String) session.getAttribute("loggedInUser");

        post.setAuthor(currentUser);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        post.setTimestamp(LocalDateTime.now().format(formatter).toString());

        feedRepo.savePost(currentUser, post);

        return "redirect:/home";

    }
    
}
