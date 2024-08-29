// 아이디가 userId, check인 요소를 얻어와 변수에 저장
const userId = document.querySelector("#userId");
const check  = document.querySelector("#check");

// #userId 에 입력(input)이 되었을 때 
userId.addEventListener("input", e => {
  
  // 비동기로 아이디 중복 값을 얻어오는 ajax 코드 작성 예정

  // - ajax : 서버와 비동기 통신을 하기 위한 JS 기술
  // - fetch() API : JS 에서 제공하는 ajax 를 쉽게 쓰는 코드

  fetch("/signUp/idCheck?userId=" + e.target.value) // 주소창에 표시 get 방식!
  .then(resp => resp.text())  // 응답을 text 로 변환
  .then(result => {
    // result : 첫 번째 then 에서 resp.text() 를 통해 변환된 값
    // console.log(result);

    // Cannot read properties of null (reading 'classList') 
    // const check = document.querySelector("#chesck");
    // check 인식 못함 오타!!
    if(result == 0){ // 중복 X -> 사용 가능
      check.classList.add("green");  // green 클래스 추가
      check.classList.remove("red"); // red 클래스 제거

      check.innerText = "사용 가능한 아이디 입니다";
   
    } else{
      check.classList.add("red");  // red 클래스 추가
      check.classList.remove("green"); // green 클래스 제거

      check.innerText = "이미 사용중인 아이디 입니다";
    }
  });   

})