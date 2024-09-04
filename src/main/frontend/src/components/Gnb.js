import React from 'react';
import logo from '../img/logo.png';
import './Gnb.css';

const Gnb = () => {
    return (
        <nav className="gnb">
            <div className="gnb-wrap">
                <div className="gnb-logo">
                    <img src={logo} alt="frienGym"/>
                </div>
                <ul className="gnb-menu">
                    <li><a href="/">About frienGym</a></li>
                    <li><a href="/posts">게시판</a></li>
                    <li><a href="/products">쇼핑몰</a></li>
                    <li><a href="/qna">고객센터</a></li>
                </ul>
            </div>
        </nav>
    );
};

export default Gnb;
