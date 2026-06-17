import { useState } from 'react';
import api from './api'; // 위에서 만든 axios 인스턴스 임포트

export default function AuthService() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [userStatus, setUserStatus] = useState('비로그인 상태');

  // 1. 로그인 요청
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post('/api/auth/login', { username, password });
      
      // 성공 시 백엔드에서 생성한 JSESSIONID 쿠키가 브라우저에 자동으로 저장됨
      alert(response.data); // "로그인 성공! 인증된 사용자: ..."
      setUserStatus(`${username} 계정으로 로그인됨`);
    } catch (error) {
      console.error(error);
      alert('로그인 실패: ' + (error.response?.data || error.message));
    }
  };

  // 2. 로그아웃 요청
  const handleLogout = async () => {
    try {
      // 로그아웃 API 호출 시 브라우저가 보관하던 JSESSIONID 쿠키가 함께 전송됨
      const response = await api.post('/api/auth/logout');
      
      // 성공 시 백엔드 지시에 의해 브라우저의 JSESSIONID 쿠키가 파기됨
      alert(response.data.message); // "Logout Success"
      setUserStatus('비로그인 상태');
      setUsername('');
      setPassword('');
    } catch (error) {
      console.error(error);
      alert('로그아웃 처리 중 오류 발생');
    }
  };

  // 3. 인가 테스트 (일반 유저 전용 대시보드 요청)
  const checkDashboard = async () => {
    try {
      const response = await api.get('/api/user/dashboard');
      alert('대시보드 데이터 수신 성공: ' + response.data);
    } catch (error) {
      // 로그아웃 이후에 누르면 401 Unauthorized 에러가 떨어짐
      if (error.response?.status === 401) {
        alert('인증 정보가 없습니다. 로그인을 먼저 해주세요.');
      } else if (error.response?.status === 403) {
        alert('접근 권한이 없습니다 (롤 불일치).');
      } else {
        alert('데이터를 가져오지 못했습니다.');
      }
    }
  };

  return (
    <div style={{ padding: '20px' }}>
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