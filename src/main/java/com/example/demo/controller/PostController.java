package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Post;
import com.example.demo.exception.PostNotFoundException;
import com.example.demo.repository.PostRepository;

@RestController
@CrossOrigin("http://localhost:3000")

public class PostController {
	


	    @Autowired
	    private PostRepository postRepository;

	    @PostMapping("/post")
	    Post newPost(@RequestBody Post newPost) {
	        return postRepository.save(newPost);
	    }

	    @GetMapping("/posts")
	    List<Post> getAllPosts() {
	        return postRepository.findAll();
	    }

	    @GetMapping("/post/{id}")
	    Post getPostById(@PathVariable Long id) {
	        return postRepository.findById(id)
	                .orElseThrow(() -> new PostNotFoundException(id));
	    }

	    @PutMapping("/post/{id}")
	    Post updatePost(@RequestBody Post newPost, @PathVariable Long id) {
	        return postRepository.findById(id)
	                .map(post -> {
	                    post.setUsername(newPost.getUsername()); // 중복 호출 제거
	                    post.setTitle(newPost.getTitle());
	                    post.setContent(newPost.getContent());
	                    return postRepository.save(post);
	                }).orElseThrow(() -> new PostNotFoundException(id));
	    }

	    @DeleteMapping("/post/{id}")
	    String deletePost(@PathVariable Long id){
	        if(!postRepository.existsById(id)){
	            throw new PostNotFoundException(id);
	        }
	        postRepository.deleteById(id);
	        return  "Post with id "+id+" has been deleted success.";
	    }
    }