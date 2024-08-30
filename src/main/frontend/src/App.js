<<<<<<< HEAD
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
=======
// src/App.js
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/NavBar";
import CreatePost from "./components/CreatePost";
import PostsList from "./components/PostsList";
import PostDetail from "./components/PostDetail";
import EditPost from './components/EditPost';

>>>>>>> temp-branch
import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';
import HomePage from './components/HomePage';
import Mypage from './components/Mypage';
import { AuthProvider } from './components/AuthContext';

<<<<<<< HEAD
function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/mypage" element={<Mypage />} />
        </Routes>
=======
export default function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <div className="container mt-4">
          <Routes>
            <Route path="/posts" element={<PostsList />} />
            <Route path="/post/:poNum" element={<PostDetail />} />
            <Route path="/create-post" element={<CreatePost />} />
            <Route path="/edit/:poNum" element={<EditPost />} />
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/mypage" element={<Mypage />} />
          </Routes>
        </div>
>>>>>>> temp-branch
      </Router>
    </AuthProvider>
  );
}
<<<<<<< HEAD

export default App;
=======
>>>>>>> temp-branch
