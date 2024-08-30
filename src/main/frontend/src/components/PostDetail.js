import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";

export default function PostDetail() {
  const [post, setPost] = useState({
    poTitle: "",
    poContents: "",
    username: "",
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate(); // 페이지 이동을 위한 hook
  const { poNum } = useParams(); // URL에서 게시글 번호를 가져옵니다.

  useEffect(() => {
    const loadPost = async () => {
      try {
        const result = await axios.get(`http://localhost:8080/post/${poNum}`);
        setPost(result.data);
      } catch (error) {
        setError("Failed to fetch post details.");
      } finally {
        setLoading(false);
      }
    };

    loadPost();
  }, [poNum]);

  const deletePost = async () => {
    try {
      await axios.delete(`http://localhost:8080/post/${poNum}`);
      navigate("/"); // 삭제 후 홈으로 리다이렉트
    } catch (error) {
      setError("Failed to delete post.");
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-8 offset-md-2 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Post Details</h2>

          <div className="card">
            <div className="card-header">
              Details of Post ID: {poNum}
            </div>
            <div className="card-body">
              <h3>{post.poTitle}</h3>
              <h5>By {post.username}</h5>
              <p>{post.poContents}</p>
              <div className="mt-3">
                <Link to={`/edit/${poNum}`} className="btn btn-outline-primary mx-2">
                  Edit
                </Link>
                <button
                  className="btn btn-danger mx-2"
                  onClick={deletePost}
                >
                  Delete
                </button>
              </div>
            </div>
          </div>
          <Link className="btn btn-primary my-2" to="/">
            Back to Home
          </Link>
        </div>
      </div>
    </div>
  );
}