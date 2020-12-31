package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.CommentDTO;
import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.exceptions.InvalidInputException;
import com.training.trainingblogapp.exceptions.UserNotAuthorizedException;
import com.training.trainingblogapp.services.CommentService;
import com.training.trainingblogapp.services.PostService;
import com.training.trainingblogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"","/"})
public class PostController {

    private PostService postService;
    private CommentService commentService;
    private UserService userService;

    @Autowired
    public PostController(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
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

    @GetMapping("/posts/search")
    public String showPostsByKeyword(@RequestParam (value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("posts", postService.findAllPostContainsPhase(keyword));

        return "search";
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

    @GetMapping("/post/edit/{id}")
    public String editPost(@PathVariable("id") long postId, Model model, Principal principal) {
        PostDTO postDTO = postService.findPostById(postId);
        Optional<UserDTO> user = userService.findByUsername(principal.getName());
        boolean val = user.get().getUsername().equals(postDTO.getUserDTO().getUsername());
        model.addAttribute("postDTO", postDTO);
        if (!val) {
            if (!user.get().getRole().getName().equals("ROLE_ADMIN")) {
                throw new UserNotAuthorizedException("Oops... You are not admin or owner of this post - deleting aborted.");
            }
        }
        return "editPost";
    }

    @PostMapping("/post/edit/update")
    public String editPost(@Valid @ModelAttribute ("postDTO") PostDTO postDTO, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "editPost";
        }
        postService.update(postDTO,principal);
        return "redirect:/";
    }

    @GetMapping("/admin/post/delete/{id}")
    public String deletePost(@PathVariable ("id") long id) {
        if (postService.findById(id).isPresent()) {
            postService.deleteById(id);
            return "redirect:/";
        }
        else {
            throw new InvalidInputException("Post with that id does not exist");
        }
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
