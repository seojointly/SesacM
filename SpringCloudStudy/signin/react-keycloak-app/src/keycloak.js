import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'http://localhost:8080',
  realm: 'msa-shop',
  // keycloak 의 client를 만들어야 application 실행가능함을 확인할 수 있음.
  clientId: 'react-client', 
});

export default keycloak;