// QnaCategories.js
import React from 'react';
import { Link } from 'react-router-dom';

const QnaCategories = () => {
  const categories = [
    { cateNum: 4, cateName: "1:1 문의하기" },
    { cateNum: 5, cateName: "자주 묻는 질문" },
    { cateNum: 6, cateName: "약관 및 정책" },
  ];

  return (
    <div>
      <h2>카테고리</h2>
      <ul>
        {categories.map((category) => (
          <li key={category.cateNum}>
            <Link to={`/qna/${category.cateNum}`}>{category.cateName}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default QnaCategories;
