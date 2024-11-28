// package com.watcher.app.the_watcher.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;


// @RestController
// @RequestMapping("/api")

// public class WeatherController {

//     private RestTemplate restTemplate;

//     public WeatherController(RestTemplate restTemplate){
//         this.restTemplate = restTemplate;
//     }

//     @GetMapping("/weather")
//     public String getWeather() {
//         String URL = "https://api-open.data.gov.sg/v2/real-time/api/twenty-four-hr-forecast";
//         String response = restTemplate.getForObject(URL,String.class);
//         return response;
//     }
    

    


    
// }
