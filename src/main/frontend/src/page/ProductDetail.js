import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './ProductDetail.css';
import Gnb from '../components/Gnb';

const ProductDetail = () => {
    const { pNum } = useParams();
    const [product, setProduct] = useState(null);
    useEffect(() => {
        fetch(`http://localhost:8080/api/products/${pNum}`)
            .then(response => response.json())
            .then(data => {
                console.log(data); // 콘솔 로그 추가
                setProduct(data);
            })
            .catch(error => console.error('Error fetching product:', error));
    }, [pNum]);

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
                    <button type="submit" className='cartBtn'>장바구니</button>
                    <button type="submit" className='buyBtn'>구매하기</button>
                </div>
            </div>
            {product.pDetailImgUrl ? (
                <img src={product.pDetailImgUrl} alt="상세 이미지" className='prodDetail'/>
            ) : (
                <div>No detailed image available</div>
            )}
            <div className="product-detail-footer">
                footer
            </div>
        </div>
    );
};

export default ProductDetail;
