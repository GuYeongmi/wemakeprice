<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>WeMakePrice</title>

  <!-- Custom fonts for this template-->
  <link rel="stylesheet" type="text/css" href="/resources/vendor/fontawesome-free/css/all.min.css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link rel="stylesheet" type="text/css" href="/resources/css/sb-admin-2.min.css">

</head>

<body id="page-top">
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800"></h1>
          </div>

          <!-- Content Row -->
          <div class="row">
          	<div class="col-lg-6">

              <!-- Default Card Example -->
              <div class="card mb-4">
                <div class="card-header">
                  WeMakePrice Interview
                </div>
                <div class="card-body">
              		URL
               	<input type="text" id="url" name="url" width="100%"/>
                </div>
                
                <div class="card-body">
                    TYPE 
                    <select id="html" name="html">
					    <option value="non">HTML 태그 제외</option>
					    <option value="all">TEXT 전체</option>
					</select>
                </div>
                
                <div class="card-body">
                                        출력묶음단위(자연수)  
                    <input type="text" id="unt" name="unt" />
                </div>
                
                <div class="card-body">
                	<button class="btn btn-success" type="button" data-dismiss="modal" aria-label="Close" id="btnView">
			            <span aria-hidden="true">출력</span>
			        </button>
                </div>
              </div>
              
              <div class="card mb-4">
                <div class="card-header">몫</div>
                <div class="card-body">
                  <p id="quot"></p>
                </div>
              </div>
              
              <div class="card mb-4">
                <div class="card-header">나머지</div>
                <div class="card-body">
                  <p id="rem"></p>
                </div>
              </div>

            
          </div>

        </div>

      </div>
      <!-- End of Main Content -->
    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>
  
  	<!-- Bootstrap core JavaScript-->

	<script type="text/javascript" src="/resources/vendor/jquery/jquery.min.js"></script>
  	<script type="text/javascript">
	  	$(document).ready(function() {
	  		
	  		$('#unt').on('focus', function() {
	  		    var x = $(this).val();
	  		    x = removeCommas(x);
	  		    $(this).val(x);
	  		}).on('focusout', function() {
	  		    var x = $(this).val();
	  		    if(x && x.length > 0) {
	  		        if(!$.isNumeric(x)) {
	  		            x = x.replace(/[^0-9]/g, '');
	  		        }
	  		        x = addCommas(x);
	  		        $(this).val(x);
	  		    }
	  		}).on('keyup', function() {
	  		    $(this).val($(this).val().replace(/[^0-9]/g, ''));
	  		});

	  		$('#btnView').on('click', function(e){
	  	    	
	  	    	var url = $('#url').val();
	  	    	var unt = $('#unt').val().replace(/[^0-9]/g,'');
	  	    	
	  	    	if(url == null || url == ''){
	  	    		alert('URL을 입력하세요.');
	  	    		e.preventDefault();
	  	    		return;
	  	    	}
	  	    	
	  	    	if(unt == null || unt == ''){
	  	    		alert('출력묶음단위를 입력하세요.');
	  	    		e.preventDefault();
	  	    		return;
	  	    	}
	  	    	
	  	    	if(unt <= 0){
	  	    		alert('0보다 큰 수를 입력하세요.')
	  	    		e.preventDefault();
	  	    		return;
	  	    	} 
	  	    	
	  	    	$.ajax({
					type: 'get',
	  	          	dataType: 'json',
	  	          	data: {url:url, html:$('#html').val(), unt:unt},
	  	          	url: '/getUrlText.do',
	  	          	success: function(data)
	  	          	{   
	  	          		if( data.result ){
	  	          			$('#quot').text(data.quot);
		  	              	$('#rem').text(data.rem);
	  	          		} else {
	  	          			if(data.check == 'url') {
	  	          				alert('URL을 입력하세요.');
	  	          			}
	  	          			
	  	          			if(data.check == 'html') {
	  	          				alert('TYPE을 선택하세요.')
	  	          			}
	  	          			
	  	          			if(data.check == 'unt') {
	  	          			alert('출력묶음단위를 바르게 입력하세요.');
	  	          			}
	  	          			
	  	          		}
	  	          	},
	  	          	error:function(data){
	  	          		console.log(data);
	  	          	}
	  	      	});
  	    	});
  	    });
	  	
	  	function addCommas(x) {
	  	    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	  	}
	  	 
	  	function removeCommas(x) {
	  	    if(!x || x.length == 0) return '';
	  	    else return x.split(',').join('');
	  	}
  	</script>
  
</body>
</html>
