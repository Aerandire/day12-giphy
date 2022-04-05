package vttp2022.paf.day12giphy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import vttp2022.paf.day12giphy.services.GiphyServices;

@Controller
public class giphySearch {

    @Autowired
    private GiphyServices gsvc;

    @PostMapping(path="/search")
    public String getSearchResult(@RequestBody MultiValueMap<String, String> form, 
    Model model){
        String phrase = form.getFirst("phrase");
        String rating = form.getFirst("rating");
        String limit = form.getFirst("limit");
        Integer limits = Integer.parseInt(limit);

        List<String> images = gsvc.getGiphs(phrase,rating,limits);

        model.addAttribute("phrase",phrase);
        model.addAttribute("images", images);

        return "search";
    }
    
}
