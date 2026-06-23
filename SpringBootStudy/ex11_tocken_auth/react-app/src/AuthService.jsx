import { useState } from 'react';
import api, { setAccessToken } from './api';

export default function AuthService() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [userStatus, setUserStatus] = useState('비로그인 상태(JWT)');

  // 1. 로그인 요청
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post('/api/auth/login', { username, password });
      const { accessToken } = response.data;
      
      // 전달받은 무상태 Access Token을 Axios 모듈에 영구 주입
      setAccessToken(accessToken);
      
      alert("로그인 성공!");
      setUserStatus(`${username} 계정으로 로그인됨 (JWT 모드)`);
    } catch (error) {
      console.error(error);
      alert('로그인 실패: ' + (error.response?.data || error.message));
    }
  };

  // 2. 로그아웃 요청
  const handleLogout = async () => {
    try {
      const response = await api.post('/api/auth/logout');
      
      // 클라이언트 메모리 내의 Access Token 파기
      setAccessToken(null);
      
      alert(response.data.message); // "Logout Success"
      setUserStatus('비로그인 상태(JWT)');
      setUsername('');
      setPassword('');
    } catch (error) {
      console.error(error);
      alert('로그아웃 처리 중 오류 발생');
    }
  };

  // 3. 인가 테스트 (보호된 자원 요청)
  const checkDashboard = async () => {
    try {
      const response = await api.get('/api/user/dashboard');
      alert('데이터 수신 완료: ' + response.data);
    } catch (error) {
      if (error.response?.status === 403) {
        alert('접근 권한이 없습니다.');
      } else {
        alert('데이터 로드 실패');
      }
    }
  };

  return (
    <div style={{ padding: '20px', border: '1px dashed #7f8c8d' }}>
      <h3>현재 상태: {userStatus}</h3>
      
      <form onSubmit={handleLogin} style={{ marginBottom: '10px' }}>
        <input type="text" value={username} onChange={e => setUsername(e.target.value)} placeholder="ID" required />
        <input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="PW" required />
        <button type="submit">로그인</button>
      </form>

      <button onClick={handleLogout} style={{ marginRight: '10px' }}>로그아웃</button>
      <button onClick={checkDashboard}>보호된 데이터 요청 테스트</button>
    </div>
  );
}