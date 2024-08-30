import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function CreatePost() {
  const [username, setUsername] = useState("");
  const [poTitle, setPoTitle] = useState("");
  const [poContents, setPoContents] = useState("");
  const [error, setError] = useState(null); // 에러 상태 추가
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newPost = { username, poTitle, poContents };

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

      // 폼 제출 후 입력 필드 초기화
      setUsername("");
      setPoTitle("");
      setPoContents("");

      navigate("/posts"); // 게시글 생성 후 게시글 목록으로 이동
    } catch (error) {
      setError("Failed to create post. Please try again."); // 사용자에게 에러 메시지 표시
      console.error(error);
    }
  };

  return (
    <div className="container mt-4">
      <h2>Create New Post</h2>
      {error && <div className="alert alert-danger">{error}</div>} {/* 에러 메시지 표시 */}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="username" className="form-label">Username</label>
          <input
            type="text"
            id="username"
            className="form-control"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
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
