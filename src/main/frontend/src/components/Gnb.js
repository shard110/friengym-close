import React from 'react';
import './Gnb.css';
import logo from '../img/logo.png'

const Gnb = () => {
    return (
        <nav className="gnb">
            <div className="gnb-wrap">
                <div className="gnb-logo">
                    <img src={logo} alt="frienGym"/>
                </div>
                <ul className="gnb-menu">
                    <li><a href="/">About frienGym</a></li>
                    <li><a href="/board">게시판</a></li>
                    <li><a href="/products">쇼핑몰</a></li>
                    <li><a href="/">고객센터</a></li>
                </ul>
            </div>
        </nav>
    );
};

export default Gnb;
