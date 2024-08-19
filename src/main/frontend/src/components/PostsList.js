// src/components/PostsList.js
import React, { useEffect, useState } from "react";

export default function PostsList() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await fetch("http://localhost:8080/posts");
        if (!response.ok) {
          throw new Error("Failed to fetch posts");
        }
        const data = await response.json();
        setPosts(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchPosts();
  }, []);

  return (
    <div className="container mt-4">
      <h2>Posts List</h2>
      <ul className="list-group">
        {posts.map((post) => (
          <li key={post.id} className="list-group-item">
            <h5>{post.title}</h5>
            <p>{post.content}</p>
            <small>By {post.username}</small>
          </li>
        ))}
      </ul>
    </div>
  );
}
