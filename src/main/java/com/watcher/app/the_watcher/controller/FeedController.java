package com.watcher.app.the_watcher.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.jayway.jsonpath.JsonPath;
import com.watcher.app.the_watcher.dto.Post;
import com.watcher.app.the_watcher.repository.FeedRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class FeedController {

    @Autowired
    private FeedRepository feedRepo;

    private RestTemplate restTemplate;

    public FeedController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {

        model.addAttribute("contentObject", new Post());
        
        if (session.getAttribute("loggedInUser") == null){
            return "redirect:/";
        }

        List<Post> allUserPost = feedRepo.getAllPosts();
        model.addAttribute("allPost", allUserPost);

        String url = "https://api-open.data.gov.sg/v2/real-time/api/twenty-four-hr-forecast";
        String weatherData = restTemplate.getForObject(url,String.class);
        // System.out.println(weatherData);
        int temperature_high = JsonPath.parse(weatherData).read("$.data.records[0].general.temperature.high");
        int temperature_low = JsonPath.parse(weatherData).read("$.data.records[0].general.temperature.low");
        int relativeHumidity_high = JsonPath.parse(weatherData).read("$.data.records[0].general.relativeHumidity.high");
        int relativeHumidity_low = JsonPath.parse(weatherData).read("$.data.records[0].general.relativeHumidity.low");
        String forecast = JsonPath.parse(weatherData).read("$.data.records[0].general.forecast.text");


        String forecastTxt = "The forecast today is " + forecast + " with temperatures as high as " + temperature_high + "° and as low as " + temperature_low + "°.";
        String humidityTxt = "The humidity level is between " + relativeHumidity_low +"% and " + relativeHumidity_high + "%.";

        model.addAttribute("forecast", forecastTxt);
        model.addAttribute("humidity", humidityTxt);


        // model.addAttribute(url, weatherData)

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
