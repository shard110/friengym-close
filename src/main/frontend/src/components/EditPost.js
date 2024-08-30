import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

export default function EditPost() {
  const { poNum } = useParams();
  const [post, setPost] = useState({
    poTitle: "",
    poContents: "",
    username: "",
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPost = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/post/${poNum}`);
        setPost(response.data);
      } catch (error) {
        setError("Failed to fetch post details.");
      } finally {
        setLoading(false);
      }
    };

    fetchPost();
  }, [poNum]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPost((prevPost) => ({ ...prevPost, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/post/${poNum}`, post);
      navigate(`/post/${poNum}`);
    } catch (error) {
      setError("Failed to update post.");
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="container mt-4">
      <h2>Edit Post</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="poTitle" className="form-label">Title</label>
          <input
            type="text"
            id="poTitle"
            name="poTitle"
            className="form-control"
            value={post.poTitle}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="poContents" className="form-label">Content</label>
          <textarea
            id="poContents"
            name="poContents"
            className="form-control"
            value={post.poContents}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary">Save Changes</button>
      </form>
    </div>
  );
}
