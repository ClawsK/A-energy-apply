import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
});

// 请求拦截器 - 添加token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// 响应拦截器 - 处理错误
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// 认证API
export const authAPI = {
  login: (data) => api.post('/auth/login', data),
  register: (data) => api.post('/auth/register', data),
  getProfile: () => api.get('/auth/profile'),
  sendSMS: (phone) => api.post('/auth/sms', { phone }),
  resetPassword: (data) => api.post('/auth/reset-password', data),
  demoLogin: () => api.post('/auth/demo-login'),
};

// 申报API
export const submissionAPI = {
  create: (data) => api.post('/submissions', data),
  getList: () => api.get('/submissions'),
  getDetail: (id) => api.get(`/submissions/${id}`),
  update: (id, data) => api.put(`/submissions/${id}`, data),
  delete: (id) => api.delete(`/submissions/${id}`),
  submit: (id) => api.post(`/submissions/${id}/submit`),
  withdraw: (id) => api.post(`/submissions/${id}/withdraw`),
};

export default api;