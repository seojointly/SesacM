import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // 백엔드 서버 주소
  withCredentials: true // 로그인/로그아웃/데이터 요청 시 쿠키를 항상 포함
});

export default api;