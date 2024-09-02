import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

import './ProductDetail.css';
import Gnb from '../components/Gnb';

const ProductDetail = () => {
    const { pNum } = useParams();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/api/products/${pNum}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                setProduct(data);
            })
            .catch(error => console.error('Error fetching product:', error));
    }, [pNum]);

    const addToCart = () => {
        // const token = localStorage.getItem('token');
        // const decodedToken = jwtDecode(token); // jwt_decode 라이브러리 사용
        // const userId = decodedToken.userId;

        const cartItem = {
            product: product,
            cCount: 1, // 기본 수량 1
            user: { id: '이용자10' }
        };

        axios.post('/api/cart', cartItem, {
            headers: {
                'Content-Type': 'application/json',
                // 'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            console.log('Item added to cart:', response.data);
        })
        .catch(error => console.error('Error adding item to cart:', error));
    };

    if (!product) {
        return <div>Loading...</div>;
    }

    return (
        <div className="product-detail">
            <div className="product-detail-header">
                <Gnb />
            </div>
            <div className='sec'>
                <img src={product.pImgUrl} alt={product.pName} className='prodImg'/>
                <div className='detail'>
                    <h3 className="title">{product.pName}</h3>
                    <div className="price">{product.pPrice.toLocaleString()}원</div>
                    <div className="count">재고 수량: {product.pCount}개</div>
                    <div className="date">업데이트: {new Date(product.pDate).toLocaleString()}</div>
                    <button type="submit" className='cartBtn' onClick={addToCart}>장바구니</button>
                    <button type="submit" className='buyBtn'>구매하기</button>
                </div>
            </div>
            {product.pDetailImgUrl ? (
                <img src={product.pDetailImgUrl} alt="상세 이미지" className='prodDetail'/>
            ) : (
                <p>상세 이미지 파일이 없습니다.</p>
            )}
            <div className="product-detail-footer">
                footer
            </div>
        </div>
    );
};

export default ProductDetail;
