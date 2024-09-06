import axios from 'axios';
import React, { useEffect, useState } from 'react';

function ReviewPage() {
    const [reviews, setReviews] = useState([]);

    useEffect(() => {
        // 서버로부터 리뷰 목록 가져오기
        axios.get('http://localhost:8080/reviews')
            .then(response => {
                setReviews(response.data);
            })
            .catch(error => {
                console.error('Error fetching reviews:', error);
            });
    }, []);

    return (
        <div className="review-page">
            <h1>리뷰 목록</h1>
            <div className="review-list">
                {reviews.map((review, index) => (
                    <div key={index} className="review-item">
                        <img src={review.productImage} alt={`상품명: ${review.productName}`} />
                        <p><strong>상품명:</strong> {review.productName}</p>
                        <p><strong>작성자:</strong> {review.userId}</p>
                        <p><strong>별점:</strong> {review.star}/5</p>
                        <p><strong>리뷰 내용:</strong> {review.rvContents}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ReviewPage;
