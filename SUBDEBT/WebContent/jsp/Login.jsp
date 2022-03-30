<%@page import="java.util.Random"%>
<%@ page language="java"%>
<%@ page import="com.cgtsi.action.CaptchaSession" %>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>


<%
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	session.setAttribute("CurrentPage", "showLogin.do");
	Random randomNumber = new Random();
	int randomNum = randomNumber.nextInt((9999 - 100) + 1) + 10;
	session.setAttribute("randomNumber",randomNum+"");
%>
<SCRIPT language="JavaScript" type="text/JavaScript"
	src="<%=request.getContextPath()%>/js/CGTSI.js">
</SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.5.1.js"></script>

<script type=text/javascript>
	function validate() {
		var chkBox = document.getElementById("checkBoxId");
		var itext = document.getElementById("inputText").value;
		var icaptcha = document.getElementById("captcha").value;
		if (itext == "") {
			alert('Captcha cannot be blank.');
			return false;
		}
		if (itext != icaptcha) {
			alert('Captcha is not same.');
			return false;
		}
		if (chkBox.checked == false) {
			alert("Kindly accept Terms & Conditions.");
			return false;
		}
	}

	function alertMsg() {
		alert("For password reset contact CGTMSE Administrator");
	}
	
	var captcha;
	function generateCaptcha() {
		var a = Math.floor((Math.random() * 10));
		var b = Math.floor((Math.random() * 10));
		var c = Math.floor((Math.random() * 10));
		var d = Math.floor((Math.random() * 10));
		captcha = a.toString() + b.toString() + c.toString() + d.toString();
		document.getElementById("captcha").value = captcha;
	}

	/* Old Code 
	function check() {
		var input = document.getElementById("inputText").value;

		if (input == captcha) {
			alert("Captcha is Equal");
		} else {
			alert("Captcha is not Equal");
		}
	}*/

	function check() {
		var input=document.getElementById("inputText").value;
		if(input==""){
			alert("Please enter Captcha");
			return false;
		}else if(input.length()>4){
			alert("Please enter valid Captcha");
			return false;
		}
	}

	function assignPasswordInHiddenField() {
		//alert("home .jsp===");
		document.getElementById("hiddenPassword").value = document.getElementById("password").value;
	}


	//For Crome Browser 
	function changeAutoComplete(){
		if (document.getElementsByTagName) {
			var inputElements = document.getElementsByTagName("input");
			for (i=0; inputElements[i]; i++) {
			if (inputElements[i].className && (inputElements[i].className.indexOf("disableAutoComplete") != -1)) {
				inputElements[i].setAttribute("autocomplete","off");
			}
		}
	}
	//For Crome Others
	if (!document.getElementById) return false;
		var f = document.getElementById('auto_off');
		var u = f.elements[0];
		f.setAttribute("autocomplete", "off");
		u.focus();
	}

	
	function doChanges(){
		if(document.getElementById("password")!=null && document.getElementById("password").value!=""){	
			//alert(document.getElementById("hidVal").value);	
			var strPassKey=document.getElementById("password").value+document.getElementById("hidVal").value;
			strPassKey=window.btoa(strPassKey);
			strPassKey=window.btoa(strPassKey);
			document.getElementById("password").value=strPassKey;
			document.getElementById("inputText").focus();
		}
	}
	
	function doReset(){
		if(document.getElementById("password")!=null){
			document.getElementById("password").value="";
			document.getElementById("password").focus();
		}
	}
	$(document).ready(function() {
		 $.ajaxSetup({
		      cache: false
		    });
		    var timestamp = (new Date()).getTime();
		    $("#btnCaptcha").click(function() {
		        var timestamp = (new Date()).getTime();
		        var newSrc = $("#imgCaptcha").attr("src").split("?");
		        newSrc = newSrc[0] + "?" + timestamp;
		        $("#imgCaptcha").attr("src", newSrc);
		        $("#imgCaptcha").slideDown("fast");

		     });
		 });
</script>

<script language="javascript"> 
	 var message="Sorry, right-click has been disabled";
	 function clickIE4(){
	     if (event.button==2){
		     alert(message);
		     return false;
	     }
	 }

	 function clickNS4(e){
	     if (document.layers||document.getElementById&&!document.all){
	         if (e.which==2||e.which==3){
	             alert(message);
	             return false;
	         }
	     }
	 }
	
	 if (document.layers){
	     document.captureEvents(Event.MOUSEDOWN);
	     document.onmousedown=clickNS4;
	 }
	
	 else if (document.all&&!document.getElementById){
        document.onmousedown=clickIE4;
	 }
	
	 document.oncontextmenu=new Function("alert(message);return false;");
</script>

<html:html>
	<head>
	  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
 		<title>Credit Guarantee Scheme for Subordinate Debt (CGSSD)</title>
    <!-- Bootstrap CSS -->
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/login.css" rel="stylesheet">
   <link href="<%=request.getContextPath()%>/css/responsive.css" rel="stylesheet">    
     <link href="<%=request.getContextPath()%>/css/font-awesome.css" rel="stylesheet">        
    <link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet">
	 <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,400i,600,600i|Raleway:400,400i,500,500i,600|Roboto:400,400i,500,500i&display=swap" rel="stylesheet"> <link href="https://fonts.googleapis.com/css2?family=Arvo:wght@400;700&display=swap" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
   <style type="text/css">
   
   </style>
	</head>

