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

  <form action="/selectName" method="GET" >      <%-- /selectName Servlet 에서 요청 위임된 searchName --%>
    <input type="text" 
           name="searchName" 
           placeholder="이름으로 검색" 
           value="${param.searchName}">
    <button >검색</button> 
  </form>

  <c:if test="${empty param.searchName}">
    <br><br><br>
  </c:if>

  <%-- empty 가 true 인 경우 : "" (빈간), 빈 배열, 빈 리스트, null --%>
  <c:if test="${not empty param.searchName}">
    <h3>"${param.searchName}" 검색 결과</h3>
  </c:if>

  <table border="1">
    <thead>
      <tr>
        <th>회원번호</th>
        <th>아이디</th>
        <%-- <th>비밀번호</th> --%>
        <th>이름</th>
        <%-- <th>등록일</th>  --%>
      </tr>
    </thead>
    <tbody>

      <%-- 간단한 문자열은 req 에 담아 보낼 필요 없음 --%>
      <%-- 이미 만들어 두었기에 그냥 두었지만 비효율적으로 보인다 --%>
<%--  <c:if test="${not empty requestScope.message}" >
        <tr>
          <th colspan="3">${message}</th>
        </tr>
      </c:if> --%>

      <c:if test="${empty userList}" >
        <tr>
          <th colspan="3">조회 결과가 없습니다</th>
        </tr>
      </c:if> <%-- servlet 안쓰고 출력     --%>   

      <c:if test="${not empty requestScope.userList}">
        <c:forEach items="${userList}" var="user">
          <tr>
            <td>${user.userNo}</td>

            <td>
              <a href="/selectUser?userNo=${user.userNo}">${user.userId}</a>
            </td>

            <%-- <td>${user.userPw}</td> --%>
            <td>${user.userName}</td>
            <%-- <td>${user.enrollDate}</td> --%>
          </tr>
        </c:forEach>
      </c:if>

    </tbody>
  </table>

  <hr>

  <a href="/"><button>돌아가기</button></a>

  <c:if test="${not empty sessionScope.message}" >
    <script>
      alert("${sessionScope.message}");
    </script>
    <c:remove var="message" scope="session" />
  </c:if>
  
</body>
</html>