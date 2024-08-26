import React from 'react';
import { Link, Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import CategoryProductsPage from './components/CategoryProductsPage';
import NewProducts from './components/NewProducts';
import PostList from './components/PostList';
import ProductHome from './components/ProductHome';
import PopularProductsPage from './PopularProductsPage';

function App() {
  return (
    <Router>
      <div className="App">
        {/* 네비게이션 바 */}
        <nav>
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
          </ul>
        </nav>
        
        {/* 라우팅 설정 */}
        <Routes>
          <Route path="/" element={<h1>Welcome to the Home Page!</h1>} />
          <Route path="/posts" element={<PostList />} />
          <Route path="/products" element={<ProductHome />} />
          <Route path="/categories/:catenum" element={<CategoryProductsPage />} />
          <Route path="/products/popular" element={<PopularProductsPage />} />
          <Route path="/products/new" element={<NewProducts />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;