import React from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import { Layout as AntLayout, Menu, Button, Avatar, Dropdown } from 'antd';
import { UserOutlined, HomeOutlined, FormOutlined, LogoutOutlined } from '@ant-design/icons';
import { useAuth } from '../hooks/useAuth';

const { Header, Content, Footer } = AntLayout;

function Layout() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const menuItems = [
    {
      key: '/',
      icon: <HomeOutlined />,
      label: '首页',
      onClick: () => navigate('/'),
    },
    {
      key: '/create',
      icon: <FormOutlined />,
      label: '创建申报',
      onClick: () => navigate('/create'),
    },
  ];

  const userMenuItems = [
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
      onClick: logout,
    },
  ];

  return (
    <AntLayout style={{ minHeight: '100vh' }}>
      <Header style={{ background: '#fff', padding: '0 24px', boxShadow: '0 2px 8px rgba(0,0,0,0.06)' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: 24 }}>
            <h2 style={{ margin: 0, color: '#1890ff' }}>申报系统</h2>
            <Menu
              mode="horizontal"
              selectedKeys={[window.location.pathname]}
              items={menuItems}
              style={{ border: 'none' }}
            />
          </div>
          <Dropdown menu={{ items: userMenuItems }} placement="bottomRight">
            <div style={{ cursor: 'pointer', display: 'flex', alignItems: 'center', gap: 8 }}>
              <Avatar icon={<UserOutlined />} />
              <span>{user?.phone || '用户'}</span>
            </div>
          </Dropdown>
        </div>
      </Header>
      <Content style={{ padding: '24px', background: '#f5f5f5' }}>
        <Outlet />
      </Content>
      <Footer style={{ textAlign: 'center', background: '#fff' }}>
        申报系统 ©{new Date().getFullYear()} 上程数据
      </Footer>
    </AntLayout>
  );
}

export default Layout;