<body onload="changeAutoComplete()">
	<html:errors />
	<form action="/login.do" method="POST" autocomplete="off">
	<input type="hidden" name="csrfCode" id="csrfCode"  value="<%=CaptchaSession.getCaptchaSession(request)%>"/>
		<div id="login">
      <div class="container-fluid">
        <div id="login-row" class="row justify-content-end">
          <div class="col-md-7 col-xs-12">
            <div class="login-detail">
                <h1>Credit Guarantee <br>Scheme for Subordinate Debt <br>(CGSSD)</h1>
                <ul>
                  <li>Launched by Government of India to support stressed MSMEs</li>
                  <li>To provide guarantee coverage to Scheduled Commercial Banks (SCBs) for providing personal loan to the promoters of stressed MSMEs for infusion as equity / quasi equity / sub-debt in the business eligible for restructuring, as per RBI guidelines.</li>

                </ul>
            </div>
          </div>

          <div id="login-column" class="col-md-5 col-xs-12">
            <div id="login-box" class="">
              <div id="login-form" class="form">
                <div class="logo">
                  <img src="<%=request.getContextPath()%>/images/udaanlogo.svg" alt="cgtmse street Vendor">
                </div>
                <!--  <h3 class="text-center text-info">Login</h3> -->
								<div class="form-group">
									<label for="username" class="text-info"><bean:message
											key="memberId" /></label><br>

									<input type="text" class="form-control" name="memberId" id="memberId"
										size="14" value="" 
										onkeypress="return numbersOnly(this, event)"
										onkeyup="isValidNumber(this)" maxlength="12" autocomplete="off" placeholder="Enter Member Id"/> 
									
								</div>
								
								<div class="form-group">
									<label for="userid" class="text-info"><bean:message
											key="userId" /></label>
									<input type="text" class="form-control" name="userId"
										id="userId" size="14" maxlength="25" value="" autocomplete="off" placeholder="Enter User Id"/>
								</div>
							
								<div class="form-group">
									<label for="password" class="text-info"><bean:message
											key="password" /></label><br>
										<input type="password" class="form-control" name="password" size="14" id="password" maxlength="16" onblur="doChanges()" onfocus="doReset()" autocomplete="off" placeholder="Enter Password"/>
										<INPUT name="method" type="hidden" value="login">
										<input type="hidden" name="hiddenPassword" id="hiddenPassword" />
										<input type="hidden" id="hidVal" name="hidVal" value="<%=randomNum%>"/>
								</div>
							
							 
							 <div class="form-group">
		                        <label  class="text-info">Enter Captcha</label><br>
		                      	 	<input type="text" class="form-control" tabindex="3"  placeholder="Captcha Code" name="inputText" id="inputText" maxlength="6" autocomplete="off" placeholder="Captcha Code">
		                        <div style="width:100%">
		                       		 <img src="<%=request.getContextPath()%>/CaptchaImg.jpg" alt="Captcha" id="imgCaptcha" style="display:inline">
	                       		 	<input type="button" value="Refresh" id="btnCaptcha" class="btn btn-md btn-save" style="display:inline">
	                       		</div>
	                        </div>	

								
								<div class="form-group mb-10">
									<!--  <input type="checkbox"  styleClass="form-control" id="checkbox" value='Y'/> -->
									<!--  <input type="checkbox"  value="Y"  Id="checkBoxId" /> -->
									<input type="checkbox" name="checkbox" id="checkBoxId"
										value="Y" onclick="assignPasswordInHiddenField();return validate();">
									
									<label class="text-info d-inline">I agree to terms and conditions mentioned in CGTMSE schemes and its circulars. </label>
									<!--  <A HREF="javascript:submitForm(applicationValidation.do?method=applicationValidation)"> -->
									
									<!--  <a href="Disclaimer1.html" target="_blank"> Click Here to see Terms and Conditions </a>-->
									<%-- <a href="<%=request.getContextPath()%>/disclaimer.html" target="_blank"> Click Here to see Terms and Conditions </a> --%>
									<a href="Disclaimer1.html" target="_blank"> Click Here to see Terms and Conditions </a>
									<!-- <input type="submit" name="submit" class="btn btn-info btn-md" value="submit"> -->
								</div>
								<%--  
                            <input type="button" name="submit" class="btn btn-info btn-md" value="submit" onClick="validate()">
                            <div class="form-group mb-10" style="text-align:center">
                            <html:submit styleClass="btn btn-md btn-save">
								<bean:message key="submit" />
							</html:submit>
                            </div> --%>

								<div class="form-group mb-10" style="text-align: center;">
									<%
										String url0 = "javascript:submitForm('" + request.getContextPath()
														+ "/login.do')";
									%>
									<html:link href="<%=url0%>" styleClass="btn btn-md btn-save">Submit</html:link>
								</div>

								 <div class="form-group line pt-10" style="text-align:center;">                                                					 
						<label class="info-btn">	
	                       <%
								String url="javascript:submitForm('"+request.getContextPath()+"/getHintQuestion.do?method=getHintQuestion')";
							%>
							<html:link href="<%=url%>"><i class="fa fa-lock" aria-hidden="true"></i> Forgot Password ? </html:link>
						 </label>
									<!-- <label class="info-btn">
							                                                                            
							 <a href="https://www.eclgs.com"><i class="fa fa-home" aria-hidden="true"></i> Home</a>
						</label>  -->
								</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		
			<!-- <div id="footer">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<p>
								Copyright © 2020 Credit Guarantee Scheme for Subordinate Debt (CGSSD) 
								<a href="Disclaimer1.html" target="_blank"> | Privacy Policy
									| Terms of Use | Terms & Condition </a>
							</p>
						</div>
					</div>
				</div>
			</div> -->
	</form>
</body>
</html:html>
