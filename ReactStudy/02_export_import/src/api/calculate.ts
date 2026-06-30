// 이름을 지정한 내보내기 수행

export const PI = 3.14 // PI 이름으로 내보내기

export const getArea = (radius: number) => PI * Math.pow(radius, 2); // 함수(getArea 이름으로) 내보내기

// 여러 개를 하나로 모아서 내보내기
// feature 객체의 인터페이스 타입 (노션페이지 확인)
interface featureType {
  add: (a: number, b: number) => number;
  sub: (a: number, b: number) => number;
}
export const feature:featureType = {
  add: (a, b) => a + b,
  sub: (a, b) => a - b,
}