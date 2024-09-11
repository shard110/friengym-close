import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import PopularProducts from './PopularProducts';
import ShopLnb from './ShopLnb';
import './ProductHome.css';
import axios from 'axios';

function ProductHome() {
    const [currentImageIndex, setCurrentImageIndex] = useState(0);
    const [recentProducts, setRecentProducts] = useState([]);

    const images = [
        'http://localhost:8080/images/banner2.jpg',
        'http://localhost:8080/images/banner3.jpg',
        'http://localhost:8080/images/banner4.jpg'
    ];

    useEffect(() => {
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
            <ShopLnb />
            <div className="banner">
                <img
                    src={images[currentImageIndex]}
                    alt="배너 이미지"
                    className="banner-image"
                />
                <button className="prev-button" onClick={() => setCurrentImageIndex(currentImageIndex === 0 ? images.length - 1 : currentImageIndex - 1)}>
                    <p className='btn-icon-prev'></p>
                </button>
                <button className="next-button" onClick={() => setCurrentImageIndex(currentImageIndex === images.length - 1 ? 0 : currentImageIndex + 1)}>
                    <p className='btn-icon-next'></p>
                </button>
            </div>
            <section id='shop_cont'>
                <div className="section popular-products">
                    <h2>Best</h2>
                    <div className='flex_box'>
                        <p className='etc'>frengym에서 최고 인기! Best 상품들을 만나보세요.</p>
                        <Link to="/products/popular">more</Link>
                    </div>
                </div>
                <PopularProducts limit={4} />

                <div className="section new-products">
                    <h2>이 달의 신규상품</h2>
                    <div className='flex_box'>
                        <p className='etc'>트레이너들이 엄선한 신규 상품, 당신에게 꼭 맞는 상품을 찾아보세요.</p>
                        <Link to="/products/new">more</Link>
                    </div>
                    <div className="product-list">
                        {recentProducts.length > 0 ? (
                            recentProducts.map(product => (
                                <div key={product.pNum} className="product-item">
                                    <img src={product.pImg} alt={`상품명: ${product.pName}`} />
                                    <p className='prod_name'>{product.pName}</p>
                                    <p className='prod_price'> ₩ {product.pPrice.toLocaleString()}</p>
                                    <p>재고 : {product.pCount}개</p>
                                </div>
                            ))
                        ) : (
                            <p>No new products available.</p>
                        )}
                    </div>
                </div>
            </section>
        </div>
    );
}

export default ProductHome;
