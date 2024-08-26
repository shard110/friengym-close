import React, { useEffect, useState } from 'react';

function PopularProducts() {
    const [popularProducts, setPopularProducts] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/product/popular')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();  // JSON으로 바로 받아옴
            })
            .then(data => {
                if (Array.isArray(data)) {
                    setPopularProducts(data);
                } else {
                    console.error('Expected an array but got:', data);
                    setPopularProducts([]);
                }
            })
            .catch(error => console.error('Error fetching popular products:', error));
    }, []);
    
    return (
        <div>
            <h2>Popular Products</h2>
            <div className="product-list">
                {popularProducts.map(product => (
                    <div key={product.pNum} className="product-item">
                        <img src={product.pImg} alt={`상품명: ${product.pName}`} />
                        <p>{product.pName}</p>
                        <p>{product.pPrice}원</p>
                        <p>재고 : {product.pCount}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default PopularProducts;
