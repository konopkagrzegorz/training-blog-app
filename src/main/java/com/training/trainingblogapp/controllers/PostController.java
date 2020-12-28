package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.CommentDTO;
import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.services.CommentService;
import com.training.trainingblogapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping({"","/"})
public class PostController {

    private PostService postService;
    private CommentService commentService;

    @Autowired
    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping({"about", "about.html"})
    public String showAboutPage() {
        return "about";
    }


    @GetMapping({"/","index","index.html"})
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

//NOT WORKING - COMMENTED FOR BUG FIXING
//    @GetMapping("/search/page/{pageNo}")
//    public String showSearchPage(@PathVariable ("phase") String phase, @PathVariable ("pageNo") int pageNo, Model model) {
//        int pageSize = 5;
//        Page<PostDTO> page = postService.findAllPostContainsPhase(pageNo,pageSize,phase);
//        List<PostDTO> postsDTO = page.getContent();
//
//        model.addAttribute("firstPage", 1);
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("posts", postsDTO);
//
//        return "index";
//    }

    @GetMapping("/search")
    public String showFirstSearchPage(@RequestParam (value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("phase", keyword);
        model.addAttribute("posts", postService.findAllPostContainsPhase(keyword));

        return "index";
    }

//    @GetMapping("/search/page/{pageNo}")
//    public String showSearchPage(@PathVariable ("pageNo") int pageNo, Model model) {
//        int pageSize = 5;
//        Page<PostDTO> page = postService.findAllPostContainsPhase(pageNo, pageSize, model.getAttribute("phase").toString());
//        List<PostDTO> postsDTO = page.getContent();
//
//        model.addAttribute("firstPage", 1);
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("posts", postsDTO);
//
//        return "index";
//    }

    @GetMapping("/post/{id}")
    public String showPost(@PathVariable ("id") long postId,@ModelAttribute ("commentDTO") CommentDTO commentDTO, Model model) {
        PostDTO postDTO = postService.findPostById(postId);
        List<CommentDTO> commentsDTO = commentService.findAllByPostId(postId);
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("commentsDTO", commentsDTO);

        return "showPost";
    }

    @GetMapping("/post/add-new")
    public String addPost(@ModelAttribute ("postDTO") PostDTO postDTO) {
        return "add-post";
    }

    @PostMapping("/post/add-new")
    public String addPost(@Valid @ModelAttribute ("postDTO") PostDTO postDTO, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "add-post";
        }
        postService.addPost(postDTO,principal);
        return "redirect:/";
    }
}
