package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({"","/"})
public class IndexController {

    private PostService postService;

    @Autowired
    public IndexController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"about", "about.html"})
    public String showAboutPage() {
        return "about";
    }

    @GetMapping("/")
    public String viewHome(Model model) {
        return showPage(1,model);
    }

    @GetMapping("/page/{pageNo}")
    public String showPage(@PathVariable ("pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<PostDTO> page = postService.listAllByPage(pageNo, pageSize);
        List<PostDTO> postsDTO = page.getContent();

        model.addAttribute("firstPage", 1);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("posts", postsDTO);

        return "index";
    }

}
