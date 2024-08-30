const deleteBtn = document.querySelector("#deleteBtn");
const goToList  = document.querySelector("#goToList");

// deleteBtn.addEventListener("click", () => {
//   location.href = "/deleteUser?userNo=" + userNo; 
// })

goToList.addEventListener("click", () => {
  // location.href = history.back;
  location.href = "/selectAll"; // 목록 페이지 요청
})

// 삭제 버튼이 클릭되었을 때
deleteBtn.addEventListener("click", () => {
  if(!confirm("정말 삭제하시겠습니까?")) { // 취소 클릭 시
    return;
  }

  // POST 방식으로 제출하기 위해서 form 태그 생성함
  // 다른 방식은 ajax
  // form 태그, input 태그 후 생성 후 body 제일 밑에 추가해 submit 하기
  // form 요소 생성
  const deleteForm = document.createElement("form");

  // 요청 주소 설정
  deleteForm.action = "/deleteUser";

  // 데이터 전달 방식 설정
  deleteForm.method = "POST";

  // input 요소 생성
  const input = document.createElement("input");

  // input 을 form에 자식으로 추가
  // append : 마지막 자식으로 추가 메서드
  deleteForm.append(input);

  // input type, name, value 설정
  input.type = "hidden";
  input.name = "userNo";

  // value 는 input 태그 innerText 는 일반 태그 내부 내용
  const userNoTd = document.querySelector("#userNoTd");
  // jsp 의 ${user.userNo} 을 그대로 가져옴
  input.value = userNoTd.innerText;

  // body 태그 제일 마지막에 form 추가
  document.querySelector("body").append(deleteForm);

  // form 태그 제출!!!!
  deleteForm.submit();

})


