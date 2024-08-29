<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>사용자 목록 조회</title>
</head>
<body>

  <h1>전체 사용자 목록</h1>

  <hr>

  <form action="/selectUser" method="GET" >
    <input type="text" name="userName" placeholder="이름으로 검색" required>
    <button >검색</button> 
  </form>

  <br>
  
  <table border=1>
    <thead>
      <tr>
        <th>회원번호</th>
        <th>아이디</th>
        <th>비밀번호</th>
        <th>이름</th>
        <th>등록일</th> 
      </tr>
    </thead>
    <tbody>
      <c:if test="${not empty requestScope.message}" >
        <tr>
          <td colspan="5">${message}</td>
        </tr>
      </c:if>

      <c:forEach items="${userList}" var="user" varStatus="vs">
        <tr>
          <td>${user.userNo}</td>
          <td>${user.userId}</td>
          <td>${user.userPw}</td>
          <td>${user.userName}</td>
          <td>${user.enrollDate}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <hr>

  <a href="/"><button>돌아가기</button></a>
  
</body>
</html>