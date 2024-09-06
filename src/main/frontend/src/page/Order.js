import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import './Order.css';

const Order = () => {
    const location = useLocation();
    const [cartItems, setCartItems] = useState([]);
    const [totalAmount, setTotalAmount] = useState(0);
    const [paymentMethod, setPaymentMethod] = useState('meet');

    useEffect(() => {
        if (location.state && location.state.cartItems) {
            setCartItems(location.state.cartItems);
            calculateTotal(location.state.cartItems);
        }
    }, [location.state]);

    const calculateTotal = (items) => {
        const total = items.reduce((sum, item) => sum + item.pPrice * item.doCount, 0);
        setTotalAmount(total);
    };

    const handleOrder = async () => {
        const token = localStorage.getItem('token'); // JWT 토큰 가져오기
        const order = {
            odate: new Date(),
            paymentMethod: paymentMethod,
            totalAmount: totalAmount
        };

        const dorders = cartItems.map(item => ({
            productNum: item.pNum,
            quantity: item.quantity,
            price: item.pPrice
        }));

        try {
            await axios.post('http://localhost:8080/api/orders', { order, dorders }, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            alert('주문이 완료되었습니다.');
        } catch (error) {
            console.error('주문 중 오류가 발생했습니다.', error);
        }
    };

    return (
        <div className="order-page">
            <h2>주문서</h2>
            <div className="order-items">
                {cartItems.map(item => (
                    <div key={item.pNum} className="order-item">
                        <span>{item.pName}</span>
                        <span>{item.quantity}개</span>
                        <span>{item.pPrice.toLocaleString()}원</span>
                    </div>
                ))}
            </div>
            <div className="order-total">
                <span>총액: {totalAmount.toLocaleString()}원</span>
            </div>
            <div className="payment-method">
                <label>
                    <input
                        type="radio"
                        value="meet"
                        checked={paymentMethod === 'meet'}
                        onChange={() => setPaymentMethod('meet')}
                    />
                    만나서 결제
                </label>
                <label>
                    <input
                        type="radio"
                        value="bank"
                        checked={paymentMethod === 'bank'}
                        onChange={() => setPaymentMethod('bank')}
                    />
                    무통장입금
                </label>
            </div>
            <button onClick={handleOrder}>결제하기</button>
        </div>
    );
};

export default Order;
