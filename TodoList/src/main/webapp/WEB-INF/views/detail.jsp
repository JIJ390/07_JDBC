<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${todo.title}</title>
  <link rel="stylesheet" href="/resources/css/detail.css">
</head>
<body>
  <%-- 할 일 제목 --%>
  <h1>${todo.title}</h1>

  <%-- 완료 여부  --%>
  <div class="complete">
    완료 여부 : 
    <c:if test="${todo.complete eq 'O'}">
      <span class="green">${todo.complete}</span>
    </c:if>
    <c:if test="${todo.complete eq 'X'}">
      <span class="red">${todo.complete}</span>
    </c:if>
  </div>


  <div>
    작성일 : ${todo.regDate}
  </div>

  <%-- 상세 내용 --%>
  <div class="content">${todo.detail}</div>

  <div class="btn-container">
    <div>
      <button id="goToList">목록으로</button>
    </div>
    <div>
      <button id="completeBtn">완료 여부 변경</button>
      <button id="updateBtn">수정</button>
      <button id="deleteBtn">삭제</button>
    </div>
  </div>

  <c:if test="${not empty sessionScope.message}" >
    <script>
      alert("${message}");
      // JSP 해석 우선 순위
      // 1 순위 : Java(EL/JSTL) -> 2 순위 : Front(HTML,CSS,JS) 
    </script>
    <%-- 한 번 출력 후 세션 종료 --%>
    <c:remove var="message" scope="session" />
  </c:if>

  <script src="/resources/js/detail.js"></script>
</body>
</html>