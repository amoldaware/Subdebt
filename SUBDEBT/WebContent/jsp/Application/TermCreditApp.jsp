<%@ page language="java"%>
<%@ page import="com.cgtsi.action.CaptchaSession"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>

<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
        
 function promoterValidation()
 {
	 
	 var constitution=document.getElementById("constitution").value;
  	//alert(constitution);
  	
 if(constitution=="Proprietary/Individual")
 	{
 	 document.getElementById("p2").style.display = "none";  
 	}
	 
	 
	 if (document.getElementById("p2").style.display == 'block') { 
		 // alert('this is p2 block'); 
		
	 
	 
	 var firstName1= document.getElementById("cpFirstName1").value;
     	//alert("1"+firstName1);
     	var cpLastName1= document.getElementById("cpLastName1").value;
   	//alert("2"+cpLastName1);
     	var cpITPAN1= document.getElementById("cpITPAN1").value;
     	
     	if(firstName1.length>0 || cpLastName1.length>0|| cpITPAN1.length>0)
		{
		//alert("if loop="+firstName1);
		 document.getElementById("p2").style.display = "block";  
		}
	
	 
   	//alert("3"+cpITPAN1);
     	var socialCategory1= document.getElementById("socialCategory1").value;
   	//alert("4"+socialCategory1);
       var email1= document.getElementById("email1").value;
       //alert("8"+email1);
     	var proMobileNo1= document.getElementById("proMobileNo1").value;
   	//alert("5"+proMobileNo1);
     	var pmrEquity1= document.getElementById("pmrEquity1").value;
   	//alert("6"+pmrEquity1);
     	var pmrDebt1= document.getElementById("pmrDebt1").value;
     //	alert("7"+pmrDebt1);
     	//alert("firstName1.length=="+firstName1.length);
   	if(firstName1.length==0 )
   		{
   			alert("Promoter2 First Name is required");
   			document.getElementById("cpFirstName1").focus();
   			document.getElementById("p3").style.display = "none";  
   			document.getElementById("p2").style.display = "block";  
   		
    		}
   	
   	if(cpLastName1.length==0 )
		{
			alert("Promoter2 Last Name is required");
			document.getElementById("cpLastName1").focus();
			document.getElementById("p3").style.display = "none";  
			document.getElementById("p2").style.display = "block";  
			return false;
		}
   	
   	
   	if(cpITPAN1.length==0 )
		{
			alert("Promoter2 ITPAN is required");
			document.getElementById("cpITPAN1").focus();
			document.getElementById("p3").style.display = "none";  
			document.getElementById("p2").style.display = "block";
			return false;
		}
   	
   	
   	if(socialCategory1.length==0 )
		{
			alert("Promoter Category is required");
			document.getElementById("socialCategory1").focus();
			document.getElementById("p3").style.display = "none";  
			document.getElementById("p2").style.display = "block";
			return false;
		}
   	
   	
   	if(email1.length==0 )
		{
			alert("Email is required");
			document.getElementById("email1").focus();
			document.getElementById("p3").style.display = "none";  
			document.getElementById("p2").style.display = "block";
			return false;
		}
   	
   	if(pmrEquity1.length==0 )
		{
			alert("Promoter2 Equity is required");
			document.getElementById("pmrEquity1").focus();
			document.getElementById("p3").style.display = "none";  
			document.getElementById("p2").style.display = "block";
			return false;
		}
   	
   	if(pmrDebt1.length==0 )
		{
			alert("Promoter Debt/Loan is required");
			document.getElementById("pmrDebt1").focus();
			document.getElementById("p3").style.display = "none";  
			document.getElementById("p2").style.display = "block";
			return false;
		}
     	  
	 }
	 
	 if (document.getElementById("p3").style.display == 'block') { 
		 // alert('this is p2 block'); 
		
	 //alert("Its block 3");
	// document.getElementById("p4").style.display = "block";
	 var firstName2= document.getElementById("cpFirstName2").value;
     	//alert("1"+firstName1);
     	var cpLastName2= document.getElementById("cpLastName2").value;
   	//alert("2"+cpLastName1);
     	var cpITPAN2= document.getElementById("cpITPAN2").value;
     	
     	if(firstName2.length>0 || cpLastName2.length>0|| cpITPAN2.length>0)
		{
		//alert("if loop="+firstName1);
		 document.getElementById("p3").style.display = "block";  
		}
	
	 
   	//alert("3"+cpITPAN1);
     	var socialCategory2= document.getElementById("socialCategory2").value;
   	//alert("4"+socialCategory1);
       var email2= document.getElementById("email2").value;
       //alert("8"+email1);
     	var proMobileNo2= document.getElementById("proMobileNo").value;
   	//alert("5"+proMobileNo1);
     	var pmrEquity2= document.getElementById("pmrEquity2").value;
   	//alert("6"+pmrEquity1);
     	var pmrDebt2= document.getElementById("pmrDebt2").value;
     //	alert("7"+pmrDebt1);
     	//alert("firstName1.length=="+firstName1.length);
   	if(firstName2.length==0 )
   		{
   			alert("Promoter3 First Name is required");
   			document.getElementById("cpFirstName2").focus();
   			document.getElementById("p4").style.display = "none";  
			document.getElementById("p3").style.display = "block";
   			return false;
    		}
   	
   	if(cpLastName2.length==0 )
		{
			alert("Promoter3 Last Name is required");
			document.getElementById("cpLastName2").focus();
			document.getElementById("p4").style.display = "none";  
			document.getElementById("p3").style.display = "block";
			return false;
		}
   	
   	
   	if(cpITPAN2.length==0 )
		{
			alert("Promoter3 ITPAN is required");
			document.getElementById("cpITPAN2").focus();
			document.getElementById("p4").style.display = "none";  
			document.getElementById("p3").style.display = "block";
			return false;
		}
   	
   	
   	if(socialCategory2.length==0 )
		{
			alert("Promoter3 Category is required");
			document.getElementById("socialCategory2").focus();
			document.getElementById("p4").style.display = "none";  
			document.getElementById("p3").style.display = "block";
			return false;
		}
   	
   	
   	if(email2.length==0 )
		{
			alert(" Promoter3 Email is required");
			document.getElementById("email2").focus();
			document.getElementById("p4").style.display = "none";  
			document.getElementById("p3").style.display = "block";
			return false;
		}
   	
   	if(pmrEquity2.length==0 )
		{
			alert("Promoter3 Equity is required");
			document.getElementById("pmrEquity2").focus();
			document.getElementById("p4").style.display = "none";  
			document.getElementById("p3").style.display = "block";
			return false;
		}
   	
   	if(pmrDebt2.length==0 )
		{
			alert("Promoter3 Debt/Loan is required");
			document.getElementById("pmrDebt2").focus();
			document.getElementById("p4").style.display = "none";  
			document.getElementById("p3").style.display = "block";
			return false;
		}
     	  
	 }
	 if (document.getElementById("p4").style.display == 'block')
		 {
		 
		  var firstName3= document.getElementById("cpFirstName3").value;
	      //	alert("1"+firstName3);
	      	var cpLastName3= document.getElementById("cpLastName3").value;
	    	//alert("2"+cpLastName1);
	      	var cpITPAN3= document.getElementById("cpITPAN3").value;
	      	
	      	if(firstName3.length>0 || cpLastName3.length>0|| cpITPAN3.length>0)
	 		{
	 		//alert("if loop="+firstName1);
	 		 document.getElementById("p4").style.display = "block";  
	 		}
	 	
	 	 
	    	//alert("3"+cpITPAN1);
	      	var socialCategory3= document.getElementById("socialCategory3").value;
	    	//alert("4"+socialCategory1);
	        var email3= document.getElementById("email3").value;
	        //alert("8"+email1);
	      	var proMobileNo3= document.getElementById("proMobileNo").value;
	    	//alert("5"+proMobileNo1);
	      	var pmrEquity3= document.getElementById("pmrEquity3").value;
	    	//alert("6"+pmrEquity1);
	      	var pmrDebt3= document.getElementById("pmrDebt3").value;
	      //	alert("7"+pmrDebt1);
	      	//alert("firstName1.length=="+firstName1.length);
	    	if(firstName3.length==0 )
	    		{
	    			alert("Promoter4 First Name is required");
	    			document.getElementById("cpFirstName3").focus();
	    			document.getElementById("p5").style.display = "none";  
	     			document.getElementById("p4").style.display = "block";
	    			return false;
	     		}
	    	
	    	if(cpLastName3.length==0 )
	 		{
	 			alert("Promoter4 Last Name is required");
	 			document.getElementById("cpLastName3").focus();
	 			document.getElementById("p5").style.display = "none";  
	 			document.getElementById("p4").style.display = "block";
	 			return false;
	 		}
	    	
	    	
	    	if(cpITPAN3.length==0 )
	 		{
	 			alert("Promoter4 ITPAN is required");
	 			document.getElementById("cpITPAN3").focus();
	 			document.getElementById("p5").style.display = "none";  
	 			document.getElementById("p4").style.display = "block";
	 			return false;
	 		}
	    	
	    	
	    	if(socialCategory3.length==0 )
	 		{
	 			alert("Promoter4 Category is required");
	 			document.getElementById("socialCategory3").focus();
	 			document.getElementById("p5").style.display = "none";  
	 			document.getElementById("p4").style.display = "block";
	 			return false;
	 		}
	    	
	    	
	    	if(email3.length==0 )
	 		{
	 			alert(" Promoter4 Email is required");
	 			document.getElementById("email3").focus();
	 			document.getElementById("p5").style.display = "none";  
	 			document.getElementById("p4").style.display = "block";
	 			return false;
	 		}
	    	
	    	if(pmrEquity3.length==0 )
	 		{
	 			alert("Promoter4 Equity is required");
	 			document.getElementById("pmrEquity3").focus();
	 			document.getElementById("p5").style.display = "none";  
	 			document.getElementById("p4").style.display = "block";
	 			return false;
	 		}
	    	
	    	if(pmrDebt3.length==0 )
	 		{
	 			alert("Promoter4 Debt/Loan is required");
	 			document.getElementById("pmrDebt3").focus();
	 			document.getElementById("p5").style.display = "none";  
	 			document.getElementById("p4").style.display = "block";
	 			return false;
	 		}
	    	  
		 }
	 
	 if (document.getElementById("p5").style.display == 'block')
		 {
		 
		 var firstName4= document.getElementById("cpFirstName4").value;
       	alert("1"+firstName4);
       	var cpLastName4= document.getElementById("cpLastName4").value;
     	alert("2"+cpLastName4);
       	var cpITPAN4= document.getElementById("cpITPAN4").value;
       	//alert("3");
       	if(firstName4.length>0 || cpLastName4.length>0|| cpITPAN4.length>0)
  		{
  		//alert("if loop="+firstName1);
  		 document.getElementById("p5").style.display = "block";  
  		}
  	
  	 
     	//alert("3"+cpITPAN1);
       	var socialCategory4= document.getElementById("socialCategory4").value;
     	//alert("4");
         var email4= document.getElementById("email4").value;
        // alert("8");
       	var proMobileNo4= document.getElementById("proMobileNo").value;
     	//alert("5");
       	var pmrEquity4= document.getElementById("pmrEquity4").value;
     	//alert("6");
       	var pmrDebt4= document.getElementById("pmrDebt4").value;
       	//alert("7");
       	//alert("firstName1.length=="+firstName1.length);
     	if(firstName4.length==0 )
     		{
     			alert("Promoter5 First Name is required");
     			document.getElementById("cpFirstName4").focus();
     			document.getElementById("p6").style.display = "none";  
      			document.getElementById("p5").style.display = "block";
     			return false;
      		}
     	
     	if(cpLastName4.length==0 )
  		{
  			alert("Promoter5 Last Name is required");
  			document.getElementById("cpLastName4").focus();
  			document.getElementById("p6").style.display = "none";  
  			document.getElementById("p5").style.display = "block";
  			return false;
  		}
     	
     	
     	if(cpITPAN4.length==0 )
  		{
  			alert("Promoter5 ITPAN is required");
  			document.getElementById("cpITPAN4").focus();
  			document.getElementById("p6").style.display = "none";  
  			document.getElementById("p5").style.display = "block";
  			return false;
  		}
     	
     	
     	if(socialCategory4.length==0 )
  		{
  			alert("Promoter5 Category is required");
  			document.getElementById("socialCategory4").focus();
  			document.getElementById("p6").style.display = "none";  
  			document.getElementById("p5").style.display = "block";
  			return false;
  		}
     	
     	
     	if(email4.length==0 )
  		{
  			alert(" Promoter5 Email is required");
  			document.getElementById("email4").focus();
  			document.getElementById("p6").style.display = "none";  
  			document.getElementById("p5").style.display = "block";
  			return false;
  		}
     	
     	if(pmrEquity4.length==0 )
  		{
  			alert("Promoter5 Equity is required");
  			document.getElementById("pmrEquity4").focus();
  			document.getElementById("p6").style.display = "none";  
  			document.getElementById("p5").style.display = "block";
  			return false;
  		}
     	
     	if(pmrDebt4.length==0 )
  		{
  			alert("Promoter5 Debt/Loan is required");
  			document.getElementById("pmrDebt4").focus();
  			document.getElementById("p6").style.display = "none";  
  			document.getElementById("p5").style.display = "block";
  			return false;
  		}
		 }
	 
	 if (document.getElementById("p6").style.display == 'block')
		 {
		 
		  var firstName5= document.getElementById("cpFirstName5").value;
       	//alert("1"+firstName4);
       	var cpLastName5= document.getElementById("cpLastName5").value;
     	//alert("2"+cpLastName4);
       	var cpITPAN5= document.getElementById("cpITPAN5").value;
       	//alert("3");
       	if(firstName5.length>0 || cpLastName5.length>0|| cpITPAN5.length>0)
  		{
  		//alert("if loop="+firstName1);
  		 document.getElementById("p6").style.display = "block";  
  		}
  	
  	 
     	//alert("3"+cpITPAN1);
       	var socialCategory5= document.getElementById("socialCategory5").value;
     	//alert("4");
         var email5= document.getElementById("email5").value;
        // alert("8");
       	var proMobileNo5= document.getElementById("proMobileNo").value;
     	//alert("5");
       	var pmrEquity5= document.getElementById("pmrEquity5").value;
     	//alert("6");
       	var pmrDebt5= document.getElementById("pmrDebt5").value;
       	//alert("7");
       	//alert("firstName1.length=="+firstName1.length);
     	if(firstName5.length==0 )
     		{
     			alert("Promoter6 First Name is required");
     			document.getElementById("cpFirstName5").focus();
     			document.getElementById("p7").style.display = "none";  
  			document.getElementById("p6").style.display = "block";
     			return false;
      		}
     	
     	if(cpLastName5.length==0 )
  		{
  			alert("Promoter6 Last Name is required");
  			document.getElementById("cpLastName5").focus();
  			document.getElementById("p7").style.display = "none";  
  			document.getElementById("p6").style.display = "block";
  			return false;
  		}
     	
     	
     	if(cpITPAN5.length==0 )
  		{
  			alert("Promoter6 ITPAN is required");
  			document.getElementById("cpITPAN5").focus();
  			document.getElementById("p7").style.display = "none";  
  			document.getElementById("p6").style.display = "block";
  			return false;
  		}
     	
     	
     	if(socialCategory5.length==0 )
  		{
  			alert("Promoter6 Category is required");
  			document.getElementById("socialCategory5").focus();
  			document.getElementById("p7").style.display = "none";  
  			document.getElementById("p6").style.display = "block";
  			return false;
  		}
     	
     	
     	if(email5.length==0 )
  		{
  			alert(" Promoter6 Email is required");
  			document.getElementById("email5").focus();
  			document.getElementById("p7").style.display = "none";  
  			document.getElementById("p6").style.display = "block";
  			return false;
  		}
     	
     	if(pmrEquity5.length==0 )
  		{
  			alert("Promoter6 Equity is required");
  			document.getElementById("pmrEquity5").focus();
  			document.getElementById("p7").style.display = "none";  
  			document.getElementById("p6").style.display = "block";
  			return false;
  		}
     	
     	if(pmrDebt5.length==0 )
  		{
  			alert("Promoter6 Debt/Loan is required");
  			document.getElementById("pmrDebt5").focus();
  			document.getElementById("p7").style.display = "none";  
  			document.getElementById("p6").style.display = "block";
  			return false;
  		}
		 }
	 
	 if (document.getElementById("p7").style.display == 'block')
	 {
		 var firstName6= document.getElementById("cpFirstName6").value;
	       	//alert("1");
	       	var cpLastName6= document.getElementById("cpLastName6").value;
	     	//alert("2");
	       	var cpITPAN6= document.getElementById("cpITPAN6").value;
	       //	alert("3");
	       	if(firstName6.length>0 || cpLastName6.length>0|| cpITPAN6.length>0)
	  		{
	  		//alert("if loop="+firstName1);
	  		 document.getElementById("p7").style.display = "block";  
	  		}
	  	
	  	 
	     	//alert("3"+cpITPAN1);
	       	var socialCategory6= document.getElementById("socialCategory6").value;
	     	//alert("4");
	         var email6= document.getElementById("email6").value;
	         //alert("8");
	       	var proMobileNo6= document.getElementById("proMobileNo").value;
	     	//alert("5");
	       	var pmrEquity6= document.getElementById("pmrEquity6").value;
	        //alert("6");
	       	var pmrDebt6= document.getElementById("pmrDebt6").value;
	       //	alert("7");
	       	//alert("firstName1.length=="+firstName1.length);
	     	if(firstName6.length==0 )
	     		{
	     			alert("Promoter7 First Name is required");
	     			document.getElementById("cpFirstName6").focus();
	     			document.getElementById("p8").style.display = "none";  
	      			document.getElementById("p7").style.display = "block";
	     			return false;
	      		}
	     	
	     	if(cpLastName6.length==0 )
	  		{
	  			alert("Promoter7 Last Name is required");
	  			document.getElementById("cpLastName6").focus();
	  			document.getElementById("p8").style.display = "none";  
	  			document.getElementById("p7").style.display = "block";
	  			return false;
	  		}
	     	
	     	
	     	if(cpITPAN6.length==0 )
	  		{
	  			alert("Promoter7 ITPAN is required");
	  			document.getElementById("cpITPAN6").focus();
	  			document.getElementById("p8").style.display = "none";  
	  			document.getElementById("p7").style.display = "block";
	  			return false;
	  		}
	     	
	     	
	     	if(socialCategory6.length==0 )
	  		{
	  			alert("Promoter7 Category is required");
	  			document.getElementById("socialCategory6").focus();
	  			document.getElementById("p8").style.display = "none";  
	  			document.getElementById("p7").style.display = "block";
	  			return false;
	  		}
	     	
	     	
	     	if(email6.length==0 )
	  		{
	  			alert(" Promoter7 Email is required");
	  			document.getElementById("email6").focus();
	  			document.getElementById("p8").style.display = "none";  
	  			document.getElementById("p7").style.display = "block";
	  			return false;
	  		}
	     	
	     	if(pmrEquity6.length==0 )
	  		{
	  			alert("Promoter7 Equity is required");
	  			document.getElementById("pmrEquity6").focus();
	  			document.getElementById("p8").style.display = "none";  
	  			document.getElementById("p7").style.display = "block";
	  			return false;
	  		}
	     	
	     	if(pmrDebt6.length==0 )
	  		{
	  			alert("Promoter7 Debt/Loan is required");
	  			document.getElementById("pmrDebt6").focus();
	  			document.getElementById("p8").style.display = "none";  
	  			document.getElementById("p7").style.display = "block";
	  			return false;
	  		}
	 }
	 
	 if (document.getElementById("p8").style.display == 'block')
		 {
		  var firstName7= document.getElementById("cpFirstName7").value;
       	//alert("1");
       	var cpLastName7= document.getElementById("cpLastName7").value;
     	//alert("2");
       	var cpITPAN7= document.getElementById("cpITPAN7").value;
       //	alert("3");
       	if(firstName7.length>0 || cpLastName7.length>0|| cpITPAN7.length>0)
  		{
  		//alert("if loop="+firstName1);
  		 document.getElementById("p8").style.display = "block";  
  		}
  	
  	 
     	//alert("3"+cpITPAN1);
       	var socialCategory7= document.getElementById("socialCategory7").value;
     	//alert("4");
         var email7= document.getElementById("email7").value;
         //alert("8");
       	var proMobileNo7= document.getElementById("proMobileNo").value;
     	//alert("5");
       	var pmrEquity7= document.getElementById("pmrEquity7").value;
        //alert("6");
       	var pmrDebt7= document.getElementById("pmrDebt7").value;
       //	alert("7");
       	//alert("firstName1.length=="+firstName1.length);
     	if(firstName7.length==0 )
     		{
     			alert("Promoter8 First Name is required");
     			document.getElementById("cpFirstName7").focus();
     			document.getElementById("p9").style.display = "none";  
  			document.getElementById("p8").style.display = "block";
     			return false;
      		}
     	
     	if(cpLastName7.length==0 )
  		{
  			alert("Promoter8 Last Name is required");
  			document.getElementById("cpLastName7").focus();
  			document.getElementById("p9").style.display = "none";  
  			document.getElementById("p8").style.display = "block";
  			return false;
  		}
     	
     	
     	if(cpITPAN7.length==0 )
  		{
  			alert("Promoter8 ITPAN is required");
  			document.getElementById("cpITPAN7").focus();
  			document.getElementById("p9").style.display = "none";  
  			document.getElementById("p8").style.display = "block";
  			return false;
  		}
     	
     	
     	if(socialCategory7.length==0 )
  		{
  			alert("Promoter8 Category is required");
  			document.getElementById("socialCategory7").focus();
  			document.getElementById("p9").style.display = "none";  
  			document.getElementById("p8").style.display = "block";
  			return false;
  		}
     	
     	
     	if(email7.length==0 )
  		{
  			alert(" Promoter8 Email is required");
  			document.getElementById("email7").focus();
  			document.getElementById("p9").style.display = "none";  
  			document.getElementById("p8").style.display = "block";
  			return false;
  		}
     	
     	if(pmrEquity7.length==0 )
  		{
  			alert("Promoter8 Equity is required");
  			document.getElementById("pmrEquity7").focus();
  			document.getElementById("p9").style.display = "none";  
  			document.getElementById("p8").style.display = "block";
  			return false;
  		}
     	
     	if(pmrDebt7.length==0 )
  		{
  			alert("Promoter8 Debt/Loan is required");
  			document.getElementById("pmrDebt7").focus();
  			document.getElementById("p9").style.display = "none";  
  			document.getElementById("p8").style.display = "block";
  			return false;
  		}		 
		 
		 }
	 
	 if (document.getElementById("p9").style.display == 'block')
		 {
		 
		 var firstName8= document.getElementById("cpFirstName8").value;
	       	//alert("1");
	       	var cpLastName8= document.getElementById("cpLastName8").value;
	     	//alert("2");
	       	var cpITPAN8= document.getElementById("cpITPAN8").value;
	       //	alert("3");
	       	if(firstName8.length>0 || cpLastName8.length>0|| cpITPAN8.length>0)
	  		{
	  		//alert("if loop="+firstName1);
	  		 document.getElementById("p9").style.display = "block";  
	  		}
	  	
	  	 
	     	//alert("3"+cpITPAN1);
	       	var socialCategory8= document.getElementById("socialCategory8").value;
	     	//alert("4");
	         var email8= document.getElementById("email8").value;
	         //alert("8");
	       	var proMobileNo8= document.getElementById("proMobileNo").value;
	     	//alert("5");
	       	var pmrEquity8= document.getElementById("pmrEquity8").value;
	        //alert("6");
	       	var pmrDebt8= document.getElementById("pmrDebt8").value;
	       //	alert("7");
	       	//alert("firstName1.length=="+firstName1.length);
	     	if(firstName8.length==0 )
	     		{
	     			alert("Promoter9 First Name is required");
	     			document.getElementById("cpFirstName8").focus();
	     			document.getElementById("p10").style.display = "none";  
	      			document.getElementById("p9").style.display = "block";
	     			return false;
	      		}
	     	
	     	if(cpLastName8.length==0 )
	  		{
	  			alert("Promoter9 Last Name is required");
	  			document.getElementById("cpLastName8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
	     	
	     	
	     	if(cpITPAN8.length==0 )
	  		{
	  			alert("Promoter9 ITPAN is required");
	  			document.getElementById("cpITPAN8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
	     	
	     	
	     	if(socialCategory8.length==0 )
	  		{
	  			alert("Promoter9 Category is required");
	  			document.getElementById("socialCategory8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
	     	
	     	
	     	if(email8.length==0 )
	  		{
	  			alert(" Promoter9 Email is required");
	  			document.getElementById("email8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
	     	
	     	if(pmrEquity8.length==0 )
	  		{
	  			alert("Promoter9 Equity is required");
	  			document.getElementById("pmrEquity8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
	     	
	     	if(pmrDebt8.length==0 )
	  		{
	  			alert("Promoter9 Debt/Loan is required");
	  			document.getElementById("pmrDebt8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
		 }
	 if (document.getElementById("p10").style.display == 'block')
	 {
		  var firstName8= document.getElementById("cpFirstName8").value;
	       	//alert("1");
	       	var cpLastName8= document.getElementById("cpLastName8").value;
	     	//alert("2");
	       	var cpITPAN8= document.getElementById("cpITPAN8").value;
	       //	alert("3");
	       	if(firstName8.length>0 || cpLastName8.length>0|| cpITPAN8.length>0)
	  		{
	  		//alert("if loop="+firstName1);
	  		 document.getElementById("p9").style.display = "block";  
	  		}
	  	
	  	 
	     	//alert("3"+cpITPAN1);
	       	var socialCategory8= document.getElementById("socialCategory8").value;
	     	//alert("4");
	         var email8= document.getElementById("email8").value;
	         //alert("8");
	       	var proMobileNo8= document.getElementById("proMobileNo").value;
	     	//alert("5");
	       	var pmrEquity8= document.getElementById("pmrEquity8").value;
	        //alert("6");
	       	var pmrDebt8= document.getElementById("pmrDebt8").value;
	       //	alert("7");
	       	//alert("firstName1.length=="+firstName1.length);
	     	if(firstName8.length==0 )
	     		{
	     			alert("Promoter9 First Name is required");
	     			document.getElementById("cpFirstName8").focus();
	     			document.getElementById("p10").style.display = "none";  
	      			document.getElementById("p9").style.display = "block";
	     			return false;
	      		}
	     	
	     	if(cpLastName8.length==0 )
	  		{
	  			alert("Promoter9 Last Name is required");
	  			document.getElementById("cpLastName8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
	     	
	     	
	     	if(cpITPAN8.length==0 )
	  		{
	  			alert("Promoter9 ITPAN is required");
	  			document.getElementById("cpITPAN8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
	     	
	     	
	     	if(socialCategory8.length==0 )
	  		{
	  			alert("Promoter9 Category is required");
	  			document.getElementById("socialCategory8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
	     	
	     	
	     	if(email8.length==0 )
	  		{
	  			alert(" Promoter9 Email is required");
	  			document.getElementById("email8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
	     	
	     	if(pmrEquity8.length==0 )
	  		{
	  			alert("Promoter9 Equity is required");
	  			document.getElementById("pmrEquity8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
	     	
	     	if(pmrDebt8.length==0 )
	  		{
	  			alert("Promoter9 Debt/Loan is required");
	  			document.getElementById("pmrDebt8").focus();
	  			document.getElementById("p10").style.display = "none";  
	  			document.getElementById("p9").style.display = "block";
	  			return false;
	  		}
	 }
   	    
	 //return false;
	 
 }
      function btnA2function() {
      	
      	
      	var firstName= document.getElementById("cpFirstName").value;
      //	alert("1"+firstName);
      	var cpLastName= document.getElementById("cpLastName").value;
    	//alert("2"+cpLastName);
      	var cpITPAN= document.getElementById("cpITPAN").value;
    	//alert("3"+cpITPAN);
      	var socialCategory= document.getElementById("socialCategory").value;
    	//alert("4"+socialCategory);
        var email= document.getElementById("email").value;
      	var proMobileNo= document.getElementById("proMobileNo").value;
    	//alert("5"+proMobileNo);
      	var pmrEquity= document.getElementById("pmrEquity").value;
    	//alert("6"+pmrEquity);
      	var pmrDebt= document.getElementById("pmrDebt").value;
      	//alert("7"+pmrDebt);
      	//alert("firstName1.length=="+firstName1.length);
    	if(firstName.length==0 )
    		{
    			alert("Promoter First Name is required");
    			document.getElementById("cpFirstName").focus();
    			document.getElementById("p2").style.display = "none";  
    			return false;
     		}
    	
    	if(cpLastName.length==0 )
		{
			alert("Promoter Last Name is required");
			document.getElementById("cpLastName").focus();
			document.getElementById("p2").style.display = "none";  
			return false;
 		}
    	
    	
    	if(cpITPAN.length==0 )
		{
			alert("ITPAN is required");
			document.getElementById("cpITPAN").focus();
			document.getElementById("p2").style.display = "none";  
			return false;
 		}
    	
    	
    	if(socialCategory.length==0 )
		{
			alert("Promoter Category is required");
			document.getElementById("socialCategory").focus();
			document.getElementById("p2").style.display = "none";  
			return false;
 		}
    	
    	
    	if(proMobileNo.length==0 )
		{
			alert("Mobile is required");
			document.getElementById("proMobileNo").focus();
			document.getElementById("p2").style.display = "none";  
			return false;
 		}
    	
    	if(email.length==0 )
		{
			alert("Email is required");
			document.getElementById("email").focus();
			document.getElementById("p2").style.display = "none";  
			return false;
 		}
    	
    	if(pmrEquity.length==0 )
		{
			alert("Promoter Equity is required");
			document.getElementById("pmrEquity").focus();
			document.getElementById("p2").style.display = "none";  
			return false;
 		}
    	
    	if(pmrDebt.length==0 )
		{
			alert("Promoter Debt/Loan is required");
			document.getElementById("pmrDebt").focus();
			document.getElementById("p2").style.display = "none";  
			return false;
 		}
      	  
    	  document.getElementById("p2").style.display = "block";  
       }

      function btnA3function() {
      	  document.getElementById("p3").style.display = "block";
      	  
      	 var firstName1= document.getElementById("cpFirstName1").value;
      	//alert("1"+firstName1);
      	var cpLastName1= document.getElementById("cpLastName1").value;
    	//alert("2"+cpLastName1);
      	var cpITPAN1= document.getElementById("cpITPAN1").value;
      	
      	if(firstName1.length>0 || cpLastName1.length>0|| cpITPAN1.length>0)
 		{
 		//alert("if loop="+firstName1);
 		 document.getElementById("p2").style.display = "block";  
 		}
 	
 	 
    	//alert("3"+cpITPAN1);
      	var socialCategory1= document.getElementById("socialCategory1").value;
    	//alert("4"+socialCategory1);
        var email1= document.getElementById("email1").value;
        //alert("8"+email1);
      	var proMobileNo1= document.getElementById("proMobileNo1").value;
    	//alert("5"+proMobileNo1);
      	var pmrEquity1= document.getElementById("pmrEquity1").value;
    	//alert("6"+pmrEquity1);
      	var pmrDebt1= document.getElementById("pmrDebt1").value;
      //	alert("7"+pmrDebt1);
      	//alert("firstName1.length=="+firstName1.length);
    	if(firstName1.length==0 )
    		{
    			alert("Promoter2 First Name is required");
    			document.getElementById("cpFirstName1").focus();
    			document.getElementById("p3").style.display = "none";  
    			document.getElementById("p2").style.display = "block";  
    			return false;
     		}
    	
    	if(cpLastName1.length==0 )
 		{
 			alert("Promoter2 Last Name is required");
 			document.getElementById("cpLastName1").focus();
 			document.getElementById("p3").style.display = "none";  
 			document.getElementById("p2").style.display = "block";  
 			return false;
 		}
    	
    	
    	if(cpITPAN1.length==0 )
 		{
 			alert("Promoter2 ITPAN is required");
 			document.getElementById("cpITPAN1").focus();
 			document.getElementById("p3").style.display = "none";  
 			document.getElementById("p2").style.display = "block";
 			return false;
 		}
    	
    	
    	if(socialCategory1.length==0 )
 		{
 			alert("Promoter Category is required");
 			document.getElementById("socialCategory1").focus();
 			document.getElementById("p3").style.display = "none";  
 			document.getElementById("p2").style.display = "block";
 			return false;
 		}
    	
    	
    	if(email1.length==0 )
 		{
 			alert("Email is required");
 			document.getElementById("email1").focus();
 			document.getElementById("p3").style.display = "none";  
 			document.getElementById("p2").style.display = "block";
 			return false;
 		}
    	
    	if(proMobileNo1.length==0 )
 		{
 			alert("Mobile is required");
 			document.getElementById("proMobileNo1").focus();
 			document.getElementById("p3").style.display = "none";  
 			document.getElementById("p2").style.display = "block";
 			return false;
 		}
    	
    	
    	if(pmrEquity1.length==0 )
 		{
 			alert("Promoter2 Equity is required");
 			document.getElementById("pmrEquity1").focus();
 			document.getElementById("p3").style.display = "none";  
 			document.getElementById("p2").style.display = "block";
 			return false;
 		}
    	
    	if(pmrDebt1.length==0 )
 		{
 			alert("Promoter Debt/Loan is required");
 			document.getElementById("pmrDebt1").focus();
 			document.getElementById("p3").style.display = "none";  
 			document.getElementById("p2").style.display = "block";
 			return false;
 		}
      	  document.getElementById("btn2").style.display = "none";
      }
      function btnR2function() {
    	  //alert("I m here");
      	  document.getElementById("p2").style.display = "none";
      	var strname = document.getElementById("cpFirstName1").value;
      	//alert(strname);
      	 document.getElementById("cpFirstName1").value="";
      	document.getElementById("pmrDebt1").value="";
      	document.getElementById("pmrEquity1").value="";
      	document.getElementById("email1").value="";
    	document.getElementById("proMobileNo1").value=""; 
    	document.getElementById("cpLastName1").value="";
      	document.getElementById("socialCategory1").value="";
      	document.getElementById("cpITPAN1").value=""; 
      	
      	  
      	 
      }
      function btnA4function() {
      	 
      	  
     	 var firstName2= document.getElementById("cpFirstName2").value;
      	//alert("1"+firstName1);
      	var cpLastName2= document.getElementById("cpLastName2").value;
    	//alert("2"+cpLastName1);
      	var cpITPAN2= document.getElementById("cpITPAN2").value;
      	
      	if(firstName2.length>0 || cpLastName2.length>0|| cpITPAN2.length>0)
 		{
 		//alert("if loop="+firstName1);
 		 document.getElementById("p3").style.display = "block";  
 		}
 	
 	 
    	//alert("3"+cpITPAN1);
      	var socialCategory2= document.getElementById("socialCategory2").value;
    	//alert("4"+socialCategory1);
        var email2= document.getElementById("email2").value;
        //alert("8"+email1);
      	var proMobileNo2= document.getElementById("proMobileNo2").value;
    	//alert("5"+proMobileNo1);
      	var pmrEquity2= document.getElementById("pmrEquity2").value;
    	//alert("6"+pmrEquity1);
      	var pmrDebt2= document.getElementById("pmrDebt2").value;
      //	alert("7"+pmrDebt1);
      	//alert("firstName1.length=="+firstName1.length);
      	document.getElementById("p4").style.display = "block";
    	if(firstName2.length==0 )
    		{
    			alert("Promoter3 First Name is required");
    			document.getElementById("cpFirstName2").focus();
    			document.getElementById("p4").style.display = "none";  
 			document.getElementById("p3").style.display = "block";
    			return false;
     		}
    	
    	if(cpLastName2.length==0 )
 		{
 			alert("Promoter3 Last Name is required");
 			document.getElementById("cpLastName2").focus();
 			document.getElementById("p4").style.display = "none";  
 			document.getElementById("p3").style.display = "block";
 			return false;
 		}
    	
    	
    	if(cpITPAN2.length==0 )
 		{
 			alert("Promoter3 ITPAN is required");
 			document.getElementById("cpITPAN2").focus();
 			document.getElementById("p4").style.display = "none";  
 			document.getElementById("p3").style.display = "block";
 			return false;
 		}
    	
    	
    	if(socialCategory2.length==0 )
 		{
 			alert("Promoter3 Category is required");
 			document.getElementById("socialCategory2").focus();
 			document.getElementById("p4").style.display = "none";  
 			document.getElementById("p3").style.display = "block";
 			return false;
 		}
    	
    	
    	if(email2.length==0 )
 		{
 			alert(" Promoter3 Email is required");
 			document.getElementById("email2").focus();
 			document.getElementById("p4").style.display = "none";  
 			document.getElementById("p3").style.display = "block";
 			return false;
 		}
    	
    	if(proMobileNo2.length==0 )
 		{
 			alert(" Promoter3 Mobile is required");
 			document.getElementById("proMobileNo2").focus();
 			document.getElementById("p4").style.display = "none";  
 			document.getElementById("p3").style.display = "block";
 			return false;
 		}
    	
    	if(pmrEquity2.length==0 )
 		{
 			alert("Promoter3 Equity is required");
 			document.getElementById("pmrEquity2").focus();
 			document.getElementById("p4").style.display = "none";  
 			document.getElementById("p3").style.display = "block";
 			return false;
 		}
    	
    	if(pmrDebt2.length==0 )
 		{
 			alert("Promoter3 Debt/Loan is required");
 			document.getElementById("pmrDebt2").focus();
 			document.getElementById("p4").style.display = "none";  
 			document.getElementById("p3").style.display = "block";
 			return false;
 		}
      	  
      	  //document.getElementById("btn3").style.display = "none";
      }
      function btnR3function() {
      	  document.getElementById("p3").style.display = "none";
      	  
      	var strname = document.getElementById("cpFirstName2").value;
      	//alert(strname);
      	 document.getElementById("cpFirstName2").value="";
      	document.getElementById("pmrDebt2").value="";
      	document.getElementById("pmrEquity2").value="";
      	document.getElementById("email2").value=""; 
      	document.getElementById("proMobileNo2").value=""; 
    	document.getElementById("cpLastName2").value="";
      	document.getElementById("socialCategory2").value="";
      	document.getElementById("cpITPAN2").value=""; 
      	
      	  document.getElementById("btn2").style.display = "inline";
      }
      function btnA5function() {
    	 // alert("I m here in block 5 ");
    	  //document.getElementById("p5").style.display = "block";
    	  var firstName3= document.getElementById("cpFirstName3").value;
      //	alert("1"+firstName3);
      	var cpLastName3= document.getElementById("cpLastName3").value;
    	//alert("2"+cpLastName1);
      	var cpITPAN3= document.getElementById("cpITPAN3").value;
      	
      	if(firstName3.length>0 || cpLastName3.length>0|| cpITPAN3.length>0)
 		{
 		//alert("if loop="+firstName1);
 		 document.getElementById("p4").style.display = "block";  
 		}
 	
 	 
    	//alert("3"+cpITPAN1);
      	var socialCategory3= document.getElementById("socialCategory3").value;
    	//alert("4"+socialCategory1);
        var email3= document.getElementById("email3").value;
        //alert("8"+email1);
      	var proMobileNo3= document.getElementById("proMobileNo3").value;
    	//alert("5"+proMobileNo1);
      	var pmrEquity3= document.getElementById("pmrEquity3").value;
    	//alert("6"+pmrEquity1);
      	var pmrDebt3= document.getElementById("pmrDebt3").value;
      //	alert("7"+pmrDebt1);
      	//alert("firstName1.length=="+firstName1.length);
    	if(firstName3.length==0 )
    		{
    			alert("Promoter4 First Name is required");
    			document.getElementById("cpFirstName3").focus();
    			document.getElementById("p5").style.display = "none";  
     			document.getElementById("p4").style.display = "block";
    			return false;
     		}
    	
    	if(cpLastName3.length==0 )
 		{
 			alert("Promoter4 Last Name is required");
 			document.getElementById("cpLastName3").focus();
 			document.getElementById("p5").style.display = "none";  
 			document.getElementById("p4").style.display = "block";
 			return false;
 		}
    	
    	
    	if(cpITPAN3.length==0 )
 		{
 			alert("Promoter4 ITPAN is required");
 			document.getElementById("cpITPAN3").focus();
 			document.getElementById("p5").style.display = "none";  
 			document.getElementById("p4").style.display = "block";
 			return false;
 		}
    	
    	
    	if(socialCategory3.length==0 )
 		{
 			alert("Promoter4 Category is required");
 			document.getElementById("socialCategory3").focus();
 			document.getElementById("p5").style.display = "none";  
 			document.getElementById("p4").style.display = "block";
 			return false;
 		}
    	
    	
    	if(email3.length==0 )
 		{
 			alert(" Promoter4 Email is required");
 			document.getElementById("email3").focus();
 			document.getElementById("p5").style.display = "none";  
 			document.getElementById("p4").style.display = "block";
 			return false;
 		}
    	
    	if(proMobileNo3.length==0 )
 		{
 			alert(" Promoter4 mobile is required");
 			document.getElementById("proMobileNo3").focus();
 			document.getElementById("p5").style.display = "none";  
 			document.getElementById("p4").style.display = "block";
 			return false;
 		}
    	
    	if(pmrEquity3.length==0 )
 		{
 			alert("Promoter4 Equity is required");
 			document.getElementById("pmrEquity3").focus();
 			document.getElementById("p5").style.display = "none";  
 			document.getElementById("p4").style.display = "block";
 			return false;
 		}
    	
    	if(pmrDebt3.length==0 )
 		{
 			alert("Promoter4 Debt/Loan is required");
 			document.getElementById("pmrDebt3").focus();
 			document.getElementById("p5").style.display = "none";  
 			document.getElementById("p4").style.display = "block";
 			return false;
 		}
    	  
    	  
    	  
    	  
      	  document.getElementById("p5").style.display = "block";
      	  document.getElementById("btn4").style.display = "none";
      }
      function btnR4function() {
      	  document.getElementById("p4").style.display = "none";
      	document.getElementById("btn3").style.display = "inline";
      	
      	document.getElementById("cpFirstName3").value;
    	 document.getElementById("cpFirstName3").value="";
    	 document.getElementById("proMobileNo3").value=""; 
    	document.getElementById("pmrDebt3").value="";
    	document.getElementById("pmrEquity3").value="";
    	document.getElementById("email3").value=""; 
  	document.getElementById("cpLastName3").value="";
    	document.getElementById("socialCategory3").value="";
    	document.getElementById("cpITPAN3").value=""; 
      }
      function btnA6function() {
    	  
    	 // alert("i m in button 6==");
      	//  document.getElementById("p6").style.display = "block";
      	  //document.getElementById("btn5").style.display = "none";
      	  
      	  
          
          	  var firstName4= document.getElementById("cpFirstName4").value;
          	alert("1"+firstName4);
          	var cpLastName4= document.getElementById("cpLastName4").value;
        	alert("2"+cpLastName4);
          	var cpITPAN4= document.getElementById("cpITPAN4").value;
          	//alert("3");
          	if(firstName4.length>0 || cpLastName4.length>0|| cpITPAN4.length>0)
     		{
     		//alert("if loop="+firstName1);
     		 document.getElementById("p5").style.display = "block";  
     		}
     	
     	 
        	//alert("3"+cpITPAN1);
          	var socialCategory4= document.getElementById("socialCategory4").value;
        	//alert("4");
            var email4= document.getElementById("email4").value;
           // alert("8");
          	var proMobileNo4= document.getElementById("proMobileNo4").value;
        	//alert("5");
          	var pmrEquity4= document.getElementById("pmrEquity4").value;
        	//alert("6");
          	var pmrDebt4= document.getElementById("pmrDebt4").value;
          	//alert("7");
          	//alert("firstName1.length=="+firstName1.length);
        	if(firstName4.length==0 )
        		{
        			alert("Promoter5 First Name is required");
        			document.getElementById("cpFirstName4").focus();
        			document.getElementById("p6").style.display = "none";  
         			document.getElementById("p5").style.display = "block";
        			return false;
         		}
        	
        	if(cpLastName4.length==0 )
     		{
     			alert("Promoter5 Last Name is required");
     			document.getElementById("cpLastName4").focus();
     			document.getElementById("p6").style.display = "none";  
     			document.getElementById("p5").style.display = "block";
     			return false;
     		}
        	
        	
        	if(cpITPAN4.length==0 )
     		{
     			alert("Promoter5 ITPAN is required");
     			document.getElementById("cpITPAN4").focus();
     			document.getElementById("p6").style.display = "none";  
     			document.getElementById("p5").style.display = "block";
     			return false;
     		}
        	
        	
        	if(socialCategory4.length==0 )
     		{
     			alert("Promoter5 Category is required");
     			document.getElementById("socialCategory4").focus();
     			document.getElementById("p6").style.display = "none";  
     			document.getElementById("p5").style.display = "block";
     			return false;
     		}
        	
        	
        	if(email4.length==0 )
     		{
     			alert(" Promoter5 Email is required");
     			document.getElementById("email4").focus();
     			document.getElementById("p6").style.display = "none";  
     			document.getElementById("p5").style.display = "block";
     			return false;
     		}
        	
        	if(proMobileNo4.length==0 )
     		{
     			alert(" Promoter5 mobile is required");
     			document.getElementById("proMobileNo4").focus();
     			document.getElementById("p6").style.display = "none";  
     			document.getElementById("p5").style.display = "block";
     			return false;
     		}
        	
        	if(pmrEquity4.length==0 )
     		{
     			alert("Promoter5 Equity is required");
     			document.getElementById("pmrEquity4").focus();
     			document.getElementById("p6").style.display = "none";  
     			document.getElementById("p5").style.display = "block";
     			return false;
     		}
        	
        	if(pmrDebt4.length==0 )
     		{
     			alert("Promoter5 Debt/Loan is required");
     			document.getElementById("pmrDebt4").focus();
     			document.getElementById("p6").style.display = "none";  
     			document.getElementById("p5").style.display = "block";
     			return false;
     		}
        	document.getElementById("p6").style.display = "block";
        	  document.getElementById("btn5").style.display = "none";
      	 
      }
      function btnR5function() {
      	  document.getElementById("p5").style.display = "none";
      	document.getElementById("btn4").style.display = "inline";
      	
  	 
    	var strname = document.getElementById("cpFirstName4").value;
    	//alert(strname);
    	 document.getElementById("cpFirstName4").value="";
    	document.getElementById("pmrDebt4").value="";
    	document.getElementById("pmrEquity4").value="";
    	document.getElementById("email4").value=""; 
    	document.getElementById("proMobileNo4").value=""; 
  	document.getElementById("cpLastName4").value="";
    	document.getElementById("socialCategory4").value="";
    	document.getElementById("cpITPAN4").value=""; 
      }
      function btnA7function() {
    	  
    	  //alert("6 promoter");
     // 	  document.getElementById("p7").style.display = "block";
      	//  document.getElementById("btn6").style.display = "none";
      	  
         	  var firstName5= document.getElementById("cpFirstName5").value;
         	//alert("1"+firstName4);
         	var cpLastName5= document.getElementById("cpLastName5").value;
       	//alert("2"+cpLastName4);
         	var cpITPAN5= document.getElementById("cpITPAN5").value;
         	//alert("3");
         	if(firstName5.length>0 || cpLastName5.length>0|| cpITPAN5.length>0)
    		{
    		//alert("if loop="+firstName1);
    		 document.getElementById("p6").style.display = "block";  
    		}
    	
    	 
       	//alert("3"+cpITPAN1);
         	var socialCategory5= document.getElementById("socialCategory5").value;
       	//alert("4");
           var email5= document.getElementById("email5").value;
          // alert("8");
         	var proMobileNo5= document.getElementById("proMobileNo5").value;
       	//alert("5");
         	var pmrEquity5= document.getElementById("pmrEquity5").value;
       	//alert("6");
         	var pmrDebt5= document.getElementById("pmrDebt5").value;
         	//alert("7");
         	//alert("firstName1.length=="+firstName1.length);
       	if(firstName5.length==0 )
       		{
       			alert("Promoter6 First Name is required");
       			document.getElementById("cpFirstName5").focus();
       			document.getElementById("p7").style.display = "none";  
    			document.getElementById("p6").style.display = "block";
       			return false;
        		}
       	
       	if(cpLastName5.length==0 )
    		{
    			alert("Promoter6 Last Name is required");
    			document.getElementById("cpLastName5").focus();
    			document.getElementById("p7").style.display = "none";  
    			document.getElementById("p6").style.display = "block";
    			return false;
    		}
       	
       	
       	if(cpITPAN5.length==0 )
    		{
    			alert("Promoter6 ITPAN is required");
    			document.getElementById("cpITPAN5").focus();
    			document.getElementById("p7").style.display = "none";  
    			document.getElementById("p6").style.display = "block";
    			return false;
    		}
       	
       	
       	if(socialCategory5.length==0 )
    		{
    			alert("Promoter6 Category is required");
    			document.getElementById("socialCategory5").focus();
    			document.getElementById("p7").style.display = "none";  
    			document.getElementById("p6").style.display = "block";
    			return false;
    		}
       	
       	
       	if(email5.length==0 )
    		{
    			alert(" Promoter6 Email is required");
    			document.getElementById("email5").focus();
    			document.getElementById("p7").style.display = "none";  
    			document.getElementById("p6").style.display = "block";
    			return false;
    		}
       	
     	if(proMobileNo5.length==0 )
		{
			alert(" Promoter6 mobile is required");
			document.getElementById("proMobileNo5").focus();
			document.getElementById("p7").style.display = "none";  
			document.getElementById("p6").style.display = "block";
			return false;
		}
     	
       	
       	if(pmrEquity5.length==0 )
    		{
    			alert("Promoter6 Equity is required");
    			document.getElementById("pmrEquity5").focus();
    			document.getElementById("p7").style.display = "none";  
    			document.getElementById("p6").style.display = "block";
    			return false;
    		}
       	
       	if(pmrDebt5.length==0 )
    		{
    			alert("Promoter6 Debt/Loan is required");
    			document.getElementById("pmrDebt5").focus();
    			document.getElementById("p7").style.display = "none";  
    			document.getElementById("p6").style.display = "block";
    			return false;
    		}
       	
       	document.getElementById("p7").style.display = "block";
      	 document.getElementById("btn6").style.display = "none";
      }
      function btnR6function() {
      	  document.getElementById("p6").style.display = "none";
      	document.getElementById("btn5").style.display = "inline";
      	
      	var strname = document.getElementById("cpFirstName5").value;
    	//alert(strname);
    	 document.getElementById("cpFirstName5").value="";
    	document.getElementById("pmrDebt5").value="";
    	document.getElementById("pmrEquity5").value="";
    	document.getElementById("email5").value=""; 
    	document.getElementById("proMobileNo5").value=""; 
  	document.getElementById("cpLastName5").value="";
    	document.getElementById("socialCategory5").value="";
    	document.getElementById("cpITPAN5").value=""; 
    	
    	
      }
      function btnA8function() {
    	  //alert("7 promoter");
    	  var firstName6= document.getElementById("cpFirstName6").value;
       	//alert("1");
       	var cpLastName6= document.getElementById("cpLastName6").value;
     	//alert("2");
       	var cpITPAN6= document.getElementById("cpITPAN6").value;
       //	alert("3");
       	if(firstName6.length>0 || cpLastName6.length>0|| cpITPAN6.length>0)
  		{
  		//alert("if loop="+firstName1);
  		 document.getElementById("p7").style.display = "block";  
  		}
  	
  	 
     	//alert("3"+cpITPAN1);
       	var socialCategory6= document.getElementById("socialCategory6").value;
     	//alert("4");
         var email6= document.getElementById("email6").value;
         //alert("8");
       	var proMobileNo6= document.getElementById("proMobileNo6").value;
     	//alert("5");
       	var pmrEquity6= document.getElementById("pmrEquity6").value;
        //alert("6");
       	var pmrDebt6= document.getElementById("pmrDebt6").value;
       //	alert("7");
       	//alert("firstName1.length=="+firstName1.length);
     	if(firstName6.length==0 )
     		{
     			alert("Promoter7 First Name is required");
     			document.getElementById("cpFirstName6").focus();
     			document.getElementById("p8").style.display = "none";  
      			document.getElementById("p7").style.display = "block";
     			return false;
      		}
     	
     	if(cpLastName6.length==0 )
  		{
  			alert("Promoter7 Last Name is required");
  			document.getElementById("cpLastName6").focus();
  			document.getElementById("p8").style.display = "none";  
  			document.getElementById("p7").style.display = "block";
  			return false;
  		}
     	
     	
     	if(cpITPAN6.length==0 )
  		{
  			alert("Promoter7 ITPAN is required");
  			document.getElementById("cpITPAN6").focus();
  			document.getElementById("p8").style.display = "none";  
  			document.getElementById("p7").style.display = "block";
  			return false;
  		}
     	
     	
     	if(socialCategory6.length==0 )
  		{
  			alert("Promoter7 Category is required");
  			document.getElementById("socialCategory6").focus();
  			document.getElementById("p8").style.display = "none";  
  			document.getElementById("p7").style.display = "block";
  			return false;
  		}
     	
     	
     	if(email6.length==0 )
  		{
  			alert(" Promoter7 Email is required");
  			document.getElementById("email6").focus();
  			document.getElementById("p8").style.display = "none";  
  			document.getElementById("p7").style.display = "block";
  			return false;
  		}
     	
     	if(proMobileNo6.length==0 )
  		{
  			alert(" Promoter7 Email is required");
  			document.getElementById("proMobileNo6").focus();
  			document.getElementById("p8").style.display = "none";  
  			document.getElementById("p7").style.display = "block";
  			return false;
  		}
     	
     	
     	if(pmrEquity6.length==0 )
  		{
  			alert("Promoter7 Equity is required");
  			document.getElementById("pmrEquity6").focus();
  			document.getElementById("p8").style.display = "none";  
  			document.getElementById("p7").style.display = "block";
  			return false;
  		}
     	
     	if(pmrDebt6.length==0 )
  		{
  			alert("Promoter7 Debt/Loan is required");
  			document.getElementById("pmrDebt6").focus();
  			document.getElementById("p8").style.display = "none";  
  			document.getElementById("p7").style.display = "block";
  			return false;
  		}
      	  document.getElementById("p8").style.display = "block";
      	  document.getElementById("btn7").style.display = "none";
      }
      function btnR7function() {
      	  document.getElementById("p7").style.display = "none";
      	document.getElementById("btn6").style.display = "inline";
      	
      	document.getElementById("cpFirstName6").value;
    	 document.getElementById("cpFirstName6").value="";
    	document.getElementById("pmrDebt6").value="";
    	document.getElementById("pmrEquity6").value="";
    	document.getElementById("proMobileN6").value=""; 
    	document.getElementById("email6").value=""; 
  	document.getElementById("cpLastName6").value="";
    	document.getElementById("socialCategory6").value="";
    	document.getElementById("cpITPAN6").value=""; 
      }
      function btnA9function() {
    	  
    	  var firstName7= document.getElementById("cpFirstName7").value;
         	//alert("1");
         	var cpLastName7= document.getElementById("cpLastName7").value;
       	//alert("2");
         	var cpITPAN7= document.getElementById("cpITPAN7").value;
         //	alert("3");
         	if(firstName7.length>0 || cpLastName7.length>0|| cpITPAN7.length>0)
    		{
    		//alert("if loop="+firstName1);
    		 document.getElementById("p8").style.display = "block";  
    		}
    	
    	 
       	//alert("3"+cpITPAN1);
         	var socialCategory7= document.getElementById("socialCategory7").value;
       	//alert("4");
           var email7= document.getElementById("email7").value;
           //alert("8");
         	var proMobileNo7= document.getElementById("proMobileNo7").value;
       	//alert("5");
         	var pmrEquity7= document.getElementById("pmrEquity7").value;
          //alert("6");
         	var pmrDebt7= document.getElementById("pmrDebt7").value;
         //	alert("7");
         	//alert("firstName1.length=="+firstName1.length);
       	if(firstName7.length==0 )
       		{
       			alert("Promoter8 First Name is required");
       			document.getElementById("cpFirstName7").focus();
       			document.getElementById("p9").style.display = "none";  
    			document.getElementById("p8").style.display = "block";
       			return false;
        		}
       	
       	if(cpLastName7.length==0 )
    		{
    			alert("Promoter8 Last Name is required");
    			document.getElementById("cpLastName7").focus();
    			document.getElementById("p9").style.display = "none";  
    			document.getElementById("p8").style.display = "block";
    			return false;
    		}
       	
       	
       	if(cpITPAN7.length==0 )
    		{
    			alert("Promoter8 ITPAN is required");
    			document.getElementById("cpITPAN7").focus();
    			document.getElementById("p9").style.display = "none";  
    			document.getElementById("p8").style.display = "block";
    			return false;
    		}
       	
       	
       	if(socialCategory7.length==0 )
    		{
    			alert("Promoter8 Category is required");
    			document.getElementById("socialCategory7").focus();
    			document.getElementById("p9").style.display = "none";  
    			document.getElementById("p8").style.display = "block";
    			return false;
    		}
       	
       	
       	if(email7.length==0 )
    		{
    			alert(" Promoter8 Email is required");
    			document.getElementById("email7").focus();
    			document.getElementById("p9").style.display = "none";  
    			document.getElementById("p8").style.display = "block";
    			return false;
    		}
       	if(proMobileNo7.length==0 )
  		{
  			alert(" Promoter8 Mobile is required");
  			document.getElementById("proMobileNo7").focus();
  			document.getElementById("p9").style.display = "none";  
  			document.getElementById("p8").style.display = "block";
  			return false;
  		}
     	
       	if(pmrEquity7.length==0 )
    		{
    			alert("Promoter8 Equity is required");
    			document.getElementById("pmrEquity7").focus();
    			document.getElementById("p9").style.display = "none";  
    			document.getElementById("p8").style.display = "block";
    			return false;
    		}
       	
       	if(pmrDebt7.length==0 )
    		{
    			alert("Promoter8 Debt/Loan is required");
    			document.getElementById("pmrDebt7").focus();
    			document.getElementById("p9").style.display = "none";  
    			document.getElementById("p8").style.display = "block";
    			return false;
    		}
      	  document.getElementById("p9").style.display = "block";
      	  document.getElementById("btn8").style.display = "none";
      }
      function btnR8function() {
      	  document.getElementById("p8").style.display = "none";
      	document.getElementById("btn7").style.display = "inline";
      	
      	document.getElementById("cpFirstName7").value;
   	 document.getElementById("cpFirstName7").value="";
   	document.getElementById("pmrDebt7").value="";
   	document.getElementById("pmrEquity7").value="";
   	document.getElementById("email7").value=""; 
   	document.getElementById("proMobileNo7").value=""; 
 	document.getElementById("cpLastName7").value="";
   	document.getElementById("socialCategory7").value="";
   	document.getElementById("cpITPAN7").value=""; 
      }
      function btnA10function() {
    	  
    	  var firstName8= document.getElementById("cpFirstName8").value;
       	//alert("1");
       	var cpLastName8= document.getElementById("cpLastName8").value;
     	//alert("2");
       	var cpITPAN8= document.getElementById("cpITPAN8").value;
       //	alert("3");
       	if(firstName8.length>0 || cpLastName8.length>0|| cpITPAN8.length>0)
  		{
  		//alert("if loop="+firstName1);
  		 document.getElementById("p9").style.display = "block";  
  		}
  	
  	 
     	//alert("3"+cpITPAN1);
       	var socialCategory8= document.getElementById("socialCategory8").value;
     	//alert("4");
         var email8= document.getElementById("email8").value;
         //alert("8");
       	var proMobileNo8= document.getElementById("proMobileNo8").value;
     	//alert("5");
       	var pmrEquity8= document.getElementById("pmrEquity8").value;
        //alert("6");
       	var pmrDebt8= document.getElementById("pmrDebt8").value;
       //	alert("7");
       	//alert("firstName1.length=="+firstName1.length);
     	if(firstName8.length==0 )
     		{
     			alert("Promoter9 First Name is required");
     			document.getElementById("cpFirstName8").focus();
     			document.getElementById("p10").style.display = "none";  
      			document.getElementById("p9").style.display = "block";
     			return false;
      		}
     	
     	if(cpLastName8.length==0 )
  		{
  			alert("Promoter9 Last Name is required");
  			document.getElementById("cpLastName8").focus();
  			document.getElementById("p10").style.display = "none";  
  			document.getElementById("p9").style.display = "block";
  			return false;
  		}
     	
     	
     	if(cpITPAN8.length==0 )
  		{
  			alert("Promoter9 ITPAN is required");
  			document.getElementById("cpITPAN8").focus();
  			document.getElementById("p10").style.display = "none";  
  			document.getElementById("p9").style.display = "block";
  			return false;
  		}
     	
     	
     	if(socialCategory8.length==0 )
  		{
  			alert("Promoter9 Category is required");
  			document.getElementById("socialCategory8").focus();
  			document.getElementById("p10").style.display = "none";  
  			document.getElementById("p9").style.display = "block";
  			return false;
  		}
     	
     	
     	if(email8.length==0 )
  		{
  			alert(" Promoter9 Email is required");
  			document.getElementById("email8").focus();
  			document.getElementById("p10").style.display = "none";  
  			document.getElementById("p9").style.display = "block";
  			return false;
  		}
     	
     	
     	if(proMobileNo8.length==0 )
  		{
  			alert(" Promoter9 Mobile is required");
  			document.getElementById("proMobileNo8").focus();
  			document.getElementById("p10").style.display = "none";  
  			document.getElementById("p9").style.display = "block";
  			return false;
  		}
     	
     	
     	
     	if(pmrEquity8.length==0 )
  		{
  			alert("Promoter9 Equity is required");
  			document.getElementById("pmrEquity8").focus();
  			document.getElementById("p10").style.display = "none";  
  			document.getElementById("p9").style.display = "block";
  			return false;
  		}
     	
     	if(pmrDebt8.length==0 )
  		{
  			alert("Promoter9 Debt/Loan is required");
  			document.getElementById("pmrDebt8").focus();
  			document.getElementById("p10").style.display = "none";  
  			document.getElementById("p9").style.display = "block";
  			return false;
  		}
      	  document.getElementById("p10").style.display = "block";
      	  document.getElementById("btn9").style.display = "none";
      }
      function btnR9function() {
      	  document.getElementById("p9").style.display = "none";
      	document.getElementById("btn8").style.display = "inline";
      	
       	document.getElementById("cpFirstName8").value;
      	 document.getElementById("cpFirstName8").value="";
      	document.getElementById("pmrDebt8").value="";
      	document.getElementById("pmrEquity8").value="";
      	document.getElementById("email8").value=""; 
      	document.getElementById("proMobileNo8").value=""; 
    	document.getElementById("cpLastName8").value="";
      	document.getElementById("socialCategory8").value="";
      	document.getElementById("cpITPAN8").value=""; 
      }
      
      function btnR10function() {
      	  document.getElementById("p10").style.display = "none";
      	document.getElementById("btn9").style.display = "inline";
      	
    	document.getElementById("cpFirstName9").value;
     	 document.getElementById("cpFirstName9").value="";
     	document.getElementById("pmrDebt9").value="";
     	document.getElementById("pmrEquity9").value="";
     	document.getElementById("email9").value=""; 
     	document.getElementById("proMobileNo9").value=""; 
   	document.getElementById("cpLastName9").value="";
     	document.getElementById("socialCategory9").value="";
     	document.getElementById("cpITPAN9").value=""; 
      }
         
      
 function promterITPANChk1()
 {
	 
		if(	document.getElementById("cpITPAN").value==	document.getElementById("cpITPAN1").value)
			{
				alert("Enter correct ITPAN for Promoter2");	
				cpITPAN1=document.getElementById("cpITPAN1").value="";
				cpITPAN1=document.getElementById("cpITPAN1").focus();
				
			}
 }     
      
 
 function promterITPANChk2()
 {
	 
		if((document.getElementById("cpITPAN2").value==	document.getElementById("cpITPAN1").value)||(document.getElementById("cpITPAN").value==	document.getElementById("cpITPAN2").value))
			{
				alert("Enter correct ITPAN for Promoter3");	
				cpITPAN1=document.getElementById("cpITPAN2").value="";
				cpITPAN1=document.getElementById("cpITPAN2").focus();
				
			}
 }     
 
 
 function promterITPANChk3()
 {
	 
		if((document.getElementById("cpITPAN3").value==	document.getElementById("cpITPAN2").value)||(document.getElementById("cpITPAN3").value==	document.getElementById("cpITPAN1").value)||(document.getElementById("cpITPAN3").value==	document.getElementById("cpITPAN").value))
			{
				alert("Enter correct ITPAN for Promoter4");	
				cpITPAN1=document.getElementById("cpITPAN3").value="";
				cpITPAN1=document.getElementById("cpITPAN3").focus();
				
			}
 }
 
 
 function promterITPANChk4()
 {
	 
		if((document.getElementById("cpITPAN4").value==	document.getElementById("cpITPAN3").value)||(document.getElementById("cpITPAN4").value==document.getElementById("cpITPAN2").value)||(document.getElementById("cpITPAN4").value==document.getElementById("cpITPAN1").value)||(document.getElementById("cpITPAN4").value==document.getElementById("cpITPAN").value))
			{
				alert("Enter correct ITPAN for Promoter5");	
				cpITPAN1=document.getElementById("cpITPAN4").value="";
				cpITPAN1=document.getElementById("cpITPAN4").focus();
				
			}
 }
 
 
 function promterITPANChk5()
 {
	 
		if((document.getElementById("cpITPAN5").value==	document.getElementById("cpITPAN4").value)||(document.getElementById("cpITPAN5").value==document.getElementById("cpITPAN3").value)||(document.getElementById("cpITPAN5").value==document.getElementById("cpITPAN2").value)||(document.getElementById("cpITPAN5").value==document.getElementById("cpITPAN1").value)||(document.getElementById("cpITPAN5").value==document.getElementById("cpITPAN").value))
			{
				alert("Enter correct ITPAN for Promoter6");	
				cpITPAN1=document.getElementById("cpITPAN5").value="";
				cpITPAN1=document.getElementById("cpITPAN5").focus();
				
			}
 }
 
 function promterITPANChk6()
 {
	 
		if((document.getElementById("cpITPAN6").value==	document.getElementById("cpITPAN5").value)||(document.getElementById("cpITPAN6").value==	document.getElementById("cpITPAN4").value)||(document.getElementById("cpITPAN6").value==document.getElementById("cpITPAN3").value)||(document.getElementById("cpITPAN6").value==document.getElementById("cpITPAN2").value)||(document.getElementById("cpITPAN6").value==document.getElementById("cpITPAN1").value)||(document.getElementById("cpITPAN6").value==document.getElementById("cpITPAN").value))
			{
				alert("Enter correct ITPAN for Promoter7");	
				cpITPAN1=document.getElementById("cpITPAN6").value="";
				cpITPAN1=document.getElementById("cpITPAN6").focus();
				
			}
 }
 
 function promterITPANChk7()
 {
	 
		if((document.getElementById("cpITPAN7").value==	document.getElementById("cpITPAN6").value)||(document.getElementById("cpITPAN7").value==document.getElementById("cpITPAN5").value)||(document.getElementById("cpITPAN7").value==	document.getElementById("cpITPAN4").value)||(document.getElementById("cpITPAN7").value==document.getElementById("cpITPAN3").value)||(document.getElementById("cpITPAN7").value==document.getElementById("cpITPAN2").value)||(document.getElementById("cpITPAN7").value==document.getElementById("cpITPAN1").value)||(document.getElementById("cpITPAN7").value==document.getElementById("cpITPAN").value))
			{
				alert("Enter correct ITPAN for Promoter8");	
				cpITPAN1=document.getElementById("cpITPAN7").value="";
				cpITPAN1=document.getElementById("cpITPAN7").focus();
				
			}
 }
 
 
 function promterITPANChk8()
 {
	 
		if((document.getElementById("cpITPAN8").value==	document.getElementById("cpITPAN7").value)||(document.getElementById("cpITPAN8").value==	document.getElementById("cpITPAN6").value)||(document.getElementById("cpITPAN8").value==document.getElementById("cpITPAN5").value)||(document.getElementById("cpITPAN8").value==	document.getElementById("cpITPAN4").value)||(document.getElementById("cpITPAN8").value==document.getElementById("cpITPAN3").value)||(document.getElementById("cpITPAN8").value==document.getElementById("cpITPAN2").value)||(document.getElementById("cpITPAN8").value==document.getElementById("cpITPAN1").value)||(document.getElementById("cpITPAN8").value==document.getElementById("cpITPAN").value))
			{
				alert("Enter correct ITPAN for Promoter9");	
				cpITPAN1=document.getElementById("cpITPAN8").value="";
				cpITPAN1=document.getElementById("cpITPAN8").focus();
				
			}
 }

 
 function promterITPANChk9()
 {
	 
		if((document.getElementById("cpITPAN9").value==	document.getElementById("cpITPAN8").value)||(document.getElementById("cpITPAN9").value==	document.getElementById("cpITPAN7").value)||(document.getElementById("cpITPAN9").value==	document.getElementById("cpITPAN6").value)||(document.getElementById("cpITPAN9").value==document.getElementById("cpITPAN5").value)||(document.getElementById("cpITPAN9").value==	document.getElementById("cpITPAN4").value)||(document.getElementById("cpITPAN9").value==document.getElementById("cpITPAN3").value)||(document.getElementById("cpITPAN9").value==document.getElementById("cpITPAN2").value)||(document.getElementById("cpITPAN9").value==document.getElementById("cpITPAN1").value)||(document.getElementById("cpITPAN9").value==document.getElementById("cpITPAN").value))
			{
				alert("Enter correct ITPAN for Promoter9");	
				cpITPAN1=document.getElementById("cpITPAN8").value="";
				cpITPAN1=document.getElementById("cpITPAN8").focus();
				
			}
 }
  
    </script> 
    
   
<script src="/csrfguard"></script>
<script type="text/javascript"> 
        
    
          function clickIE4(){
        	            if (event.button==2){
        	                             //  alert(message);
        	                               return false;
        	                               }
        	                 }
        	   
        	                 function clickNS4(e){
        	                               if (document.layers||document.getElementById&&!document.all){
        	                                              if (e.which==2||e.which==3){
        	                                                       // alert(message);
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
        	                 document.oncontextmenu=new Function(";return false;");
        	                 
        	                 
        	                 function callWaiting(){
        	                		document.getElementById("waitId").style.display="block";
        	                	} 
        	                 
       //=====================================================================
        	   /*  function disableselect(e) {
        	        return false;
        	    }

        		function reEnable() {
        		    return true;
        		}

        	document.onselectstart = new Function("return false"); */

        	if (window.sidebar) {
        	    document.onmousedown = disableselect;
        	    document.onclick = reEnable;
        	};
      </script> 

<% 
		if(request.getSession(false)!=null){
			
		String focusField="";



if(session.getAttribute(SessionConstants.APPLICATION_LOAN_TYPE)!=null)
{
System.out.println(session.getAttribute(SessionConstants.APPLICATION_LOAN_TYPE));
if(request.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG)!=null && request.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("15") && session.getAttribute(SessionConstants.APPLICATION_LOAN_TYPE).equals("TC"))
{
session.setAttribute("CurrentPage","tcMli.do?method=getTCMliInfo");
focusField="district";
}
else if(request.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG)!=null && request.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("16") && session.getAttribute(SessionConstants.APPLICATION_LOAN_TYPE).equals("TC"))
{
session.setAttribute("CurrentPage","tcMli.do?method=getTCMliInfo");
focusField="industrySector";
}
else if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG)!=null && session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("17"))
{
System.out.println(request.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG));
session.setAttribute("CurrentPage","afterTcMli.do?method=getBorrowerDetails");
focusField="guarantorsName1";

}
else if(session.getAttribute(SessionConstants.APPLICATION_LOAN_TYPE).equals("TC"))
{
session.setAttribute("CurrentPage","tcMli.do?method=getTCMliInfo");
focusField="mliRefNo";
}
}
else if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("3") /*|| session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("11")*/)
{
session.setAttribute("CurrentPage","afterModifyApp.do?method=showCgpanList");
focusField="mliRefNo";

}else if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("14"))
{
session.setAttribute("CurrentPage","afterSsiRefPage.do?method=afterSsiRefPage");
focusField="mliRefNo";
}
else if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("15"))
{
session.setAttribute("CurrentPage","afterSsiRefPage.do?method=afterSsiRefPage");
focusField="mliRefNo";
}

if(focusField.equals(""))
{
focusField=null;
}

org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
focusField="test";
};
%>



 

<%@ include file="/jsp/SetMenuInfo.jsp" %>


<body onLoad="enableAssistance(),enableNone(),setConstEnabled(),enableDistrictOthers(),enableOtherLegalId(),enableSubsidyName(),calProjectCost(),calProjectOutlay(),enableGender(),enableHandiCrafts(),enabledcHandlooms(),callWhenPageWillLoad(),calSubDebtEquity();">


<html:form action="addTermCreditApp.do?method=submitApp" method="POST" focus="<%=focusField%>" styleId="auto_off">
<html:hidden name="appForm" property="test"/>
<html:errors />
<input type="hidden" name="csrfCode" id="csrfCode"  value="<%=CaptchaSession.getCaptchaSession(request)%>"/>
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">

<TR> 
<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
<TD background="images/TableBackground1.gif"><IMG src="images/ApplicationProcessingHeading.gif" width="91" height="31"></TD>
<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
</TR>


<TR>
<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>

<TD>
<div align="right">			
<!-- <A HREF="javascript:submitForm('termCreditHelp.do?method=termCreditHelp')">
HELP</A> -->
</div>
<%@ include file="CommonAppDetails.jsp" %>
<%@ include file="TermCreditDetails.jsp" %>



<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
<%
String appFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();
if(appFlag.equals("3"))
{
%>
<tr align="left">
<td class="ColumnBackground" height="28">&nbsp;
Existing Remarks
</td>
<td class="TableData" height="28" colspan="5">				

<bean:write name="appForm" property="existingRemarks"/>


</td>
</tr>	
<%}%>

<tr align="left">
<td class="ColumnBackground" height="28" colspan="2">&nbsp;
Remarks
</td>
<td class="TableData" height="28" colspan="4">
<%		
if(appFlag.equals("11") || appFlag.equals("13"))
{
%>
<bean:write property="remarks" name="appForm"/>
<%} else {%>

<html:textarea property="remarks" cols="75" alt="address" name="appForm" rows="4"/>
<%}%>

</td>
</tr>
	
<TR class="ColumnBackground"><font color="#FF0000" size="2">*</font>
<!--Check box Added by sukant@pathinfotech on 15/05/2007-->
<html:checkbox property="agree" value="Y" disabled="false"/>
We certify that the borrower MSME unit was standard as on 31/03/2018 and having been in regular operation either as standard account(s) or as NPA account(s) during FY 2018-2019 and FY 2019-2020 and also is stressed viz. SMA-2 / NPA as on 30/04/2020 
<!-- <A HREF="applicationValidation.do?method=applicationValidation">
Click Here</A> to see Terms and Conditions: -->
</TR>

<TR>
<TD align="center" valign="baseline" colspan="7">
<DIV align="center">
<%	
if (request.getParameter("detail")!=null)
{
%>
		<A href="javascript:window.close()">
		<IMG src="images/Close.gif" alt="Close" width="49" height="37" border="0"></A>

<% }else if(appFlag.equals("11") || appFlag.equals("13")){ %>
<A href="javascript:history.back()">
		<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
<%}	else if(appFlag.equals("3")){%>
	<A  id="submitbuttionlog" href="javascript:submitForm1('addTermCreditApp.do?method=submitApp'),calSubDebtEquity(),calExistTotalOs();" ><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>		
	<A href="javascript:document.appForm.reset()">
		<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
	<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
	<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
<%}else{%>
	<A id="submitbuttionlog" href="javascript:submitForm1('addTermCreditApp.do?method=submitApp'),calSubDebtEquity(),calExistTotalOs(),promoterValidation();"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>		
	<A href="javascript:document.appForm.reset()">
		<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
	<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
	<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
<%}%>

</DIV>
</TD>
</TR>	
				
</TABLE>
</TD>

<TD width="20" background="images/TableVerticalRightBG.gif">
&nbsp;
</TD>

</TR>
 
<TR>
<TD width="20" align="right" valign="top">
<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
</TD>
<TD background="images/TableBackground2.gif">
&nbsp;
</TD>
<TD width="20" align="left" valign="top">
<IMG src="images/TableRightBottom1.gif" width="23" height="15">
</TD>
</TR>


</TABLE>

</html:form>
</body>
<%
	  }else{		  
		  request.getSession().invalidate();
		  response.sendRedirect(request.getContextPath() + "/showLogin");
	  }
  %>





