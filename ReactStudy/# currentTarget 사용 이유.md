# `e.target` 대신 `e.currentTarget`을 사용하는 이유

리액트(React)나 자바스크립트(JavaScript)에서 이벤트를 처리할 때 `e.target` 대신 `e.currentTarget`을 사용하는 이유는 **이벤트를 등록한 요소를 정확하게 참조하기 위해서**입니다.

특히 `<button>` 안에 `<span>`, `<i>`, `<img>`, `<svg>` 같은 자식 요소가 있을 때 두 속성의 차이가 명확하게 드러납니다.

---

# 1. `target` vs `currentTarget`

| 속성 | 정의 | 실제 의미 |
|------|------|-----------|
| `e.target` | 이벤트를 처음 발생시킨 요소 | 사용자가 실제로 클릭한 가장 안쪽(자식) 요소 |
| `e.currentTarget` | 이벤트를 처리하는 요소 | 이벤트 핸들러가 등록된 요소 |

---

# 2. `e.target`을 사용하면 발생하는 문제

예를 들어 다음과 같은 버튼이 있다고 가정해 보겠습니다.

```jsx
<button onClick={handleClick}>
  <span>POST</span>
  <i className="icon-arrow"></i>
</button>
```

우리는 버튼 어디를 클릭하든 `handleClick`이 실행되길 원합니다.

하지만 `e.target`을 사용하면 클릭 위치에 따라 참조하는 요소가 달라집니다.

### ① `<span>` 클릭

```jsx
e.target // <span>
```

---

### ② `<i>`(아이콘) 클릭

```jsx
e.target // <i>
```

---

### ③ 버튼의 빈 공간 클릭

```jsx
e.target // <button>
```

즉, **클릭한 위치에 따라 `e.target`이 계속 변경됩니다.**

따라서 다음과 같은 코드는 예상대로 동작하지 않을 수 있습니다.

```jsx
console.log(e.target.dataset.id);
```

왜냐하면 클릭한 대상이 `<span>`이나 `<i>`라면 `dataset`이 존재하지 않아 `undefined`가 될 수 있기 때문입니다.

---

# 3. `e.currentTarget`을 사용하는 이유

반면 `e.currentTarget`은 클릭 위치와 상관없이 **항상 이벤트가 등록된 요소를 가리킵니다.**

```tsx
const handleClick = (
  e: React.MouseEvent<HTMLButtonElement>
) => {
  console.log(e.currentTarget);

  // 항상 <button>을 반환
}
```

사용자가

- `<span>`을 클릭하든
- `<i>`를 클릭하든
- 버튼의 빈 공간을 클릭하든

항상 결과는 동일합니다.

```jsx
e.currentTarget // <button>
```

즉, 버튼의 속성이나 `data-*` 값을 항상 안전하게 사용할 수 있습니다.

```tsx
const handleClick = (
  e: React.MouseEvent<HTMLButtonElement>
) => {
  const id = e.currentTarget.dataset.id;

  console.log(id);
}
```

---

# 4. 언제 `currentTarget`을 사용해야 할까?

다음과 같은 상황이라면 `e.currentTarget`을 사용하는 것이 좋습니다.

- 버튼 내부에 `<span>`, `<img>`, `<svg>`, `<i>` 등의 자식 요소가 있는 경우
- 버튼 어디를 클릭해도 동일한 동작을 해야 하는 경우
- `data-*` 속성을 읽어야 하는 경우
- 이벤트가 등록된 요소 자체의 속성이나 클래스를 사용해야 하는 경우

---

# 5. 핵심 비교

| 클릭 위치 | `e.target` | `e.currentTarget` |
|-----------|------------|-------------------|
| `<span>` | `<span>` | `<button>` |
| `<i>` | `<i>` | `<button>` |
| 버튼 여백 | `<button>` | `<button>` |

---

# 한 줄 요약

> **`e.target`은 사용자가 실제 클릭한 요소를 반환하고, `e.currentTarget`은 이벤트가 등록된 요소를 반환합니다.**
>
> 따라서 **버튼 내부에 자식 요소가 있는 컴포넌트에서는 클릭 위치와 상관없이 동일한 요소를 참조할 수 있는 `e.currentTarget`을 사용하는 것이 안전하고 권장되는 방법입니다.**