import React, { useState } from 'react';
import { Link, Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import { AuthProvider } from './components/AuthContext';
import CreatePost from "./components/CreatePost";
import Navbar from "./components/NavBar";

import './App.css';
import CategoryProductsPage from './components/CategoryProductsPage';
import EditPost from './components/EditPost';
import EditProfilePage from './components/EditProfilePage';
import HomePage from './components/HomePage';
import LoginPage from './components/LoginPage';
import Mypage from './components/Mypage';
import NewProducts from './components/NewProducts';
import PopularProductsPage from './components/PopularProductsPage';
import PostDetail from "./components/PostDetail";
import PostsList from "./components/PostsList";
import ProductHome from './components/ProductHome';
import RegisterPage from './components/RegisterPage';
import AskPage from './page/AskPage';
import Board from './page/Board';
import MastersList from './page/MastersList';
import ProductDetail from './page/ProductDetail';
import ProductList from './page/ProductList';
import QnaPage from './page/QnaPage';

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
                                        <Link to="/productslist/1">상품상세페이지</Link>
                                    </li>
                                    <li>
                                        <Link to="/productslist">상품 목록</Link>
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
                    <div className="container mt-4"></div>
                    <Routes>
                        <Route path="/" element={<HomePage />} />
                        <Route path="/posts" element={<PostsList />} />
                        <Route path="/products" element={<ProductHome />} />
                        <Route path="/categories/:catenum" element={<CategoryProductsPage />} />
                        <Route path="/products/popular" element={<PopularProductsPage />} />
                        <Route path="/products/new" element={<NewProducts />} />
                        <Route path="/masters" element={<MastersList />} />
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
                        <Route path="/qna" element={<QnaPage />} />
                        <Route path="/asks" element={<AskPage />} />
                    </Routes>
                </div>
            </Router>
        </AuthProvider>
    );
}
