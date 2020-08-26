<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
	<h1 align="center">Spring에서의 Ajax 사용 테스트 페이지</h1>
	
	<button onclick="location.href='testtest.do'">하이</button> <!-- 리퀘스트 맵핑 필요 -->
	
	<ol>
		<li>
			서버 쪽 컨트롤러로 값 보내기
			<button id="test1">테스트</button>
			<script>
				// test1 버튼을 눌렀을 때 test1.do로 가고 data는 name과 age를 보내기(값은 임의로 집어넣으세요)
				$("#test1").on('click', function(){
					$.ajax({
						url : 'test1.do',
						data : {
							name : "임연화",
							age : "29"
						},
						success : function(data){
							if(data == "ok"){
								alert('전송성공');
							}else{
								alert('전송 실패');
							}
						}
					});
				});
			</script>
		</li>
		<li>
			컨트롤러에서 리턴하는 json객체 받아서 출력하기
			<button id="test2">테스트</button>
			<div id="d2"></div>
			<script>
				$("#test2").on('click', function(){
					$.ajax({
						url: 'test2.do',
						dataType: 'json',
						
						success:function(data){
							$('#d2').html('번호 : '+ data.no + "<br>"
										+ '제목 : '+ data.title +"<br>"
										+ '작성자 : '+ data.writer + "<br>"
										+ '내용 : ' + data.content);
						}
					});
				});
			</script>
		</li>
		<li>
			컨트롤러에서 리턴하는 json객체 받아서 출력하기
			<button id="test3">테스트</button>
			<div id="d3"></div>
			<script>
				$('#test3').on('click', function(){
					$.ajax({
						url:'test3.do',
						//dataType: 'json',
						success:function(data){
							$('#d3').html('번호 : '+ data.no + "<br>"
										 +'제목 : '+ data.title + "<br>"
										 + '작성자 : '+ data.writer + "<br>"
										 + '내용 : ' + data.content);
						}
					});
				});
			</script>
		</li>
		<li>
			컨트롤러에서 리턴하는 json배열을 받아서 출력하기
			<button id="test4">테스트</button>
			<div id="d4"></div>
			<script>
				$('#test4').on('click',function(){
					$.ajax({
						url:'test4.do',
						dataType: 'json',//이걸로 하거나  response.setContentType("application/json; charset=utf-8"); 이거하거나
						success:function(data){
							console.log(data);
							
							var values = $('#d4').html();
							for(var i in data.list){
								values += data.list[i].userId + ", "
										+ data.list[i].userPwd + ", "
										+ data.list[i].age + ", "
										+ data.list[i].email + ", "
										+ data.list[i].phone + "<br>";
							}
							$('#d4').html(values);
						}
					});
				});
			</script>
		</li>
	</ol>
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>
