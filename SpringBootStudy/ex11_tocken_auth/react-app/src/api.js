import axios from 'axios'; // 설치해야하는 외부 라이브러리임. 

const api = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true // HttpOnly 쿠키로 교환되는 Refresh Token을 위해 설정 필수
});

// 메모리 보안을 위해 전역 변수로 액세스 토큰 관리 (XSS 방지)
let localAccessToken = null;

export const setAccessToken = (token) => {
  localAccessToken = token;
};

// Request Interceptor: 모든 요청 직전에 헤더에 Access Token 탑재 -> 요청할때마다 토큰값 붙여서 넘겨주는 것을 자동화해주는 것 (${localAccessToken}에 들어감)
api.interceptors.request.use(
  (config) => {
    if (localAccessToken) {
      config.headers['Authorization'] = `Bearer ${localAccessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response Interceptor: 401 에러(토큰 만료 등) 감지 시 재발급 자동화
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // 에러 상태가 401(Unauthorized)이고, 이미 재시도한 요청이 아니라면 작동
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        // 백엔드에 토큰 갱신 요청 (HttpOnly 쿠키 안의 Refresh Token이 전송됨)
        const res = await axios.post('http://localhost:8080/api/auth/refresh', {}, { withCredentials: true });
        const newAccessToken = res.data.accessToken;

        // 새 토큰 저장 및 헤더 재정렬
        setAccessToken(newAccessToken);
        originalRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;

        // 갱신된 토큰을 가지고 실패했던 원래의 요청을 완벽하게 재시도(Retry)하여 결과 반환
        return api(originalRequest); // 재요청
      } catch (refreshError) {
        // 만약 리프레시 토큰마저 만료된 상태라면 강제 로그아웃 화면 전환 필요
        setAccessToken(null);
        alert("세션이 만료되었습니다. 다시 로그인해 주세요.");
        window.location.reload(); // 오류가 나면 아예 새로고침. 처음부터 다시
        return Promise.reject(refreshError);
      }
    }
    return Promise.reject(error);
  }
);

export default api;