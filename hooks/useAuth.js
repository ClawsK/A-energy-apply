import React, { createContext, useContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { message } from 'antd';
import { authAPI } from '../services/api';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      authAPI.getProfile()
        .then(res => {
          setUser(res.data);
        })
        .catch(() => {
          localStorage.removeItem('token');
        })
        .finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, []);

  const login = async (phone, password) => {
    try {
      const res = await authAPI.login({ phone, password });
      const token = res.data.data.token;
      const user = res.data.data;
      localStorage.setItem('token', token);
      setUser(user);
      message.success('登录成功');
      navigate('/');
    } catch (error) {
      message.error(error.response?.data?.message || '登录失败');
    }
  };

  const register = async (data) => {
    try {
      const res = await authAPI.register(data);
      const token = res.data.data.token;
      const user = res.data.data;
      localStorage.setItem('token', token);
      setUser(user);
      message.success('注册成功');
      navigate('/');
    } catch (error) {
      message.error(error.response?.data?.message || '注册失败');
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    setUser(null);
    message.success('已退出登录');
    navigate('/login');
  };

  return (
    <AuthContext.Provider value={{ user, login, register, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);