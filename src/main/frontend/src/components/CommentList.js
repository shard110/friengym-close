import axios from "axios";
import React, { useEffect, useState } from "react";
import { useAuth } from "./AuthContext"; // 인증 컨텍스트 추가

export default function CommentList({ poNum }) {
  const [comments, setComments] = useState([]);
  const [error, setError] = useState(null);
  const [editingCommentNo, setEditingCommentNo] = useState(null); // 수정할 댓글 번호
  const [editedCommentText, setEditedCommentText] = useState(""); // 수정된 댓글 내용
  const { user } = useAuth(); // 인증 정보 가져오기

  useEffect(() => {
    const fetchComments = async () => {
      try {
        const result = await axios.get(
          `http://localhost:8080/posts/${poNum}/comments`
        );
        setComments(result.data);
        console.log("Fetched comments:", result.data); // comments 로그 출력
      } catch (error) {
        setError("Failed to fetch comments.");
      }
    };

    fetchComments();
  }, [poNum]);

  const deleteComment = async (commentNo) => {
    try {
      await axios.delete(
        `http://localhost:8080/posts/${poNum}/comments/${commentNo}`,
        {
          headers: {
            Authorization: `Bearer ${
              user.token || localStorage.getItem("jwtToken")
            }`,
          },
        }
      );
      setComments((prevComments) =>
        prevComments.filter((comment) => comment.commentNo !== commentNo)
      );
    } catch (error) {
      setError("Failed to delete comment.");
      console.error("Error deleting comment:", error); // 오류를 콘솔에 출력
    }
  };

  const startEditing = (comment) => {
    setEditingCommentNo(comment.commentNo);
    setEditedCommentText(comment.comment);
  };

  const cancelEditing = () => {
    setEditingCommentNo(null);
    setEditedCommentText("");
  };

  const handleEditChange = (e) => {
    setEditedCommentText(e.target.value);
  };

  const saveEdit = async () => {
    try {
      const response = await axios.put(
        `http://localhost:8080/posts/${poNum}/comments/${editingCommentNo}`,
        { comment: editedCommentText },
        {
          headers: {
            Authorization: `Bearer ${
              user.token || localStorage.getItem("jwtToken")
            }`,
          },
        }
      );
      const updatedComment = response.data;
      setComments((prevComments) =>
        prevComments.map((comment) =>
          comment.commentNo === editingCommentNo
            ? { ...comment, comment: updatedComment.comment, modifiedDate: updatedComment.modifiedDate }
            : comment
        )
      );
      cancelEditing();
    } catch (error) {
      setError("Failed to update comment.");
      console.error("Error updating comment:", error); // 오류를 콘솔에 출력
    }
  };

  if (error) return <div className="error">Error: {error}</div>;

  return (
    <div className="card">
      <div className="card-header bi bi-chat-dots">
        {comments.length} Comments
      </div>
      <ul className="list-group-flush">
        {comments.map((comment) => (
          <li key={comment.commentNo} className="list-group-item">
            <span>
              <span style={{ fontSize: "small" }}>
                {comment.name || "Anonymous"}
              </span>
              <span style={{ fontSize: "xx-small" }}>
                {editingCommentNo === comment.commentNo ? (
                  "Editing..."
                ) : (
                  <>
                    {comment.modifiedDate || comment.createdDate}
                  </>
                )}
              </span>
              {user && user.id === comment.id && (
                <>
                  {editingCommentNo === comment.commentNo ? (
                    <>
                      <textarea
                        value={editedCommentText}
                        onChange={handleEditChange}
                      />
                      <button
                        className="badge bi bi-check"
                        onClick={saveEdit}
                      >
                        Save
                      </button>
                      <button
                        className="badge bi bi-x"
                        onClick={cancelEditing}
                      >
                        Cancel
                      </button>
                    </>
                  ) : (
                    <>
                      <button
                        className="badge bi bi-pencil"
                        onClick={() => startEditing(comment)}
                      >
                        Edit
                      </button>
                      <button
                        className="badge bi bi-trash"
                        onClick={() => deleteComment(comment.commentNo)}
                      >
                        Delete
                      </button>
                    </>
                  )}
                </>
              )}
            </span>
            <div>{comment.comment}</div>
          </li>
        ))}
      </ul>
    </div>
  );
}
