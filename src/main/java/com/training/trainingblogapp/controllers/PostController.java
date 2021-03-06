package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.domain.dtos.CommentDTO;
import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.dtos.TagDTO;
import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.exceptions.InvalidInputException;
import com.training.trainingblogapp.exceptions.UserNotAuthorizedException;
import com.training.trainingblogapp.services.CommentService;
import com.training.trainingblogapp.services.PostService;
import com.training.trainingblogapp.services.TagService;
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
    private TagService tagService;

    @Autowired
    public PostController(PostService postService, CommentService commentService, UserService userService, TagService tagService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
        this.tagService = tagService;
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
        List<TagDTO> tagsDTO = tagService.findAll();

        model.addAttribute("firstPage", 1);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("posts", postsDTO);
        model.addAttribute("tags", tagsDTO);

        return "index";
    }

    @GetMapping("/posts/search")
    public String showPostsByKeyword(@RequestParam (value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("posts", postService.findAllPostContainsPhase(keyword));

        return "search";
    }

    @GetMapping("/posts/search-by-tag/{id}")
    public String showPostsByTagKeyword(@PathVariable ("id") long id, Model model) {
        model.addAttribute("posts", postService.findByTags_Id(id));
        return "search";
    }

    @GetMapping("/post/{id}")
    public String showPost(@PathVariable ("id") long postId,@ModelAttribute ("commentDTO") CommentDTO commentDTO, Model model) {
        Optional<PostDTO> postDTOs = postService.findById(postId);
        List<CommentDTO> commentsDTO = commentService.findAllByPostId(postId);
        List<TagDTO> tagsDTO = tagService.findAllByPostId(postId);
        PostDTO postDTO = postService.findPostById(postId);
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("commentsDTO", commentsDTO);
        model.addAttribute("tagsDTO", tagsDTO);

        return "showPost";
    }

    @GetMapping("/post/edit/{id}")
    public String editPost(@PathVariable("id") long postId, Model model, Principal principal) {
        Optional<PostDTO> postDTO = postService.findById(postId);
        Optional<UserDTO> user = userService.findByUsername(principal.getName());
        boolean val = user.get().getUsername().equals(postDTO.get().getUserDTO().getUsername());
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("tagsDTO", tagService.findAll());
        if (!val) {
            if (!user.get().getRole().getName().equals("ROLE_ADMIN")) {
                throw new UserNotAuthorizedException("Oops... You are not admin or owner of this post - editing not allowed.");
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
    public String deletePost(@PathVariable ("id") long id, Principal principal) {
        postService.findById(id);
        postService.deleteById(id,principal);
        return "redirect:/";
    }

    @GetMapping("/post/add-new")
    public String addPost(@ModelAttribute ("postDTO") PostDTO postDTO, Model model) {
        model.addAttribute("tagsDTO", tagService.findAll());
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
