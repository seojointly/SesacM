import { useState, useEffect } from 'react';
import keycloak from './keycloak';
import api from './api';

function App() {
  const [authenticated, setAuthenticated] = useState(false);
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // PKCE 기반 표준 인증 초기화
    keycloak
      .init({
        onLoad: 'check-sso',
        pkceMethod: 'S256',
        checkLoginIframe: false,
      })
      .then((auth) => {
        setAuthenticated(auth);
        setLoading(false);
      })
      .catch((err) => {
        console.error('Keycloak 초기화 오류:', err);
        setLoading(false);
      });
  }, []);

  const handleLogin = () => {
    keycloak.login();
  };

  const handleLogout = () => {
    keycloak.logout({ redirectUri: 'http://localhost:5173' });
  };

  const fetchProfile = async () => {
    try {
      const response = await api.get('/api/users/me');
      setProfile(response.data);
    } catch (error) {
      console.error('API 호출 실패:', error);
      alert('프로필 조회 실패: ' + (error.response?.status || error.message));
    }
  };

  if (loading) {
    return <div style={{ padding: '2rem' }}>Keycloak SSO 상태 확인 중...</div>;
  }

  return (
    <div style={{ padding: '2rem', fontFamily: 'sans-serif', maxWidth: '800px', margin: '0 auto' }}>
      <h1>React + Spring Boot 4.1 Resource Server 실습</h1>
      <hr />

      {!authenticated ? (
        <div style={{ marginTop: '1.5rem' }}>
          <p>현재 인증되지 않은 상태입니다. Keycloak 로그인을 진행해 주세요.</p>
          <button 
            onClick={handleLogin}
            style={{ padding: '10px 20px', fontSize: '16px', cursor: 'pointer', backgroundColor: '#0066cc', color: '#fff', border: 'none', borderRadius: '4px' }}
          >
            Keycloak으로 로그인
          </button>
        </div>
      ) : (
        <div style={{ marginTop: '1.5rem' }}>
          <h3>인증 성공: {keycloak.tokenParsed?.preferred_username}님 환영합니다.</h3>
          
          <div style={{ display: 'flex', gap: '10px', marginTop: '10px' }}>
            <button 
              onClick={fetchProfile}
              style={{ padding: '8px 16px', cursor: 'pointer', backgroundColor: '#28a745', color: '#fff', border: 'none', borderRadius: '4px' }}
            >
              내 프로필 조회 (Spring Boot API 호출)
            </button>
            <button 
              onClick={handleLogout}
              style={{ padding: '8px 16px', cursor: 'pointer', backgroundColor: '#dc3545', color: '#fff', border: 'none', borderRadius: '4px' }}
            >
              로그아웃
            </button>
          </div>

          <div style={{ marginTop: '1.5rem' }}>
            <h4>클라이언트 보유 Access Token (JWT):</h4>
            <textarea
              readOnly
              value={keycloak.token || ''}
              style={{ width: '100%', height: '80px', fontFamily: 'monospace', fontSize: '12px' }}
            />
          </div>

          {profile && (
            <div style={{ marginTop: '1.5rem', backgroundColor: '#f8f9fa', padding: '1rem', borderRadius: '4px', border: '1px solid #ddd' }}>
              <h4>Spring Boot 4.1 Resource Server 응답 데이터:</h4>
              <pre style={{ margin: 0, fontFamily: 'monospace' }}>{JSON.stringify(profile, null, 2)}</pre>
            </div>
          )}
        </div>
      )}
    </div>
  );
}

export default App;