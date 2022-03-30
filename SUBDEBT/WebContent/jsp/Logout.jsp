<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<LINK href="css/StyleSheet.css" rel="stylesheet" type="text/css">
<script src="/csrfguard"></script>
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
    <style>
    #login .container-fluid #login-row #login-column #login-box{margin-top:120px;}
    </style>
</head>
<body>
<form name="form1">
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
                  <img src="<%=request.getContextPath()%>/images/udaanlogo.svg" alt="cgtmse Sub debt">
                </div>
          	<div class="cgt_inner">            	  
		       <h1 style="color:rgba(49, 112, 143, 1);">	
		            Thank You for visiting !
	             </h1>
	             <div>
           			<a href="showLogin.do">
           			<i class="fa fa-sign-in" aria-hidden="true"></i>
           			Log in again</a>
           			<a href="https://www.eclgs.com">
           			<i class="fa fa-home" aria-hidden="true"></i>
           			HOME</a>
           		</div>
	         </div> 		              
                                                           
                   </div>
               </div>
           </div>
        
        </div>
 </div>
 
</div>

</form>
</body>
</html:html>
