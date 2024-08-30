<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <%-- 조회되는 사용자의 ID --%>
  <title> 사용자 상세 조회</title>
</head>
<body>
  <h1> 사용자 상세 조회</h1>


  <%-- 사용자 정보 수정 --%>
  <form action="/updateUser" method="POST">
    <hr>
    <table border="1">
      <tr>
        <th>사용자 번호</th>
        <td id="userNoTd">${user.userNo}</td>
      </tr>
      <tr>
        <th>아이디</th>
        <td>${user.userId}</td>
      </tr>

      <%-- 동시에 수정할 수 있도록 input 태그 사용 --%>
      <tr>
        <th>비밀번호</th>
        <td><input type="text" name="userPw" value="${user.userPw}"></td>
      </tr>
      <tr>
        <th>이름</th>
        <td><input type="text" name="userName" value="${user.userName}"></td>
      </tr>
      <tr>
        <th>등록일</th>
        <td>${user.enrollDate}</td>
      </tr>
    </table>
  
    <br>
  
    <div>
      <button type="submit">수정</button>
      <button type="button" id="deleteBtn">삭제</button>
      <button type="button" id="goToList">목록으로 돌아가기</button>
    </div>

<%--  수정 시 누구의 비밀번호, 이름을 수정할지 지정하기위해
      userNo를 form에 숨겨놓기 --%>
    <input type="hidden" name="userNo" value="${user.userNo}">
  </form>
  
  <%-- js 에서  userNo 사용할수 있게 하는 코드 --%>
  <%-- id 로 직접 받아와서 필요 없어짐 --%>
  <%-- <script language=JavaScript>
    var userNo = "${user.userNo}";
  </script> --%>


  <%-- 세션이 삭제 안되는 오류 --%>
  <%-- c:if, c:remove 가 인식 안된 경우였음 --%>
  <%-- 위에 core 구문 썼는지 확인!! --%>
  <%-- jstl core tag library --%>
  <c:if test="${not empty sessionScope.message}" >
    <script>
      alert("${sessionScope.message}");
    </script>
    <c:remove var="message" scope="session" />
  </c:if>

  <script src="/resources/js/selectUser.js"></script>
</body>
</html>