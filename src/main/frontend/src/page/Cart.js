import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useAuth } from '../components/AuthContext'; // useAuth 훅 추가
import './Cart.css';

const Cart = () => {
    const { user, loading } = useAuth(); // useAuth 훅 사용
    const [cartItems, setCartItems] = useState([]);

    useEffect(() => {
        const fetchCartItems = async () => {
            const token = user?.token || localStorage.getItem('authToken');
            if (!token) {
                console.error('토큰을 찾을 수 없습니다.');
                return;
            }

            try {
                const response = await axios.get('/api/cart/' + user.id, {
                    headers: {
                        'Authorization': `${token}` // 요청 헤더에 JWT 토큰 추가
                    }
                });
                setCartItems(response.data);
            } catch (error) {
                console.error('장바구니 아이템을 불러오는 동안 오류 발생:', error);
            }
        };

        if (user) {
            fetchCartItems();
        }
    }, [user]);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="cart">
            <h2>장바구니</h2>
            {cartItems.length === 0 ? (
                <p>장바구니가 비어 있습니다.</p>
            ) : (
                <table>
                    <thead>
                        <tr>
                            <th></th>
                            <th>상품명</th>
                            <th>가격</th>
                            <th>수량</th>
                            <th>합계</th>
                        </tr>
                    </thead>
                    <tbody>
                        {cartItems.map(item => (
                            <tr key={item.cnum}>
                                <img src={item.product.pImg} alt={item.product.pName} className='CartImg'/>
                                <td>{item.product.pName}</td>
                                <td>{item.product.pPrice.toLocaleString()}원</td>
                                <td>{item.cCount}</td>
                                <td>{(item.product.pPrice * item.cCount).toLocaleString()}원</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default Cart;
