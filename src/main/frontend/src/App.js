import React, { useState } from 'react';
import { Link, Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';

import { AuthProvider } from './components/AuthContext';
import CategoryProductsPage from './components/CategoryProductsPage';
import CommentCreate from './components/CommentCreate';
import CommentEdit from './components/CommentEdit';
import CommentList from './components/CommentList';
import CreatePost from "./components/CreatePost";
import EditPost from './components/EditPost';
import EditProfilePage from './components/EditProfilePage';
import HomePage from './components/HomePage';
import LoginPage from './components/LoginPage';
import Mypage from './components/Mypage';
import NewProducts from './components/NewProducts';
import PopularProductsPage from './components/PopularProductsPage';
import PostDetail from "./components/PostDetail";
import PostsList from './components/PostsList';
import ProductHome from './components/ProductHome';
import RegisterPage from './components/RegisterPage';
import UpdateAsk from './components/UpdateAsk';
import ViewAsk from './components/ViewAsk';
import AskPage from './page/AskPage';
import Board from './page/Board';
import Cart from './page/Cart';
import MastersList from './page/MastersList';
import ProductDetail from './page/ProductDetail';
import ProductList from './page/ProductList';
import QnaPage from './page/QnaPage';
import ReviewPage from './page/ReviewPage'; // ReviewPage 임포트

export default function App() {
  const [searchKeyword, setSearchKeyword] = useState('');

  const handleSearch = () => {
    window.location.href = `/productslist?keyword=${searchKeyword}`;
  };

  return (
    <AuthProvider>
      <Router>
    
        <div className="App">
          <nav>
            <ul>
              <li>
                <ul>
                  <li>
                    <Link to="/">Home</Link>
                  </li>
                  <li>
                    <Link to="/posts">Posts</Link>
                  </li>
                  <li>
                    <Link to="/products">Products</Link>
                  </li>
                  <li>
                    <Link to="/masters">Master 목록</Link>
                  </li>
                  <li>
                    <Link to="/productslist">상품 목록</Link>
                  </li>
                  <li>
                    <Link to="/cart">장바구니</Link>
                  </li>
                                    <li>
                                    <Link to="/qna">고객센터</Link>
                                    </li>
                </ul>
              </li>
              <li>
                <div className="search-bar">
                  <input
                    type="text"
                    placeholder="검색어를 입력하세요..."
                    value={searchKeyword}
                    onChange={(e) => setSearchKeyword(e.target.value)}
                  />
                  <button onClick={handleSearch}>검색</button>
                </div>
              </li>
            </ul>
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
                          <Route path="/qna" element={<QnaPage />} />
                        <Route path="/asks" element={<AskPage />} />
                        <Route path="/asks/view/:anum" element={<ViewAsk />} />
                        <Route path="/asks/update/:anum" element={<UpdateAsk />} />
                        <Route path="/reviews" element={<ReviewPage />} />
                    </Routes>
                </div>
            </Router>
        </AuthProvider>
    );
}