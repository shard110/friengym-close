// src/components/PostsList.js
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";  // Link 컴포넌트 import

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
          <li key={post.ponum} className="list-group-item"> {/* 'ponum'을 key로 사용 */}
            <h5>{post.title}</h5>
            <p>{post.content}</p>
            <small>By {post.username}</small>
            <Link to={`/post/${post.poNum}`} className="btn btn-outline-info mt-2">
              View Details
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}