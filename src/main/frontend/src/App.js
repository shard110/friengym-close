// src/App.js
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/NavBar";
import CreatePost from "./components/CreatePost";
import PostsList from "./components/PostsList";
import Home from "./components/Home";

export default function App() {
  return (
    <Router>
      <Navbar />
      <div className="container mt-4">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/posts" element={<PostsList />} />
          <Route path="/create-post" element={<CreatePost />} />
        </Routes>
      </div>
    </Router>
  );
}
