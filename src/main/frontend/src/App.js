import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import './App.css';

import CategoryProductsPage from './components/CategoryProductsPage';
import NewProducts from './components/NewProducts';
import ProductHome from './components/ProductHome';
import PopularProductsPage from './components/PopularProductsPage';
import { AuthProvider } from './components/AuthContext';
import Navbar from "./components/NavBar";
import CreatePost from "./components/CreatePost";
import PostsList from "./components/PostsList";
import PostDetail from "./components/PostDetail";
import EditPost from './components/EditPost';
import CommentEdit from './components/CommentEdit';
import CommentList from './components/CommentList';
import CommentCreate from './components/CommentCreate';
import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';
import HomePage from './components/HomePage';
import Mypage from './components/Mypage';
import EditProfilePage from './components/EditProfilePage';
import MastersList from './page/MastersList';
import Board from './page/Board';
import ProductDetail from './page/ProductDetail';
import ProductList from './page/ProductList';
import Cart from './page/Cart';


export default function App() {
  const [searchKeyword, setSearchKeyword] = useState('');

  const handleSearch = () => {
    window.location.href = `/productslist?keyword=${searchKeyword}`;
  };

  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <div className="App">
          <nav>


          </nav>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/posts" element={<PostsList />} />
            <Route path="/products" element={<ProductHome />} />
            <Route path="/categories/:catenum" element={<CategoryProductsPage />} />
            <Route path="/products/popular" element={<PopularProductsPage />} />
            <Route path="/products/new" element={<NewProducts />} />
            <Route path="/masters" element={<MastersList />} />
            <Route path="/cart" element={<Cart />} />
            <Route path="/board" element={<Board />} />
            <Route path="/productslist/:pNum" element={<ProductDetail />} />
            <Route path="/productslist" element={<ProductList />} />
            <Route path="/post/:poNum" element={<PostDetail />} />
            <Route path="/create-post" element={<CreatePost />} />
            <Route path="/edit/:poNum" element={<EditPost />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/mypage" element={<Mypage />} />
            <Route path="/edit-profile" element={<EditProfilePage />} />

            {/* 댓글 관련 경로 추가 */}
            <Route path="/post/:poNum/comments" element={<CommentList />} />
            <Route path="/post/:poNum/comments/create" element={<CommentCreate />} />
            <Route path="/post/:poNum/comments/:commentNo/edit" element={<CommentEdit />} />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}