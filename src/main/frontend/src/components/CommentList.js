import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from './AuthContext'; // 인증 컨텍스트 추가

export default function CommentList({ poNum }) {
  const [comments, setComments] = useState([]);
  const [error, setError] = useState(null);
  const { user } = useAuth(); // 인증 정보 가져오기

  useEffect(() => {
    const fetchComments = async () => {
      try {
        const result = await axios.get(`http://localhost:8080/posts/${poNum}/comments`);
        setComments(result.data);
      } catch (error) {
        setError("Failed to fetch comments.");
      }
    };

    fetchComments();
  }, [poNum]);

  const deleteComment = async (commentNo) => {
    try {
      await axios.delete(`http://localhost:8080/posts/${poNum}/comments/${commentNo}`, {
        headers: {
          'Authorization': `Bearer ${user.token || localStorage.getItem('authToken')}`
        }
      });
      setComments(comments.filter(comment => comment.commentNo !== commentNo));
    } catch (error) {
      setError("Failed to delete comment.");
    }
  };

  if (error) return <div className="error">Error: {error}</div>;

  return (
    <div className="card">
      <div className="card-header bi bi-chat-dots">
        {comments.length} Comments
      </div>
      <ul className="list-group-flush">
        {comments.map(comment => (
          <li key={comment.commentNo} className="list-group-item">
            <span>
              <span style={{ fontSize: "small" }}>{comment.userId || 'Anonymous'}</span>
              <span style={{ fontSize: "xx-small" }}>{comment.createdDate}</span>
              {user && user.username === comment.author && (
                <button 
                  className="badge bi bi-trash" 
                  onClick={() => deleteComment(comment.commentNo)}
                >
                  Delete
                </button>
              )}
            </span>
            <div>{comment.comment}</div>
          </li>
        ))}
      </ul>
    </div>
  );
}
