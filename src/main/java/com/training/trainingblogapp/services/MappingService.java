package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.*;
import com.training.trainingblogapp.domain.model.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import java.io.IOException;
import java.util.*;

@Service
public class MappingService {

    public Message messageDtoToMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setId(messageDTO.getId());
        message.setName(messageDTO.getName());
        message.setSubject(messageDTO.getSubject());
        message.setContactEmail(messageDTO.getContactEmail());
        message.setText(messageDTO.getText());
        message.setStatus(messageDTO.isStatus());

        return message;
    }

    public MessageDTO messageToDtoMessage(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setName(message.getName());
        messageDTO.setSubject(message.getSubject());
        messageDTO.setContactEmail(message.getContactEmail());
        messageDTO.setText(message.getText());
        messageDTO.setStatus(message.getStatus());

        return messageDTO;
    }

    public User userRegistrationDtoToUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername().toLowerCase());
        user.setPassword("");
        user.setEmail(userRegistrationDTO.getEmail().toLowerCase());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());

        return user;
    }

    public UserDTO userRegistrationDtoToUserDto(UserRegistrationDTO userRegistrationDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(0);
        userDTO.setUsername(userRegistrationDTO.getUsername());
        userDTO.setFirstName(userRegistrationDTO.getFirstName());
        userDTO.setLastName(userRegistrationDTO.getLastName());
        userDTO.setEmail(userRegistrationDTO.getLastName());

        return userDTO;
    }

    public UserPasswordDTO userToUserPasswordDto(User user) {
        UserPasswordDTO userPasswordDTO = new UserPasswordDTO();
        userPasswordDTO.setId(user.getId());

        return userPasswordDTO;
    }

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public User userDtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        return user;
    }

    public PostDTO postToPostDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setDate(post.getDate());
        postDTO.setHeading(post.getHeading());
        postDTO.setText(post.getText());
        Set<TagDTO> tagsDTO = new HashSet<>();
        for (Tag tag : post.getTags()) {
            TagDTO tagDTO;
            tagDTO = tagToTagDto(tag);
            tagsDTO.add(tagDTO);
        }
        postDTO.setTags(tagsDTO);
        if (post.getImage() != null) {
            postDTO.setImageEncoded(Base64.getEncoder().encodeToString(post.getImage()));
        }
        postDTO.setUserDTO(userToUserDTO(post.getUser()));

        return postDTO;
    }

    public Post newPostDtoToPost(PostDTO postDTO) throws IOException {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setHeading(postDTO.getHeading());
        post.setText(postDTO.getText());
        post.setDate(postDTO.getDate());
        Set<Tag> tags = new HashSet<>();
        Optional<Set<TagDTO>> tagDTOOptional = Optional.ofNullable(postDTO.getTags());

        if (tagDTOOptional.isPresent()) {
            for (TagDTO tagDTO : postDTO.getTags()) {
                Tag tag;
                tag = tagDtoToTag(tagDTO);
                tags.add(tag);
            }
            post.setTags(tags);
        }
        if (postDTO.getImage().getSize() >= 1048576) {
            throw new MaxUploadSizeExceededException(1048576);
        } else {
            if(postDTO.getImage().getSize() > 0) {
                post.setImage(postDTO.getImage().getBytes());
            } else {
                post.setImage(null);
            }
        }
        return post;
    }

    public Post updatePostDtoToPost(PostDTO postDTO) throws IOException {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setHeading(postDTO.getHeading());
        post.setText(postDTO.getText());
        Set<Tag> tags = new HashSet<>();
        for (TagDTO tagDTO : postDTO.getTags()) {
            Tag tag;
            tag = tagDtoToTag(tagDTO);
            tags.add(tag);
        }
        post.setTags(tags);
        if (postDTO.getImage().getSize() >= 1048576) {
            throw new MaxUploadSizeExceededException(1048576);
        } else {
            if(postDTO.getImage().getSize() > 0) {
                post.setImage(postDTO.getImage().getBytes());
            } else {
                post.setImage(null);
            }
        }
        return post;
    }

    public CommentDTO commentToCommentDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDate(comment.getDate());
        commentDTO.setPostDTO(postToPostDto(comment.getPost()));
        commentDTO.setText(comment.getText());
        commentDTO.setUserDTO(userToUserDTO(comment.getUser()));

        return commentDTO;
    }

    public Comment commentDtoToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setText(commentDTO.getText());

        return comment;
    }

    @SneakyThrows
    public Tag tagDtoToTag(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());
//        Set<Post> posts = new HashSet<>();
//        for (PostDTO postDTO : tagDTO.getPostsDTO()) {
//            Post post;
//            post = newPostDtoToPost(postDTO);
//            posts.add(post);
//        }
//        tag.setPosts(posts);
        return tag;
    }

    public TagDTO tagToTagDto(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
//        Set<PostDTO> postsDTO = new HashSet<>();
//        for (Post post : tag.getPosts()) {
//            PostDTO postDTO;
//            postDTO = postToPostDto(post);
//            postsDTO.add(postDTO);
//        }
//        tagDTO.setPostsDTO(postsDTO);
        return tagDTO;
    }
}
