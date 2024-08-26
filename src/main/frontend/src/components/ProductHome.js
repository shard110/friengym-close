import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import PopularProducts from './PopularProducts';
import './ProductHome.css';

function ProductHome() {
    const [currentImageIndex, setCurrentImageIndex] = useState(0);
    const [categories, setCategories] = useState([]);
    const [showCategories, setShowCategories] = useState(false);
    const [recentProducts, setRecentProducts] = useState([]);

    const images = [
        'http://localhost:8080/images/banner2.jpg',
        'http://localhost:8080/images/banner3.jpg',
        'http://localhost:8080/images/banner4.jpg'
    ];

    useEffect(() => {
        axios.get('/api/categories')
            .then(response => {
                setCategories(response.data);
            })
            .catch(error => {
                console.error('Error fetching categories:', error);
            });

        axios.get('http://localhost:8080/product')
            .then(response => {
                setRecentProducts(response.data);
            })
            .catch(error => {
                console.error('Error fetching recent products:', error);
            });

        const interval = setInterval(() => {
            setCurrentImageIndex(prevIndex =>
                prevIndex === images.length - 1 ? 0 : prevIndex + 1
            );
        }, 2000);

        return () => clearInterval(interval);
    }, [images.length]);

    return (
        <div className="product-home">
            <nav className="navbar">
                <ul>
                    <li><Link to="/">회사 정보</Link></li>
                    <li><Link to="/posts">게시판</Link></li>
                    <li><Link to="/products">쇼핑몰</Link></li>
                    <li><Link to="/support">고객센터</Link></li>
                </ul>
            </nav>

            <div className="interaction-area">
                <div className="category-menu"
                     onMouseEnter={() => setShowCategories(true)}
                     onMouseLeave={() => setShowCategories(false)}>
                    <div className="category-toggle">카테고리</div>
                    {showCategories && (
                        <ul className="category-list"
                            onMouseEnter={() => setShowCategories(true)}
                            onMouseLeave={() => setShowCategories(false)}>
                            {categories.map(category => (
                                <li key={category.catenum}>
                                    <Link to={`/categories/${category.catenum}`}
                                          onClick={() => setShowCategories(false)}>{category.catename}</Link>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
                <div className="search-bar">
                    <input type="text" placeholder="검색어를 입력하세요..." />
                    <button>검색</button>
                </div>
            </div>

            <div className="banner">
                <img
                    src={images[currentImageIndex]}
                    alt="배너 이미지"
                    className="banner-image"
                />
                <button className="prev-button" onClick={() => setCurrentImageIndex(currentImageIndex === 0 ? images.length - 1 : currentImageIndex - 1)}>
                    &#10094;
                </button>
                <button className="next-button" onClick={() => setCurrentImageIndex(currentImageIndex === images.length - 1 ? 0 : currentImageIndex + 1)}>
                    &#10095;
                </button>
            </div>

            <div className="section popular-products">
                <h3><Link to="/products/popular">인기상품 모두보기</Link></h3>
                <PopularProducts limit={4} />
            </div>

            <div className="section new-products">
                <h3><Link to="/products/new">신상품 모두보기</Link></h3>
                <div className="product-list">
                    {recentProducts.length > 0 ? (
                        recentProducts.map(product => (
                            <div key={product.pNum} className="product-item">
                                <img src={product.pImg} alt={`상품명: ${product.pName}`} />
                                <p>{product.pName}</p>
                                <p>{product.pPrice}원</p>
                            </div>
                        ))
                    ) : (
                        <p>No new products available.</p>
                    )}
                </div>
            </div>
        </div>
    );
}

export default ProductHome;
