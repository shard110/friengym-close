// src/App.js
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/NavBar";
import CreatePost from "./components/CreatePost";
import PostsList from "./components/PostsList";
import PostDetail from "./components/PostDetail";
import EditPost from './components/EditPost';

export default function App() {
  return (
    <Router>
      <Navbar />
      <div className="container mt-4">
        <Routes>
          <Route path="/" element={<PostsList />} />
          <Route path="/posts" element={<PostsList />} />
          <Route path="/post/:poNum" element={<PostDetail />} />
          <Route path="/create-post" element={<CreatePost />} />
          <Route path="/edit/:poNum" element={<EditPost />} />
        </Routes>
      </div>
    </Router>
  );
}
