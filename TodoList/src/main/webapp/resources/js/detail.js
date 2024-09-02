// 쿼리스트링 값 얻어오기

// location.search : 쿼리스트링만 얻어오기
// URLSearchParams : 쿼리스트링을 객체 형태로 다룰 수 있는 객체
const params = new URLSearchParams(location.search);

// 쿼리스트링 중 key 가 "index" 인 파라미터의 값 얻어오기
// -> 할 일 완료 여부 변경, 삭제, 수정에 사용
// const index = params.get("index");

const goToList = document.querySelector("#goToList");
const completeBtn = document.querySelector("#completeBtn");
const deleteBtn = document.querySelector("#deleteBtn");
const updateBtn = document.querySelector("#updateBtn");

goToList.addEventListener("click", () => {
// "/" (메인 페이지) 요청 (GET 방식)
  location.href = "/";
});

completeBtn.addEventListener("click", () => {

  // 현재 보고 있는 Todo 의 완료 여부를 
  // (true) O <-> X(false) 변경 요청
  location.href = "/todo/complete?index=" + params.get("index");
 
});

deleteBtn.addEventListener("click", () => {
  // 1. 정말 삭제 할 것인지 confirm() 을 이용해서 확인
  if (!confirm("정말 삭제 하시겠습니까?")) return;

  // 2. confirm() 확인 클릭 시
  location.href = "/todo/delete?index=" + params.get("index");

  // "/todo/delete?index=" get 방식 요청 보내기
  
});

updateBtn.addEventListener("click", () => {
  location.href = "/todo/update?index=" + params.get("index");
});