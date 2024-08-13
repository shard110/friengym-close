package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Post;
import com.example.demo.exception.PostNotFoundException;
import com.example.demo.repository.PostRepository;

@RestController
@CrossOrigin("http://localhost:3000")

public class PostController {
	


	    @Autowired
	    private PostRepository postRepository;

		//Create
	    @PostMapping("/post")
	    Post newPost(@RequestBody Post newPost) {
	        return postRepository.save(newPost);
	    }

		//모든 게시글
	    @GetMapping("/posts")
	    List<Post> getAllPosts() {
	        return postRepository.findAll();
	    }

		//상세페이지
		@GetMapping("/post/{postId}")
		public Post getPostById(@PathVariable("postId") Long id) {
			return postRepository.findById(id)
					.orElseThrow(() -> new PostNotFoundException(id));
		}

//업데이트
	    @PutMapping("/post/{postId}")
	    Post updatePost(@RequestBody Post newPost, @PathVariable("postId") Long id) {
	        return postRepository.findById(id)
	                .map(post -> {
	                    post.setUsername(newPost.getUsername());
	                    post.setTitle(newPost.getTitle());
	                    post.setContent(newPost.getContent());
	                    return postRepository.save(post);
	                }).orElseThrow(() -> new PostNotFoundException(id));
	    }

		//Delete
	    @DeleteMapping("/post/{postId}")
	    String deletePost(@PathVariable("postId") Long id){
	        if(!postRepository.existsById(id)){
	            throw new PostNotFoundException(id);
	        }
	        postRepository.deleteById(id);
	        return  "Post with id "+id+" has been deleted success.";
	    }



	}




