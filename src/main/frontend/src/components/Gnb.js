import React from 'react';
import logo from '../img/logo.svg'
import './Gnb.css';

const Gnb = () => {
    return (
        <nav className="gnb">
            <div className="gnb-wrap">
                <div className="gnb-logo">
                    <img src={logo} alt="frienGym"/>
                </div>
                <ul className="gnb-menu">
                    <li><link href="#">About frienGym</link></li>
                    <li><link href="/board">게시판</link></li>
                    <li><link href="/products">쇼핑몰</link></li>
                    <li><link href="#">고객센터</link></li>
                </ul>
                <ul className="gnb-sign">
                    <li><link href="/login">회원가입</link></li>
                    <li><link href="/register">로그인</link></li>
                </ul>
            </div>
        </nav>
    );
};

export default Gnb;
