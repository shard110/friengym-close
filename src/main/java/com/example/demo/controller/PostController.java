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
		@GetMapping("/post/{poNum}")
		public Post getPostById(@PathVariable("poNum") Long poNum) {
			return postRepository.findById(poNum)
					.orElseThrow(() -> new PostNotFoundException(poNum));
		}

//업데이트
	    @PutMapping("/post/{poNum}")
	    Post updatePost(@RequestBody Post newPost, @PathVariable("poNum") Long poNum) {
	        return postRepository.findById(poNum)
	                .map(post -> {
	                    post.setUsername(newPost.getUsername());
	                    post.setTitle(newPost.getTitle());
	                    post.setContent(newPost.getContent());
	                    return postRepository.save(post);
	                }).orElseThrow(() -> new PostNotFoundException(poNum));
	    }

		//Delete
	    @DeleteMapping("/post/{poNum}")
	    String deletePost(@PathVariable("poNum") Long poNum){
	        if(!postRepository.existsById(poNum)){
	            throw new PostNotFoundException(poNum);
	        }
	        postRepository.deleteById(poNum);
	        return  "Post with poNum " +poNum+ " has been deleted success.";
	    }



	}




