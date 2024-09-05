import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../components/AuthContext"; // AuthContext import

export default function CreatePost() {
  const { user } = useAuth(); // 로그인한 사용자 정보 가져오기
  const [poTitle, setPoTitle] = useState("");
  const [poContents, setPoContents] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!user) {
      setError("You need to be logged in to create a post.");
      return;
    }

    const newPost = { 
      username: user.name, // 로그인한 사용자의 이름 사용
      poTitle, 
      poContents 
    };

    try {
      const response = await fetch("http://localhost:8080/post", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(newPost),
      });

      if (!response.ok) {
        throw new Error("Failed to create post");
      }

      setPoTitle("");
      setPoContents("");
      navigate("/posts");
    } catch (error) {
      setError("Failed to create post. Please try again.");
      console.error(error);
    }
  };

  return (
    <div className="container mt-4">
      <h2>Create New Post</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="poTitle" className="form-label">Title</label>
          <input
            type="text"
            id="poTitle"
            className="form-control"
            value={poTitle}
            onChange={(e) => setPoTitle(e.target.value)}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="poContents" className="form-label">Content</label>
          <textarea
            id="poContents"
            className="form-control"
            rows="4"
            value={poContents}
            onChange={(e) => setPoContents(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary">Create Post</button>
      </form>
    </div>
  );
}
