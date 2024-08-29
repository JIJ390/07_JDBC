<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>사용자 등록</title>
  <%-- css 파일 연결 (webapp 폴더를 기준으로 경로 작성) --%>
  <link rel="stylesheet" href="/resources/css/signUp.css">

</head>
<body>
  <h1>사용자 등록</h1>

  <hr>

  <form action="/signUp" method="POST" id="signUpForm">

    <div>
      ID : <input type="text" name="userId" id="userId" required>

      <span id="check"></span>
    </div>
    <div>
      PW : <input type="password" name="userPw" autoComplete="off" required>
    </div>
    <div>
      NAME : <input type="text" name="userName" required>
    </div>
    <div>
      <button>등록</button>
    </div>

  </form>

  <a href="/"><button>돌아가기</button></a>


  <%-- js 파일 연결 (webapp 폴더를 기준으로 경로 작성) --%>
  <script src="/resources/js/signUp.js"></script>
</body>
</html>