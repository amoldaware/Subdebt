
var selection;
var mainMenuItem;
var subMenuItem;
var homeAction ="home.do?method=getMainMenu";
var subHomeAction="subHome.do?method=getSubMenu";
var mainMenus = new Array();
var subMenus = new Array();
var subMenuValues = new Array();
var mainMenuValues = new Array();
var links="";
var booleanVal = false;
var creditammt="";
var checkbox;

function check(){
	
}


function checkEmailFormatValid(){
	   var emailVal=document.getElementById('emailId').value;
	   if(emailVal==''){
		   alert('E-mail id is required.');
	   }else{
		   if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(emailVal))
		   {
		     return (true);
		   }
		     alert("You have entered an invalid email address!");
		     return (false); 
	   }
}

function isvalidFadralBank(){/*
	checkbox=document.getElementsByName("exposureFbId");
	var cgValue = document.forms[0].creditGuaranteed.value;
	var exValue = document.forms[0].exposurelmtAmt.value;
	for(var j=0; j<checkbox.length; j++)
	{
	  if(checkbox[j].checked && cgValue!="" && exValue != ""){
	var xmlhttp;
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); // for IE7+, Firefox, Chrome, Opera,
										// Safari
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); // for IE6, IE5
    }  

    var url =  "addTermCreditAppFB.do?method=isvalidfbammt&creditammt="+document.forms[0].creditGuaranteed.value+"&ammt="+document.forms[0].exposurelmtAmt.value;
    xmlhttp.open("POST",url, true);   
    xmlhttp.onreadystatechange = function() {  
    
    if (xmlhttp.readyState == 4) 
	{    	   
            if (xmlhttp.status == 200)
            {    
            	
//            	
            	  var temp = new Array();
	              temp =xmlhttp.responseText;
//	         
	              if(temp!='')
	              {	   
	         
		            document.getElementById("FBerrorsMessage").innerHTML = temp;
		            var started = Date.now();
		            var interval = setInterval(function()
		            {		            	
		                if (Date.now() - started > 4500) 
		                {
		                	clearInterval(interval);

		                } else 
		                {		                 
		                	document.getElementById("FBerrorsMessage").innerHTML ='';
		                }
		              }, 4000);
		       
	              }
	              else
	              {
	                 document.gmClosureForm.submit();
	              }
            }
            else
            {
            	document.getElementById("FBerrorsMessage").innerHTML = 'Something is wrong !! , Please contact CGTMSE support[support@cgtmse.in] team .';                
            }
        }
       };
	  }
    }
    xmlhttp.send(null);	*/
}

function findObj(n, d) 
{
  var p,i,x; 
  
  if(!d)
  d=document; 
  
  if((p=n.indexOf("?"))>0 && parent.frames.length) 
  {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);
  }
  if(!(x=d[n])&&d.all) 
  x=d.all[n];
  
  for (i=0;!x&&i<d.forms.length;i++) 
  x=d.forms[i][n];
  
  for(i=0;!x&&d.layers&&i<d.layers.length;i++)
  x=findObj(n,d.layers[i].document);
  
  if(!x && d.getElementById) 
  x=d.getElementById(n); 
  
  return x;
}

/* added by sukumar on 12-04-2008 */
function checkbox_checker()
{

var checkbox_choices = 0
var counter = document.getElementById('rpAllocationForm');
for (count = 0; count < counter.length; count++){
if (counter[count].checked == true){
 checkbox_choices = checkbox_choices + 1;
 }

if ((counter[count].disabled == true))	
{
	checkbox_choices = checkbox_choices + 1;
}
}
if (checkbox_choices > 50){
var msg="You're limited to only FIFTY selections.\n";
msg=msg + "You have made " + checkbox_choices + " selections.\n";
msg=msg + "Please remove " + (checkbox_choices-50) + " selection(s).";
alert(msg);
return false;
}
else{
	return true;
}
}

/* - ------- DKR ------------- 35  */

function enableHybridSecurity(){
	var hybridSecVal = "";
	if(null!=document.querySelector('input[name="hybridSecurity"]:checked')){
		hybridSecVal = document.querySelector('input[name="hybridSecurity"]:checked').value;
	}
	if (hybridSecVal=='Y' || hybridSecVal=='y') {		   
		document.getElementById("movCollateratlSecurityLblId").style.display="block"; 
	}else if (hybridSecVal=='N'||hybridSecVal=='n'){				  
	    document.getElementById("movCollateratlSecurityLblId").style.display="none";  		  	
    }
} 
/*function calTotalMIcollatSecAmt(){
	alert("calTotalMIcollatSecAmt function called==");
	var hybridSecVal = document.querySelector('input[name="hybridSecurity"]:checked').value;
	var movCollateratlVal=document.getElementById('movCollateratlSecurityAmt').value;
	var immovCollateratlVal=document.getElementById('immovCollateratlSecurityAmt').value;
	if (movCollateratlVal == "")
		movCollateratlVal = 0.0;
    if (immovCollateratlVal == "")
    	immovCollateratlVal = 0.0;
	if((movCollateratlVal!=null || movCollateratlVal!="") && (immovCollateratlVal!=null || immovCollateratlVal!=""))
	{         
		if (hybridSecVal=='Y' || hybridSecVal=='y') {
			   var collateratlValResult = parseFloat(movCollateratlVal) + parseFloat(immovCollateratlVal);
		       if (!isNaN(collateratlValResult)) {
		    	   document.getElementById("totalMIcollatSecAmt").value=collateratlValResult;		    	 	
		    		var termCreditSanctione = document.getElementById("termCreditSanctioned").value;
		    		var totalMIcollatSecAmt = document.getElementById("totalMIcollatSecAmt").value;		    				    			 
		    			var dtotal = termCreditSanctione-totalMIcollatSecAmt; 		    			
		    		    document.getElementById("creditGuaranteed").value=dtotal;
		    		    document.getElementById("creditGuaranteedhid").value=dtotal;
		       }
		       var termCreditSanAmt=  document.getElementById("termCreditSanctioned").value;	
		       if(!isNan(termCreditSanAmt)){
		    	   
		    	   
		       }
		
	  }else if (hybridSecVal=='N'||hybridSecVal=='n'){			  
		    document.getElementById('movCollateratlSecurityAmt').value=0.0;
			document.getElementById('immovCollateratlSecurityAmt').value=0.0;
			document.getElementById("totalMIcollatSecAmt").value=0.0;	
		    document.getElementById("movCollateratlSecurityLblId").style.display="none";
      }
	}
}*/
function calTotalMIcollatSecAmt(){
	var movCollateratlVal=0.0;
	var termCreditSanctione=0.0;
	var creditGuaranteed=0.0;  
	var creditGuaranteedhid=0.0; 
	var dtotal = 0.0;
	var hybridSecVal="";
	var wcFundBasedSanctionedVal=0.0;
	var wcNonFundBasedSanctionedVal =0.0;
	 var finalVald = 0.0;	
	if(null!=document.querySelector('input[name="hybridSecurity"]:checked')){
		hybridSecVal = document.querySelector('input[name="hybridSecurity"]:checked').value;
	}
	var immovCollateratlVal =0.0;
	if(null!=document.getElementById("immovCollateratlSecurityAmt")){
		immovCollateratlVal = document.getElementById("immovCollateratlSecurityAmt").value;
	}	
	var existExpoCgtVal =0.0;
	if(null!=document.getElementById("existExpoCgt")){
		existExpoCgtVal = parseFloat(document.getElementById("existExpoCgt").value);
	}	
	var unseqLoanportionVal =0.0;
	if(null!=document.getElementById("unseqLoanportion")){
		unseqLoanportionVal = parseFloat(document.getElementById("unseqLoanportion").value);
	}
	
	if(immovCollateratlVal!=null || immovCollateratlVal!="")
	{        
		if (hybridSecVal=='Y' || hybridSecVal=='y') {
			 var collateratlValResult = parseFloat(immovCollateratlVal);
			 if (!isNaN(collateratlValResult)) {

		    	    if(null!=document.getElementById("creditGuaranteed") && (null==document.getElementById("creditFundBased") &&  null==document.getElementById("creditNonFundBased")))		                      //  tc
		    	    {
		    	    	    if(null!=document.getElementById("termCreditSanctioned")){
	    		    		 termCreditSanctione = parseFloat(document.getElementById("termCreditSanctioned").value);
		    		        }
		    	    	    unseqLoanportionVal = termCreditSanctione - collateratlValResult;
		    	    	   // dtotal = 20000000 - existExpoCgtVal;	
		    	    	    document.getElementById("unseqLoanportion").value=Math.abs(unseqLoanportionVal);
			    		       		        	
		    		    }
		    	       if((null!=document.getElementById("creditFundBased") || null!=document.getElementById("creditNonFundBased")) && null==document.getElementById("creditGuaranteed"))  // wc
		    		    {      
		    	    	    if(null!=document.getElementById("wcFundBasedSanctioned")){
		    		    		wcFundBasedSanctionedVal = document.getElementById("wcFundBasedSanctioned").value;
		    		    	}		    		    
		    		    	if(null!=document.getElementById("wcNonFundBasedSanctioned")){
		    		    		wcNonFundBasedSanctionedVal = document.getElementById("wcNonFundBasedSanctioned").value;
		    		    	}		    		 		    		   	      		    		    	    
		    		    	unseqLoanportionVal = ((parseFloat(wcFundBasedSanctionedVal)) + (parseFloat(wcNonFundBasedSanctionedVal))) - collateratlValResult;
		    		    	document.getElementById("unseqLoanportion").value=Math.abs(unseqLoanportionVal);
		    		   }
		    		    if(null!=document.getElementById("creditGuaranteed") && (null!=document.getElementById("creditFundBased") || null!=document.getElementById("creditNonFundBased")))                            // both
		    		    { 	    		    	
		    		    	if(null!=document.getElementById("termCreditSanctioned")){
		    		    		 termCreditSanctione = document.getElementById("termCreditSanctioned").value;
			    		    }
		    		    	if(null!=document.getElementById("wcFundBasedSanctioned")){
		    		    		wcFundBasedSanctionedVal = document.getElementById("wcFundBasedSanctioned").value;
		    		    	}		    		    	
		    		    	if(null!=document.getElementById("wcNonFundBasedSanctioned")){
		    		    		wcNonFundBasedSanctionedVal = document.getElementById("wcNonFundBasedSanctioned").value;
		    		    	}
		    		    	 
		    		    	unseqLoanportionVal = ((parseFloat(termCreditSanctione)) + (parseFloat(wcFundBasedSanctionedVal)) + (parseFloat(wcNonFundBasedSanctionedVal))) - collateratlValResult;
		    		    	document.getElementById("unseqLoanportion").value=Math.abs(unseqLoanportionVal);		    		    	
		    		    	
		    		    }  
		     
				 }	      		
		  }else if (hybridSecVal=='N'||hybridSecVal=='n'){		   
				document.getElementById('immovCollateratlSecurityAmt').value=0.0;				
			    document.getElementById("movCollateratlSecurityLblId").style.display="none";
	     }
   }
}
/*function getSelectIndustryRetail(){	
	var hybridSecVal = document.querySelector('input[name="hybridSecurity"]:checked').value;	
	var termCreditSanctione = document.getElementById("termCreditSanctione").value;
	var totalMIcollatSecAmt = document.getElementById("totalMIcollatSecAmt").value;
	
}*/

function getSelectIndustryRetail(){	
	var hybridSecVal="";
	if(null!=document.querySelector('input[name="hybridSecurity"]:checked')){
		hybridSecVal = document.querySelector('input[name="hybridSecurity"]:checked').value;
	}		
	var termCreditSanctione = 0.0;
	if(null != document.getElementById("termCreditSanctione")){
		termCreditSanctione = document.getElementById("termCreditSanctione").value;
	}	
	var totalMIcollatSecAmt = 0.0;
	if(null!=document.getElementById("immovCollateratlSecurityAmt")){
		totalMIcollatSecAmt = document.getElementById("immovCollateratlSecurityAmt").value;
	}	
}
/*function checkGurentyMaxtotalMIcollatSecAmt(){	
	
	 var creditGuaranteedd = document.getElementById("creditGuaranteed").value;
	 var creditGuaranteedhidd = document.getElementById("creditGuaranteedhid").value;
	 var totalMIcollatSecAmtd = document.getElementById("totalMIcollatSecAmt").value
	if((creditGuaranteedd!=0 && totalMIcollatSecAmtd!=0) && (creditGuaranteedd > creditGuaranteedhidd ) ){     
		alert("Credit to be Guaranteed should not be greater than "+creditGuaranteedhidd);
		document.getElementById("creditGuaranteed").value = creditGuaranteedhidd;		 
	}
}*/
function checkGurentyMaxtotalMIcollatSecAmt(){	
	 var creditGuaranteedd = 0.0; 
	 var creditGuaranteedhidd = 0.0; 	 
	 var totalMIcollatSecAmtd = 0.0;
	 var creditFundBasedhidd = 0.0;
	 var unLoanPortionExcludCgtCoveredVal = 0.0;
	 var unseqLoanportionVal = 0.0;
	 var unsecExcCGTGuarAmt = 0.0;
	
	    if(null!=document.getElementById("immovCollateratlSecurityAmt")){
		   totalMIcollatSecAmtd = document.getElementById("immovCollateratlSecurityAmt").value;
		}
	    if(null!=document.getElementById("unseqLoanportion")){
			unseqLoanportionVal = parseFloat(document.getElementById("unseqLoanportion").value);
		}
	    
	   if((null!=document.getElementById("creditGuaranteed") && null!=document.getElementById("creditGuaranteedhid")) &&                       //tc
			   (null==document.getElementById("creditFundBased") || null==document.getElementById("creditNonFundBased"))){
		
		    creditGuaranteedd = document.getElementById("creditGuaranteed").value;
		    creditGuaranteedhidd =	document.getElementById("creditGuaranteedhid").value;
		    unsecExcCGTGuarAmt = unseqLoanportionVal - creditGuaranteedd;
		    if(unsecExcCGTGuarAmt>0)
		     document.getElementById("unLoanPortionExcludCgtCovered").value = unsecExcCGTGuarAmt;
		    else //alert(unsecExcCGTGuarAmt);
		     document.getElementById("unLoanPortionExcludCgtCovered").value = 0.0;		
	    }
	   
	   if(((null!=document.getElementById('creditFundBased') && null!=document.getElementById('creditNonFundBased')) && null!=document.getElementById("creditFundBasedhid"))
			   && (null==document.getElementById("creditGuaranteed") || null==document.getElementById("creditGuaranteedhid"))){
		   var creditGuaranteedd2 = parseFloat(document.getElementById("creditFundBased").value) + parseFloat(document.getElementById("creditNonFundBased").value);
		    var creditFundBasedhidd2 = parseFloat(document.getElementById("creditFundBasedhid").value); 
	 	  // alert(creditGuaranteedd2+' wc2 '+creditFundBasedhidd2);
		    unsecExcCGTGuarAmt = unseqLoanportionVal - creditGuaranteedd2;
		    //alert('unsecExcCGTGuarAmt2'+unsecExcCGTGuarAmt);		    
		    if(unsecExcCGTGuarAmt>0)
		     document.getElementById("unLoanPortionExcludCgtCovered").value = unsecExcCGTGuarAmt;
		    else //alert(unsecExcCGTGuarAmt);
		     document.getElementById("unLoanPortionExcludCgtCovered").value = 0.0;	
	    } 
	   
	   if(null!=document.getElementById("creditGuaranteed") && (null!=document.getElementById("creditFundBased") && null!=document.getElementById('creditNonFundBased')) && null!=document.getElementById("creditGuaranteedhid")){
	  //  	alert('..1..to..checkGurentyMaxtotalMIcollatSecAmt.BO...');	       	        
	    	var creditGuaranteedd3 =  parseFloat(document.getElementById("creditNonFundBased").value) + parseFloat(document.getElementById("creditFundBased").value) + parseFloat(document.getElementById("creditGuaranteed").value);
	    	var creditGuaranteedhidd3 = document.getElementById("creditGuaranteedhid").value; 
	    	unsecExcCGTGuarAmt = unseqLoanportionVal - creditGuaranteedd3;
		    if(unsecExcCGTGuarAmt>0)
		     document.getElementById("unLoanPortionExcludCgtCovered").value = unsecExcCGTGuarAmt;
		    else //alert(unsecExcCGTGuarAmt);
		     document.getElementById("unLoanPortionExcludCgtCovered").value = 0.0;	    	
	    } 	
}


// Added BY DKR
function enableExtGreenFld(loanType){	
	var parts ='2018-12-01'.split('-');
	var validationChkDate = new Date(parts[0], parts[1] - 1, parts[2]);
	var amountSanctionedDate;	
	if((loanType=='TC') && null!=document.getElementById('amountSanctionedDate')){		
		amountSanctionedDate = document.getElementById('amountSanctionedDate').value; 		
	} 	
	if( null!=document.getElementById('creditGuaranteed') && loanType=='TC' && loanType!='WC' && loanType!='CC'){
		
		var findGurantAmt = parseFloat(document.getElementById('creditGuaranteed').value);	
		var amountSanctionedDate=document.getElementById('amountSanctionedDate').value;
		var parts2=amountSanctionedDate.split("/");
		var amountSanctionedDateFinal=new Date(parts2[2], parts2[1] - 1, parts2[0]);
		
		
		if(findGurantAmt !=0.0 && findGurantAmt >= 100000 && findGurantAmt <= 1000000 && ((validationChkDate - amountSanctionedDateFinal) <= 0 == true)){
			document.getElementById('existGreenFldUnitType_id').style.display='block';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='block';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none'; 
			 
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
			
			 
		 }else if(findGurantAmt !=0.0 && findGurantAmt > 1000000 && findGurantAmt <= 5000000 && ((validationChkDate - amountSanctionedDateFinal) <= 0 == true)){
			 document.getElementById('existGreenFldUnitType_id').style.display='block';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='block';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='block';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none';			 
			 
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
					 
		 }else if(findGurantAmt !=0.0 && findGurantAmt > 5000000 && findGurantAmt < 10000000  && ((validationChkDate - amountSanctionedDateFinal) <= 0 == true)){
			 document.getElementById('existGreenFldUnitType_id').style.display='block';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='block';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='block';
			 
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
			 
		 }else if(findGurantAmt !=0.0 && findGurantAmt >= 10000000 && findGurantAmt <= 20000000  && ((validationChkDate - amountSanctionedDateFinal) <= 0 == true)){
   //			 enableOtherFinancialDtl('TC');			 
			 document.getElementById('existGreenFldUnitType_id').style.display='none';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='none';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none';
			 
			 document.getElementById("financialOtherDtlLblId").style.display="block";
			 document.getElementById("promDirDefaltFlg_Y").style.display="block";
			 document.getElementById("promDirDefaltFlg_N").style.display="block";
		 
		 }else{
			 document.getElementById('existGreenFldUnitType_id').style.display='none';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='none';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none';	
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
			 
		}
	}
	
	var limitFundbasedSanctionedDateVal;
	var limitNonFundBasedSanctionedDateVal;	
	if((loanType=='WC'|| loanType=='CC') && (null!=document.getElementById('limitFundbasedSanctionedDate')) ){		
		limitFundbasedSanctionedDateVal = document.getElementById('limitFundbasedSanctionedDate').value; 		
	}
	if((loanType=='WC'|| loanType=='CC') && (null!=document.getElementById('limitNonFundBasedSanctionedDate'))){
		limitNonFundBasedSanctionedDateVal = document.getElementById('limitNonFundBasedSanctionedDate').value; 		
	}	
	if( loanType=="WC" && null!=document.getElementById('creditFundBased') && null!=document.getElementById('creditNonFundBased') && null!=document.getElementById('limitFundBasedSanctionedDate')
			&& null!=document.getElementById('limitNonFundBasedSanctionedDate')){
	    var findGurantAmt = parseFloat(document.getElementById('creditFundBased').value) + parseFloat(document.getElementById('creditNonFundBased').value);
		
		var limitFundbasedSanctionedDate=document.getElementById('limitFundBasedSanctionedDate').value;
		var parts3=limitFundbasedSanctionedDate.split("/");
		var limitFundbasedSanctionedDateFinal=new Date(parts3[2], parts3[1] - 1, parts3[0]);		
		
		var limitNonFundBasedSanctionedDate=document.getElementById('limitNonFundBasedSanctionedDate').value;
		var parts4=limitNonFundBasedSanctionedDate.split("/");
		var limitNonFundBasedSanctionedDateFinal=new Date(parts4[2], parts4[1] - 1, parts4[0]);
		
		/*alert("validationChkDate:"+validationChkDate+"\amountSanctionedDateFinal:"+amountSanctionedDateFinal);*/	
		//alert((validationChkDate - limitNonFundBasedSanctionedDateFinal) < 0 ? true : false);
		
		if(findGurantAmt !=0.0 && findGurantAmt >= 100000 && findGurantAmt <= 1000000 && ((validationChkDate - limitFundbasedSanctionedDateFinal) <= 0 == true && (validationChkDate - limitNonFundBasedSanctionedDateFinal) <= 0 == true)){
			// alert("in WC"); 
			document.getElementById('existGreenFldUnitType_id').style.display='block';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='block';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none'; 
			 
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
			 
		 }else if(findGurantAmt !=0.0 && findGurantAmt  > 1000000 && findGurantAmt  <= 5000000 && ((validationChkDate - limitFundbasedSanctionedDateFinal) <= 0 == true && (validationChkDate - limitNonFundBasedSanctionedDateFinal) <= 0 == true)){
			 document.getElementById('existGreenFldUnitType_id').style.display='block';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='block';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='block';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none';
			 
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
					 
		 }else if(findGurantAmt  !=0.0 && findGurantAmt  > 5000000 && findGurantAmt < 10000000  && ((validationChkDate - limitFundbasedSanctionedDateFinal) <= 0 == true && (validationChkDate - limitNonFundBasedSanctionedDateFinal) <=0 == true)){
			 document.getElementById('existGreenFldUnitType_id').style.display='block';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='block';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='block';
			 
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
		 }else if(findGurantAmt !=0.0 && findGurantAmt >= 10000000 && findGurantAmt <= 20000000 && ((validationChkDate - limitFundbasedSanctionedDateFinal) <= 0 == true && (validationChkDate - limitNonFundBasedSanctionedDateFinal) <=0 == true)){
			// enableOtherFinancialDtl('WC');	
			 document.getElementById('existGreenFldUnitType_id').style.display='none';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='none';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none';	
		
			 document.getElementById("financialOtherDtlLblId").style.display="block";
			 document.getElementById("promDirDefaltFlg_Y").style.display="block";
			 document.getElementById("promDirDefaltFlg_N").style.display="block";
		 }else{
			 document.getElementById('existGreenFldUnitType_id').style.display='none';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='none';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none';	
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
		}
		
	}
	
	if((loanType=='CC' || loanType=='BO') && (document.getElementById('creditGuaranteed')!=null && document.getElementById('amountSanctionedDate')!=null  && null!=document.getElementById('creditFundBased') && null!=document.getElementById('creditNonFundBased') && null!=document.getElementById('limitFundBasedSanctionedDate')
			&& null!=document.getElementById('limitNonFundBasedSanctionedDate'))){ 
		
	 var findGurantAmt = parseFloat(document.getElementById('creditGuaranteed').value) + parseFloat(document.getElementById('creditFundBased').value) + parseFloat(document.getElementById('creditNonFundBased').value);
			
		var amountSanctionedDate=document.getElementById('amountSanctionedDate').value;
		var parts2=amountSanctionedDate.split("/");
		var amountSanctionedDateFinal=new Date(parts2[2], parts2[1] - 1, parts2[0]);		
		
		var limitFundbasedSanctionedDate=document.getElementById('limitFundBasedSanctionedDate').value;
		var parts3=limitFundbasedSanctionedDate.split("/");
		var limitFundbasedSanctionedDateFinal=new Date(parts3[2], parts3[1] - 1, parts3[0]);		
		/*alert("validationChkDate:"+validationChkDate+"\amountSanctionedDateFinal:"+amountSanctionedDateFinal);*/	
		//alert((validationChkDate - limitFundbasedSanctionedDateFinal) < 0 ? true : false);		
				
		
		var limitNonFundBasedSanctionedDate=document.getElementById('limitNonFundBasedSanctionedDate').value;
		var parts4=limitNonFundBasedSanctionedDate.split("/");
		var limitNonFundBasedSanctionedDateFinal=new Date(parts4[2], parts4[1] - 1, parts4[0]);
		
		/*alert("validationChkDate:"+validationChkDate+"\amountSanctionedDateFinal:"+amountSanctionedDateFinal);*/	
		//alert((validationChkDate - limitNonFundBasedSanctionedDateFinal) < 0 ? true : false);
		if(findGurantAmt !=0.0 && findGurantAmt >= 100000 && findGurantAmt <= 1000000 && ((validationChkDate - amountSanctionedDateFinal) <= 0 == true &&(validationChkDate - limitFundbasedSanctionedDateFinal) < 0 == true && (validationChkDate - limitNonFundBasedSanctionedDateFinal) < 0 == true)){
			document.getElementById('existGreenFldUnitType_id').style.display='block';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='block';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none'; 
			 
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
			 
		 }else if(findGurantAmt !=0.0 && findGurantAmt > 1000000 && findGurantAmt <= 5000000 && ((validationChkDate - amountSanctionedDateFinal) <= 0 == true &&(validationChkDate - limitFundbasedSanctionedDateFinal) < 0 == true && (validationChkDate - limitNonFundBasedSanctionedDateFinal) < 0 == true)){
			 document.getElementById('existGreenFldUnitType_id').style.display='block';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='block';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='block';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none';
			 
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
					 
		 }else if(findGurantAmt !=0.0 && findGurantAmt > 5000000 && findGurantAmt < 10000000  && ((validationChkDate - amountSanctionedDateFinal) <= 0 == true &&(validationChkDate - limitFundbasedSanctionedDateFinal) < 0 == true && (validationChkDate - limitNonFundBasedSanctionedDateFinal) < 0 == true)){
			 document.getElementById('existGreenFldUnitType_id').style.display='block';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='block';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='block';
			 
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
		 }else if(findGurantAmt !=0.0 && findGurantAmt >= 10000000 && findGurantAmt <= 20000000   && ((validationChkDate - amountSanctionedDateFinal) <= 0 == true &&(validationChkDate - limitFundbasedSanctionedDateFinal) < 0 == true && (validationChkDate - limitNonFundBasedSanctionedDateFinal) < 0 == true)){
			// enableOtherFinancialDtl('CC');	
			 document.getElementById('existGreenFldUnitType_id').style.display='none';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='none';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none';	
		
			 document.getElementById("financialOtherDtlLblId").style.display="block";
			 document.getElementById("promDirDefaltFlg_Y").style.display="block";
			 document.getElementById("promDirDefaltFlg_N").style.display="block";
		 }else{
			 document.getElementById('existGreenFldUnitType_id').style.display='none';
			 document.getElementById('existGreenUnitUI_1to10L').style.display='none';
			 document.getElementById('existGreenUnitUI_10to50L').style.display='none';
			 document.getElementById('existGreenUnitUI_50to100L').style.display='none';	
			 
			 document.getElementById("financialOtherDtlLblId").style.display="none";
			 document.getElementById("promDirDefaltFlg_Y").style.display="none";
			 document.getElementById("promDirDefaltFlg_N").style.display="none";
		}
	  }
	/*
	  if(findGurantAmt ==0.0 || findGurantAmt == null && findGurantAmt < 10000000){		
	 	    document.getElementById("financialOtherDtlLblId").style.display="none"; 
    }*/
	return;
}
/* - -------DKR------END------- -  */
function disable(objName)
{
	var obj=findObj(objName);
	obj.disabled = true;
	if(obj.options[0])
	{
		obj.options[0].selected=true;
	}
}

function preloadImages() 
{

  var d=document; 
  
  if(d.images)
  { 
  	if(!d.MM_p)
  	d.MM_p=new Array();
    
    	var i,j=d.MM_p.length,
    	
    	a=preloadImages.arguments;
    	
    	for(i=0; i<a.length; i++)
    	{
    		if (a[i].indexOf("#")!=0)
    		{ 
    			d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];
    		}
    	}
  }
}

function swapImage() 
{
  	var i,j=0,x,a=swapImage.arguments; 
  	document.MM_sr=new Array; 
  	
  	for(i=0;i<(a.length-2);i+=3)
  	{
   		if ((x=findObj(a[i]))!=null)
   		{
   			document.MM_sr[j++]=x; 
   				
   				if(!x.oSrc)
   				x.oSrc=x.src;
   				
   				x.src=a[i+2];
   		}
   	}
}
function swapImgRestore()
{
	  var i,x,a=document.MM_sr; 
	  
	  for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++)
	  {
		x.src=x.oSrc;
	  }
}

function setMenuOptions(menuOption,contextPath)
{
	
	var mainMenu=findObj("MainMenu");
	//alert(menuOption);
	var path=new String(contextPath);
	//alert(path);
	selection=new String(menuOption);
	mainMenuItem="";
	subMenuItem="";

	//alert("perform Action for selection "+selection);
	performAction(path+"/"+homeAction+"&menuIcon="+selection+"&mainMenu="+mainMenuItem+"&subMenu="+subMenuItem);
	disable("SubMenu");
}
function setSubMenuOptions()
{
	//alert("okkkkkCGTSI");
	
	var subMenu=findObj("SubMenu");
	var selObj=setSubMenuOptions.arguments[0];
	
	if(selObj)
	{
		//alert("Index "+selObj.selectedIndex);
		
		//alert(selObj.options[1].text);
		//alert(selObj.options[selObj.selectedIndex].text);
		//selObj.options[
		//alert(" action is "+setSubMenuOptions.arguments[1]+"/"+subHomeAction+"&menuIcon="+selection+"&mainMenu="+selObj.options[selObj.selectedIndex].text);
		mainMenuItem=selObj.options[selObj.selectedIndex].text;
		subMenuItem="";
		
		if(mainMenuItem=="Select")
		{
			performAction(setSubMenuOptions.arguments[1]+"/"+homeAction+"&menuIcon="+selection+"&mainMenu="+mainMenuItem+"&subMenu="+subMenuItem);
			document.forms[0].SubMenu.selectedIndex=0;
			subMenu.disabled=true;
		}
		else
		{
			//alert("selection and main menu while setting sub menus..."+selection+", "+mainMenuItem);
			//performAction(setSubMenuOptions.arguments[1]+"/"+subHomeAction+"&menuIcon="+selection+"&mainMenu="+mainMenuItem+"&subMenu="+subMenuItem);
			var actionValue="";
			//alert("Action avl is "+selObj.options[selObj.selectedIndex].value+" text "+selObj.options[selObj.selectedIndex].text);
			
			if(selObj.options[selObj.selectedIndex].value && selObj.options[selObj.selectedIndex].value!="undefined")
			{
				var indexValue=new String(selObj.options[selObj.selectedIndex].value);
				var indexIs=indexValue.indexOf("?",0);

				if(indexIs!=-1)
				{
					actionValue=indexValue+"&";
				}
				else
				{
					actionValue=selObj.options[selObj.selectedIndex].value+"?";
				}
				//alert ("actionValue" +actionValue);
				//mainMenuItem=selObj.options[selObj.selectedIndex].text;
			}
			else
			{
				actionValue=subHomeAction+"&";
			}
			
			//alert("Action Value is "+actionValue);
			performAction(setSubMenuOptions.arguments[1]+"/"+actionValue+"menuIcon="+selection+"&mainMenu="+mainMenuItem+"&subMenu="+subMenuItem);
		}
	}
}

function performAction(strAction)
{
	//alert("action "+strAction);
	content.document.forms[0].target = "_self";
	content.document.forms[0].method="POST";
	content.document.forms[0].action= strAction;
	content.document.forms[0].submit();
	//alert("After submission");
	
	if(document.getElementById('naviBar'))
	{
		var naviBar=document.getElementById('naviBar');
		//var mainMenuStr=new String(mainMenuItem);
		setNaviBar(naviBar);
	}

}

function callFunction()
{
     selection = "";
     mainMenuItem = "";
     subMenuItem = "";
}

function setNaviBar(naviBar)
{
	links="";
	if(selection)
	{
		if(mainMenuItem && mainMenuItem!="Select")
		{
			links+="<a href=javascript:load('"+homeAction+"&menuIcon="+selection+"',0)>"+selection+" </a>";
		}
		else
		{
			links+=selection;
		}
	}
	if(mainMenuItem && mainMenuItem!="Select")
	{
		if(subMenuItem && subMenuItem!="Select")
		{
			var split=new String(mainMenuItem);
			var array=split.split(" ");
			var newStr="";
			
			//alert("length is "+array.length);
			if(array.length>1)
			{
				for(i=0;i<array.length;i++)
				{
					newStr+=array[i];
					
					if(i!=array.length-1)
					{
						newStr+="%20";
					}
				}
				split=newStr;
				//alert("entered"+split);
				//split="Add"+"%20"+"Role"
				
			}
			links+="&gt;&gt;"+"<a href=javascript:load('"+subHomeAction+"&menuIcon="+selection+"&mainMenu="+split+"',1)>"+mainMenuItem+" </a> ";
		}
		else
		{
			links+="&gt;&gt;"+mainMenuItem;
		}
	}

	if(subMenuItem && subMenuItem!="Select")
	{
		links+="&gt;&gt;"+subMenuItem;
	}
	//alert("link is "+links);
	if(links)
	{
		naviBar.innerHTML=links; 
	}
	else
	{
		naviBar.innerHTML="&nbsp;";
	}

}
function doActionForSelection(selectedObj, contextPath)
{
	//alert("selected index "+selectedObj.selectedIndex);
	//alert("selected value "+selectedObj.options[selectedObj.selectedIndex].value);
	subMenuItem=selectedObj.options[selectedObj.selectedIndex].text;
	
	var params=new String("?");
	
	if(subMenuItem)
	{
		if(subMenuItem=="Select")
		{
			params=subHomeAction+"&";
		}
		
		else if( selectedObj.options[selectedObj.selectedIndex].value)
		{
			//alert ("value" +selectedObj.options[selectedObj.selectedIndex].value);
			var indexValue=new String(selectedObj.options[selectedObj.selectedIndex].value);
			var indexIs=indexValue.indexOf("?",0);
			
			if(indexIs!=-1)
			{
				params=indexValue+"&";
			}
			else
			{
				params=indexValue+"?";
			}
		}
	}
	//alert ("params" +params);
	params+="menuIcon="+selection+"&mainMenu="+mainMenuItem+"&subMenu="+subMenuItem;
	//alert ("params" +params);
	performAction(new String(contextPath)+"/"+params);
}

function load(action,type)
{
	//alert("action is ");
	document.forms[0].SubMenu.selectedIndex=0;
	
	var naviBar=document.getElementById('naviBar');	
	
	if(type==0)
	{
		document.forms[0].MainMenu.selectedIndex=0;	
		disable("SubMenu");
		
		mainMenuItem="";
		subMenuItem="";
		setNaviBar(naviBar);
	}
	else
	{
		subMenuItem="";
		setNaviBar(naviBar);
	
	}
	
	content.document.forms[0].target = "_self";
	content.document.forms[0].action= action;
	content.document.forms[0].method="POST";
	content.document.forms[0].submit();
}
function setMainMenu(mainMenus)
{
	var mainMenu=top.document.Main.MainMenu;
	//alert("mainMenu.length "+mainMenu.length);
	mainMenu.disabled=false;
	//mainMenu.length = 5;
	mainMenu.length=mainMenus.length;
	//var naviBar=top.document.getElementById('naviBar');
	//alert("mainMenuItem is "+mainMenuItem);
	//setNaviBar(naviBar);
	
	//alert("main menu item "+mainMenuItem);	
	
	for(i=0; i< mainMenus.length;i++)
	{
		mainMenu.options[i].text=mainMenus[i];
		mainMenu.options[i].value=mainMenuValues[i];

		if(mainMenu.options[i].text==mainMenuItem)
		{
			mainMenu.options[i].selected=true;
		}
	}
	//alert("mainMenuItem "+mainMenuItem);
	if(mainMenu.length>=1 && !mainMenuItem)
	{
		//alert("inside ");
		mainMenu.options[0].selected=true;
		var naviBar=top.document.getElementById('naviBar');
		// setNaviBar(naviBar);
	}
	var subMenu=top.document.Main.SubMenu;
	if(subMenu)
	{
		if(subMenu.length>1)
		{
			subMenu.disabled=true;
			subMenu.selectedIndex=0;
			var naviBar=top.document.getElementById('naviBar');
			mainMenuItem='';
			subMenuItem='';
			// setNaviBar(naviBar);
		}
	}
}

function setSubMenu (subMenus)
{
	//var mainMenu=top.document.Main.MainMenu;
	//mainMenu.disabled=false;
	
	//alert("before "+top.document.Main);
	
	var subMenu;
	if(top.document.Main)
	{
		subMenu=top.document.Main.SubMenu;
	}
	
	if(subMenu && subMenu!=null)
	{
		subMenu.length=0;
		setMainMenu(mainMenus);
		//alert("setting sub menu options "+mainMenuItem);
		subMenu.disabled=false;

		subMenu.length=subMenus.length;


		var naviBar=top.document.getElementById('naviBar');
		/*
		var mainMenuCombo=top.document.Main.MainMenu;

		alert("Main menu length is "+mainMenus.length);

		for(i=0;i<mainMenus.length;i++)
		{
			alert("Item "+i+"is "+mainMenus.options[i].text);
			if(mainMenus.options[i].text==mainMenuItem)
			{
				mainMenus.options[i].selected=true;
			}
		}
		//mainMenuSelected.selectedValue=mainMenuItem;
		*/
		// setNaviBar(naviBar);

		//alert("sub menu length "+subMenu.length);
		//alert("subMenuItem is setting sub menu is "+subMenuItem);
		for(i=0; i< subMenus.length;i++)
		{
			subMenu.options[i].text=subMenus[i];
			subMenu.options[i].value=subMenuValues[i];

			if(i==0)
			{
				subMenu.options[i].selected=true;
			}

			if(subMenu.options[i].text==subMenuItem)
			{
				subMenu.options[i].selected=true;
			}
		}
		/*
		if(subMenus.length>1)
		{

			subMenu.options[0].selected=true;
		}
		*/
		if(subMenus.length==1)
		{
			subMenu.disabled=true;
		}

		if(subMenus.length==0)
		{
			subMenu.length=1;
			subMenu.disabled=true;
			subMenu.options[0].text='Select';
			subMenu.options[0].value=subHomeAction+"&menuIcon="+selection+"&mainMenu="+mainMenuItem+"&subMenu="+subMenuItem;	
			subMenu.options[0].selected=true;	
		}
	}
}

var dobleClickdiable=true;
function submitForm1(action){	
	//alert("okk");
	var dcHandlooms = findObj("dcHandlooms");
    var WvCreditScheme = findObj("WeaverCreditScheme");
	if(document.forms[0].agree.disabled==false)	{
	        //  alert("hello");
		  if(!document.forms[0].agree.checked)
		   {			
			  alert("Pls accept Terms & conditions");
		   }
		  else{
			  if(dobleClickdiable){
				  document.forms[0].action=action;
				  document.forms[0].target="_self";
				  document.forms[0].method="POST";
				  document.forms[0].submit();
				  dobleClickdiable=false;
				 // alert("hello");
			  }
			  //document.getElementById("submitbuttionlog").disabled=true;
		  //	$('#submitbuttionlog').val(action).prop("disabled", true);
		}
	}
	
	else if(dcHandlooms!=null && dcHandlooms!="")
     {
	if((!document.forms[0].handloomchk.checked)&&((document.forms[0].dcHandlooms[0].checked))){	
		alert("Pls accept Terms & conditions under DC(HL) section of application form");		
		}else{
		if(dobleClickdiable){
		document.forms[0].action=action;
		document.forms[0].target="_self";
		document.forms[0].method="POST";
		document.forms[0].submit();
		dobleClickdiable=false;
		}
		}	   
     }else{
    	 if(dobleClickdiable){
		document.forms[0].action=action;
		document.forms[0].target="_self";
		document.forms[0].method="POST";
		document.forms[0].submit();	
		dobleClickdiable=false;
    	 }
	}	
	
}

function submitForm(action)
{	
	if(action=="modifyBorrowerDetails.do?method=modifyBorrowerDetails")
	{
		//alert("in if loop");
		document.getElementById("loading").style.display = "inline";	
	}	
	document.forms[0].action=action;
	document.forms[0].target="_self";
	document.forms[0].method="POST";	
	document.forms[0].submit();
	
	
}
function getInbox(action)
{
	document.forms[1].action=action;
	document.forms[1].target="_self";
	document.forms[1].method="POST";
	document.forms[1].submit();
}

function setHome(contextPath)
{
	selection="";
	mainMenuItem="";
	subMenuItem="";
	performAction(contextPath+"/showCGTSIHome.do?method=showCGTSIHome");
}

function isValidNumber(field)
{
	if(!isValid(field.value))
	{
		field.focus();
		field.value='';
		return false;
	}
}

function isValid(thestring)
{
//alert(thestring)
	if(thestring && thestring.length)
	{
		//alert("inner");
		for (i = 0; i < thestring.length; i++) {
			ch = thestring.substring(i, i+1);
			if (ch < "0" || ch > "9")
			  {
			  //alert("The numbers may contain digits 0 thru 9 only!");
			  return false;
			  }
		}
	}
	else
	{
		return false;
	}
    return true;
}

function numbersOnly(myfield, e)
{
	var key;
	var keychar;

	if (window.event)
	   key = window.event.keyCode;
	else if (e)
	   key = e.which;
	else
	   return true;
	keychar = String.fromCharCode(key);

	// control keys
	if ((key==null) || (key==0) || (key==8) ||
	    (key==9) || (key==13) || (key==27) )
	   return true;

	// numbers
	else if ((("0123456789").indexOf(keychar) > -1))
	   return true;
	else
	   return false;
}

function decimalOnly(myfield, e, maxIntegers)
{
	var key;
	var keychar;

	if (window.event)
	   key = window.event.keyCode;
	else if (e)
	   key = e.which;
	else
	   return true;
	keychar = String.fromCharCode(key);

	// control keys
	if ((key==null) || (key==0) || (key==8) ||
	    (key==9) || (key==13) || (key==27) )
	   return true;

	// numbers
	else if ((("0123456789.").indexOf(keychar) > -1))
	{
		if(myfield.value.indexOf('.') > -1 && (".").indexOf(keychar) > -1)
		{
			
			return false;
		}
		var index=myfield.value.indexOf('.');
		
		var val=myfield.value.toString();
		
		if(index > -1)
		{
			var str=val.substring(index,val.length);
			
			if(str.length>2)
			{
				return false;
			}
			
			return true;
			//alert("index, str "+index+" "+str);
		}
		
		//alert("length is "+val.length+" "+(keychar!='.'));
		
		
		if(val.length>(maxIntegers-1) && keychar!='.')
		{
			return false;
		}
		
		return true;
		
	}	
	   
	else
	{
	   return false;
	}
}

function isValidDecimal(field)
{
	if(!isDecimalValid(field.value))
	{
		field.focus();
		field.value='';
		return false;
	}
}

function isDecimalValid(thestring)
{
//alert(thestring)
	if(thestring && thestring.length)
	{
		//alert("inner");
		for (i = 0; i < thestring.length; i++) {
			ch = thestring.substring(i, i+1);
			if ((ch < "0" || ch > "9")  && ch!=".")
			  {
			  //alert("The numbers may contain digits 0 thru 9 only!");
			  return false;
			  }
		}
	}
	else
	{
		return false;
	}
    return true;
}


/**************************************** Inward Outward ************************************/

function setSourceId(obj1) 
{
	var obj=obj1.options[obj1.selectedIndex].value;
	var objName=findObj("sourceId");
	var objValue=findObj("sourceType");
	
	if(objValue.value == "")
	{
		objName.value = "";
	}
	else
	{
		var str = "001";
		var d= new Date();
		var str1=d.getDate();		
		if (str1 < 10 ) str1 = "0" + str1.toString(); 
		var str2=d.getMonth()+1;
		if(str2<10)
		{
			str2 = "0" + str2;
		}
		var str3=d.getYear().toString().substring(2,4);
		objName.value= obj.concat("/").concat(str1).concat(str2).concat(str3).concat("/").concat("001");
	}

}


function setDestId(obj1) 
{
	var obj=obj1.options[obj1.selectedIndex].value;
	var objName=findObj("referenceId");
	var objValue=findObj("destinationType");
	if(objValue.value == "")
	{
		objName.value = "";
	}
	else
	{
		var str = "001";
		var d= new Date();
		var str1=d.getDate();
		if (str1 < 10 ) str1 = "0" + str1.toString(); 
		var str2=d.getMonth()+1;
		if(str2<10)
		{
			str2 = "0" + str2;   
		}
		var str3=d.getYear().toString().substring(2,4);
		objName.value= obj.concat("/").concat(str1).concat(str2).concat(str3).concat("/").concat("001");
	}
}


function printpage() {
window.print();  
}


function selectDeselect(field,counter)
{
	
	//alert("length "+document.forms[0].elements.length);
	
	//alert("0 "+document.forms[0].elements[0].value);
	
	//alert("3 "+document.forms[0].elements[3].value);
	
	var start=3;
	if(counter)
	{
		start=counter;
	}
	//alert("counter "+counter);
	
	if(field.checked==true)
	{
		for(i=start;i<document.forms[0].elements.length;i++)
		{

			document.forms[0].elements[i].checked=true;
		}
	}
	if(field.checked==false)
	{
		for(i=start;i<document.forms[0].elements.length;i++)
		{

			document.forms[0].elements[i].checked=false;
		}
	}	 
}


function selectAllItems(selectAll)
{
	
	if(selectAll.checked==true)
	{
		for(i=0;i<document.forms[0].elements.length;i++)
		{

			document.forms[0].elements[i].checked=true;
		}
	}
	if(selectAll.checked==false)
	{
		for(i=0;i<document.forms[0].elements.length;i++)
		{

			document.forms[0].elements[i].checked=false;
		}
	}	 
}



function setToDefault()
{
	//alert("Entered");
	top.document.Main.MainMenu.selectedIndex=0;
	top.document.Main.SubMenu.selectedIndex=0;
	top.document.Main.SubMenu.disabled=true;
	top.document.Main.MainMenu.disabled=true;
	
}

function setRZonesEnabled()
{
	var value0=document.forms[0].setZoRo[0].checked;
	var value1=document.forms[0].setZoRo[1].checked;
	var value2=document.forms[0].setZoRo[2].checked;
	var value3=document.forms[0].setZoRo[3].checked;

	//new zone
	if(value0==true)
	{
		document.forms[0].reportingZone.disabled=true;
		document.forms[0].reportingZone.selectedIndex=0;
		document.forms[0].branchName.disabled=true;
		document.forms[0].branchName.value="";
		document.forms[0].zoneName.disabled=false;
		document.forms[0].zoneList.disabled=true;
		document.forms[0].zoneList.selectedIndex=0;		
	}

   //new region
	if(value1==true)
	{
		document.forms[0].reportingZone.disabled=false;
		document.forms[0].branchName.disabled=true;
		document.forms[0].branchName.value="";
		document.forms[0].zoneName.disabled=false;	
		document.forms[0].zoneList.disabled=true;
		document.forms[0].zoneList.selectedIndex=0;	
	}

	//new branch
	if(value2==true)
	{
		document.forms[0].reportingZone.disabled=true;	
		document.forms[0].reportingZone.selectedIndex=0;	
		document.forms[0].branchName.disabled=false;
		document.forms[0].zoneName.disabled=true;	
		document.forms[0].zoneName.value="";
		document.forms[0].zoneList.disabled=false;		
	}

	//Branch reporting to bank
	if(value3==true)
	{
		document.forms[0].reportingZone.disabled=true;
		document.forms[0].reportingZone.selectedIndex=0;
		document.forms[0].zoneName.disabled=true;
		document.forms[0].zoneName.value="";	
		document.forms[0].branchName.disabled=false;
		document.forms[0].zoneList.disabled=true;
		document.forms[0].zoneList.selectedIndex=0;	
	}
	
}
/********************************************************************************************/

/**************************************** Receipts and Payments *****************************/
function setUncheckedValue(count, name, targetURL, value) {
	var objName;
	for(i = 0; i < count; ++i) {
		objName =findObj(name+"(key-"+i+")");
		if (objName.checked==false)	{
			objName.value = value;
		}
	}
	submitForm(targetURL);
}

function setHiddenFieldValue(name, value, targetURL)
{
	var objName;
	objName =findObj(name) ;
	objName.value = value ;
	submitForm(targetURL);
}

function setChkHiddenValue(count, checkboxName, hiddenFieldName, targetURL, checkedValue, uncheckedValue)
{
//	alert(count);
	var objCheckbox;
	var objHidden ;
/*	for(i = 0; i < count; ++i) {
		objCheckbox =findObj(checkboxName+""+i);
		objHidden = findObj(hiddenFieldName+"(key-"+i+")");
		if (objCheckbox.checked==true)	{
			objHidden.value = checkedValue;
		} else {
			objHidden.value = uncheckedValue;
		}
	}*/
	submitForm(targetURL);
}


function calculateTotalAppropriated(checkboxName, amount)
{	
/*	var appropriated=document.getElementById('appropriatedAmount');
	double newAppropriatedAmount ;

	if(checkboxName.checked==true) {
		newAppropriatedAmount = appropriatedAmount + amount ;
		appropriated.innerHTML = newAppropriatedAmount ;
	} else {
		newAppropriatedAmount = appropriatedAmount - amount ;
		appropriated.innerHTML = newAppropriatedAmount ;
	}

	alert(newAppropriatedAmount) ;

	appropriatedAmount = newAppropriatedAmount ;
*/
}

function setTotalAppropriated(amount)
{
	var appropriated=document.getElementById('appropriatedAmount');
	appropriated.innerHTML = amount ;
}
/********************************************************************************************/

/***********************************Application Processing*********************************/
function setConstEnabled()
{	
	var obj=findObj("constitution");
	var objOther=findObj("constitutionOther");
	if(objOther!=null && objOther!="")
	{
		if((obj.options[obj.selectedIndex].value)=="proprietary")
		{
			document.forms[0].firstName.disabled=true;
			document.forms[0].firstName.value="";

			document.forms[0].firstItpan.disabled=true;
			document.forms[0].firstItpan.value="";

			document.forms[0].firstDOB.disabled=true;
			document.forms[0].firstDOB.value="";

			document.forms[0].secondName.disabled=true;
			document.forms[0].secondName.value="";

			document.forms[0].secondItpan.disabled=true;
			document.forms[0].secondItpan.value="";

			document.forms[0].secondDOB.disabled=true;
			document.forms[0].secondDOB.value="";

			document.forms[0].thirdName.disabled=true;
			document.forms[0].thirdName.value="";

			document.forms[0].thirdItpan.disabled=true;
			document.forms[0].thirdItpan.value="";

			document.forms[0].thirdDOB.disabled=true;
			document.forms[0].thirdDOB.value="";

			document.forms[0].constitutionOther.disabled=true;
			document.forms[0].constitutionOther.value="";

		}
		else if ((obj.options[obj.selectedIndex].value)=="Others")
		{
			document.forms[0].constitutionOther.disabled=false;

			document.forms[0].firstName.disabled=false;
			document.forms[0].firstItpan.disabled=false;
			document.forms[0].firstDOB.disabled=false;

			document.forms[0].secondName.disabled=false;
			document.forms[0].secondItpan.disabled=false;
			document.forms[0].secondDOB.disabled=false;

			document.forms[0].thirdName.disabled=false;
			document.forms[0].thirdItpan.disabled=false;
			document.forms[0].thirdDOB.disabled=false;

		}else
		{
			document.forms[0].constitutionOther.disabled=true;
			document.forms[0].constitutionOther.value="";

			document.forms[0].firstName.disabled=false;
			document.forms[0].firstItpan.disabled=false;
			document.forms[0].firstDOB.disabled=false;

			document.forms[0].secondName.disabled=false;
			document.forms[0].secondItpan.disabled=false;
			document.forms[0].secondDOB.disabled=false;

			document.forms[0].thirdName.disabled=false;
			document.forms[0].thirdItpan.disabled=false;
			document.forms[0].thirdDOB.disabled=false;


		}
	}
	
}

/*function setConstDisabled()
{
	var value=document.forms[0].constitution.checked;
	document.forms[0].constitutionOther.disabled=true;

}*


function enableUnitValue()
{
	var value=document.forms[0].none.checked;

	if (value==true)
	{
		document.forms[0].unitValue.disabled=true;
	}
	else
	{
		document.forms[0].unitValue.disabled=false;
	}
}

function disableUnitValue()
{
	var value=document.forms[0].none.checked;
	document.forms[0].unitValue.disabled=true;

}*/

function enableNone()
{	
	var obj=findObj("unitValue");	
	if(obj!=null && obj!="")
	{
		if (document.forms[0].previouslyCovered[1].checked && !booleanVal)
		{

		document.forms[0].none[0].checked=true;
		document.forms[0].none[0].disabled=true;
		document.forms[0].none[1].disabled=true;
		document.forms[0].none[2].disabled=true;
		document.forms[0].unitValue.disabled=true;
		document.forms[0].unitValue.value="";
		
/*			if(document.forms[0].osAmt!=null && document.forms[0].osAmt!="")
			{
			document.forms[0].osAmt.disabled=false;
			}
*/			

			if(document.forms[0].constitution!=null && document.forms[0].constitution!="")
			{
			document.forms[0].constitution.disabled=false;
			}
			
			if(document.forms[0].constitutionOther!=null && document.forms[0].constitutionOther!="")
			{
			document.forms[0].constitutionOther.disabled=false;
			}

			if(document.forms[0].ssiType!=null && document.forms[0].ssiType!="")
			{
			document.forms[0].ssiType.disabled=false;
			}

			if(document.forms[0].ssiName!=null && document.forms[0].ssiName!="")
			{
			document.forms[0].ssiName.disabled=false;
			}

			if(document.forms[0].regNo!=null && document.forms[0].regNo!="")
			{
			document.forms[0].regNo.disabled=false;
			}

			if(document.forms[0].ssiITPan!=null && document.forms[0].ssiITPan!="")
			{
			document.forms[0].ssiITPan.disabled=false;
			}

			if(document.forms[0].industryNature!=null && document.forms[0].industryNature!="")
			{
			document.forms[0].industryNature.disabled=false;
			}

			if(document.forms[0].industrySector!=null && document.forms[0].industrySector!="")
			{
			document.forms[0].industrySector.disabled=false;
			}

			if(document.forms[0].activityType!=null && document.forms[0].activityType!="")
			{
			document.forms[0].activityType.disabled=false;
			}

			if(document.forms[0].employeeNos!=null && document.forms[0].employeeNos!="")
			{
			document.forms[0].employeeNos.disabled=false;
			}

			if(document.forms[0].projectedSalesTurnover!=null && document.forms[0].projectedSalesTurnover!="")
			{
			document.forms[0].projectedSalesTurnover.disabled=false;
			}

			if(document.forms[0].projectedExports!=null && document.forms[0].projectedExports!="")
			{
			document.forms[0].projectedExports.disabled=false;
			}

			if(document.forms[0].address!=null && document.forms[0].address!="")
			{
			document.forms[0].address.disabled=false;
			}

			if(document.forms[0].state!=null && document.forms[0].state!="")
			{
			document.forms[0].state.disabled=false;
			}

			if(document.forms[0].district!=null && document.forms[0].district!="")
			{
 			document.forms[0].district.disabled=false;
 			}

			if(document.forms[0].districtOthers!=null && document.forms[0].districtOthers!="")
			{
			document.forms[0].districtOthers.disabled=false; 																																				
			}

			if(document.forms[0].city!=null && document.forms[0].city!="")
			{
			document.forms[0].city.disabled=false; 																																				
			}

			if(document.forms[0].pincode!=null && document.forms[0].pincode!="")
			{
			document.forms[0].pincode.disabled=false; 																																				
			}

			if(document.forms[0].cpTitle!=null && document.forms[0].cpTitle!="")
			{
			document.forms[0].cpTitle.disabled=false; 																																				
			}
			
			if(document.forms[0].cpFirstName!=null && document.forms[0].cpFirstName!="")
			{
			document.forms[0].cpFirstName.disabled=false; 																																				
			}

			if(document.forms[0].cpMiddleName!=null && document.forms[0].cpMiddleName!="")
			{
			document.forms[0].cpMiddleName.disabled=false; 																																				
			}

			if(document.forms[0].cpLastName!=null && document.forms[0].cpLastName!="")
			{
			document.forms[0].cpLastName.disabled=false; 																																				
			}

			if(document.forms[0].cpGender!=null && document.forms[0].cpGender!="")
			{
			document.forms[0].cpGender.disabled=false; 																																				
			}

			if(document.forms[0].cpITPAN!=null && document.forms[0].cpITPAN!="")
			{
			document.forms[0].cpITPAN.disabled=false; 																																				
			}

			if(document.forms[0].cpDOB!=null && document.forms[0].cpDOB!="")
			{
			document.forms[0].cpDOB.disabled=false; 																																				
			}

			if(document.forms[0].socialCategory!=null && document.forms[0].socialCategory!="")
			{
			document.forms[0].socialCategory.disabled=false; 																																				
			}

			if(document.forms[0].cpLegalID!=null && document.forms[0].cpLegalID!="")
			{
			document.forms[0].cpLegalID.disabled=false; 																																				
			}

			if(document.forms[0].otherCpLegalID!=null && document.forms[0].otherCpLegalID!="")
			{
			document.forms[0].otherCpLegalID.disabled=false; 																																				
			}

			if(document.forms[0].cpLegalIdValue!=null && document.forms[0].cpLegalIdValue!="")
			{
			document.forms[0].cpLegalIdValue.disabled=false; 																																				
			}

			if(document.forms[0].firstName!=null && document.forms[0].firstName!="")
			{
			document.forms[0].firstName.disabled=false; 																																				
			}

			if(document.forms[0].firstItpan!=null && document.forms[0].firstItpan!="")
			{
			document.forms[0].firstItpan.disabled=false; 																																				
			}

			if(document.forms[0].firstDOB!=null && document.forms[0].firstDOB!="")
			{
			document.forms[0].firstDOB.disabled=false; 																																				
			}

			if(document.forms[0].secondName!=null && document.forms[0].secondName!="")
			{
			document.forms[0].secondName.disabled=false; 																																				
			}

			if(document.forms[0].secondItpan!=null && document.forms[0].secondItpan!="")
			{
			document.forms[0].secondItpan.disabled=false; 																																																																														
			}

			if(document.forms[0].secondDOB!=null && document.forms[0].secondDOB!="")
			{
			document.forms[0].secondDOB.disabled=false; 																																																																																	
			}

			if(document.forms[0].thirdName!=null && document.forms[0].thirdName!="")
			{
			document.forms[0].thirdName.disabled=false; 																																																																														
			}

			if(document.forms[0].thirdItpan!=null && document.forms[0].thirdItpan!="")
			{
			document.forms[0].thirdItpan.disabled=false; 																																																																														
			}

			if(document.forms[0].thirdDOB!=null && document.forms[0].thirdDOB!="")
			{
			document.forms[0].thirdDOB.disabled=false;																																																																											
			}

		}else if (document.forms[0].previouslyCovered[0].checked && !booleanVal)
		{
			document.forms[0].none[0].checked=false;
			document.forms[0].none[0].disabled=true;
			document.forms[0].none[1].disabled=false;		
			document.forms[0].none[2].disabled=false;
			document.forms[0].unitValue.disabled=false;
			
			//disbaling all the borrower fields
			if(document.forms[0].constitution!=null && document.forms[0].constitution!="")
			{
			document.forms[0].constitution.disabled=true;
			}
			
			if(document.forms[0].osAmt!=null && document.forms[0].osAmt!="")
			{
			document.forms[0].osAmt.disabled=true;
			document.forms[0].osAmt.value="";
			}
			
			if(document.forms[0].constitutionOther!=null && document.forms[0].constitutionOther!="")
			{
			document.forms[0].constitutionOther.disabled=true;
			document.forms[0].constitutionOther.value="";
			}
			
			if(document.forms[0].ssiType!=null && document.forms[0].ssiType!="")
			{
			document.forms[0].ssiType.disabled=true;
			document.forms[0].ssiType.value="";
			}
			
			if(document.forms[0].ssiName!=null && document.forms[0].ssiName!="")
			{
			document.forms[0].ssiName.disabled=true;
			document.forms[0].ssiName.value="";
			}
			
			if(document.forms[0].regNo!=null && document.forms[0].regNo!="")
			{
			document.forms[0].regNo.disabled=true;
			document.forms[0].regNo.value="";
			}

			if(document.forms[0].ssiITPan!=null && document.forms[0].ssiITPan!="")
			{
			document.forms[0].ssiITPan.disabled=true;
			document.forms[0].ssiITPan.value="";
			}

			if(document.forms[0].industryNature!=null && document.forms[0].industryNature!="")
			{
			document.forms[0].industryNature.disabled=true;
			document.forms[0].industryNature.options.selectedIndex=0;			
			}
			
			if(document.forms[0].industrySector!=null && document.forms[0].industrySector!="")
			{
			document.forms[0].industrySector.disabled=true;
			document.forms[0].industrySector.options.selectedIndex=0;									
			}

			if(document.forms[0].activityType!=null && document.forms[0].activityType!="")
			{
			document.forms[0].activityType.disabled=true;
			document.forms[0].activityType.value="";
			}
			
			if(document.forms[0].employeeNos!=null && document.forms[0].employeeNos!="")
			{
			document.forms[0].employeeNos.disabled=true;
			document.forms[0].employeeNos.value="";
			}

			if(document.forms[0].projectedSalesTurnover!=null && document.forms[0].projectedSalesTurnover!="")
			{
			document.forms[0].projectedSalesTurnover.disabled=true;
			document.forms[0].projectedSalesTurnover.value="";
			}
			
			if(document.forms[0].projectedExports!=null && document.forms[0].projectedExports!="")
			{
			document.forms[0].projectedExports.disabled=true;
			document.forms[0].projectedExports.value="";
			}

			if(document.forms[0].address!=null && document.forms[0].address!="")
			{
			document.forms[0].address.disabled=true;
			document.forms[0].address.value ="";
			}
			
			if(document.forms[0].state!=null && document.forms[0].state!="")
			{	
			document.forms[0].state.disabled=true;
			document.forms[0].state.options.selectedIndex=0;									
			}
				
			if(document.forms[0].district!=null && document.forms[0].district!="")
			{						
 			document.forms[0].district.disabled=true; 			
			document.forms[0].district.options.selectedIndex=0;									 			
			}
 			
			if(document.forms[0].districtOthers!=null && document.forms[0].districtOthers!="")
			{						
			document.forms[0].districtOthers.disabled=true; 																																				
			document.forms[0].districtOthers.value=""; 																																				
			}
			
			if(document.forms[0].city!=null && document.forms[0].city!="")
			{						
			document.forms[0].city.disabled=true; 																																				
			document.forms[0].city.value="";
			}

			if(document.forms[0].pincode!=null && document.forms[0].pincode!="")
			{						
			document.forms[0].pincode.disabled=true; 																																				
			document.forms[0].pincode.value="";
			}
			 																																							
			if(document.forms[0].cpTitle!=null && document.forms[0].cpTitle!="")
			{						
			document.forms[0].cpTitle.disabled=true;
			document.forms[0].cpTitle.options.selectedIndex=0;									 			
			}
			
			if(document.forms[0].cpFirstName!=null && document.forms[0].cpFirstName!="")
			{						
			document.forms[0].cpFirstName.disabled=true; 																																				
			document.forms[0].cpFirstName.value=""; 																																				
			}
			
			if(document.forms[0].cpMiddleName!=null && document.forms[0].cpMiddleName!="")
			{						
			document.forms[0].cpMiddleName.disabled=true; 																																				
			document.forms[0].cpMiddleName.value=""; 																																							
			}
			
			if(document.forms[0].cpLastName!=null && document.forms[0].cpLastName!="")
			{						
			document.forms[0].cpLastName.disabled=true; 																																				
			document.forms[0].cpLastName.value=""; 																																							
			}
			
			if(document.forms[0].cpGender!=null && document.forms[0].cpGender!="")
			{						
			document.forms[0].cpGender.disabled=true; 																																				
			}
			
			if(document.forms[0].cpITPAN!=null && document.forms[0].cpITPAN!="")
			{						
			document.forms[0].cpITPAN.disabled=true; 																																				
			document.forms[0].cpITPAN.value=""; 																																							
			}
			
			if(document.forms[0].cpDOB!=null && document.forms[0].cpDOB!="")
			{						
			document.forms[0].cpDOB.disabled=true; 																																				
			document.forms[0].cpDOB.value=""; 																																							
			}
			
			if(document.forms[0].socialCategory!=null && document.forms[0].socialCategory!="")
			{						
			document.forms[0].socialCategory.disabled=true; 																																				
			document.forms[0].socialCategory.options.selectedIndex=0;									 						
			}

			if(document.forms[0].cpLegalID!=null && document.forms[0].cpLegalID!="")
			{						
			document.forms[0].cpLegalID.disabled=true; 																																				
			document.forms[0].cpLegalID.options.selectedIndex=0;									 						
			}
			
			if(document.forms[0].otherCpLegalID!=null && document.forms[0].otherCpLegalID!="")
			{						
			document.forms[0].otherCpLegalID.disabled=true; 																																				
			document.forms[0].otherCpLegalID.value=""; 																																							
			}
			
			if(document.forms[0].cpLegalIdValue!=null && document.forms[0].cpLegalIdValue!="")
			{						
			document.forms[0].cpLegalIdValue.disabled=true; 																																				
			document.forms[0].cpLegalIdValue.value=""; 																																							
			}
			
			if(document.forms[0].firstName!=null && document.forms[0].firstName!="")
			{				
			document.forms[0].firstName.disabled=true; 																																				
			document.forms[0].firstName.value=""; 																																							
			}
			
			if(document.forms[0].firstItpan!=null && document.forms[0].firstItpan!="")
			{				
			document.forms[0].firstItpan.disabled=true; 																																				
			document.forms[0].firstItpan.value=""; 																																							
			}
			
			if(document.forms[0].firstDOB!=null && document.forms[0].firstDOB!="")
			{				
			document.forms[0].firstDOB.disabled=true; 																																				
			document.forms[0].firstDOB.value=""; 																																							
			}
			
			if(document.forms[0].secondName!=null && document.forms[0].secondName!="")
			{				
			document.forms[0].secondName.disabled=true; 																																				
			document.forms[0].secondName.value=""; 																																							
			}
			
			if(document.forms[0].secondItpan!=null && document.forms[0].secondItpan!="")
			{				
			document.forms[0].secondItpan.disabled=true; 																																																																														
			document.forms[0].secondItpan.value=""; 																																																																																	
			}
			
			if(document.forms[0].secondDOB!=null && document.forms[0].secondDOB!="")
			{				
			document.forms[0].secondDOB.disabled=true; 																																																																																	
			document.forms[0].secondDOB.value=""; 																																																																																				
			}
			
			if(document.forms[0].thirdName!=null && document.forms[0].thirdName!="")
			{				
			document.forms[0].thirdName.disabled=true; 																																																																														
			document.forms[0].thirdName.value=""; 																																																																																	
			}
			
			if(document.forms[0].thirdItpan!=null && document.forms[0].thirdItpan!="")
			{				
			document.forms[0].thirdItpan.disabled=true; 																																																																														
			document.forms[0].thirdItpan.value=""; 																																																																																	
			}
			
			if(document.forms[0].thirdDOB!=null && document.forms[0].thirdDOB!="")
			{				
			document.forms[0].thirdDOB.disabled=true;																																																																											
			document.forms[0].thirdDOB.value="";																																																																														
			}
			

		}
		else if(booleanVal)
		{
			document.forms[0].none[0].checked=false;
			document.forms[0].none[0].disabled=true;
			document.forms[0].none[1].disabled=true;
			document.forms[0].none[2].disabled=true;
			document.forms[0].unitValue.disabled=true;
				
		}
	}	
}


function disableIdOther(field)
{
	var value=field.checked;
	
	//alert(field.checked);
	
	//alert(field.value);
	
	if(field.value=="none")
	{
		document.forms[0].idTypeOther.disabled=true;
	}
	else
	{
		document.forms[0].idTypeOther.disabled=false;
	}

}

function enableConstituitionOther(field)
{
	if(field[field.selectedIndex].value=="proprietary")
	{
		document.forms[0].firstName.disabled=true;
		document.forms[0].firstName.value="";

		document.forms[0].firstItpan.disabled=true;
		document.forms[0].firstItpan.value="";

		document.forms[0].firstDOB.disabled=true;
		document.forms[0].firstDOB.value="";

		document.forms[0].secondName.disabled=true;
		document.forms[0].secondName.value="";

		document.forms[0].secondItpan.disabled=true;
		document.forms[0].secondItpan.value="";

		document.forms[0].secondDOB.disabled=true;
		document.forms[0].secondDOB.value="";

		document.forms[0].thirdName.disabled=true;
		document.forms[0].thirdName.value="";

		document.forms[0].thirdItpan.disabled=true;
		document.forms[0].thirdItpan.value="";

		document.forms[0].thirdDOB.disabled=true;
		document.forms[0].thirdDOB.value="";

		document.forms[0].constitutionOther.disabled=true;
		document.forms[0].constitutionOther.value="";
	}
	else if(field[field.selectedIndex].value=="Others")
	{
		document.forms[0].constitutionOther.disabled=false;

			document.forms[0].firstName.disabled=false;
			document.forms[0].firstItpan.disabled=false;
			document.forms[0].firstDOB.disabled=false;

			document.forms[0].secondName.disabled=false;
			document.forms[0].secondItpan.disabled=false;
			document.forms[0].secondDOB.disabled=false;

			document.forms[0].thirdName.disabled=false;
			document.forms[0].thirdItpan.disabled=false;
			document.forms[0].thirdDOB.disabled=false;

	}
	else
	{
		document.forms[0].constitutionOther.disabled=true;
		document.forms[0].constitutionOther.value="";

			document.forms[0].firstName.disabled=false;
			document.forms[0].firstItpan.disabled=false;
			document.forms[0].firstDOB.disabled=false;

			document.forms[0].secondName.disabled=false;
			document.forms[0].secondItpan.disabled=false;
			document.forms[0].secondDOB.disabled=false;

			document.forms[0].thirdName.disabled=false;
			document.forms[0].thirdItpan.disabled=false;
			document.forms[0].thirdDOB.disabled=false;

	}
}

function enableDistrictOthers()
{
	//alert("District Other");
	var obj=findObj("district");
	var objOther=findObj("districtOthers");
	if(objOther!=null && objOther!="")
	{
		if ((obj.options[obj.selectedIndex].value)=="Others")
		{
			//alert("District Other enabled");
			document.forms[0].districtOthers.disabled=false;
		}
		else
		{
			//alert("District Other disabled");
			document.forms[0].districtOthers.disabled=true;
			document.forms[0].districtOthers.value="";
		}
	}
}

function enableOtherLegalId()
{
	var obj=findObj("cpLegalID");
	var objOther=findObj("otherCpLegalID");
	if(objOther!=null && objOther!="")
	{
		if ((obj.options[obj.selectedIndex].value)=="Others")
		{
			document.forms[0].otherCpLegalID.disabled=false;
		}
		else
		{
			document.forms[0].otherCpLegalID.disabled=true;
			document.forms[0].otherCpLegalID.value="";
		}
	}
}

function enableSubsidyName()
{
	var obj=findObj("subsidyName");
	var obj1=findObj("otherSubsidyEquityName");
	if(obj1!=null && obj1!="")
	{

		if ((obj.options[obj.selectedIndex].value)=="Others")
		{
			document.forms[0].otherSubsidyEquityName.disabled=false;
		}
		else
		{
			document.forms[0].otherSubsidyEquityName.disabled=true;
			document.forms[0].otherSubsidyEquityName.value="";

		}
	}
}

/*
* This method is to calculate the project Cost 
*/

function calProjectCost()
{	
	var projectCostValue=0;
	var tcSanctioned=findObj("termCreditSanctioned");	
	if(tcSanctioned!=null && tcSanctioned!="")
	{
	var tcSanctionedVal=tcSanctioned.value;
	}

	if (!(isNaN(tcSanctionedVal)) && tcSanctionedVal!="")
	{
		projectCostValue+=parseFloat(tcSanctionedVal);	
	}
	
	var promoterCont=findObj("tcPromoterContribution");
	if(promoterCont!=null && promoterCont!="")
	{	
		var promoterContValue=promoterCont.value;
	}
	
	if (!(isNaN(promoterContValue)) && promoterContValue!="")
	{		
		projectCostValue+=parseFloat(promoterContValue);
	}
	
	
	var tcSubsidy=findObj("tcSubsidyOrEquity");
	if(tcSubsidy!=null && tcSubsidy!="")
	{
	var tcSubsidyVal=tcSubsidy.value;
	}
	if (!(isNaN(tcSubsidyVal)) && tcSubsidyVal!="")
	{
		projectCostValue+=parseFloat(tcSubsidyVal);
	}
	
	var tcOther=findObj("tcOthers");
	if(tcOther!=null && tcOther!="")
	{
	var tcOtherVal=tcOther.value;
	}
	
	if (!(isNaN(tcOtherVal))&& tcOtherVal!="")
	{
		projectCostValue+=parseFloat(tcOtherVal);
	}	
	
	var projectCost=document.getElementById('projectCost');
	projectCost.innerHTML=projectCostValue; 

	var amtSanctioned=document.getElementById('amountSanctioned');
	if(amtSanctioned!=null && amtSanctioned!="")
	{
	amtSanctioned.innerHTML=tcSanctionedVal;
	}

}

/*
* This method calculates the working capital assessed and displays it on the screen
*
function calWcAssessed()
{
	var wcAssessedValue=0;
	var wcFundBasedSanctioned=findObj("wcFundBasedSanctioned");	
	var wcFundBasedSanctionedVal=wcFundBasedSanctioned.value;

	if (!(isNaN(wcFundBasedSanctionedVal)) && wcFundBasedSanctionedVal!="")
	{
		wcAssessedValue+=parseFloat(wcFundBasedSanctionedVal);	
	}

	var wcNonFundBasedSanctioned=findObj("wcNonFundBasedSanctioned");	
	var wcNonFundBasedSanctionedVal=wcNonFundBasedSanctioned.value;

	if (!(isNaN(wcNonFundBasedSanctionedVal)) && wcNonFundBasedSanctionedVal!="")
	{
		wcAssessedValue+=parseFloat(wcNonFundBasedSanctionedVal);	
	}
	
	var wcPromoterCont=findObj("wcPromoterContribution");
	
	var wcPromoterContVal=wcPromoterCont.value;
	
	if (!(isNaN(wcPromoterContVal)) && wcPromoterContVal!="")
	{		
		wcAssessedValue+=parseFloat(wcPromoterContVal);
	}
	
	
	var wcSubsidy=findObj("wcSubsidyOrEquity");
	var wcSubsidyVal=wcSubsidy.value;
	if (!(isNaN(wcSubsidyVal)) && wcSubsidyVal!="")
	{
		wcAssessedValue+=parseFloat(wcSubsidyVal);
	}
	
	var wcOther=findObj("wcOthers");
	var wcOtherVal=wcOther.value;
	if (!(isNaN(wcOtherVal))&& wcOtherVal!="")
	{
		wcAssessedValue+=parseFloat(wcOtherVal);
	}	
	
	var wcAssessed=document.getElementById('wcAssessed');
	wcAssessed.innerHTML=wcAssessedValue; 

	var fundBasedLimitSanctioned=document.getElementById('fundBasedLimitSanctioned');
	fundBasedLimitSanctioned.innerHTML=wcFundBasedSanctionedVal; 

	var nonFundBasedLimitSantioned=document.getElementById('nonFundBasedLimitSantioned');
	nonFundBasedLimitSantioned.innerHTML=wcNonFundBasedSanctionedVal; 

	
}
*/

function calProjectOutlay()
{	
	var projectCostValue=0;
	var wcAssessedValue=0;
	var projectOutlayValue=0;
	var renewalTotalValue=0;
	var enhancedTotalValue=0;
	var existingFundBasedTotal=0;
	var enhancedFundBasedTotal=0;
	var tcSanctionedVal;
	

	var tcSanctioned=findObj("termCreditSanctioned");	
	
	if(tcSanctioned!=null && tcSanctioned!="")
	{	
	var tcSanctionedVal=tcSanctioned.value;
	
	}else{
	
		var tcSanctioned=document.getElementById('tcSanctioned');
		tcSanctionedVal=tcSanctioned.innerHTML;
	}

	if (!(isNaN(parseFloat(tcSanctionedVal))) && tcSanctionedVal!="")
	{
		projectCostValue+=parseFloat(tcSanctionedVal);		
	}
	
	var promoterCont=findObj("tcPromoterContribution");	
	if(promoterCont!=null && promoterCont!="")
	{	
	var promoterContValue=promoterCont.value;
	
	}else{
	
		var promoterCont=document.getElementById('tcCont');
		promoterContValue=promoterCont.innerHTML;

	}
	
	if (!(isNaN(parseFloat(promoterContValue))) && promoterContValue!="")
	{		
		projectCostValue+=parseFloat(promoterContValue);
		
	}
	
	
	var tcSubsidy=findObj("tcSubsidyOrEquity");
	if(tcSubsidy!=null && tcSubsidy!="")
	{
	var tcSubsidyVal=tcSubsidy.value;
	
	}else{
	
		var tcSubsidy=document.getElementById('tcSubsidy');
		tcSubsidyVal=tcSubsidy.innerHTML;
	}
	if (!(isNaN(parseFloat(tcSubsidyVal))) && tcSubsidyVal!="")
	{
		projectCostValue+=parseFloat(tcSubsidyVal);
		
	}
	
	var tcOther=findObj("tcOthers");
	if(tcOther!=null && tcOther!="")
	{
	var tcOtherVal=tcOther.value;
	
	}else{
	
		var tcOther=document.getElementById('tcOther');
		tcOtherVal=tcOther.innerHTML;
	}
	
	if (!(isNaN(parseFloat(tcOtherVal)))&& tcOtherVal!="")
	{
		projectCostValue+=parseFloat(tcOtherVal);
		
	}	
	
	var wcFundBasedSanctioned=findObj("wcFundBasedSanctioned");	
	var wcNonFundBasedSanctioned=findObj("wcNonFundBasedSanctioned");	
	if(wcFundBasedSanctioned!=null && wcFundBasedSanctioned!="")
	{
	var wcFundBasedSanctionedVal=wcFundBasedSanctioned.value;
	
	}else{
	
		var wcFundBasedSanctioned=document.getElementById('wcFBsanctioned');
		wcFundBasedSanctionedVal=wcFundBasedSanctioned.innerHTML;	
	}

	if(wcNonFundBasedSanctioned!=null && wcNonFundBasedSanctioned!="")
	{
	var wcNonFundBasedSanctionedVal=wcNonFundBasedSanctioned.value;
	
	}else{
	
		var wcNonFundBasedSanctioned=document.getElementById('wcNFBsanctioned');
		wcNonFundBasedSanctionedVal=wcNonFundBasedSanctioned.innerHTML;	
	}

	if (!(isNaN(parseFloat(wcFundBasedSanctionedVal))) && wcFundBasedSanctionedVal!="")
	{
		wcAssessedValue+=parseInt(wcFundBasedSanctionedVal,10);	
		renewalTotalValue+=parseFloat(wcFundBasedSanctionedVal);

		var fundBasedTotal=document.getElementById('wcFundBased');
		
		if (fundBasedTotal!=null && fundBasedTotal!="")
		{
			var wcFundBased = fundBasedTotal.innerHTML;
			enhancedFundBasedTotal=parseFloat(wcFundBasedSanctionedVal); 			
		
			enhancedTotalValue=+parseFloat(enhancedFundBasedTotal);

		}
				
	}

	if (!(isNaN(parseFloat(wcNonFundBasedSanctionedVal))) && wcNonFundBasedSanctionedVal!="")
	{
		wcAssessedValue+=parseInt(wcNonFundBasedSanctionedVal,10);	
		renewalTotalValue+=parseFloat(wcNonFundBasedSanctionedVal);

/*		var fundBasedTotal=document.getElementById('wcFundBased');
		
		if (fundBasedTotal!=null && fundBasedTotal!="")
		{
			var wcFundBased = fundBasedTotal.innerHTML;
			enhancedFundBasedTotal=parseFloat(wcFundBasedSanctionedVal); 			
		
			enhancedTotalValue=+parseFloat(enhancedFundBasedTotal);

		}
*/				
	}
	
	var wcPromoterCont=findObj("wcPromoterContribution");
	if(wcPromoterCont!=null && wcPromoterCont!="")	
	{
	var wcPromoterContVal=wcPromoterCont.value;
	
	}else{
	
		var wcPromoterCont=document.getElementById('wcCont');
		wcPromoterContVal=wcPromoterCont.innerHTML;	
	}
	
	if (!(isNaN(parseFloat(wcPromoterContVal))) && wcPromoterContVal!="")
	{		
		wcAssessedValue+=parseFloat(wcPromoterContVal);
	}
	
	
	var wcSubsidy=findObj("wcSubsidyOrEquity");
	if(wcSubsidy!=null && wcSubsidy!="")
	{
		var wcSubsidyVal=wcSubsidy.value;
		
	}else{
	
		var wcSubsidy=document.getElementById('wcSubsidy');
		wcSubsidyVal=wcSubsidy.innerHTML;	

	}
	
	if (!(isNaN(parseFloat(wcSubsidyVal))) && wcSubsidyVal!="")
	{
		wcAssessedValue+=parseFloat(wcSubsidyVal);

	}
	
	var wcOther=findObj("wcOthers");
	if(wcOther!=null && wcOther!="")
	{
		var wcOtherVal=wcOther.value;
		
	}else{
	
		var wcOther=document.getElementById('wcOther');
		wcOtherVal=wcOther;	
	}
	if (!(isNaN(parseFloat(wcOtherVal)))&& wcOtherVal!="")
	{
		wcAssessedValue+=parseFloat(wcOtherVal);

	}	
		
	if (isNaN(parseFloat(projectCostValue)))
	{
		projectCostValue=0;
	}
	if (isNaN(parseFloat(tcSanctionedVal)))
	{
		tcSanctionedVal=0;
	}
	if (isNaN(parseFloat(wcFundBasedSanctionedVal)))
	{
		wcFundBasedSanctionedVal=0;
	}
	if (isNaN(parseFloat(wcNonFundBasedSanctionedVal)))
	{
		wcNonFundBasedSanctionedVal=0;
	}
	if (isNaN(parseFloat(wcAssessedValue)))
	{
		wcAssessedValue=0;
	}
	if (isNaN(parseFloat(projectOutlayValue)))
	{
		projectOutlayValue=0;
	}	
		
	var projectCost=document.getElementById('projectCost');
	projectCost.innerHTML=projectCostValue; 

	var wcAssessed=document.getElementById('wcAssessed');
	wcAssessed.innerHTML=wcAssessedValue;
	
	var projectOutlay=document.getElementById('projectOutlay');
	var marginMoneyAsTL=findObj("marginMoneyAsTL");	

	if(marginMoneyAsTL!=null && marginMoneyAsTL!="")
	{
		if (document.forms[0].marginMoneyAsTL[0].checked)
		{
			wcPromoterCont.disabled=true;
			wcPromoterCont.value="";
			var projectOutlayValue=parseFloat(wcAssessedValue);
			
			projectOutlay.innerHTML=projectOutlayValue; 
		
		}else if (document.forms[0].marginMoneyAsTL[1].checked){		
			
			wcPromoterCont.disabled=false;			
			var projectOutlayValue=parseFloat(projectCostValue) + parseFloat(wcAssessedValue);
		}	
	}

	projectOutlay.innerHTML=projectOutlayValue; 
	
	var amtSanctioned=document.getElementById('amountSanctioned');	

	if (amtSanctioned!=null && amtSanctioned!="")
	{	
		amtSanctioned.innerHTML=tcSanctionedVal;		
	}

	
	var fundBasedLimitSanctioned=document.getElementById('fundBasedLimitSanctioned');
	if (fundBasedLimitSanctioned!=null && fundBasedLimitSanctioned!="")
	{
		fundBasedLimitSanctioned.innerHTML=wcFundBasedSanctionedVal; 
	}
	
	var nonFundBasedLimitSantioned=document.getElementById('nonFundBasedLimitSantioned');
	if (nonFundBasedLimitSantioned!=null && nonFundBasedLimitSantioned!="")
	{
		nonFundBasedLimitSantioned.innerHTML=wcNonFundBasedSanctionedVal; 	
	}

	var renewalFundBased=document.getElementById('renewalFundBased');
	if (renewalFundBased!=null && renewalFundBased!="")
	{
		renewalFundBased.innerHTML=wcFundBasedSanctionedVal;
	}

	var renewalNonFundBased=document.getElementById('renewalNonFundBased');
	if (renewalNonFundBased!=null && renewalNonFundBased!="")
	{
		renewalNonFundBased.innerHTML=wcNonFundBasedSanctionedVal;
	}

	var renewalTotal=document.getElementById('renewalTotal');
	if (renewalTotal!=null && renewalTotal!="")
	{	
		renewalTotal.innerHTML=renewalTotalValue;
	}

	var enhancedFundBased=document.getElementById('enhancedFundBased');
	if (enhancedFundBased!=null && enhancedFundBased!="")
	{
		enhancedFundBased.innerHTML=enhancedFundBasedTotal;
	}

	var enhancedNonFundBased=document.getElementById('enhancedNonFundBased');
	if (enhancedNonFundBased!=null && enhancedNonFundBased!="")
	{
		enhancedNonFundBased.innerHTML=wcNonFundBasedSanctionedVal;
	}

	var enhancedTotal=document.getElementById('enhancedTotal');
	if (enhancedTotal!=null && enhancedTotal!="")
	{	
		enhancedTotal.innerHTML=enhancedTotalValue;
	}

}

/**
* This method calculates the primary Security Total worth
*/
function calculatePsTotal()
{
	var psTotal=0;
	var landValue=findObj("landValue");	
	if(landValue!=null && landValue!="")
	{
	var landVal=landValue.value;
	}
	if (!(isNaN(landVal)) && landVal!="")
	{
		psTotal+=parseFloat(landVal);	
	}

	var bldgValue=findObj("bldgValue");	
	if(bldgValue!=null && bldgValue!="")
	{
	var bldgVal=bldgValue.value;
	}
	if (!(isNaN(bldgVal)) && bldgVal!="")
	{
		psTotal+=parseFloat(bldgVal);	
	}

	var machineValue=findObj("machineValue");	
	if(machineValue!=null && machineValue!="")
	{
	var machineVal=machineValue.value;
	}
	if (!(isNaN(machineVal)) && machineVal!="")
	{
		psTotal+=parseFloat(machineVal);	
	}

	var assetsValue=findObj("assetsValue");	
	if(assetsValue!=null && assetsValue!="")
	{
	var assetsVal=assetsValue.value;
	}
	if (!(isNaN(assetsVal)) && assetsVal!="")
	{
		psTotal+=parseFloat(assetsVal);	
	}

	var currentAssetsValue=findObj("currentAssetsValue");	
	if(currentAssetsValue!=null && currentAssetsValue!="")
	{
	var currentAssetsVal=currentAssetsValue.value;
	}
	if (!(isNaN(currentAssetsVal)) && currentAssetsVal!="")
	{
		psTotal+=parseFloat(currentAssetsVal);	
	}

	var othersValue=findObj("othersValue");	
	if(othersValue!=null && othersValue!="")
	{
	var othersVal=othersValue.value;
	}
	if (!(isNaN(othersVal)) && othersVal!="")
	{
		psTotal+=parseFloat(othersVal);	
	}

	var psTotalWorth=document.getElementById('psTotalWorth');
	psTotalWorth.innerHTML=psTotal; 

}

/*
* This method calculates the enhanced total and returns it back to the screen
*/
function enhancedTotal()
{
	var enhancedValue=0;
	var enhancedFundBased=findObj("enhancedFundBased");	
	var enhancedFundBasedVal=enhancedFundBased.value;

	if (!(isNaN(enhancedFundBasedVal)) && enhancedFundBasedVal!="")
	{
		enhancedValue+=parseFloat(enhancedFundBasedVal);	
	}
	var enhancedNonFundBased=findObj("enhancedNonFundBased");	
	var enhancedNonFundBasedVal=enhancedNonFundBased.value;
	if (!(isNaN(enhancedNonFundBasedVal)) && enhancedNonFundBasedVal!="")
	{
		enhancedValue+=parseFloat(enhancedNonFundBasedVal,10);	
	}

	var enhancedTotal=document.getElementById('enhancedTotal');
	enhancedTotal.innerHTML=enhancedValue; 

}

function renewedTotal()
{
	var renewedValue=0;
	var renewalFundBased=findObj("renewalFundBased");	
	var renewalFundBasedVal=renewalFundBased.value;

	if (!(isNaN(renewalFundBasedVal)) && renewalFundBasedVal!="")
	{
		renewedValue+=parseFloat(renewalFundBasedVal,10);	
	}
	var renewalNonFundBased=findObj("renewalNonFundBased");	
	var renewalNonFundBasedVal=renewalNonFundBased.value;
	if (!(isNaN(renewalNonFundBasedVal)) && renewalNonFundBasedVal!="")
	{
		renewedValue+=parseFloat(renewalNonFundBasedVal,10);	
	}

	var renewalTotal=document.getElementById('renewalTotal');
	renewalTotal.innerHTML=renewedValue; 

}

function enableAssistance()
{
	var osAmount=findObj("osAmt");
	var npaValue = findObj("npa");
	if(osAmount!=null && osAmount!="")
	{
		if (document.forms[0].assistedByBank[1].checked)
		{
			osAmount.disabled=true;
			osAmount.value="";
			if(npaValue!=null && npaValue!="")
			{
			document.forms[0].npa[1].checked=true;
			document.forms[0].npa[1].disabled=true;
			document.forms[0].npa[0].disabled=true;
			document.forms[0].previouslyCovered[1].checked=true;
			document.forms[0].previouslyCovered[0].disabled=true;
			document.forms[0].previouslyCovered[1].disabled=true;	
			
			document.forms[0].none[0].checked=true;	
			document.forms[0].none[0].disabled=false;	
			document.forms[0].none[1].disabled=true;	
			document.forms[0].none[2].disabled=true;	
			document.forms[0].unitValue.disabled=true;	
			
				if(document.forms[0].constitution!=null && document.forms[0].constitution!="")
				{
				document.forms[0].constitution.disabled=false;
				}
				
				if(document.forms[0].constitutionOther!=null && document.forms[0].constitutionOther!="")
				{
				document.forms[0].constitutionOther.disabled=false;
				}

				if(document.forms[0].ssiType!=null && document.forms[0].ssiType!="")
				{
				document.forms[0].ssiType.disabled=false;
				}

				if(document.forms[0].ssiName!=null && document.forms[0].ssiName!="")
				{
				document.forms[0].ssiName.disabled=false;
				}

				if(document.forms[0].regNo!=null && document.forms[0].regNo!="")
				{
				document.forms[0].regNo.disabled=false;
				}

				if(document.forms[0].ssiITPan!=null && document.forms[0].ssiITPan!="")
				{
				document.forms[0].ssiITPan.disabled=false;
				}

				if(document.forms[0].industryNature!=null && document.forms[0].industryNature!="")
				{
				document.forms[0].industryNature.disabled=false;
				}

				if(document.forms[0].industrySector!=null && document.forms[0].industrySector!="")
				{
				document.forms[0].industrySector.disabled=false;
				}

				if(document.forms[0].activityType!=null && document.forms[0].activityType!="")
				{
				document.forms[0].activityType.disabled=false;
				}

				if(document.forms[0].employeeNos!=null && document.forms[0].employeeNos!="")
				{
				document.forms[0].employeeNos.disabled=false;
				}

				if(document.forms[0].projectedSalesTurnover!=null && document.forms[0].projectedSalesTurnover!="")
				{
				document.forms[0].projectedSalesTurnover.disabled=false;
				}

				if(document.forms[0].projectedExports!=null && document.forms[0].projectedExports!="")
				{
				document.forms[0].projectedExports.disabled=false;
				}

				if(document.forms[0].address!=null && document.forms[0].address!="")
				{
				document.forms[0].address.disabled=false;
				}

				if(document.forms[0].state!=null && document.forms[0].state!="")
				{
				document.forms[0].state.disabled=false;
				}

				if(document.forms[0].district!=null && document.forms[0].district!="")
				{
				document.forms[0].district.disabled=false;
				}

				if(document.forms[0].districtOthers!=null && document.forms[0].districtOthers!="")
				{
				document.forms[0].districtOthers.disabled=false; 																																				
				}

				if(document.forms[0].city!=null && document.forms[0].city!="")
				{
				document.forms[0].city.disabled=false; 																																				
				}

				if(document.forms[0].pincode!=null && document.forms[0].pincode!="")
				{
				document.forms[0].pincode.disabled=false; 																																				
				}

				if(document.forms[0].cpTitle!=null && document.forms[0].cpTitle!="")
				{
				document.forms[0].cpTitle.disabled=false; 																																				
				}
				
				if(document.forms[0].cpFirstName!=null && document.forms[0].cpFirstName!="")
				{
				document.forms[0].cpFirstName.disabled=false; 																																				
				}

				if(document.forms[0].cpMiddleName!=null && document.forms[0].cpMiddleName!="")
				{
				document.forms[0].cpMiddleName.disabled=false; 																																				
				}

				if(document.forms[0].cpLastName!=null && document.forms[0].cpLastName!="")
				{
				document.forms[0].cpLastName.disabled=false; 																																				
				}

				if(document.forms[0].cpGender!=null && document.forms[0].cpGender!="")
				{
				document.forms[0].cpGender.disabled=false; 																																				
				}

				if(document.forms[0].cpITPAN!=null && document.forms[0].cpITPAN!="")
				{
				document.forms[0].cpITPAN.disabled=false; 																																				
				}

				if(document.forms[0].cpDOB!=null && document.forms[0].cpDOB!="")
				{
				document.forms[0].cpDOB.disabled=false; 																																				
				}

				if(document.forms[0].socialCategory!=null && document.forms[0].socialCategory!="")
				{
				document.forms[0].socialCategory.disabled=false; 																																				
				}

				if(document.forms[0].cpLegalID!=null && document.forms[0].cpLegalID!="")
				{
				document.forms[0].cpLegalID.disabled=false; 																																				
				}

				if(document.forms[0].otherCpLegalID!=null && document.forms[0].otherCpLegalID!="")
				{
				document.forms[0].otherCpLegalID.disabled=false; 																																				
				}

				if(document.forms[0].cpLegalIdValue!=null && document.forms[0].cpLegalIdValue!="")
				{
				document.forms[0].cpLegalIdValue.disabled=false; 																																				
				}

				if(document.forms[0].firstName!=null && document.forms[0].firstName!="")
				{
				document.forms[0].firstName.disabled=false; 																																				
				}

				if(document.forms[0].firstItpan!=null && document.forms[0].firstItpan!="")
				{
				document.forms[0].firstItpan.disabled=false; 																																				
				}

				if(document.forms[0].firstDOB!=null && document.forms[0].firstDOB!="")
				{
				document.forms[0].firstDOB.disabled=false; 																																				
				}

				if(document.forms[0].secondName!=null && document.forms[0].secondName!="")
				{
				document.forms[0].secondName.disabled=false; 																																				
				}

				if(document.forms[0].secondItpan!=null && document.forms[0].secondItpan!="")
				{
				document.forms[0].secondItpan.disabled=false; 																																																																														
				}

				if(document.forms[0].secondDOB!=null && document.forms[0].secondDOB!="")
				{
				document.forms[0].secondDOB.disabled=false; 																																																																																	
				}

				if(document.forms[0].thirdName!=null && document.forms[0].thirdName!="")
				{
				document.forms[0].thirdName.disabled=false; 																																																																														
				}

				if(document.forms[0].thirdItpan!=null && document.forms[0].thirdItpan!="")
				{
				document.forms[0].thirdItpan.disabled=false; 																																																																														
				}

				if(document.forms[0].thirdDOB!=null && document.forms[0].thirdDOB!="")
				{
				document.forms[0].thirdDOB.disabled=false;																																																																											
				}

			}
			
			

		}else if (document.forms[0].assistedByBank[0].checked)
		{		
			osAmount.disabled=false;
			if(npaValue!=null && npaValue!="")
			{
			document.forms[0].npa[0].disabled=false;
			document.forms[0].npa[1].disabled=false;
			document.forms[0].previouslyCovered[0].disabled=false;		
			document.forms[0].previouslyCovered[1].disabled=false;	
			}
			
		}

	}

}

function enabledcHandlooms()
{

var dcHandlooms = findObj("dcHandlooms");
var wvCreditScheme = findObj("WeaverCreditScheme");
var handloomchk = findObj("handloomchk");
var icardNo = findObj("icardNo");

var icardIssueDate = findObj("icardIssueDate");

var dcHandloomsStatus = findObj("dcHandloomsStatus");

if(dcHandloomsStatus!=null && dcHandloomsStatus!="")
{
document.forms[0].dcHandloomsStatus[0].disabled=true;
document.forms[0].dcHandloomsStatus[1].disabled=true;
wvCreditScheme.disabled=true;	
handloomchk.disabled=true;	


}

if(dcHandlooms!=null && dcHandlooms!="")
{
	if (document.forms[0].dcHandlooms[0].checked)
		{
	
document.forms[0].dcHandlooms[0].disabled=false;
document.forms[0].dcHandlooms[1].disabled=false;
		
	
		
document.forms[0].dcHandicrafts[1].checked=true;
document.forms[0].dcHandicrafts[1].disabled=true;
document.forms[0].dcHandicrafts[0].disabled=true;
document.forms[0].handiCrafts[1].checked=true;

icardNo.disabled=true;
icardIssueDate.disabled=true;


handloomchk.disabled=false;
wvCreditScheme.disabled=false;	
		}

if (document.forms[0].dcHandlooms[1].checked)
		{
		//alert("checked");
	
//hlIcardNo=null;

handloomchk.disabled=true;
wvCreditScheme.disabled=true;
 document.forms[0].WeaverCreditScheme.value='Select';
 document.forms[0].handloomchk.checked=false;

		}

}

}


function enableHandiCrafts()
{

var handiCrafts = findObj("handiCrafts");
var dcHandicrafts = findObj("dcHandicrafts");
var icardNo = findObj("icardNo");
var icardIssueDate = findObj("icardIssueDate");
var handiCraftsStatus = findObj("handiCraftsStatus");
var dcHandicraftsStatus = findObj("dcHandicraftsStatus");
var handloomchk= findObj("handloomchk");

//var hlcalimg = findObj("hlcalimg");
var wvCreditScheme = findObj("WeaverCreditScheme");
if(dcHandicraftsStatus!=null && dcHandicraftsStatus!="")
{
 
document.forms[0].dcHandicraftsStatus[1].disabled=true;
document.forms[0].dcHandicraftsStatus[0].disabled=true;
document.forms[0].handiCraftsStatus[1].disabled=true;
document.forms[0].handiCraftsStatus[0].disabled=true;


if(icardNo!=null && icardNo!="")
	        { 
			icardNo.disabled=true;
		    }
         if(icardIssueDate!=null && icardIssueDate!=" ")
	        { 
              icardIssueDate.disabled=true;
			
		    }

}

// document.forms[0].dcHandicrafts[1].checked=true;
// document.forms[0].dcHandicrafts[1].disabled=true;
// document.forms[0].dcHandicrafts[0].disabled=true;
		
// iCardIssueDate.disabled=true;
// iCardNo.disabled=true;
if(handiCrafts!=null && handiCrafts!=""){


	if (document.forms[0].handiCrafts[1].checked)
		{
		//alert("NO");
		
		if(dcHandicrafts!=null && dcHandicrafts!="")
			{
			document.forms[0].dcHandicrafts[1].checked=true;
			document.forms[0].dcHandicrafts[1].disabled=true;
			document.forms[0].dcHandicrafts[0].disabled=true;
			document.forms[0].icardNo.value='';
            document.forms[0].icardIssueDate.value='';
			
			document.forms[0].dcHandlooms[0].disabled=false;
		document.forms[0].dcHandlooms[1].disabled=false;



wvCreditScheme.disabled=true;	

			}
		if(icardNo!=null && icardNo!="")
	        { 
              icardNo.disabled=true;
		    }
         if(icardIssueDate!=null && icardIssueDate!="")
	        { 
              icardIssueDate.disabled=true;
		    }
	}
	else if(document.forms[0].handiCrafts[0].checked){
	// alert("YES");
	       if(dcHandicrafts!=null && dcHandicrafts!="")
			{
			document.forms[0].dcHandicrafts[0].checked=true;
			document.forms[0].dcHandicrafts[1].disabled=false;
			document.forms[0].dcHandicrafts[0].disabled=false;
			
			//document.forms[0].dcHandlooms[0].disabled=true;
		//document.forms[0].dcHandlooms[1].disabled=true;

document.forms[0].dcHandlooms[0].checked=false;
document.forms[0].dcHandlooms[1].checked=true;

wvCreditScheme.disabled=true;
handloomchk.disabled=true;
document.forms[0].WeaverCreditScheme.value='Select';
 document.forms[0].handloomchk.checked=false;	
			}
		  
		   icardNo.disabled=false;
		   icardIssueDate.disabled=false;
         
	}
	
}


}


function enableJointFinance()
{

var jointFinance= findObj("jointFinance");
//alert("jointFinance="+jointFinance);
var jointcgpan = findObj("jointcgpan");

if(jointFinance!=null && jointFinance!=""){
	if (document.forms[0].jointFinance[1].checked)
		{
                   //alert("NO");
                   jointcgpan.disabled=true;

                }
 else if (document.forms[0].jointFinance[0].checked)
		{
                   //alert("Yes"); 
                   alert("Please mention Existing Cgpan for this Borrower");
                   jointcgpan.disabled=false;

                }
}


}


function enableGender()
{
	var promoterTitle = findObj("cpTitle");
	if(promoterTitle!=null && promoterTitle!="")
	{
		
		if(promoterTitle.value == "Mr.")
		{
			document.forms[0].cpGender[1].disabled=true;
			document.forms[0].cpGender[0].disabled=false;
			document.forms[0].cpGender[0].checked=true;
			document.forms[0].cpGender[1].checked=false;
			
		}
		else if(promoterTitle.value == "Smt" || promoterTitle.value == "Ku"){
		
			document.forms[0].cpGender[0].disabled=true;
			document.forms[0].cpGender[1].disabled=false;
			document.forms[0].cpGender[1].checked=true;
			document.forms[0].cpGender[0].checked=false;
		}
		else if(promoterTitle.value == ""){
		
			document.forms[0].cpGender[1].disabled=true;
			document.forms[0].cpGender[0].disabled=false;
			document.forms[0].cpGender[0].checked=true;
			document.forms[0].cpGender[1].checked=false;
			
		}

	}
	
}


/*********************************************************************************/
function getExceptionDetails(field,action)
{
	if(field.options[field.selectedIndex].text!="Select")
	{
		submitForm(action);
	}
	else
	{
		document.forms[0].exceptionMessage.value="";
		document.forms[0].exceptionType.options[0].selected=true;
	}
}

function getDesignationDetails(field,action)
{
	if(field.options[field.selectedIndex].text!="Select")
	{
		submitForm(action);
	}
	else
	{
		document.forms[0].desigDesc.value="";
		
	}
}

function getAlertDetails(field,action)
{
	if(field.options[field.selectedIndex].text!="Select")
	{
		submitForm(action);
	}
	else
	{
		document.forms[0].alertMessage.value="";
		
	}
}

function getPlrDetails(field,action)
{
	if(field.options[field.selectedIndex].text!="Select")
	{
		submitForm(action);		
	}
	else
	{
		document.forms[0].startDate.value="";
		document.forms[0].endDate.value="";
		document.forms[0].shortTermPLR.value="";
		document.forms[0].mediumTermPLR.value="";
		document.forms[0].longTermPLR.value="";
		document.forms[0].shortTermPeriod.value="";
		document.forms[0].mediumTermPeriod.value="";
		document.forms[0].longTermPeriod.value="";	
		document.forms[0].BPLR.value="";	
	}
}
function selectAll(field)
{
	var obj=findObj("checks");
	
	if(field.checked)
	{
		//alert("checked");
		if(obj)
		{
			//alert("length "+obj.length);
			
			if(obj.length)
			{
				for(i=0;i<obj.length;i++)
				{
					obj[i].checked=true;
				}
			}
			else
			{
				//alert("undefined ");
				obj.checked=true;
			}
		}
	}
	else
	{
		//alert("un checked");
		
		if(obj)
		{
			if(obj.length)
			{
				for(i=0;i<obj.length;i++)
				{
					obj[i].checked=false;
				}
			}
			else
			{
				obj.checked=false;
			}
		}
		
	}
}
function submitTop(action)
{
	top.document.forms[0].action=action;
	top.document.forms[0].target="_self";
	top.document.forms[0].method="POST";
	top.document.forms[0].submit();
}

function chooseBroadcast()
{
	
	if (document.forms[0].selectBM[0].checked){	
		
		document.forms[0].bankName.options.selectedIndex=0;
		document.forms[0].bankName.disabled=true;
		
		document.forms[0].zoneRegionNames.options.selectedIndex=0;
		document.forms[0].zoneRegionNames.disabled=true;

		document.forms[0].branchNames.options.selectedIndex=0;
		document.forms[0].branchNames.disabled=true;

	}
	else if(document.forms[0].selectBM[1].checked){
			
			document.forms[0].bankName.disabled=false;

			document.forms[0].zoneRegionNames.options.selectedIndex=0;
			document.forms[0].zoneRegionNames.disabled=true;

			document.forms[0].branchNames.options.selectedIndex=0;
			document.forms[0].branchNames.disabled=true;
	}
	else if(document.forms[0].selectBM[2].checked){

				document.forms[0].bankName.options.selectedIndex=0;
				document.forms[0].bankName.disabled=false;

				document.forms[0].zoneRegionNames.options.selectedIndex=0;
				document.forms[0].zoneRegionNames.disabled=false;

				document.forms[0].branchNames.options.selectedIndex=0;
				document.forms[0].branchNames.disabled=true;
	}
	else if(document.forms[0].selectBM[3].checked){

					document.forms[0].bankName.options.selectedIndex=0;
					document.forms[0].bankName.disabled=false;

					document.forms[0].zoneRegionNames.options.selectedIndex=0;
					document.forms[0].zoneRegionNames.disabled=true;

					document.forms[0].branchNames.options.selectedIndex=0;
					document.forms[0].branchNames.disabled=false;
	}
	else if(document.forms[0].selectBM[4].checked){

					document.forms[0].bankName.options.selectedIndex=0;
					document.forms[0].bankName.disabled=false;

					document.forms[0].zoneRegionNames.options.selectedIndex=0;
					document.forms[0].zoneRegionNames.disabled=true;

					document.forms[0].branchNames.options.selectedIndex=0;
					document.forms[0].branchNames.disabled=true;
		
	}
	else if(document.forms[0].selectBM[5].checked){

				document.forms[0].bankName.options.selectedIndex=0;
				document.forms[0].bankName.disabled=false;

				document.forms[0].zoneRegionNames.options.selectedIndex=0;
				document.forms[0].zoneRegionNames.disabled=true;

				document.forms[0].branchNames.options.selectedIndex=0;
				document.forms[0].branchNames.disabled=false;
	}
	else if(document.forms[0].selectBM[6].checked){

				document.forms[0].bankName.options.selectedIndex=0;
				document.forms[0].bankName.disabled=false;

				document.forms[0].zoneRegionNames.options.selectedIndex=0;
				document.forms[0].zoneRegionNames.disabled=false;

				document.forms[0].branchNames.options.selectedIndex=0;
				document.forms[0].branchNames.disabled=true;		
	}
	else if (document.forms[0].selectBM[7].checked){	
		
				document.forms[0].bankName.options.selectedIndex=0;
				document.forms[0].bankName.disabled=true;
				
				document.forms[0].zoneRegionNames.options.selectedIndex=0;
				document.forms[0].zoneRegionNames.disabled=true;

				document.forms[0].branchNames.options.selectedIndex=0;
				document.forms[0].branchNames.disabled=true;

	}

}
function reloadBroadcast()
{
	
	if (document.forms[0].selectBM[0].checked){	
		
		
		document.forms[0].bankName.disabled=true;

		document.forms[0].zoneRegionNames.options.selectedIndex=0;
		document.forms[0].zoneRegionNames.disabled=true;

		document.forms[0].branchNames.options.selectedIndex=0;
		document.forms[0].branchNames.disabled=true;
	}
	else if(document.forms[0].selectBM[1].checked){
			
			document.forms[0].bankName.disabled=false;

			document.forms[0].zoneRegionNames.options.selectedIndex=0;
			document.forms[0].zoneRegionNames.disabled=true;

			document.forms[0].branchNames.options.selectedIndex=0;
			document.forms[0].branchNames.disabled=true;
	}
	else if(document.forms[0].selectBM[2].checked){

				
				document.forms[0].bankName.disabled=false;

				document.forms[0].zoneRegionNames.options.selectedIndex=0;
				document.forms[0].zoneRegionNames.disabled=false;

				document.forms[0].branchNames.options.selectedIndex=0;
				document.forms[0].branchNames.disabled=true;
	}
	else if(document.forms[0].selectBM[3].checked){

					
					document.forms[0].bankName.disabled=false;

					document.forms[0].zoneRegionNames.options.selectedIndex=0;
					document.forms[0].zoneRegionNames.disabled=true;

					document.forms[0].branchNames.options.selectedIndex=0;
					document.forms[0].branchNames.disabled=false;
	}
	else if(document.forms[0].selectBM[4].checked){

					
					document.forms[0].bankName.disabled=false;

					document.forms[0].zoneRegionNames.options.selectedIndex=0;
					document.forms[0].zoneRegionNames.disabled=true;

					document.forms[0].branchNames.options.selectedIndex=0;
					document.forms[0].branchNames.disabled=true;
		
	}
	else if(document.forms[0].selectBM[5].checked){

				
				document.forms[0].bankName.disabled=false;

				document.forms[0].zoneRegionNames.options.selectedIndex=0;
				document.forms[0].zoneRegionNames.disabled=true;

				document.forms[0].branchNames.options.selectedIndex=0;
				document.forms[0].branchNames.disabled=false;
	}
	else if(document.forms[0].selectBM[6].checked){

				
				document.forms[0].bankName.disabled=false;

				document.forms[0].zoneRegionNames.options.selectedIndex=0;
				document.forms[0].zoneRegionNames.disabled=false;

				document.forms[0].branchNames.options.selectedIndex=0;
				document.forms[0].branchNames.disabled=true;		
	}
	else if (document.forms[0].selectBM[7].checked){	
	
			document.forms[0].bankName.options.selectedIndex=0;
			document.forms[0].bankName.disabled=true;
			
			document.forms[0].zoneRegionNames.options.selectedIndex=0;
			document.forms[0].zoneRegionNames.disabled=true;

			document.forms[0].branchNames.options.selectedIndex=0;
			document.forms[0].branchNames.disabled=true;

	}


}
/*	************************Start Of Guarantee Maintenance *************************/
/*	function setFlagForClosure(count, name, targetURL, value) {
		
		for(i = 0; i < count; ++i) {
			var objName =findObj(name+"(key-"+i+")");
			if (objName.checked==false)	{
				objName.value = value;
				objName.checked=true;
			}
		}
		submitForm(targetURL);
	} */

/*	function setDisbursementFlag(count, name, targetURL, value) {
		var objName;
		for(i = 0; i < count; ++i) {
			objName =findObj(name+"(key-"+i+")");
			alert(objName);
			if (objName.checked==false)	{
				objName.value = value;
				objName.checked=true;
			}
		}
		submitForm(targetURL);	
	} */

	function addNewRecoveryProc() {
		
		task = document.gmPeriodicInfoForm.recActionType.value;
		tasks = task.split("+");

	    x = eval("document."+"all(\"addRecovery\")");
		i = document.gmPeriodicInfoForm.noOfActions.value;

		i++;
        x.insertRow();

		x.rows[i].insertCell(); 
		x.rows[i].insertCell(); 
		x.rows[i].insertCell(); 
		x.rows[i].insertCell(); 
		tActionType = "<select Name = actionType(key-"+i+")"+"><option></option>";
		j = 0;
		k = 0;
		while(j < tasks.length-1)
		{
			tActionType = tActionType+"<option value="+tasks[k+1]+">"+tasks[j+1]+"</option>";
			j+=2;
			k+=2;
		}
		tActionType = tActionType+"</select>";				
		tActionDetails = "<textarea name = actionDetails(key-"+i+")"+" size=15 rows = 2 ></textarea>";
		tActionDate	= "<input type='text' name = actionDate(key-"+i+")"+" size=10><IMG src='images/CalendarIcon.gif' width = '20' onClick=showCalendar('gmPeriodicInfoForm.actionDate') align='center'>";
		tFile = "<input type='file' name=attachmentName(key-"+i+")"+" >";
       
		x.rows[i].cells[0].innerHTML = tActionType;
		x.rows[i].cells[1].innerHTML = tActionDetails;
		x.rows[i].cells[2].innerHTML = tActionDate;
		x.rows[i].cells[3].innerHTML = tFile;	
		document.gmPeriodicInfoForm.noOfActions.value = i;
	}


function AddActivities(addRowNo)
{	
	x = document.getElementById('add1col('+addRowNo+')');
	y = document.getElementById('add2col('+addRowNo+')');
	alert(addRowNo);
	if(addRowNo==0)
	{
		i = document.gmPeriodicInfoForm.rowCount.value ;

		x.insertRow();
		y.insertRow();
		
		i++;
			
		x.rows[i].insertCell(); 
		y.rows[i].insertCell(); 

		t1 = "<input Name=repaymentAmount(key-"+i+")"+"maxlength=10 size=10>";	
		t2 = "<input Name=repaymentDate(key-"+i+")"+"maxlength=10 size=10><img src='images/CalendarIcon.gif' onclick=show_calendar('form1.asondate3') width='20'  align='center'>";

		x.rows[i].cells[0].innerHTML = t1;
		y.rows[i].cells[0].innerHTML = t2;
		
		document.gmPeriodicInfoForm.rowCount.value = i ;
		
	}
	//document.gmPeriodicInfoForm.rowCount.value = 0;
	if(addRowNo==1)
	{
		i = document.gmPeriodicInfoForm.rowCount.value ;

		x.insertRow();
		y.insertRow();
		
		i++;
			
		x.rows[i].insertCell(); 
		y.rows[i].insertCell(); 

		t1 = "<input Name=repaymentAmount(key-"+i+")"+"maxlength=10 size=10>";	
		t2 = "<input Name=repaymentDate(key-"+i+")"+"maxlength=10 size=10><img src='images/CalendarIcon.gif' onclick=show_calendar('form1.asondate3') width='20'  align='center'>";

		x.rows[i].cells[0].innerHTML = t1;
		y.rows[i].cells[0].innerHTML = t2;
		
		document.gmPeriodicInfoForm.rowCount.value = i ;
		
	}
	
}

/* function AddActivities(addRowNo)
{
	count = document.gmPeriodicInfoForm.rowCount.value ;
	callAppropriate(addRowNo,count);
}*/


function displayDbrTotal() {
	var j = 0;
	var totalAmount = 0;
	var cgpanVal;
	index = document.gmPeriodicInfoForm.disbursementEntryIndex.value;

	for(i=0;i<index;++i) 
	{
		cgpan = findObj("cgpans(key-"+i+")");	
		if(cgpan!=null)
		{
			cgpanVal = cgpan.value;

			sanctionedAmt = document.getElementById("sanDisb("+i+")");
			sanctionedAmtVal = sanctionedAmt.innerHTML;
			
			totalId = document.getElementById("totalDisbAmt"+i);

			disbAmt = findObj("disbursementAmount("+cgpanVal+"-"+j+")");
			while(disbAmt != null)	
			{
				if (!(isNaN(disbAmt.value)) && disbAmt.value != "")
				{
					totalAmount += parseInt(disbAmt.value,10);
				}
			if (totalAmount > sanctionedAmtVal )
			{
				findObj("disbursementAmount("+cgpanVal+"-"+j+")").value="";				
			} 
				
				++j;
				disbAmt = findObj("disbursementAmount("+cgpanVal+"-"+j+")");
			}
			if (totalAmount > sanctionedAmtVal )
			{
				alert("Disbursement Amount is more than the Sanctioned Amount For the CGPAN "+cgpanVal);
				findObj("disbursementAmount("+cgpanVal+"-"+(j-1)+")").value="";				
			} 
			
			totalId.innerHTML = totalAmount;
			
			j=0;
			totalAmount = 0;
		}
	}
}


function validateFinalDisbursement(field) 
{
	var j = 0;
	index = document.gmPeriodicInfoForm.disbursementEntryIndex.value;
	var matched=false;
	//alert(field.name);
	for(i=0;i<index;++i) 
	{
		cgpan = findObj("cgpans(key-"+i+")");      
		matched=false;
		if(cgpan!=null)
		{
			cgpanVal = cgpan.value;
			//alert(cgpanVal);
			disbAmt = findObj("disbursementAmount("+cgpanVal+"-"+j+")");
			disbDate = findObj("disbursementDate("+cgpanVal+"-"+j+")");
			finalDisb = findObj("finalDisbursement("+cgpanVal+"-"+j+")");                    
			
			while((finalDisb!=null) && finalDisb.value=="Y")   
			{
				//alert(cgpanVal);
				if (field.name==finalDisb.name)
				{
					//alert("matched "+finalDisb.value);                                                
					matched=true;
				}
				++j;
				disbAmt = findObj("disbursementAmount("+cgpanVal+"-"+j+")");
				disbDate = findObj("disbursementDate("+cgpanVal+"-"+j+")");
				finalDisb = findObj("finalDisbursement("+cgpanVal+"-"+j+")");        
				
				if(matched==true)
				{
					if(disbAmt)
					{
						 disbAmt.value="";
						 if(field.checked)
						 {
							 disbAmt.disabled=true;
						 }
						 else
						 {
							 disbAmt.disabled=false;
						 }
					}
					if(disbDate)
					{
						disbDate.value="";
						if(field.checked)
						{
							disbDate.disabled=true;
						}
						else
						{
							disbDate.disabled=false;
						}                                                           
					}
					if(finalDisb)
					{
						finalDisb.checked=false;
						if(field.checked)
						{
							finalDisb.disabled=true;
						}
						else
						{
							finalDisb.disabled=false;
						}                                                           
					}
				}
			}           
			j=0;
		}
	}
}



function displayRpmtTotal()
{
	var j = 0;
	var totalAmount = 0;
	var totalId;
	index = document.gmPeriodicInfoForm.repaymentEntryIndex.value;
	for(i=0;i<index;++i) 
	{
		cgpan = findObj("cgpans(key-"+i+")");	
		if(cgpan!=null)
		{
			cgpanVal = cgpan.value;
			//alert(cgpanVal);
			totalId = document.getElementById("totalAmt"+i);

			repAmt = findObj("repaymentAmount("+cgpanVal+"-"+j+")");
			while(repAmt != null)	
			{
				if (!(isNaN(repAmt.value)) && repAmt.value != "")
				{
					totalAmount += parseInt(repAmt.value,10);
				}
				++j;
				repAmt = findObj("repaymentAmount("+cgpanVal+"-"+j+")");
			}
			j=0;
			//alert(cgpanVal+"total Amount"+totalAmount);
			totalId.innerHTML = totalAmount;
			totalAmount = 0;
		}
	}

}


function displayTcOutAmtTotal()
{
	var j = 0;
	var totalTcAmount = 0;
	index = document.gmPeriodicInfoForm.outDetailIndex.value;
	for(i=0;i<index;++i) 
	{
		cgpanTc = findObj("cgpansForTc(key-"+i+")");	
		
		if (cgpanTc!=null)
		{
			cgpanTcVal = cgpanTc.value;
			totalTcId = document.getElementById("totalTcOutAmt"+i);
			
			tcOutAmount = document.getElementById("tcSanct(key-"+i+")");
			tcSanctionedAmt = tcOutAmount.innerHTML;
			
			tcOutAmt = findObj("tcPrincipalOutstandingAmount("+cgpanTcVal+"-"+j+")");

			while(tcOutAmt != null)	
			{
				if (!(isNaN(tcOutAmt.value)) && tcOutAmt.value != "")
				{
					totalTcAmount += parseInt(tcOutAmt.value,10);
				}
				++j;
				tcOutAmt = findObj("tcPrincipalOutstandingAmount("+cgpanTcVal+"-"+j+")");
			}
			if(totalTcAmount> tcSanctionedAmt)
			{
				alert("Outstanding Amount is more than the Sanctioned Amount For the CGPAN "+cgpanTcVal);	
				findObj("tcPrincipalOutstandingAmount("+cgpanTcVal+"-"+(j-1)+")").value="";
				return;
			}
			
			j=0;
			totalTcId.innerHTML = totalTcAmount;
			
			totalTcAmount = 0;
			
		}
	}
}


function checkForTcClosure()
{
	var j = 0;
	index = document.gmPeriodicInfoForm.outDetailIndex.value;
	var status;
	for(i=0;i<index;++i) 
	{
		cgpanTc = findObj("cgpansForTc(key-"+i+")");	
		//status = false;
		if (cgpanTc!=null)
		{
			cgpanTcVal = cgpanTc.value;

			tcOutAmt = findObj("tcPrincipalOutstandingAmount("+cgpanTcVal+"-"+j+")");

			while(tcOutAmt != null)	
			{
				if (!(isNaN(tcOutAmt.value)) && tcOutAmt.value != "")
				{
					if (parseInt(tcOutAmt.value) == 0)
					{
						status = confirm(" If TC Outstanding Amount is ZERO application will be closed ");
						if (status==true)
						{
							submitForm('saveOutstandingDetails.do?method=saveOutstandingDetails');
						}
					}
				}
				++j;
				tcOutAmt = findObj("tcPrincipalOutstandingAmount("+cgpanTcVal+"-"+j+")");
			}
			j=0;
		}
	}
	if(!status && status!=false)
	{
		submitForm('saveOutstandingDetails.do?method=saveOutstandingDetails');
	}
}

function displayWcOutAmtTotal()
{
	var j = 0;
	var totalWcAmount = 0;
	var totamt = 0;
	var wcPrOutAmt = 0;
	var wcSanctionedAmt = 0;
	index = document.gmPeriodicInfoForm.outDetailIndex.value;
	for(i=0;i<index;++i) 
	{
		cgpanWc = findObj("cgpansForWc(key-"+i+")");	
		
		if (cgpanWc!=null)
		{
		
			cgpanWcVal = cgpanWc.value;
			//alert(cgpanWcVal);
			totalWcId = document.getElementById("totalWcOutAmt"+i);
			
			wcPrOutAmt = findObj("wcFBPrincipalOutstandingAmount("+cgpanWcVal+"-"+j+")");
			wcPrIntAmt = findObj("wcFBInterestOutstandingAmount("+cgpanWcVal+"-"+j+")");
			
			wcFBDate = findObj("wcFBOutstandingAsOnDate("+cgpanWcVal+"-"+j+")");
			
			wcOutAmount = document.getElementById("wcSanct(key-"+i+")");
			
			wcSanctionedAmt = wcOutAmount.innerHTML;
			
			
			if(wcSanctionedAmt==0)
			{
				while((wcPrOutAmt != null))	
				{
				//alert(j);
					
					wcPrOutAmt.disabled=true;
					wcPrOutAmt.value="";
					
					wcPrIntAmt.disabled=true;
					wcPrIntAmt.value="";
					
					wcFBDate.disabled=true;
					wcFBDate.value="";

					++j;
					wcPrOutAmt = findObj("wcFBPrincipalOutstandingAmount("+cgpanWcVal+"-"+j+")");	
					wcPrIntAmt = findObj("wcFBInterestOutstandingAmount("+cgpanWcVal+"-"+j+")");
					
					wcFBDate = findObj("wcFBOutstandingAsOnDate("+cgpanWcVal+"-"+j+")");
					
				}
					
			}
				
				
				while((wcPrOutAmt != null) && (wcPrIntAmt != null))	
				{
					if ( !(isNaN(wcPrOutAmt.value)) && (wcPrOutAmt.value != ""))
					{
						pramt = parseInt(wcPrOutAmt.value,10);
						totalWcAmount += pramt;
					}
					if ( !(isNaN(wcPrIntAmt.value)) && (wcPrIntAmt.value != ""))
					{
						intamt = parseInt(wcPrIntAmt.value,10);
						totalWcAmount += intamt;
					}
	
					++j;
					wcPrOutAmt = findObj("wcFBPrincipalOutstandingAmount("+cgpanWcVal+"-"+j+")");	
					wcPrIntAmt = findObj("wcFBInterestOutstandingAmount("+cgpanWcVal+"-"+j+")");
				}
				if(totalWcAmount> wcSanctionedAmt)
				{
					alert("Outstanding Amount is more than the Sanctioned Amount For the CGPAN "+cgpanWcVal);	
					findObj("wcFBPrincipalOutstandingAmount("+cgpanWcVal+"-"+(j-1)+")").value="";
					
					return;
				}
				
				j=0;
				totalWcId.innerHTML = totalWcAmount;
				totalWcAmount = 0;
			}
	}

}

function displayWcNFBOutAmtTotal()
{
	var j = 0;
	var totalNFBWcAmount = 0;
	var totamt = 0;
	var wcNFBPrOutAmt = 0;
	var wcNFBSanctionedAmt =0;
	index = document.gmPeriodicInfoForm.outDetailIndex.value;
	
	for(i=0;i<index;++i) 
	{

		cgpanWc = findObj("cgpansForWc(key-"+i+")");	
		
		//alert(cgpanWc);

		if (cgpanWc!=null)
		{
			cgpanWcVal = cgpanWc.value;
			
			totalNFBWcId = document.getElementById("totalNFBWcOutAmt"+i);
			
			wcNFBPrOutAmt = findObj("wcNFBPrincipalOutstandingAmount("+cgpanWcVal+"-"+j+")");
			wcNFBPrIntAmt = findObj("wcNFBInterestOutstandingAmount("+cgpanWcVal+"-"+j+")");
			
			wcNFBDate = findObj("wcNFBOutstandingAsOnDate("+cgpanWcVal+"-"+j+")");
			
			wcNFBOutAmount = document.getElementById("wcNFBSanct(key-"+i+")");		
			wcNFBSanctionedAmt = wcNFBOutAmount.innerHTML;
			
			//alert("i :" + i);
			
			if(wcNFBSanctionedAmt==0)
			{
			
					while((wcNFBPrOutAmt != null))	
					{
						//alert("j :" + j);
						wcNFBPrOutAmt.disabled=true;
						wcNFBPrOutAmt.value="";
						
						wcNFBPrIntAmt.disabled=true;
						wcNFBPrIntAmt.value="";
						
						wcNFBDate.disabled=true;
						wcNFBDate.value="";
						++j;
						
						wcNFBPrOutAmt = findObj("wcNFBPrincipalOutstandingAmount("+cgpanWcVal+"-"+j+")");	
						//alert("wcNFBPrOutAmt" + wcNFBPrOutAmt);
						wcNFBPrIntAmt = findObj("wcNFBInterestOutstandingAmount("+cgpanWcVal+"-"+j+")");
						//alert("wcNFBPrIntAmt" + wcNFBPrIntAmt);
						wcNFBDate= findObj("wcNFBOutstandingAsOnDate("+cgpanWcVal+"-"+j+")");
						//alert("wcNFBDate" + wcNFBDate);
					}
					j=0;			
					//alert("after while loop");		
			}
			else{
			
				while((wcNFBPrOutAmt != null) && (wcNFBPrIntAmt != null))	
				{
					if ( !(isNaN(wcNFBPrOutAmt.value)) && (wcNFBPrOutAmt.value != ""))
					{
						pramt = parseInt(wcNFBPrOutAmt.value,10);
	
						totalNFBWcAmount += pramt;
					}
					if ( !(isNaN(wcNFBPrIntAmt.value)) && (wcNFBPrIntAmt.value != ""))
					{
						intamt = parseInt(wcNFBPrIntAmt.value,10);
						totalNFBWcAmount += intamt;
					}
	
					++j;
					wcNFBPrOutAmt = findObj("wcNFBPrincipalOutstandingAmount("+cgpanWcVal+"-"+j+")");	
					wcNFBPrIntAmt = findObj("wcNFBInterestOutstandingAmount("+cgpanWcVal+"-"+j+")");
				}
				if(totalNFBWcAmount> wcNFBSanctionedAmt)
				{
					alert("Outstanding Amount is more than the Sanctioned Amount For the CGPAN "+cgpanWcVal);	
					findObj("wcNFBPrincipalOutstandingAmount("+cgpanWcVal+"-"+(j-1)+")").value="";
					
					return;
				}
				
				j=0;
				totalNFBWcId.innerHTML = totalNFBWcAmount;
				totalNFBWcAmount = 0;
			
			}
			
			
		}
		//alert("i value:" + i);
	}

}

function enableReportingDate()
{
	var obj=document.forms[0].whetherNPAReported[0].checked;
	if(obj==true)
	{
		document.forms[0].reportingDate.disabled=false;
	}
	else if(obj==false)
	{
		document.forms[0].reportingDate.value="";
		document.forms[0].reportingDate.disabled=true;
	}

	
}



function validateFinalDisbursementOnLoad() 
{
	var j = 0;
	index = document.gmPeriodicInfoForm.disbursementEntryIndex.value;
	var matched=false;
	//alert(index);
	for(i=0;i<index;++i) 
	{
		cgpan = findObj("cgpans(key-"+i+")");      
		matched=false;
		if(cgpan!=null)
		{
			cgpanVal = cgpan.value;
			//alert(cgpanVal);
			disbAmt = findObj("disbursementAmount("+cgpanVal+"-"+j+")");
			disbDate = findObj("disbursementDate("+cgpanVal+"-"+j+")");
			finalDisb = findObj("finalDisbursement("+cgpanVal+"-"+j+")");                    
			
			while((finalDisb!=null))   
			{
//				alert("j " +  j + " " +finalDisb.checked);
				if(finalDisb!=null && finalDisb.checked)
				{
				disbAmt = findObj("disbursementAmount("+cgpanVal+"-"+(j+1)+")");
				disbDate = findObj("disbursementDate("+cgpanVal+"-"+(j+1)+")");
				finalDisb = findObj("finalDisbursement("+cgpanVal+"-"+(j+1)+")");
					if(disbAmt)
					{
						 disbAmt.value="";
						 disbAmt.disabled=true;
					}
					if(disbDate)
					{
						disbDate.value="";
						disbDate.disabled=true;
					}
					if(finalDisb)
					{
						finalDisb.checked=false;
						finalDisb.disabled=true;
					}
				}
				j++;
				disbAmt = findObj("disbursementAmount("+cgpanVal+"-"+j+")");
				disbDate = findObj("disbursementDate("+cgpanVal+"-"+j+")");
				finalDisb = findObj("finalDisbursement("+cgpanVal+"-"+j+")");
/*				else
				{
					if(disbAmt)
					{
						 disbAmt.disabled=false;
					}
					if(disbDate)
					{
						disbDate.disabled=false;
					}
					if(finalDisb)
					{
						finalDisb.disabled=false;
					}
				}*/
			}
			j=0;
		} 
	}
}

function setForumOthersEnabled()
{	
	var obj=findObj("courtName");
	var objOther=findObj("initiatedName");
	if(objOther!=null && objOther!="")
	{
		if ((obj.options[obj.selectedIndex].value)=="others")
		{
			document.forms[0].initiatedName.disabled=false;
		}
		else
		{
			document.forms[0].initiatedName.disabled=true;
			document.forms[0].initiatedName.value="";
		}
	}
	
}    

function checkProceedings()
{

//alert(document.forms[0].isRecoveryInitiated[0].checked);
//alert(document.forms[0].isRecoveryInitiated[1].checked);

	if(document.forms[0].isRecoveryInitiated[0].checked)
	{
		if(document.forms[0].courtName!=null && document.forms[0].courtName!="")
		{
			document.forms[0].courtName.disabled=false;
		}
		if(document.forms[0].initiatedName!=null && document.forms[0].initiatedName!="")
		{
			document.forms[0].initiatedName.disabled=false;
		}
		if(document.forms[0].legalSuitNo!=null && document.forms[0].legalSuitNo!="")
		{
			document.forms[0].legalSuitNo.disabled=false;
		}
		if(document.forms[0].dtOfFilingLegalSuit!=null && document.forms[0].dtOfFilingLegalSuit!="")
		{
			document.forms[0].dtOfFilingLegalSuit.disabled=false;
		}
		if(document.forms[0].forumName!=null && document.forms[0].forumName!="")
		{
			document.forms[0].forumName.disabled=false;
		
		}
		if(document.forms[0].location!=null && document.forms[0].location!="")
		{
			document.forms[0].location.disabled=false;
			
		}
		if(document.forms[0].amountClaimed!=null && document.forms[0].amountClaimed!="")
		{
			document.forms[0].amountClaimed.disabled=false;
			
		}
		if(document.forms[0].currentStatus!=null && document.forms[0].currentStatus!="")
		{
			document.forms[0].currentStatus.disabled=false;
			
		}
		if(document.forms[0].recoveryProceedingsConcluded!=null && document.forms[0].recoveryProceedingsConcluded!="")
		{
			document.forms[0].recoveryProceedingsConcluded[0].selected=true;
			document.forms[0].recoveryProceedingsConcluded[0].disabled=false;			
			document.forms[0].recoveryProceedingsConcluded[1].disabled=false;			

		}
		if(document.forms[0].effortsConclusionDate!=null && document.forms[0].effortsConclusionDate!="")
		{
			document.forms[0].effortsConclusionDate.disabled=false;

		}
		
		index = document.gmPeriodicInfoForm.npaIndex.value;	
		
		if(index==0)
		{
			index++;
		}		
		for(i=0;i<index;++i) 
		{
			actionType = findObj("recProcedures(key-"+i+").actionType");			
			details = findObj("recProcedures(key-"+i+").actionDetails");
			date = findObj("recProcedures(key-"+i+").actionDate");
			attachment = findObj("recProcedures(key-"+i+").attachmentName");						

			actionType.disabled=false;

			details.disabled=false;

			date.disabled=false;

			attachment.disabled=false;

		}	
	
	}
	else if (document.forms[0].isRecoveryInitiated[1].checked)
	{
	
		if(document.forms[0].courtName!=null && document.forms[0].courtName!="")
		{
			document.forms[0].courtName.disabled=true;
			document.forms[0].courtName.value="";
		}
		if(document.forms[0].initiatedName!=null && document.forms[0].initiatedName!="")
		{
			document.forms[0].initiatedName.disabled=true;
			document.forms[0].initiatedName.value="";
		}
		if(document.forms[0].legalSuitNo!=null && document.forms[0].legalSuitNo!="")
		{
			document.forms[0].legalSuitNo.disabled=true;
			document.forms[0].legalSuitNo.value="";
		}
		if(document.forms[0].dtOfFilingLegalSuit!=null && document.forms[0].dtOfFilingLegalSuit!="")
		{
			document.forms[0].dtOfFilingLegalSuit.disabled=true;
			document.forms[0].dtOfFilingLegalSuit.value="";
		}
		if(document.forms[0].forumName!=null && document.forms[0].forumName!="")
		{
			document.forms[0].forumName.disabled=true;
			document.forms[0].forumName.value="";
		}
		if(document.forms[0].location!=null && document.forms[0].location!="")
		{
			document.forms[0].location.disabled=true;
			document.forms[0].location.value="";
		}
		if(document.forms[0].amountClaimed!=null && document.forms[0].amountClaimed!="")
		{
			document.forms[0].amountClaimed.disabled=true;
			document.forms[0].amountClaimed.value="";
		}
		if(document.forms[0].currentStatus!=null && document.forms[0].currentStatus!="")
		{
			document.forms[0].currentStatus.disabled=true;
			document.forms[0].currentStatus.value="";
		}
		if(document.forms[0].recoveryProceedingsConcluded!=null && document.forms[0].recoveryProceedingsConcluded!="")
		{
			document.forms[0].recoveryProceedingsConcluded[0].selected=false;
			document.forms[0].recoveryProceedingsConcluded[0].disabled=true;			
			document.forms[0].recoveryProceedingsConcluded[1].selected=false;
			document.forms[0].recoveryProceedingsConcluded[1].disabled=true;			

		}
		if(document.forms[0].effortsConclusionDate!=null && document.forms[0].effortsConclusionDate!="")
		{
			document.forms[0].effortsConclusionDate.disabled=true;
			document.forms[0].effortsConclusionDate.value="";
		}
		
		index = document.gmPeriodicInfoForm.npaIndex.value;		
		
		//alert("index is "+index);
		
		if(index==0)
		{
			index++;
		}
		for(i=0;i<index;++i) 
		{
			actionType = findObj("recProcedures(key-"+i+").actionType");			
			details = findObj("recProcedures(key-"+i+").actionDetails");
			date = findObj("recProcedures(key-"+i+").actionDate");
			attachment = findObj("recProcedures(key-"+i+").attachmentName");						
			//alert(attachment.value);
			actionType.value="";
			actionType.disabled=true;

			details.value="";
			details.disabled=true;

			date.value="";
			date.disabled=true;

			attachment.value="";
			attachment.disabled=true;
			
			//alert(attachment.value);
		}	
	
	}
}



/*	************************End Of Guarantee Maintenance Script *************************/


/********after build 7 (by rp14480)***/

/*function alertOption()
{
	var obj=findObj("alertTitle");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
		document.forms[0].newAlert.disabled=false;
	}
	else
	{
		document.forms[0].newAlert.value="";
		document.forms[0].newAlert.disabled=true;
	}
}*/

function exceptionOption()
{
	var obj=findObj("exceptionTitle");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
		document.forms[0].newExceptionTitle.disabled=false;
	}
	else
	{
		document.forms[0].newExceptionTitle.value="";
		document.forms[0].newExceptionTitle.disabled=true;
	}

}

function designationOption()
{
	var obj=findObj("desigName");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
		document.forms[0].newDesigName.disabled=false;
	}
	else
	{
		document.forms[0].newDesigName.value="";
		document.forms[0].newDesigName.disabled=true;
	}
}

/*function plrOption()
{
	var obj=findObj("bankName");
	if ((obj.options[obj.selectedIndex].value)=="")
	{		
		document.forms[0].newBankName.disabled=false;
	}
	else
	{
		
		document.forms[0].newBankName.value="";
		document.forms[0].newBankName.disabled=true;
	}
}*/

function danDelivery()
{
	document.forms[0].danDelivery[0].checked=true;
	document.forms[0].danDelivery[1].checked=true;
}
function selectMember()
{
	document.forms[0].memberBank.options.selectedIndex=0;	
}
function choosePLR()
{
	var value=document.forms[0].PLR[0].checked;
	
	if(value==false){

		document.forms[0].BPLR.value="";
		document.forms[0].BPLR.disabled=true;
	}
	else{
		document.forms[0].BPLR.disabled=false;
	}
	
}

function disableDefRate()
{
     var obj = findObj("defaultRate");
     if(document.forms[0].isDefaultRateApplicable[0].checked)     
     {
         document.forms[0].defaultRate.value="";         
         document.forms[0].defaultRate.disabled=true;         
     }
}

function enableDefRate()
{
     var obj = findObj("defaultRate");
     if(document.forms[0].isDefaultRateApplicable[1].checked)     
     {
         document.forms[0].defaultRate.disabled=false;
     }
}

function setCPOthersEnabled()
{	
	var obj=findObj("forumthrulegalinitiated");
	var objOther=findObj("otherforums");
	if(objOther!=null && objOther!="")
	{
		if((obj.options[obj.selectedIndex].value)=="Civil Court")
		{			
			document.forms[0].otherforums.disabled=true;
			document.forms[0].otherforums.value="";

		}
		if((obj.options[obj.selectedIndex].value)=="DRT")
		{			
			document.forms[0].otherforums.disabled=true;
			document.forms[0].otherforums.value="";

		}
		if((obj.options[obj.selectedIndex].value)=="LokAdalat")
		{			
			document.forms[0].otherforums.disabled=true;
			document.forms[0].otherforums.value="";

		}
		if((obj.options[obj.selectedIndex].value)=="Revenue Recovery Autority")
		{			
			document.forms[0].otherforums.disabled=true;
			document.forms[0].otherforums.value="";

		}	
		if((obj.options[obj.selectedIndex].value)=="Securitisation Act ")
		{			
			document.forms[0].otherforums.disabled=true;
			document.forms[0].otherforums.value="";

		}
		if((obj.options[obj.selectedIndex].value)=="others")
		{			
			document.forms[0].otherforums.disabled=false;			
		}						
	}
	
}    

function enableAppFilingTimeLimit()
{	
	if (document.forms[0].rule[1].checked)
	{
		document.forms[0].noOfDays.disabled=true;
		document.forms[0].noOfDays.value="";
		document.forms[0].periodicity.disabled=false;
//		document.forms[0].periodicity.options[0].selected=true;
	}else if (document.forms[0].rule[0].checked)
	{
		document.forms[0].noOfDays.disabled=false;
//		document.forms[0].noOfDays.value="";
		document.forms[0].periodicity.disabled=true;
		document.forms[0].periodicity.options[0].selected=true;
	}
}

function enableAppFilingTimeLimitInParameterPage()
{	
	if (document.forms[0].rule[1].checked)
	{
		document.forms[0].noOfDays.disabled=true;
		// document.forms[0].noOfDays.value="";
		document.forms[0].periodicity.disabled=false;
		// document.forms[0].periodicity.options[0].selected=true;
	}else if (document.forms[0].rule[0].checked)
	{
		document.forms[0].noOfDays.disabled=false;
		// document.forms[0].noOfDays.value="";
		document.forms[0].periodicity.disabled=true;
		// document.forms[0].periodicity.options[0].selected=true;
	}
}


function enableDefaultRate()
{
	if (document.forms[0].defaultRateApplicable[0].checked)
	{
		document.forms[0].defaultRate.disabled=false;
//		document.forms[0].defaultRate.value="";
		document.forms[0].defRateValidFrom.disabled=false;
//		document.forms[0].defRateValidFrom.value="";
		document.forms[0].defRateValidTo.disabled=false;
//		document.forms[0].defRateValidTo.value="";
	}
	else if (document.forms[0].defaultRateApplicable[1].checked)
	{
		document.forms[0].defaultRate.disabled=true;
		document.forms[0].defaultRate.value="";
		document.forms[0].defRateValidFrom.disabled=true;
		document.forms[0].defRateValidFrom.value="";
		document.forms[0].defRateValidTo.disabled=true;
		document.forms[0].defRateValidTo.value="";
	}
}

function calculateFirstSettlementAmount()
{
   var penaltyAmountValue;
   var approvedAmountVal;
   var pendingAmntValue;
   var settlementAmnt;
 
 if(document.cpTcDetailsForm.firstSettlementIndexValue)
 {
   firstIndex = document.cpTcDetailsForm.firstSettlementIndexValue.value;   
   // alert('firstIndex' + firstIndex);
   for(i=0;i<firstIndex;++i) 
   {
	approvedAmount = document.getElementById("ApprovedAmount#"+i);		

	if(approvedAmount)
	{

		approvedAmountVal = approvedAmount.innerHTML;

	}

	// borrowerId = document.getElementById("BORROWERID#"+i);
	borrowerId = document.getElementById("BORROWERID##"+i);

	if(borrowerId)
	{	        
		// borrowerIdVal = borrowerId.innerHTML;
		borrowerIdVal = borrowerId.value;
		// alert('borrowerIdVal :' + borrowerIdVal);
	}

	cgclan = document.getElementById("cgclan#"+i);
	if(cgclan)
	{
		cgclanVal = cgclan.innerHTML;
	}		

	penaltyAmount = findObj("penaltyFees("+"F" + "#"+borrowerIdVal +"#"+ cgclanVal +")");
	if(penaltyAmount)
	{
		penaltyAmountValue = penaltyAmount.value;
	}


	pendingAmnt = findObj("pendingAmntsFromMLI("+"F" + "#"+borrowerIdVal +"#"+ cgclanVal +")");

	if(pendingAmnt)
	{
		pendingAmntValue = pendingAmnt.value;
	}	

        if(!(isNaN(penaltyAmountValue)) && penaltyAmountValue != "")
        {
            settlementAmnt = parseFloat(approvedAmountVal) + parseFloat(penaltyAmountValue);
        }
        if(!(isNaN(pendingAmntValue)) && pendingAmntValue != "")
        {
            settlementAmnt = settlementAmnt - parseFloat(pendingAmntValue);
        }
       /*
	if ((!(isNaN(approvedAmountVal)) && approvedAmountVal != "") &&
	   (!(isNaN(penaltyAmountValue)) && penaltyAmountValue != "") &&
	   (!(isNaN(pendingAmntValue)) && pendingAmntValue != "")) 
	   {
		settlementAmnt = parseFloat(approvedAmountVal) + parseFloat(penaltyAmountValue) - parseFloat(pendingAmntValue);
	   }
        */
	settleAmountObj = findObj("settlementAmounts("+ "F" + "#"+borrowerIdVal +"#" +cgclanVal +")");

	if(settleAmountObj)
	{
		settleAmountObj.value = settlementAmnt;
	}
	settlementAmnt = 0.0;
   }
   }
    
}

function calculateSecondSettlementAmount()
{
   var penaltyAmountValue = 0;
   var approvedAmountVal = 0;

   var pendingAmntValue = 0;
   var settlementAmnt = 0;
   
 if(document.cpTcDetailsForm.secondSettlementIndexValue)  
 {
   secondIndex = document.cpTcDetailsForm.secondSettlementIndexValue.value;
   // alert('Hi');
   // alert('secondIndex :'+secondIndex);
   for(i=0;i<secondIndex;++i) 
   {
	approvedAmount = document.getElementById("ApprovedAmount@"+i);		
	
	if(approvedAmount)
	{
		
		approvedAmountVal = approvedAmount.innerHTML;
	
	}
	// alert('approvedAmountVal :' + approvedAmountVal);

	// borrowerId = document.getElementById("BORROWERID@"+i);

	borrowerId = document.getElementById("BORROWERID@"+i);
	
	if(borrowerId)
	{
		// borrowerIdVal = borrowerId.innerHTML;
		borrowerIdVal = borrowerId.value;
		// alert('borrowerIdVal :' + borrowerIdVal);
	}
	// alert('borrowerIdVal :' + borrowerIdVal);
	cgclan = document.getElementById("cgclan@"+i);
	if(cgclan)
	{
		cgclanVal = cgclan.innerHTML;
	}		
	// alert('cgclan :' + cgclanVal);
	penaltyAmount = findObj("penaltyFees("+"S" + "#"+borrowerIdVal +"#"+ cgclanVal +")");
	if(penaltyAmount)
	{
		penaltyAmountValue = penaltyAmount.value;
	}
	// alert('penaltyAmountValue :' + penaltyAmountValue);
					
	pendingAmnt = findObj("pendingAmntsFromMLI("+"S" + "#"+borrowerIdVal +"#"+ cgclanVal +")");
	
	if(pendingAmnt)
	{
		pendingAmntValue = pendingAmnt.value;
	}	
	// alert('pendingAmntValue :' + pendingAmntValue);
	if(!(isNaN(penaltyAmountValue)) && penaltyAmountValue != "")
	{
	     settlementAmnt = parseFloat(approvedAmountVal) + parseFloat(penaltyAmountValue);
	}
	
	if(!(isNaN(pendingAmntValue)) && pendingAmntValue != "")
	{
	    settlementAmnt = settlementAmnt - parseFloat(pendingAmntValue);
	}
	
	/*
	if ((!(isNaN(approvedAmountVal)) && approvedAmountVal != "") &&
	   (!(isNaN(penaltyAmountValue)) && penaltyAmountValue != "") &&
	   (!(isNaN(pendingAmntValue)) && pendingAmntValue != "")) 
	   {
		settlementAmnt = parseFloat(approvedAmountVal) + parseFloat(penaltyAmountValue) - parseFloat(pendingAmntValue);
	   }
	*/
	
	// alert('settlementAmnt :' + settlementAmnt);
	settleAmountObj = findObj("settlementAmounts("+ "S" + "#"+borrowerIdVal +"#" +cgclanVal +")");
	
	if(settleAmountObj)
	{
		settleAmountObj.value = settlementAmnt;
	}
	settlementAmnt = 0.0;
   }
  }
}

function calculateAmountPayable()
{
   //alert("ABC");
   var tcOutstandingAmtNPAValue = 0;
   var tcInterestChargesValue = 0;
   var wcPrincipalAsOnNPAValue = 0;
   var wcOtherChargesAsOnNPAValue = 0;
   var totalOSAmountAsOnNPAValue = 0;
   
   var tcPrinRecoveriesAfterNPAValue = 0;
   var tcInterestChargesRecovAfterNPAValue = 0;
   var wcPrincipalRecoveAfterNPAValue = 0;
   var wcothercgrgsRecAfterNPAValue = 0;
   var totalrecoveriesafternpaValue =0;
   var totalAmntPayableNowValue =0;     
   
  tcOutstandingAmtNPAValue=   document.getElementById("tcOutstandingAmtNPA").innerHTML;
  
  if(!(isNaN(tcOutstandingAmtNPAValue)) && tcOutstandingAmtNPAValue!="")
  {
     totalOSAmountAsOnNPAValue = totalOSAmountAsOnNPAValue + parseFloat(tcOutstandingAmtNPAValue);
  }
  
  tcInterestCharges = findObj("tcInterestChargeForThisBorrower");
  if(tcInterestCharges != null && tcInterestCharges != "")  
  {
      tcInterestChargesValue = tcInterestCharges.value;
  }  
  
  if(!(isNaN(tcInterestChargesValue)) && tcInterestChargesValue!="")
  {
      totalOSAmountAsOnNPAValue = totalOSAmountAsOnNPAValue + parseFloat(tcInterestChargesValue);
  }
  
  wcPrincipalAsOnNPAValue = document.getElementById("wcPrincipalNPA").innerHTML;
  
  if(!(isNaN(wcPrincipalAsOnNPAValue)) && wcPrincipalAsOnNPAValue!="")
  {
     totalOSAmountAsOnNPAValue = totalOSAmountAsOnNPAValue + parseFloat(wcPrincipalAsOnNPAValue);
  }  
    
  wcOtherChargesAsOnNPA = findObj("wcOtherChargesAsOnNPA");
  if(wcOtherChargesAsOnNPA != null && wcOtherChargesAsOnNPA != "")  
  {
      wcOtherChargesAsOnNPAValue = wcOtherChargesAsOnNPA.value;
  }
  
  if(!(isNaN(wcOtherChargesAsOnNPAValue)) && wcOtherChargesAsOnNPAValue!="")
  {
      totalOSAmountAsOnNPAValue = totalOSAmountAsOnNPAValue + parseFloat(wcOtherChargesAsOnNPAValue);
  }

  var totalOSAmountAsOnNPAObj= findObj('totalAmntAsOnNPA');
  if(totalOSAmountAsOnNPAObj != null)
  {
        if(!(isNaN(totalOSAmountAsOnNPAValue)) && totalOSAmountAsOnNPAValue!="")
        {
  	    totalOSAmountAsOnNPAObj.innerHTML = totalOSAmountAsOnNPAValue;     
  	}
  }
  

  tcPrinRecovriesAfterNPA = findObj("tcPrinRecoveriesAfterNPA");
  if(tcPrinRecovriesAfterNPA != null && tcPrinRecovriesAfterNPA != "")  
  {
      tcPrinRecoveriesAfterNPAValue = tcPrinRecovriesAfterNPA.value;
  }
  
  if(!(isNaN(tcPrinRecoveriesAfterNPAValue)) && tcPrinRecoveriesAfterNPAValue!="")
  {
      totalrecoveriesafternpaValue = totalrecoveriesafternpaValue + parseFloat(tcPrinRecoveriesAfterNPAValue);
  }     

  tcInterestChrgsRecovAfterNPA = findObj("tcInterestChargesRecovAfterNPA");
  if(tcInterestChrgsRecovAfterNPA != null && tcInterestChrgsRecovAfterNPA != "")  
  {
      tcInterestChargesRecovAfterNPAValue = tcInterestChrgsRecovAfterNPA.value;
  }
  
  if(!(isNaN(tcInterestChargesRecovAfterNPAValue)) && tcInterestChargesRecovAfterNPAValue!="")
  {
      totalrecoveriesafternpaValue = totalrecoveriesafternpaValue + parseFloat(tcInterestChargesRecovAfterNPAValue);
  }
   
  wcPrncpalRecoveAfterNPA = findObj("wcPrincipalRecoveAfterNPA");
  if(wcPrncpalRecoveAfterNPA != null && wcPrncpalRecoveAfterNPA != "")  
  {
      wcPrincipalRecoveAfterNPAValue = wcPrncpalRecoveAfterNPA.value;
  }
  
  if(!(isNaN(wcPrincipalRecoveAfterNPAValue)) && wcPrincipalRecoveAfterNPAValue!="")
  {
      totalrecoveriesafternpaValue = totalrecoveriesafternpaValue + parseFloat(wcPrincipalRecoveAfterNPAValue);
  }     
  
  wcotherchrgsRecAfterNPA = findObj("wcothercgrgsRecAfterNPA");
  if(wcotherchrgsRecAfterNPA != null && wcotherchrgsRecAfterNPA != "")  
  {
      wcothercgrgsRecAfterNPAValue = wcotherchrgsRecAfterNPA.value;
  }
  
  if(!(isNaN(wcothercgrgsRecAfterNPAValue)) && wcothercgrgsRecAfterNPAValue!="")
  {
      totalrecoveriesafternpaValue = totalrecoveriesafternpaValue + parseFloat(wcothercgrgsRecAfterNPAValue);
  }     
  
  ttlrecoveriesafternpa = findObj("totalrecoveriesafternpa");
  // alert("hi 1");
  if(ttlrecoveriesafternpa != null && ttlrecoveriesafternpa != "")
  {
  // alert("hi 2");
      ttlrecoveriesafternpa.innerHTML = totalrecoveriesafternpaValue;
      // alert("totalrecoveriesafternpaValue" + totalrecoveriesafternpaValue);
  }
  
  var payableNow = document.cpTcDetailsForm.totalAmntPayableNow;
  if(payableNow)
  {
       payableNow = payableNow.value;
       // alert('payableNow :' + payableNow);
  }
  
  
  // alert("wcothercgrgsRecAfterNPAValue" + wcothercgrgsRecAfterNPAValue);   
  // alert("hi");
  ttlAmntPayableNow = findObj("totalAmntPayableNow");
  totalAmntPayableNowValue = parseFloat(totalOSAmountAsOnNPAValue) - parseFloat(totalrecoveriesafternpaValue);
  /*  
  if(payableNow != "")
  {
       ttlAmntPayableNow.value = payableNow;
  }
  else
  {
       ttlAmntPayableNow.value = totalAmntPayableNowValue;           
  }
  */
// alert("wcothercgrgsRecAfterNPAValue" + wcothercgrgsRecAfterNPAValue);   
  // alert("hi"); 
  ttlAmntPayableNow.value = totalAmntPayableNowValue;    
}

function disableSecondClmApprvdAMnt()
{ 
   var memberIdVal;
   var clmrefnumberVal;
   var decisionVal;
   var cgclan;
   
   if(document.cpTcDetailsForm.secondClmDtlIndexValue)
   {
	   secondIndex = document.cpTcDetailsForm.secondClmDtlIndexValue.value;   

	   for(i=0;i<secondIndex;++i) 
	   {
		cgclan = document.cpTcDetailsForm.CGCLAN.value;   
		// alert("CGCLAN" + cgclan);
		memberId = document.getElementById("MEMBERID#"+i);		

		if(memberId)
		{

			memberIdVal = memberId.innerHTML;

		}
		// alert("MEMBER ID" + memberIdVal);

		clmrefnumber = document.getElementById("CLMREFNUMBERID#"+i);

		if(clmrefnumber)
		{
			clmrefnumberVal = clmrefnumber.innerHTML;
		}			
		// alert("CLM REF NUMBER" + clmrefnumberVal);

		decision = findObj("decision("+"F" + "#"+memberIdVal +"#"+ clmrefnumberVal +")");
		if(decision)
		{
			decisionVal = decision.value;
		}						
		// alert("decision" + decisionVal);
	   }      
    }
}
function invalidateSession(path)
{
	var newPath=path+"/logout.do?method=logout";
		
	var iX = window.document.body.offsetWidth + window.event.clientX ;
      	var iY = window.event.clientY ;
      	
	if (iX <= 30 && iY < 0 ) 
	{
		submitForm(newPath);
		//alert("Hi");
	}
	
}

/*function modifyMLI()
{

document.forms[0].supportMCGF.disabled=true;

}
*/

function investeeGrpOption()
{
	var obj=findObj("investeeGroup");
	// varmodinvgrp ="";
/*	if(document.ifForm.modifiedInvstGroup)
        {
	    varmodinvgrp = document.ifForm.modifiedInvstGroup.value;
	}*/
	// alert('varmodinvgrp :' + varmodinvgrp);
	if ((obj.options[obj.selectedIndex].value)=="")
	{
		document.forms[0].newInvesteeGrp.disabled=false;
		document.forms[0].investeeGroup.value="";

		document.forms[0].modInvesteeGroup.value="";
		document.forms[0].modInvesteeGroup.disabled=true;
	}
	else
	{
	     
		document.forms[0].newInvesteeGrp.value="";
		document.forms[0].newInvesteeGrp.disabled=true;
		document.forms[0].investeeGroup.value=obj.options[obj.selectedIndex].value;
		document.forms[0].investeeGroup.disabled=false;
/*		if(varmodinvgrp != "")
		{
			document.forms[0].modInvesteeGroup.value=varmodinvgrp;
			document.ifForm.modifiedInvstGroup.value = "";
		}
		else
		{*/
//		     document.forms[0].modInvesteeGroup.value=obj.options[obj.selectedIndex].value;
//		}
		document.forms[0].modInvesteeGroup.disabled=false;
	}
	varmodinvgrp ="";
}

function investeeOption()
{
	var obj=findObj("newInvesteeFlag");
	if (document.ifForm.newInvesteeFlag[0].checked)
	{

		document.ifForm.newInvestee.disabled=false;
		var obj1= findObj("investee1");
		document.forms[0].investee1.options[0].selected=true;
		document.ifForm.investee1.disabled=true;

		document.forms[0].modInvestee.value="";
		document.forms[0].modInvestee.disabled=true;
//		document.forms[0].investeeNetWorth.value="";
//		document.forms[0].investeeTangibleAssets.value="";
		document.forms[0].investeeTangibleAssets.disabled=false;
		document.forms[0].investeeNetWorth.disabled=false;
	}
	else if (document.forms[0].newInvesteeFlag[1].checked)
	{
//		document.forms[0].investee1.value="";
		document.forms[0].investee1.disabled=false;
		document.forms[0].newInvestee.value="";
		document.forms[0].newInvestee.disabled=true;
		document.forms[0].modInvestee.disabled=false;

	}
	
}

function maturity()
{
	var obj=findObj("maturityType");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
		document.forms[0].newMaturityType.disabled=false;
//		document.forms[0].newMaturityType.value="";

		document.forms[0].modMaturityType.value="";
		document.forms[0].modMaturityType.disabled=true;

		document.forms[0].maturityDescription.value="";
	}
	else
	{
		document.forms[0].newMaturityType.value="";
		document.forms[0].newMaturityType.disabled=true;

//		document.forms[0].modMaturityType.value=obj.options[obj.selectedIndex].value;
		document.forms[0].modMaturityType.disabled=false;
	}
}

function newMaturity()
{
	var obj=findObj("maturityType");
	obj.selectedIndex=0;

	document.forms[0].modMaturityType.value="";
	document.forms[0].modMaturityType.disabled=true;
	document.forms[0].maturityDescription.value="";
}

function budgetHeadOption()
{
	var obj=findObj("budgetHead");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
//		document.forms[0].newBudgetHead.value="";
		document.forms[0].newBudgetHead.disabled=false;
		document.forms[0].modBudgetHead.value="";
		document.forms[0].modBudgetHead.disabled=true;
	}
	else
	{
		document.forms[0].newBudgetHead.value="";
		document.forms[0].newBudgetHead.disabled=true;
//		document.forms[0].modBudgetHead.value=obj.options[obj.selectedIndex].value;
		document.forms[0].modBudgetHead.disabled=false;
	}
}

function budgetSubHeadOption()
{
	var obj=findObj("budgetSubHeadTitle");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
//		document.forms[0].newBudgetSubHeadTitle.value="";
		document.forms[0].newBudgetSubHeadTitle.disabled=false;
		document.forms[0].modBudgetSubHeadTitle.value="";
		document.forms[0].modBudgetSubHeadTitle.disabled=true;
	}
	else
	{
		document.forms[0].newBudgetSubHeadTitle.value="";
		document.forms[0].newBudgetSubHeadTitle.disabled=true;
//		document.forms[0].modBudgetSubHeadTitle.value=obj.options[obj.selectedIndex].value;
		document.forms[0].modBudgetSubHeadTitle.disabled=false;
	}
}

function instrumentNameOption()
{
	var obj=findObj("instrumentName");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
//		document.forms[0].newInstrumentName.value="";
		document.forms[0].newInstrumentName.disabled=false;
		document.forms[0].modInstrumentName.value="";
		document.forms[0].modInstrumentName.disabled=true;
	}
	else
	{
		document.forms[0].newInstrumentName.value="";
		document.forms[0].newInstrumentName.disabled=true;
//		document.forms[0].modInstrumentName.value=obj.options[obj.selectedIndex].value;
		document.forms[0].modInstrumentName.disabled=false;
	}
}

function instFeatureOption()
{
	var obj=findObj("instrumentFeatures");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
//		document.forms[0].newInstrumentFeatures.value="";
		document.forms[0].newInstrumentFeatures.disabled=false;
		document.forms[0].modInstrumentFeatures.value="";
		document.forms[0].modInstrumentFeatures.disabled=true;
	}
	else
	{
		document.forms[0].newInstrumentFeatures.value="";
		document.forms[0].newInstrumentFeatures.disabled=true;
//		document.forms[0].modInstrumentFeatures.value=obj.options[obj.selectedIndex].value;
		document.forms[0].modInstrumentFeatures.disabled=false;
	}
}

function instSchemeOption()
{
	var obj=findObj("instrumentSchemeType");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
//		document.forms[0].newInstrumentSchemeType.value="";
		document.forms[0].newInstrumentSchemeType.disabled=false;
		document.forms[0].modInstrumentSchemeType.value="";
		document.forms[0].modInstrumentSchemeType.disabled=true;
	}
	else
	{
		document.forms[0].newInstrumentSchemeType.value="";
		document.forms[0].newInstrumentSchemeType.disabled=true;
//		document.forms[0].modInstrumentSchemeType.value=obj.options[obj.selectedIndex].value;
		document.forms[0].modInstrumentSchemeType.disabled=false;
	}
}

function ratingOption()
{
	var obj=findObj("rating");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
//		document.forms[0].newRating.value="";
		document.forms[0].newRating.disabled=false;
		document.forms[0].modRating.value="";
		document.forms[0].modRating.disabled=true;
	}
	else
	{
		document.forms[0].newRating.value="";
		document.forms[0].newRating.disabled=true;
//		document.forms[0].modRating.value=obj.options[obj.selectedIndex].value;
		document.forms[0].modRating.disabled=false;
	}
}

function holidayDateOption()
{
	var obj=findObj("holidayDate");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
//		document.forms[0].newHolidayDate.value="";
		document.forms[0].newHolidayDate.disabled=false;
		document.forms[0].modHolidayDate.value="";
		document.forms[0].modHolidayDate.disabled=true;
		document.forms[0].holidayDescription.value="";
		document.forms[0].holidayDescription.disabled=false;
	}
	else
	{
		document.forms[0].newHolidayDate.value="";
		document.forms[0].newHolidayDate.disabled=true;
//		document.forms[0].modHolidayDate.value=obj.options[obj.selectedIndex].value;
		document.forms[0].modHolidayDate.disabled=false;
	}
}

function enableCheque()
{
	var obj=findObj("ifChequed");
		
		if(document.forms[0].ifChequed[0].checked)
			{
				document.forms[0].bankName.value="";
				document.forms[0].bankName.disabled=false;
				document.forms[0].chequeNumber.value="";
				document.forms[0].chequeNumber.disabled=false;
				document.forms[0].chequeDate.value="";
				document.forms[0].chequeDate.disabled=false;
				document.forms[0].chequeAmount.value="";
				document.forms[0].chequeAmount.disabled=false;
				document.forms[0].chequeIssuedTo.value="";
				document.forms[0].chequeIssuedTo.disabled=false;				
			}
		else if(document.forms[0].ifChequed[1].checked)
			{		
				document.forms[0].bankName.value="";
				document.forms[0].bankName.disabled=true;
				document.forms[0].chequeNumber.value="";
				document.forms[0].chequeNumber.disabled=true;
				document.forms[0].chequeDate.value="";
				document.forms[0].chequeDate.disabled=true;
				document.forms[0].chequeAmount.value="";
				document.forms[0].chequeAmount.disabled=true;
				document.forms[0].chequeIssuedTo.value="";
				document.forms[0].chequeIssuedTo.disabled=true;
			}
}

function negativeNumbers(myfield, e)
{
	var key;
	var keychar;

	if (window.event)
	   key = window.event.keyCode;
	else if (e)
	   key = e.which;
	else
	   return true;
	keychar = String.fromCharCode(key);

	// control keys
	if ((key==null) || (key==0) || (key==8) ||
	    (key==9) || (key==13) || (key==27) )
	   return true;

	// numbers
	else if ((("0123456789-").indexOf(keychar) > -1))
	{
//		alert(keychar);
		var index=myfield.value.indexOf('-');
		
		var val=myfield.value.toString();
		if ((val.length > 0 && ("-").indexOf(keychar) > -1) || 
			(myfield.value.indexOf('-') > -1  && ("023456789-").indexOf(keychar) > -1))
		{
			return false;
		}
		if(myfield.value.indexOf('-') > -1  && ("-").indexOf(keychar) > -1)
		{
			return false;
		}

		
		if(index > -1)
		{
			var str=val.substring(index,val.length);
			
			if(str.length>1)
			{
				return false;
			}

			//alert("index, str "+index+" "+str);
		}
		return true;
	}	
	   
	else
	   return false;
}

function isValidNegative(field)
{
	if(!isNegativeValid(field.value))
	{
		field.focus();
		field.value='';
		return false;
	}
}

function isNegativeValid(thestring)
{
//alert(thestring)
	if(thestring && thestring.length)
	{
		//alert("inner");
		for (i = 0; i < thestring.length; i++) {
			ch = thestring.substring(i, i+1);
			if ((ch < "0" || ch > "9")  && ch!="-")
			  {
			  //alert("The numbers may contain digits 0 thru 9 only!");
			  return false;
			  }
		}
	}
	else
	{
		return false;
	}
    return true;
}

function negDecNumbers(myfield, e)
{
	var key;
	var keychar;

	if (window.event)
	   key = window.event.keyCode;
	else if (e)
	   key = e.which;
	else
	   return true;
	keychar = String.fromCharCode(key);

	// control keys
	if ((key==null) || (key==0) || (key==8) ||
	    (key==9) || (key==13) || (key==27) )
	   return true;

	// numbers
	else if ((("0123456789-.").indexOf(keychar) > -1))
	{
//		alert(keychar);
		var index=myfield.value.indexOf('-');
		var index1=myfield.value.indexOf('.');
		
		var val=myfield.value.toString();
		if ((val.length > 0 && ("-").indexOf(keychar) > -1) || 
			(myfield.value.indexOf('-') > -1  && ("023456789-").indexOf(keychar) > -1))
		{
			return false;
		}
		if(myfield.value.indexOf('-') > -1  && ("-").indexOf(keychar) > -1)
		{
			return false;
		}

		
		if(index > -1)
		{
			var str=val.substring(index,val.length);
			
			if(str.length>1)
			{
				return false;
			}

			//alert("index, str "+index+" "+str);
		}

		if(index1 > -1)
		{
			var str=val.substring(index1,val.length);
			
			if(str.length>2)
			{
				return false;
			}

			//alert("index, str "+index+" "+str);
		}
		return true;
	}	
	   
	else
	   return false;
}

function isValidNegDec(field)
{
	if(!isNegDecValid(field.value))
	{
		field.focus();
		field.value='';
		return false;
	}
}

function isNegDecValid(thestring)
{
//alert(thestring)
	if(thestring && thestring.length)
	{
		//alert("inner");
		for (i = 0; i < thestring.length; i++) {
			ch = thestring.substring(i, i+1);

			if ((ch < "0" || ch > "9")  && ch != "-" && ch != '.')
			  {
			  //alert("The numbers may contain digits 0 thru 9 only!");
			  return false;
			  }
		}
	}
	else
	{
		return false;
	}
    return true;
}

function setTotalAppropriated(object)
{
	var total=0;
	var penaltyObject="";
	var guaranteeFeeObject="";
	for(i=0;;i++)
	{
		var flagObject=findObj("appropriatedFlags(key-"+i+")");
//		alert(flagObject.name+" "+flagObject.value+" "+flagObject.checked);
		if(flagObject)
		{
			//alert(flagObject.name+""+flagObject.value+""+flagObject.checked);

			if(flagObject.checked)
			{
				penaltyObject=findObj("penalties(key-"+i+")");
				guaranteeFeeObject=findObj("amountsRaised(key-"+i+")");
				total+=parseFloat(penaltyObject.value)+parseFloat(guaranteeFeeObject.value);
//				alert(total);
			}
		}
		else
		{
			break;
		}
	}

	//alert(total);
	var appAmount=document.getElementById('appropriatedAmount');
	appAmount.innerHTML=Math.round(total);

	var shortExcessAmount=document.getElementById('shortOrExcessAmount');
	var allocatedAmount=document.getElementById('allocatedAmount').innerHTML;
	
	//alert(shortExcessAmount+","+allocatedAmount);

	shortExcessAmount.innerHTML=Math.round(parseFloat(allocatedAmount)-parseFloat(total));

}


function Blink(layerName){
 if (NS4 || IE4) {

	 if(i%2==0)
	 {
		 eval(layerRef+'["'+layerName+'"]'+
		 styleSwitch+'.visibility="visible"');
	 }
	 else
	 {
		 eval(layerRef+'["'+layerName+'"]'+
		 styleSwitch+'.visibility="hidden"');
	 }
 }
 if(i<1)
 {
 	i++;
 }
 else
 {
 	i--
 }
 setTimeout("Blink('"+layerName+"')",blink_speed);
}
//  End -->


function ioFlagOption()
{
	var obj=findObj("inflowOutFlowFlag");
	if (document.ifForm.inflowOutFlowFlag[0].checked)
	{
		//enable receipt number
		document.forms[0].receiptNumber.value="";
		document.forms[0].receiptNumber.disabled=false;
		document.forms[0].investmentRefNumber.value="";
		document.forms[0].investmentRefNumber.disabled=true;
		document.forms[0].instrumentType.value="";
		document.forms[0].instrumentNumber.value="";
		document.forms[0].instrumentDate.value="";
		document.forms[0].instrumentAmount.value="";
		document.forms[0].drawnBank.value="";
		document.forms[0].drawnBranch.value="";
		document.forms[0].payableAt.value="";
	}
	else
	{
		//enable inv ref nos
		document.forms[0].receiptNumber.value="";
		document.forms[0].receiptNumber.disabled=true;
//		document.forms[0].investmentRefNumber.value="";
		document.forms[0].investmentRefNumber.disabled=false;
	}
}


function dispTotalAmountPV() {
	var totalAmount = 0;
	var amtVal;
	var dcVal;
	var dbAmt = 0;
	var crAmt = 0;

	for(i=0;;++i) 
	{
		var dcObj = findObj("voucherDetails(key-"+i+").debitOrCredit");	
		if(dcObj==null)
		{
			break;
		}
		else
		{
			dcVal = dcObj.value;
		}

		var amtObj = findObj("voucherDetails(key-"+i+").amountInRs");	
		if(amtObj==null)
		{
			break;
		}
		else
		{
			amtVal = amtObj.value;
			if (!(isNaN(amtVal)) && amtVal != "")
			{
				if (dcVal=="D")
				{
					dbAmt += parseInt(amtVal,10);
				}
				else if (dcVal=="C")
				{
					crAmt += parseInt(amtVal,10);
				}
			}
		}
	}
	totalAmount = crAmt - dbAmt;
	var totalAmtObj = findObj("amount");
	totalAmtObj.value=totalAmount;
}

function dispTotalAmountRV() {
	var totalAmount = 0;
	var amtVal;
	var dcVal;
	var dbAmt = 0;
	var crAmt = 0;

	for(i=0;;++i) 
	{
		var dcObj = findObj("voucherDetails(key-"+i+").debitOrCredit");	
		if(dcObj==null)
		{
			break;
		}
		else
		{
			dcVal = dcObj.value;
		}

		var amtObj = findObj("voucherDetails(key-"+i+").amountInRs");	
		if(amtObj==null)
		{
			break;
		}
		else
		{
			amtVal = amtObj.value;
			if (!(isNaN(amtVal)) && amtVal != "")
			{
				if (dcVal=="D")
				{
					dbAmt += parseInt(amtVal,10);
				}
				else if (dcVal=="C")
				{
					crAmt += parseInt(amtVal,10);
				}
			}
		}
	}
	totalAmount = dbAmt - crAmt;
	var totalAmtObj = findObj("amount");
	totalAmtObj.value=totalAmount;
}

function dispTotalAmountJV() {
	var totalAmount = 0;
	var amtVal;
	var dcVal;
	var dbAmt = 0;
	var crAmt = 0;

	for(i=0;;++i) 
	{
		var dcObj = findObj("voucherDetails(key-"+i+").debitOrCredit");	
		if(dcObj==null)
		{
			break;
		}
		else
		{
			dcVal = dcObj.value;
		}

		var amtObj = findObj("voucherDetails(key-"+i+").amountInRs");	
		if(amtObj==null)
		{
			break;
		}
		else
		{
			amtVal = amtObj.value;
			if (!(isNaN(amtVal)) && amtVal != "")
			{
				if (dcVal=="D")
				{
					dbAmt += parseInt(amtVal,10);
				}
				else if (dcVal=="C")
				{
					crAmt += parseInt(amtVal,10);
				}
			}
		}
	}
	totalAmount = crAmt - dbAmt;
	var totalAmtObj = findObj("amount");
	totalAmtObj.value=totalAmount;
}

function calMaturityAmount()
{
	var prlAmtObj=findObj("principalAmount");
	var compFreqObj=findObj("compoundingFrequency");
	var intRateObj=findObj("interestRate");
	var tenureTypeObj=findObj("tenureType");
	var tenureObj=findObj("tenure");
	var faceValueObj=findObj("faceValue");
	var couponRateObj=findObj("couponRate");

	var prlAmt=0;
	var compFreq=0;
	var intRate=0;
	var tenureType="";
	var tenure=0;
	var amount=0;
	var intAmt=0;
	var maturityAmt=0;
	var balDays=0;

	if (prlAmtObj!=null && prlAmtObj.value!="")
	{
		prlAmt=prlAmtObj.value;
	}

	if (faceValueObj!=null && faceValueObj.value!="")
	{
		prlAmt=faceValueObj.value;
	}

	if (compFreqObj!=null && compFreqObj.value!="")
	{
		compFreq=compFreqObj.value;
	}

	if (intRateObj!=null && intRateObj.value!="")
	{
		intRate=intRateObj.value;
	}

	if (couponRateObj!=null && couponRateObj.value!="")
	{
		intRate=couponRateObj.value;
	}

	if (tenureTypeObj!=null && tenureTypeObj.value!="")
	{
		if (document.forms[0].tenureType[0].checked)
		{
			tenureType="D";
		}
		else if (document.forms[0].tenureType[1].checked)
		{
			tenureType="M";
		}
		else if (document.forms[0].tenureType[2].checked)
		{
			tenureType="Y";
		}
	}

	if (tenureObj!=null && tenureObj.value!="")
	{
		tenure=tenureObj.value;
	}

	if (tenureType=="D")
	{
		balDays=tenure-(parseInt((tenure/365),10));
		tenure=(parseInt(tenure/365),10);
	}
	else if (tenureType=="M")
	{
		balDays=tenure-(parseInt((tenure/12),10));
		tenure=parseInt(tenure/12,10);
	}

	if (intRate>=100)
	{
		intRate=0;
	}

	if (compFreq==4)
	{
		intRate=intRate/4;
		tenure=tenure*4;
	}

	if (prlAmt!=0 && intRate!=0 && tenure!=0)
	{
		amount = prlAmt * (1+(intRate/100))^tenure;
	}

	if (balDays!=0)
	{
		intAmt = (amount * (intRate/100) * balDays)/365;
	}

	maturityAmt=amount + intAmt;

	var maturityAmtObj = findObj("maturityAmount");
	if (maturityAmtObj!=null)
	{
		maturityAmtObj.value=maturityAmt;
	}
}

function calMaturityDate()
{
	var dateOfDepObj=findObj("dateOfDeposit");
	var dateOfInvObj=findObj("dateOfInvestment");
	var dateOfMatObj=findObj("maturityDate");
	var startDate;
	var endDate;
	var index=0;
	var index1=0;
	var day;
	var month;
	var year;

	var tenureTypeObj=findObj("tenureType");
	var tenureObj=findObj("tenure");

	var tenureType="";
	var tenure=0;
	var matDate="";
	var date;

	if (dateOfDepObj!=null && dateOfDepObj.value!="")
	{
		date = new String(dateOfDepObj.value);
	}

	if (dateOfInvObj!=null && dateOfInvObj.value!="")
	{
		date = new String(dateOfInvObj.value);
	}

	if (tenureTypeObj!=null && tenureTypeObj.value!="")
	{
		if (document.forms[0].tenureType[0].checked)
		{
			tenureType="D";
		}
		else if (document.forms[0].tenureType[1].checked)
		{
			tenureType="M";
		}
		else if (document.forms[0].tenureType[2].checked)
		{
			tenureType="Y";
		}
	}

	if (tenureObj!=null && tenureObj.value!="")
	{
		tenure=tenureObj.value;
	}

	if (tenureType=="M")
	{
		tenure=parseFloat((tenure/12)*365);
	}
	else if (tenureType=="Y")
	{
		tenure=parseFloat(tenure*365);
	}

	if (date!=null && date!="" && tenure!=0)
	{
		index=date.indexOf("/");
		index1=date.lastIndexOf("/");
		day = date.substring(0, index);
		month = date.substring(index+1, index1);
		year = date.substring(index1+1, date.length);
		startDate=new Date(parseInt(year,10), parseInt(month,10), parseInt(day,10));
		endDate = new Date();
		endDate.setTime(startDate.getTime()+(tenure*24*60*60*1000));
		day=endDate.getDate();
		month=endDate.getMonth();
		year=endDate.getYear();
		if (day<10)
		{
			day="0"+day;
		}
		if (month<10)
		{
			month="0"+month;
		}
		matDate=day+"/"+month+"/"+year;
	}
	dateOfMatObj.value=matDate;
}

function enableDecision()
{
	var j=0;
	index= document.apForm.tcEntryIndex.value;
	
	for(i=0; i<index; i++)
	{
		appRefNo = findObj("tcAppRefNo(key-"+j+")");
		if(appRefNo!=null)
		{
			decision = findObj("tcDecision(key-"+j+")");
			cgpanText = findObj("tcCgpan(key-"+j+")");
			if(decision.checked)
			{
				cgpanText.disabled = false;
				
			}
			else
			{
				cgpanText.disabled = true;
				cgpanText.value="";			
			}
		}
		++j;
	}
	
}

function enableWcDecision()
{	
	
	var j=0;
	index= document.apForm.wcEntryIndex.value;
	
	for(i=0; i<index; i++)
	{
		appRefNo = findObj("wcAppRefNo(key-"+j+")");
		if(appRefNo!=null)
		{
			decision = findObj("wcDecision(key-"+j+")");
			cgpanText = findObj("wcCgpan(key-"+j+")");

			if(decision.value!="")
			{
				cgpanText.disabled = false;										
			}
			else
			{
				cgpanText.disabled = true;
				
			}
		}
		++j;
	}
}

function openNewWindow (url)
{
	window.open(url);
	return;
}

function disableUnits()
{
	var unitsObj=findObj("noOfUnits");
	var field=findObj("instrumentName");
	if (field.options[field.selectedIndex].value=="FIXED DEPOSIT")
	{
		document.forms[0].noOfUnits.value="1";
		unitsObj.disabled=true;
	}
	else
	{
//		document.forms[0].noOfUnits.value="";
		unitsObj.disabled=false;
	}
}

function disableUnitsSubmitForm()
{
	submitForm("showBuyOrSellInvRefNos.do?method=showBuyOrSellInvRefNos");
}

function enableInvRefNo()
{
	if (document.forms[0].isBuyOrSellRequest[0].checked)
	{
		document.forms[0].investeeName.value="";
		document.forms[0].instrumentName.value="";
		document.forms[0].investmentRefNumber[0].selected=true;
		document.forms[0].investmentRefNumber.disabled=true;
		document.forms[0].worthOfUnits.value="";
		document.forms[0].noOfUnits.value="";
	}
	else if (document.forms[0].isBuyOrSellRequest[1].checked)
	{
		document.forms[0].investeeName.value="";
		document.forms[0].instrumentName.value="";
		document.forms[0].investmentRefNumber[0].selected=true
		document.forms[0].investmentRefNumber.disabled=false;
		document.forms[0].worthOfUnits.value="";
		document.forms[0].noOfUnits.value="";
	}
}

function disableDtOfRecProc(objName)
{	
	if (document.forms[0].proceedingsConcluded[1].checked)
	{	      
		document.forms[0].dtOfConclusionOfRecoveryProc.disabled=true;
		document.forms[0].dtOfConclusionOfRecoveryProc.value="";
	}else if (document.forms[0].proceedingsConcluded[0].checked)
	{	     
             document.forms[0].dtOfConclusionOfRecoveryProc.disabled=false;
        }
}

function disableDtOfAccWrittenOff(objName)
{	
	if (document.forms[0].whetherAccntWasWrittenOffBooks[1].checked)
	{	      
		document.forms[0].dtOnWhichAccntWrittenOff.disabled=true;
		document.forms[0].dtOnWhichAccntWrittenOff.value="";
	}else if (document.forms[0].whetherAccntWasWrittenOffBooks[0].checked)
	{	     
             document.forms[0].dtOnWhichAccntWrittenOff.disabled=false;
        }
}

function setCPOthersEnabled()
{	
	var obj=findObj("forumthrulegalinitiated");
	var objOther=findObj("otherforums");
	if(objOther!=null && objOther!="")
	{	
		if((obj.options[obj.selectedIndex].value)=="Others")
		{			
			document.forms[0].otherforums.disabled=false;			
		}						
		else
		{
			document.forms[0].otherforums.disabled=true;
			document.forms[0].otherforums.value="";
		}
	}
	
}

function investeeGrpOptionSubmit1()
{
	submitForm('showInvesteeGroup.do?method=showModInvesteeGroup');
}

function instrumentTypeOption()
{
	if (document.forms[0].instrumentType[1].checked)
	{
		document.forms[0].instrumentName[0].selected=true;
		document.forms[0].instrumentName.disabled=true;
		document.forms[0].modInstrumentName.value="";
		document.forms[0].modInstrumentName.disabled=true;
//		document.forms[0].newInstrumentName.value="";
		document.forms[0].newInstrumentName.disabled=false;
	}
	else
	{
//		document.forms[0].instrumentName[0].selected=true;
		document.forms[0].instrumentName.disabled=false;
//		document.forms[0].modInstrumentName.value="";
		document.forms[0].modInstrumentName.disabled=true;
		document.forms[0].newInstrumentName.value="";
		document.forms[0].newInstrumentName.disabled=false;
	}
}

function processForwardedToFirst(field)
{        
       var decisionVal;
      
       // alert('Control in ProcessForwardedToFirst');
       if(document.cpTcDetailsForm.firstClmDtlIndexValue)
       {
        firstIndex = document.cpTcDetailsForm.firstClmDtlIndexValue.value;   
        // alert('firstIndex' + firstIndex);
        for(i=0;i<firstIndex;++i) 
        {
		var memberId = document.getElementById("MEMBERID#"+i);		
		
		if(memberId)
		{
		     memberIdVal = memberId.innerHTML;
		     // alert('memberIdVal :' + memberIdVal);
		}
		
		var clmRefNum = document.getElementById("CLMREFNUM##"+i);
		if(clmRefNum)
		{
		    clmRefNumVal = clmRefNum.value;
		    // alert('clmRefNumVal :' + clmRefNumVal);
		}                
		
                var decisionObj = findObj("decision("+"F" + "#"+memberIdVal +"#"+ clmRefNumVal +")");
                // alert('decisionObj :' + decisionObj);
		
		if(decisionObj)
		{     
		        decisionVal = decisionObj.value;			  
			// alert('decisionVal :' + decisionVal);
			if(decisionVal =="FW")			
			{
			    // alert('Decision is Forward');
			    var forwardedIdObj = findObj("forwardedToIds("+"F" + "#"+memberIdVal +"#"+ clmRefNumVal +")");
			    findObj("forwardedToIds("+"F" + "#"+memberIdVal +"#"+ clmRefNumVal +")").disabled = false;			    
			}
			else 
			{
			    // alert('Decision is not Forward');
			    var forwardedIdObj = findObj("forwardedToIds("+"F" + "#"+memberIdVal +"#"+ clmRefNumVal +")");
			    findObj("forwardedToIds("+"F" + "#"+memberIdVal +"#"+ clmRefNumVal +")").value="";
			    findObj("forwardedToIds("+"F" + "#"+memberIdVal +"#"+ clmRefNumVal +")").disabled = true;
			}
		}
       }
     }
}



var allocatePayment=0;
var OnlineAllocatePayment=0;
var numCount=0;
var numCountOnlinePayment=0;
function calcAllocatePayment(amount,name)
{

var checkBoolean = document.forms[0].elements[name].checked;
//alert("checkBoolean>>>>>>>>>>>>>>"+checkBoolean);
if(checkBoolean==true)
	{
	
	allocatePayment = ((Number(allocatePayment)) + (Number(amount)));
	
	numCount++;
	}
else if(checkBoolean==false)
	{
	allocatePayment = ((Number(allocatePayment)) - (Number(amount)));
	//alert("2 else=="+allocatePayment);
	numCount--;
	}
document.getElementById("tAmount").innerHTML = allocatePayment.toFixed(2);
document.getElementById("tAmount1").innerHTML = numCount;
	
}

function calcOnlineAllocatePayment(amount,name)
{

if(document.forms[0].elements[name].checked==true)
	{
	OnlineAllocatePayment=Number(OnlineAllocatePayment)+Number(amount);
	numCountOnlinePayment++;
	}
else
	{
	OnlineAllocatePayment=Number(OnlineAllocatePayment)-Number(amount);
	numCountOnlinePayment--;
	}

	alert('hi='+numCountOnlinePayment);
	
	document.getElementById("tAmount").innerHTML = OnlineAllocatePayment.toFixed(2);
	document.getElementById("paymentAmountRP").innerHTML = numCountOnlinePayment;
	
	
}


function calcAllocatePayment1(amount,name)
{
//alert(name)	;
//alert("length "+document.forms[0].elements.length);
for ( var int = 0; int < document.forms[0].elements.length; int++) {
	//alert(int+"length "+document.forms[0].elements[int].checked);
}


if(document.forms[0].elements[name].checked == true)
	{
	//alert('in if loop');
	//alert('in if loop allocatePayment ='+allocatePayment);
	//alert('in if loop amount ='+amount);
	allocatePayment=Number(allocatePayment)+Number(amount);
//	alert('in if loop allocatePayment111    ='+allocatePayment);
	numCount++;
	}
else
	{
	numCount--;
	//alert('in else loop');
	//alert('in else loop allocatePayment ='+allocatePayment);
	//alert('in else loop amount ='+amount);
	allocatePayment=Number(allocatePayment)-Number(amount);
	//alert('in else loop allocatePayment111    ='+allocatePayment);
	}


	//alert(numCount+'hi='+allocatePayment);
	document.getElementById("tAmount").innerHTML = allocatePayment.toFixed(2);
	document.getElementById("tAmount1").innerHTML = numCount;
	
}



var OnlinePaymentModify=0;
var numCountcancDans=0;

function calcAllocatePaymentFordans(amount,name)
{
	
	for ( var int = 0; int < document.forms[0].elements.length; int++) {
		//alert(int+"length "+document.forms[0].elements[int].checked);
	}


if(document.forms[0].elements[name].checked==true)
	{
	OnlinePaymentModify=Number(OnlinePaymentModify)+Number(amount);
	numCountcancDans++;
	}
else
	{
	OnlinePaymentModify=Number(OnlinePaymentModify)-Number(amount);
	numCountcancDans--;
	}

	//alert('hi='+allocatePayment);
	
	document.getElementById("tAmount").innerHTML = OnlinePaymentModify;
	document.getElementById("tAmount1").innerHTML = numCountcancDans;
	
	
}

function resetMakePaymentDetails()
{
document.getElementById("tAmount").innerHTML = "";
document.getElementById("tAmount1").innerHTML = "";

}

function processForwardedToSecond(field)
{
      // alert('Control in ProcessForwardedToSecond');
      var decisionVal;
            
      if(document.cpTcDetailsForm.secondClmDtlIndexValue)
      {
        secondIndex = document.cpTcDetailsForm.secondClmDtlIndexValue.value;   
        // alert('secondIndex' + secondIndex);
        for(i=0;i<secondIndex;++i) 
        {
		var memberId = document.getElementById("MEMBERID##"+i);		
		
		if(memberId)
		{
		     memberIdVal = memberId.innerHTML;
		     // alert('memberIdVal :' + memberIdVal);
		}
		var clmRefNum = document.getElementById("CLMREFNUM###"+i);
		if(clmRefNum)
		{
		    clmRefNumVal = clmRefNum.value;
		    // alert('clmRefNumVal :' + clmRefNumVal);
		}                
		
		var cgclan = document.getElementById("CGCLAN##"+i);
		if(cgclan)
		{
		    cgclanVal = cgclan.value;
		    // alert('cgclan :' + cgclanVal);
		}
                
                var decisionObj = findObj("decision("+"S" + "#"+memberIdVal +"#"+ clmRefNumVal +"#"+cgclanVal+")");
                // alert("S" + "#"+memberIdVal +"#"+ clmRefNumVal +"#"+cgclanVal);
                // alert('decisionObj :' + decisionObj);
		
		if(decisionObj)
		{     
		        decisionVal = decisionObj.value;			  
			// alert('decisionVal :' + decisionVal);
			if(decisionVal =="FW")			
			{
			    // alert('Decision is Forward');
			    var forwardedIdObj = findObj("forwardedToIds("+"S" + "#"+memberIdVal +"#"+ clmRefNumVal +"#"+cgclanVal+")");
			    findObj("forwardedToIds("+"S" + "#"+memberIdVal +"#"+ clmRefNumVal +"#"+cgclanVal+")").disabled = false;			    
			}
			else
			{
			    // alert('Decision is not Forward');			    
			    var forwardedIdObj = findObj("forwardedToIds("+"S" + "#"+memberIdVal +"#"+ clmRefNumVal +"#"+cgclanVal+")");
			    findObj("forwardedToIds("+"S" + "#"+memberIdVal +"#"+ clmRefNumVal +"#"+cgclanVal+")").value="";
			    findObj("forwardedToIds("+"S" + "#"+memberIdVal +"#"+ clmRefNumVal +"#"+cgclanVal+")").disabled = true;
			}
		}
       }
     }
}
function chooseModifyPLR(field)
{
	var plrObject=findObj("plrMaster.PLR");
	//alert(field);
	
	//alert(plrObject[0].checked);
	var benchPLRObject=findObj("plrMaster.BPLR");
	
	if(plrObject[0].checked)
	{
		benchPLRObject.disabled=false;
	}
	else
	{
		
		benchPLRObject.value="";
		benchPLRObject.disabled=true;
	}
}


/*function displayCorpusTotal()
{

	var corpusTotal = 0;
	
	var corpusAmt=findObj("exposureCorpusAmount");
	
	var corpusOtherAmt=findObj("otherReceiptsAmount");
	
	var totalCorpus=document.getElementById('corpusTotal');
	
	var corpusAmtValue = corpusAmt.value;
	var corpusOtherValue = corpusOtherAmt.value;
	
	if(document.forms[0].availableCorpusAmount[0].checked)
	{
		corpusTotal+=parseFloat(corpusAmtValue) ;
	}
	
	if(document.forms[0].availableOtherAmount[0].checked)
	{
		corpusTotal+=parseFloat(corpusOtherValue) ;
	}
	totalCorpus.innerHTML = corpusTotal;
	
}*/




function submitClaimDetailForm(action)
{

var cgpan=document.forms[0].cgpan.value;
var clmRefNumber=document.forms[0].clmRefNumber.value;

if((cgpan!='' &&clmRefNumber!='')||(cgpan==''&&clmRefNumber==''))
{
alert("Enter Any one, Cgpan or Claim Ref No ");
}
else
{
document.forms[0].action=action;
	document.forms[0].target="_self";
	document.forms[0].method="POST";
	document.forms[0].submit();
}
     
}




function displaySurplusTotal()
{
	var surplusTotal = 0;
	var corpusTotal = 0;
	
	var liveAmt=findObj("liveInvtAmount");
	var investedAmt=findObj("investedAmount");
	var matureAmt=findObj("maturedAmount");
	var corpusAmount=findObj("exposureCorpusAmount");
	var otherAmount=findObj("otherReceiptsAmount");
	var expAmount=findObj("expenditureAmount");

	
	var totalCorpus=document.getElementById('corpusTotal');
	
	if(document.forms[0].availableLiveInv[0].checked)
	{
		if (!(isNaN(parseFloat(liveAmt.value))) && liveAmt.value!="")
		{
			surplusTotal+=parseFloat(liveAmt.value) ;	
		}
		
	}
	
	if(document.forms[0].availableInvAmount[0].checked)
	{
		if (!(isNaN(parseFloat(investedAmt.value))) && investedAmt.value!="")
		{
			surplusTotal+=parseFloat(investedAmt.value) ;	
		}
	}
	
	if(document.forms[0].availableMaturingAmount[0].checked)
	{
		if (!(isNaN(parseFloat(matureAmt.value))) && matureAmt.value!="")
		{
			surplusTotal+=parseFloat(matureAmt.value) ;	
		}
	}
	
	if(document.forms[0].availableCorpusAmount[0].checked)
	{
		if (!(isNaN(parseFloat(corpusAmount.value))) && corpusAmount.value!="")
		{
			surplusTotal+=parseFloat(corpusAmount.value) ;
			corpusTotal+=parseFloat(corpusAmount.value) ;
		}
	
	}

	if(document.forms[0].availableOtherAmount[0].checked)
	{
		if (!(isNaN(parseFloat(otherAmount.value))) && otherAmount.value!="")
		{
			surplusTotal+=parseFloat(otherAmount.value) ;
			corpusTotal+=parseFloat(otherAmount.value) ;
		}
	
	}
	totalCorpus.innerHTML = corpusTotal;
	
//	if(document.forms[0].availableExpAmount[0].checked)
//	{
		if (!(isNaN(parseFloat(expAmount.value))) && expAmount.value!="")
		{
			surplusTotal-=parseFloat(expAmount.value) ;
		}
//	}
	
	var surplusAmount=findObj("totalSurplusAmount");
	var totalsurplus=document.getElementById('surplusTotal');
	totalsurplus.innerHTML = surplusTotal;


}

function insCategory()
{
	var obj=findObj("instrumentCategory");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
		document.forms[0].newInstrumentCat.disabled=false;
//		document.forms[0].newInstrumentCat.value="";

		document.forms[0].modInstrumentCat.value="";
		document.forms[0].modInstrumentCat.disabled=true;

		document.forms[0].ictDesc.value="";
	}
	else
	{

		document.forms[0].newInstrumentCat.value="";
		document.forms[0].newInstrumentCat.disabled=true;
		

//		document.forms[0].modInstrumentCat.value=obj.options[obj.selectedIndex].value;
		document.forms[0].modInstrumentCat.disabled=false;
	}
}


function newInstCategory()
{
	var obj=findObj("instrumentCategory");
	obj.selectedIndex=0;

	document.forms[0].modInstrumentCat.value="";
	document.forms[0].modInstrumentCat.disabled=true;
	document.forms[0].ictDesc.value="";
}

function displayMiscReceiptsTotal() {
	var totalAmount = 0;
	var amtVal;
	var invFlagVal;
	var sourceVal;
	var instDtVal;
	var instNoVal;
	var rectDtVal;

	for(i=0;;++i) 
	{
		var souceObj = findObj("miscReceipts(key-"+i+").sourceOfFund");
		var instDtObj = findObj("miscReceipts(key-"+i+").instrumentDate");
		var instNoObj = findObj("miscReceipts(key-"+i+").instrumentNo");
		var rectDtObj = findObj("miscReceipts(key-"+i+").dateOfReceipt");
		var invFlagObj = findObj("miscReceipts(key-"+i+").isConsideredForInv");

		if (souceObj==null)
		{
			break;
		}
		else
		{
			sourceVal=souceObj.value;
		}

		if (instDtObj==null)
		{
			break;
		}
		else
		{
			instDtVal=instDtObj.value;
		}

		if (instNoObj==null)
		{
			break;
		}
		else
		{
			instNoVal=instNoObj.value;
		}

		if (rectDtObj==null)
		{
			break;
		}
		else
		{
			rectDtVal=rectDtObj.value;
		}

		if (invFlagObj==null)
		{
			break;
		}
		else
		{
			if (invFlagObj[0].checked)
			{
				invFlagVal="Y";
			}
			else if (invFlagObj[1].checked)
			{
				invFlagVal="N";
			}
		}

		var amtObj = findObj("miscReceipts(key-"+i+").amount");	
		if(amtObj==null)
		{
			break;
		} 
		else
		{
			amtVal = amtObj.value;
			if ((!(isNaN(amtVal)) && amtVal != "") && invFlagVal=="Y" && (sourceVal!="" && instDtVal!="" && instNoVal!="" && rectDtVal!=""))
			{
				totalAmount += parseInt(amtVal, 10);
			}
		}
	}
	totalId = document.getElementById("totalMiscAmount");
	totalId.innerHTML=totalAmount;
}

function displayBalanceFundTransfer() {

	var totalAmount = 0;

	var utilBalVal;
	var amtIDBIVal;

	var clBalVal;
	var stmtBalVal;
	var unclBalVal;
	var amtCaVal;
	var invFlagVal;

	for(i=0;;++i) 
	{
		var clbalObj = findObj("fundTransfers(key-"+i+").closingBalanceDate");
		var stmtBalObj = findObj("fundTransfers(key-"+i+").balanceAsPerStmt");
		var unclBalObj = findObj("fundTransfers(key-"+i+").unclearedBalance");
		var amtCaObj = findObj("fundTransfers(key-"+i+").amtCANotReflected");

		var minBalObj = findObj("fundTransfers(key-"+i+").minBalance");
		var invFlagObj = findObj("fundTransfers(key-"+i+").availForInvst");

		var utilBalObj = findObj("fundTransfers(key-"+i+").balanceUtil");
		var amtIDBIObj = findObj("fundTransfers(key-"+i+").amtForIDBI");

		if (clbalObj==null)
		{
			break;
		}
		else
		{
			clBalVal=clbalObj.value;
		}

		if (stmtBalObj==null)
		{
			break;
		}
		else
		{
			stmtBalVal=stmtBalObj.value;
			if (stmtBalVal=="")
			{
				stmtBalVal=0;
			}
		}

		if (unclBalObj==null)
		{
			break;
		}
		else
		{
			unclBalVal=unclBalObj.value;
			if (unclBalVal=="")
			{
				unclBalVal=0;
			}
		}

		if (amtCaObj==null)
		{
			break;
		}
		else
		{
			amtCaVal=amtCaObj.value;
			if (amtCaVal=="")
			{
				amtCaVal=0;
			}
		}

		if (invFlagObj==null)
		{
			break;
		}
		else
		{
			if (invFlagObj[0].checked)
			{
				invFlagVal="Y";
			}
			else if (invFlagObj[1].checked)
			{
				invFlagVal="N";
			}
		}

		if (minBalObj==null)
		{
			break;
		}
		else
		{
			minBalVal=minBalObj.value;
		}

		if (!isNaN(stmtBalVal) && !isNaN(unclBalVal))
		{
			utilBalVal = parseInt(stmtBalVal, 10) - parseInt(unclBalVal, 10);
			utilBalId = document.getElementById("fundTransfers(key-"+i+").balanceUtil");
			utilBalId.innerHTML=utilBalVal;
		}

		if (!isNaN(stmtBalVal) && !isNaN(unclBalVal) && !isNaN(minBalVal) && !isNaN(amtCaVal))
		{
			if (stmtBalVal==0 )
			{
				amtIDBIVal=0;
			}
			else
			{
				amtIDBIVal = parseInt(stmtBalVal, 10) - parseInt(unclBalVal, 10) - parseInt(minBalVal, 10) - parseInt(amtCaVal, 10);
			}
			amtIDBIId = document.getElementById("fundTransfers(key-"+i+").amtForIDBI");
			amtIDBIId.innerHTML=amtIDBIVal;

			if (invFlagVal=="Y")
			{
				totalAmount += parseInt(amtIDBIVal, 10);
			}
		}
	}
	totalId = document.getElementById("totalFundTransfer");
	totalId.innerHTML=totalAmount;
}

function displayBankReconTotal() {

	var cgtsiBalObj=findObj("cgtsiBalance");
	var chqIssuedObj=findObj("chequeIssuedAmount");
	var directCdtObj=findObj("directCredit");
	var directDbtObj=findObj("directDebit");

	var cgtsiBal=cgtsiBalObj.value;
	var chqIssued=chqIssuedObj.value;
	var directCdt=directCdtObj.value;
	var directDbt=directDbtObj.value;

	var total=parseInt(cgtsiBal, 10) + parseInt(chqIssued, 10) + parseInt(directCdt, 10) - parseInt(directDbt, 10);

	if (isNaN(total))
	{
		total=0;
	}

	totalId = document.getElementById("total");
	totalId.innerHTML=total;
}

function enableAgency()
{
	var obj=findObj("agency");
	if ((obj.options[obj.selectedIndex].value)=="")
	{
		document.forms[0].newAgency.disabled=false;
//		document.forms[0].newMaturityType.value="";

		document.forms[0].modAgencyName.value="";
		document.forms[0].modAgencyName.disabled=true;

		document.forms[0].modAgencyDesc.value="";
	}
	else
	{
		document.forms[0].newAgency.value="";
		document.forms[0].newAgency.disabled=true;

//		document.forms[0].modMaturityType.value=obj.options[obj.selectedIndex].value;
		document.forms[0].modAgencyName.disabled=false;
	}
}

function newagency()
{
	var obj=findObj("agency");
	obj.selectedIndex=0;

	document.forms[0].modAgencyName.value="";
	document.forms[0].modAgencyName.disabled=true;
	document.forms[0].modAgencyDesc.value="";
}

function disableCheque()
{

	var instrumentType = findObj("instrumentType");
	
	if(instrumentType.value=="CHEQUE")
	{
		document.forms[0].bnkName.disabled = false;
	}
	else{
	
		document.forms[0].bnkName[0].selected=true;
	
		document.forms[0].bnkName.disabled = true;
	}
}

function disableClaimCheque()
{

	var instrumentType = findObj("modeOfPayment");
	
	if(instrumentType.value=="CHEQUE")
	{
		document.forms[0].bnkName.disabled = false;
	}
	else{
	
		document.forms[0].bnkName[0].selected=true;
	
		document.forms[0].bnkName.disabled = true;
	}
}


function displayMaturityAmtsTotal() {
	var totalAmount = 0;
	var amtVal;
	var invFlagVal;

	for(i=0;;++i) 
	{
		var invFlagObj = findObj("invstMaturingDetails(key-"+i+").invFlag");

		if (invFlagObj==null)
		{
			break;
		}
		else
		{
			if (invFlagObj[0].checked)
			{
				invFlagVal="Y";
			}
			else if (invFlagObj[1].checked)
			{
				invFlagVal="N";
			}
		}

		var amtObj = findObj("invstMaturingDetails(key-"+i+").maturityAmt");	
		if(amtObj==null)
		{
			break;
		} 
		else
		{
			amtVal = amtObj.value;
			if ((!(isNaN(amtVal)) && amtVal != "") && invFlagVal=="Y")
			{
				totalAmount += parseInt(amtVal, 10);
			}
		}
	}
	totalId = document.getElementById("totalMatAmt");
	totalId.innerHTML=totalAmount;
}

function dateOnly(myfield, e)
{
	var key;
	var keychar;

	if (window.event)
	   key = window.event.keyCode;
	else if (e)
	   key = e.which;
	else
	   return true;
	keychar = String.fromCharCode(key);

	// control keys
	if ((key==null) || (key==0) || (key==8) ||
	    (key==9) || (key==13) || (key==27) )
	   return true;

	// numbers
	else if ((("0123456789/").indexOf(keychar) > -1))
	{
/*		if(myfield.value.indexOf('.') > -1 && (".").indexOf(keychar) > -1)
		{
			
			return false;
		}
		var index=myfield.value.indexOf('.');
		
		var val=myfield.value.toString();
		
		if(index > -1)
		{
			var str=val.substring(index,val.length);
			
			if(str.length>2)
			{
				return false;
			}
			
			return true;
			//alert("index, str "+index+" "+str);
		}
		
		//alert("length is "+val.length+" "+(keychar!='.'));
		
		
		if(val.length>(maxIntegers-1) && keychar!='.')
		{
			return false;
		}*/
		
		return true;
		
	}	
	   
	else
	{
	   return false;
	}
}

function isValidDate(field)
{
	if(!isDateValid(field.value))
	{
		field.focus();
		field.value='';
		return false;
	}
}

function isDateValid(thestring)
{
//alert(thestring)
	if(thestring && thestring.length)
	{
		//alert("inner");
		for (i = 0; i < thestring.length; i++) {
			ch = thestring.substring(i, i+1);
			if ((ch < "0" || ch > "9")  && ch!="/")
			  {
			  //alert("The numbers may contain digits 0 thru 9 only!");
			  return false;
			  }
		}
	}
	else
	{
		return false;
	}
    return true;
}





function SubmitNpaApprForm(actionType)
{
	//alert("SubmitNpaApprForm S : "+actionType);	
	var checkList1 = document.getElementsByName('check');
	//alert("checkList name : "+checkList1.length);
	var check = false;
	var comment = false;
	
	for(var j=0; j<checkList1.length; j++)
	{
		if(checkList1[j].checked)
		{
			check = true;
			if(document.getElementById(checkList1[j].value) != null)
			{
				if(document.getElementById(checkList1[j].value).value == "")
				{
					alert("Emp Comments Is Required.");
					comment = true;
					break;					
				}				
			}
		}			
	}
	//alert("check val : "+check+ "\t comment : "+comment);
	if(check==true && comment==false)
	{		
//alert("SubmitNpaApprForm2 S : "+actionType);
		document.forms[0].action="showApprRegistrationFormSubmit.do?method=showApprRegistrationFormSubmit&action="+actionType;
		document.forms[0].target="_self";
		document.forms[0].method="POST";
		document.adminActionForm.submit();
	}
	//alert("SubmitNpaApprForm E");
}


function validationForITPan()
{
//	alert("hi");
	//document.forms[0].addNewRows.style.display="none";
	
	if(document.forms[0].cpITPAN.value!="")
		{
		document.forms[0].cpITPAN.disabled = true;
		}
}

var counterForIDCreation=1;
var rowCounter=0;
var claimRefArray =new Array();
var arrayForTypeOfRecovery =new Array();
var rowCounterForHeader=0;
function addTableRowForRecovery(claimSettledDecision)
{	
	 var table = document.getElementById("tblRecoveryDetails");	
	 var rowCount = table.rows.length;
     var row = table.insertRow(rowCount);  
     
     var element2 = document.createElement("input");
	    var element3 = document.createElement("input");
	    var element4 = document.createElement("input");
	    var element5 = document.createElement("input");
	    var element6 = document.createElement("input");
	    var element7 = document.createElement("select");
	    var element8 = document.createElement("input");
	    var element9 = document.createElement("input");
	    var element10 = document.createElement("input");
	    var element11 = document.createElement("input");
	    var element12 = document.createElement("input");
	    var elementCount = parseInt(document.getElementById("count").value);   
     //alert(rowCounter);
	  
	   
     counterForIDCreation=rowCounter*10;
   
   if(counterForIDCreation > 0)
	   {
	   //alert("before"+counterForIDCreation);
	   
   counterForIDCreation=(Number(counterForIDCreation)-10);
  // alert("after"+counterForIDCreation);
	   }
   
  /*  var element2 = document.createElement("input");
    var element3 = document.createElement("input");
    var element4 = document.createElement("input");
    var element5 = document.createElement("input");
    var element6 = document.createElement("input");
    var element7 = document.createElement("select");
    var element8 = document.createElement("input");
    var element9 = document.createElement("input");
    var element10 = document.createElement("input");
    var element11 = document.createElement("input");
    var element12 = document.createElement("input");
    var elementCount = parseInt(document.getElementById("count").value);   */
   
    var cell2 = row.insertCell(0);
    element2.setAttribute("type","text"); 
    element2.setAttribute("property","claimRecoveryForm");
    element2.setAttribute("name","objRecoveryActionForm["+ elementCount +"].claimRefNo");
    element2.setAttribute("id",(counterForIDCreation+1));
    element2.onblur=getRecoveryDetail;
    element2.setAttribute("size","19");
  //  element2.setAttribute("value",(counterForIDCreation+1));
    if(rowCounter==0)
    {
    	cell2.innerHTML = "CLAIM REFERENCE NO.";
    	cell2.style.fontSize = "10px";
    	cell2.style.backgroundColor = "#65bdd5";
    }
    
    if(rowCounter>0)
    	{
    	  cell2.appendChild(element2);
  
    	}
  
    
    var cell3 = row.insertCell(1);
    element3.setAttribute("type","text"); 
    element3.setAttribute("property","claimRecoveryForm");
    element3.setAttribute("name","objRecoveryActionForm["+ elementCount +"].cgpan");
    element3.setAttribute("id",(counterForIDCreation+2));
  //  element3.setAttribute("value",(counterForIDCreation+2));
    element3.setAttribute("readOnly",true);
    element3.setAttribute("size","16");
    if(rowCounter==0)
    {
    	cell3.innerHTML = "CGPAN                                                .";
    	cell3.style.fontSize = "10px";
    	cell3.style.backgroundColor = "#65bdd5";
    }
    if(rowCounter>0)
	{
    	cell3.appendChild(element3);   
	}
     
    
    var cell4 = row.insertCell(2);
    element4.setAttribute("type","text"); 
    element4.setAttribute("property","claimRecoveryForm");
    element4.setAttribute("name","objRecoveryActionForm["+ elementCount +"].unitName");
    element4.setAttribute("id",(counterForIDCreation+3));
 //   element4.setAttribute("value",(counterForIDCreation+3));
    element4.setAttribute("readOnly",true);
    element4.setAttribute("size","16");
    if(rowCounter==0)
    {
    	cell4.innerHTML = "UNIT NAME                                              .";
    	cell4.style.fontSize = "10px";
    	cell4.style.backgroundColor = "#65bdd5";
    }
    if(rowCounter>0)
	{
    	  cell4.appendChild(element4);
	}
  
    
    var cell5 = row.insertCell(3);
    element5.setAttribute("type","text"); 
    element5.setAttribute("property","claimRecoveryForm");
    element5.setAttribute("name","objRecoveryActionForm["+ elementCount +"].firstInstallmentAmount");
    element5.setAttribute("id",(counterForIDCreation+4));
  //  element5.setAttribute("value",(counterForIDCreation+4));
    element5.setAttribute("readOnly",true);
    element5.setAttribute("size","27");
    if(rowCounter==0)
    {
    	cell5.innerHTML = "FIRST INSTALLMENT AMT                                      .";
    	cell5.style.fontSize = "10px";
    	cell5.style.backgroundColor = "#65bdd5";
    }
    if(rowCounter>0)
	{
    	cell5.appendChild(element5);
	}
    
    
    var cell6 = row.insertCell(4);
    element6.setAttribute("type","text"); 
    element6.setAttribute("property","claimRecoveryForm");
    element6.setAttribute("name","objRecoveryActionForm["+ elementCount +"].previouseRecoveredAmount");
    element6.setAttribute("id",(counterForIDCreation+5));
 //   element6.setAttribute("value",(counterForIDCreation+5));
    element6.setAttribute("readOnly",true);
    element6.setAttribute("size","21");
    if(rowCounter==0)
    {
    	cell6.innerHTML = "PREVIOUS RECOVERY REMITTED TO_CGTMSE";
    	cell6.style.fontSize = "10px";
    	cell6.style.backgroundColor = "#65bdd5";
    }
    if(rowCounter>0)
	{
    cell6.appendChild(element6);
	}
    
    var cell7 = row.insertCell(5);
    element7.setAttribute("property","claimRecoveryForm");
    element7.setAttribute("name","objRecoveryActionForm["+ elementCount +"].typeOfRecovery");
    element7.setAttribute("id",(counterForIDCreation+6));
    element7.setAttribute("width","10px");  
    element7.options[0] = new Option("--Select--","0");
    element7.options[1] = new Option("OTS","1");
    element7.options[2] = new Option("Partial Recovery","8");
    element7.options[3] = new Option("Recovery through Inspection","10"); 
  //  element7.options[4] = new Option("Refund","11"); 
    element7.options[4] = new Option("1st Installment","9");
    element7.onchange=calcAmtRemmitted;   
    if(rowCounter==0)
    {
    	cell7.innerHTML = "TYPE OF RECOVERY                                           .";
    	cell7.style.fontSize = "10px";
    	cell7.style.backgroundColor = "#65bdd5";
    }
    if(rowCounter>0)
	{
    cell7.appendChild(element7);
	}
    
    var cell8 = row.insertCell(6);
    element8.setAttribute("type","text"); 
    element8.setAttribute("property","claimRecoveryForm");
    element8.setAttribute("name","objRecoveryActionForm["+ elementCount +"].recoveredAmout");
    element8.setAttribute("id",(counterForIDCreation+7));
   // element8.setAttribute("value",(counterForIDCreation+7));
    element8.setAttribute("style","text-align: right");   
    //element8.setAttribute("size","21");
    element8.onkeyup=isNumberKey;
    if(rowCounter==0)
    {
    	cell8.innerHTML = "NEW / ADDITIONAL RECOVERY                                .";
    	cell8.style.fontSize = "10px";
    	cell8.style.backgroundColor = "#65bdd5";
    }
    if(rowCounter>0)
	{
    cell8.appendChild(element8);
	}
    
    var cell9 = row.insertCell(7);
    element9.setAttribute("type","text"); 
    element9.setAttribute("property","claimRecoveryForm");
    element9.setAttribute("name","objRecoveryActionForm["+ elementCount +"].legalExpenses");
    element9.setAttribute("id",(counterForIDCreation+8));
 //   element9.setAttribute("value",(counterForIDCreation+8));
    element9.setAttribute("style","text-align: right");   
    element9.onkeyup=isNumberKey;
    element9.onblur=remittedAmountCalc;
    if(rowCounter==0)
    {
    	cell9.innerHTML = "LEGAL EXPENSES                                           .";
    	cell9.style.fontSize = "10px";
    	cell9.style.backgroundColor = "#65bdd5";
    }
    if(rowCounter>0)
	{
    cell9.appendChild(element9);
	}
    
    var cell10 = row.insertCell(8);
    element10.setAttribute("type","text"); 
    element10.setAttribute("property","claimRecoveryForm");
    element10.setAttribute("name","objRecoveryActionForm["+ elementCount +"].amoutRemitted");
    element10.setAttribute("id",(counterForIDCreation+9));
   // element10.setAttribute("value",(counterForIDCreation+9));   
    element10.setAttribute("readOnly",true);
    element10.setAttribute("style","text-align: right");  
    element10.setAttribute("size","19");
    if(rowCounter==0)
    {
    	cell10.innerHTML = "AMT REMITTED TO CGTMSE                                  .";
    	cell10.style.fontSize = "10px";
    	cell10.style.backgroundColor = "#65bdd5";
    }
    if(rowCounter>0)
	{
    cell10.appendChild(element10); 
	}
    
    var cell11 = row.insertCell(9);
    element11.setAttribute("type","checkbox"); 
    element11.setAttribute("property","claimRecoveryForm");
    element11.setAttribute("name","objRecoveryActionForm["+ elementCount +"].decision");
    element11.setAttribute("id",(counterForIDCreation+10));
    element11.onclick=checkboxvalue;
    if(rowCounter==0)
    {
    	cell11.innerHTML = "PAY                                                       .";
    	cell11.style.fontSize = "10px";
    	cell11.style.backgroundColor = "#65bdd5";
    }
    if(rowCounter>0)
	{
    cell11.appendChild(element11);
	}
    
    var cell12 = row.insertCell(10);
    element12.setAttribute("type","hidden"); 
    element12.setAttribute("property","claimRecoveryForm");
    element12.setAttribute("name","objRecoveryActionForm["+ elementCount +"].hiddenFieldForClaimNotSettled");
    if(rowCounter>0)
	{
    element12.setAttribute("id","HIDDEN"+(counterForIDCreation+1));
	}
    //element12.setAttribute("value","HIDDEN"+(counterForIDCreation+1));
    cell12.appendChild(element12);
    
   // alert("HIDDEN"+(counterForIDCreation+1))
    
    counterForIDCreation=(elementCount+3);
	
    document.getElementById("count").value = elementCount + parseInt(1);
    document.getElementById("errorsMessage").innerHTML ='';
   if(rowCounterForHeader>0)
   {
	   
   }
   rowCounter++;
  // rowCounterForHeader++;
     
}
function checkboxvalue()
{
	
	if(document.getElementById("HIDDEN"+(Number(this.id)-9)).value=="1")
	{
		document.getElementById((Number(this.id)-1)).value="0";
		document.getElementById((Number(this.id)-2)).value="0";
		printMessage("" ,"errorsMessage");
	}
	
	if(document.getElementById((Number(this.id)-9)).value.trim()=="")
	{
		document.getElementById(this.id).checked=false;
		printMessage("First enter claim details" ,"errorsMessage");
	}
	
	
	
	
	var idOfCurrentComponand= this.id;
	for (i = 0; i < arrayForTypeOfRecovery.length; i++) { 
		
		
		 var res = arrayForTypeOfRecovery[i].split(",");
		
		if(document.getElementById((Number(idOfCurrentComponand)-9)).value==res[0])
			{
			
			
			var typeOfRecoveryValue= document.getElementById((Number(idOfCurrentComponand)-4)).value;
			
				if(typeOfRecoveryValue=="9")
				{		
					var flag=false;
					if(document.getElementById(this.id).checked==true)
						{
							flag=true;
						}
					for (var k= 1; k<res.length; k++) 
					{
						
						document.getElementById((Number(res[k])+4)).checked=flag;
					}
					
				}
		
		
			}
	}
	
}
function remittedAmountCalc(elementCount)
{ 
	var idOfCurrentComponand=this.id;	
	//alert("hi");
	//alert("=="+document.getElementById((Number(idOfCurrentComponand)-2)).value);
	if(document.getElementById((Number(idOfCurrentComponand)-2)).value.trim()!="9" && document.getElementById("HIDDEN"+(Number(idOfCurrentComponand)-7)).value.trim()!="1")
	{	
		var recoveredAmtID=(idOfCurrentComponand-1);	
		var amtRemmitedID=(Number(idOfCurrentComponand)+1);	
		var recoveredAmt=document.getElementById(recoveredAmtID).value;
		var legalExpence=document.getElementById(idOfCurrentComponand).value;
		document.getElementById(amtRemmitedID).value = recoveredAmt-legalExpence;
		document.getElementById((Number(NumIdPlusOne)+1)).checked=false;	
		document.getElementById("errorsMessage").innerHTML ='';
	}
	
}
function printMessage(message , componand)
{
	 document.getElementById(componand).innerHTML ='';
     document.getElementById(componand).innerHTML = message;
}

var claimSettledDecision;
function getRecoveryDetail(elementCount)
{
	var idOfCurrentComponand=this.id;
	
	if(document.getElementById(idOfCurrentComponand).readOnly  == false)
	{		
		var counterForblankValueChck=0;
		var intNum=(Number(idOfCurrentComponand)+1);
		var totalIntNum=((intNum/10));
		var claimRefNo=this.value;
		
		if(claimRefNo.trim()!="")
		{
			
			for (var l = 0; l < totalIntNum; l++)
			{		
				var idverVar=((Number(l)*10)+1);		
				if(document.getElementById(idverVar).value.trim()=="" )
				{
					counterForblankValueChck=1;
				}				
			}	
			if(counterForblankValueChck==0)
			{
				var xmlhttp;
			    if (window.XMLHttpRequest){
			        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
			    } else {
			        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
			    }			  
			    var arraycontainsturtles = indexOfForArray(claimRefNo);			 
			    if(arraycontainsturtles==false)
			    {
			    	xmlhttp.open("POST", "fetchRecoveryDetailsAction.do?method=fetchRecoveryDetails&clmRefNo="+this.value, true);		    
			    	xmlhttp.onreadystatechange = function() {
			    	
			    	if (xmlhttp.readyState == 4) 
			    	{
			    		
			    		if (xmlhttp.status == 200)
			    		{
			    			
			    			if(claimRefNo.trim()!="")
			    			{
			    				claimRefArray.push(claimRefNo);
			    			}          	
		            	   	if(xmlhttp.responseText.trim()!="0")
					    	{            		
		            	   		varResponseText =xmlhttp.responseText.split("===");	            
					    		var n = xmlhttp.responseText.lastIndexOf("==="); 
					    		var lastNum=xmlhttp.responseText.substring((Number(n)+3),xmlhttp.responseText.length-2);
					    		claimSettledDecision=xmlhttp.responseText.substring(xmlhttp.responseText.length-1,xmlhttp.responseText.length);					    	
					    		var a =(varResponseText.length+1)/6;
					    		document.getElementById(idOfCurrentComponand).readOnly  = true;		
					    		for (var i = 0; i < (Number(lastNum)-1); i++)
					    		{		
					    			
					    			addTableRowForRecovery();					    		
					    		}
					    		var counterForHiddenFiled=0;
					    		var idToMakeFieldDisabled=0;
					    		var abc=0;
					    		for (var j = 1; j <= (Number(lastNum)); j++)
					    		{
					    			if(counterForHiddenFiled > 0)
					    			{					    		
					    				abc=idToMakeFieldDisabled;					     		
					    				if(claimSettledDecision=="1")
						    			{
						    				document.getElementById((Number(abc)+6)).disabled   =true;
						    				document.getElementById((Number(abc)+8)).disabled   =true;
						    				document.getElementById((Number(abc)+9)).disabled   =true;
						    			}
					    		
					    				document.getElementById("HIDDEN"+((Number(abc)+1))).value  = claimSettledDecision;
					    				idToMakeFieldDisabled=(Number(idToMakeFieldDisabled)+10);
					    			}
					    			else
					    			{		
					    		
						    			if(claimSettledDecision=="1")
						    			{
						    				document.getElementById((Number(idOfCurrentComponand)+5)).disabled   =true;
						    				document.getElementById((Number(idOfCurrentComponand)+7)).disabled   =true;
						    				document.getElementById((Number(idOfCurrentComponand)+8)).disabled   =true;
						    			}
						    	
					    				document.getElementById("HIDDEN"+(Number(idOfCurrentComponand))).value  = claimSettledDecision;
					    				idToMakeFieldDisabled=Number(idOfCurrentComponand)+9;
					    				
					    			}
					    			counterForHiddenFiled++;
					    		}
					    		
					    	
					    		var counterForLinkedCGPan=0;
					    		var counter=0;			
					    		var typeOfRecoveryElement;
					    		var IdsOfTypeOfRecovery="";
					            for (var i = 0; i < (varResponseText.length-1); i++)
					            {
					            	//alert("Counter "+(Number(lastNum)-1));
					            	//alert("I "+counter);
					            	
					            	if(((Number(lastNum)-1) > 0) && counter==4)
					            	{
					            		//alert("inside if ");
					            		//typeOfRecoveryElement=varResponseText[0]+","+idOfCurrentComponand;
					            		IdsOfTypeOfRecovery=IdsOfTypeOfRecovery+","+(Number(idOfCurrentComponand)+1);
					            	}
						              	if(counter==5)
						              	{						              
						              		 document.getElementById((Number(idOfCurrentComponand))).readOnly  = true;
						              		 document.getElementById((Number(idOfCurrentComponand))).style.backgroundColor="#696969";
						              		 counter=0;
						              	}						            	
						            	counterForLinkedCGPan=(Number(counterForLinkedCGPan)+1);
						            	//alert("counter "+i+" Response "+varResponseText[i]);
						            	document.getElementById(idOfCurrentComponand).value =varResponseText[i];
						            	document.getElementById(idOfCurrentComponand).readOnly  = true;
						            	idOfCurrentComponand=(Number(idOfCurrentComponand)+1);
						            	if(counterForLinkedCGPan==5)
						            	{
						            		counterForLinkedCGPan=0;	            		 
						            		idOfCurrentComponand=Number(idOfCurrentComponand)+5;
						            	}					            				            
						                counter++;
						              //  alert(IdsOfTypeOfRecovery);
						                
					            }
					            if(((Number(lastNum)-1) > 0))
					            {
					            	//alert(claimRefNo+IdsOfTypeOfRecovery);
					            	arrayForTypeOfRecovery.push(claimRefNo+IdsOfTypeOfRecovery);
					            }
					            
					             document.getElementById("errorsMessage").innerHTML ='';
			              
					    	}
					    	else
					    	{	            	
					    		printMessage("Invalid claim ref no.["+claimRefNo+"]." ,"errorsMessage");	                
					    		document.getElementById(idOfCurrentComponand).value ="";
					    		var index = claimRefArray.indexOf(claimRefNo);
					    		if (index > -1) {
					    			claimRefArray.splice(index, 1);
					    		}
					    	}
			            }
			            else
			            {
			            	 printMessage("Something is wrong !! Plz contact CGTMSE Support[support@cgtmse.com] team" ,"errorsMessage");
			               
			            }
			          
			    	  }
			    	};
			    	xmlhttp.send(null);
  
			    }
			    else
			    {
			    	printMessage("You have already done entry for this["+claimRefNo+"] claim ref no.","errorsMessage");	   
			    	document.getElementById(this.id).value  = "";
			    }
			}
		    else
			{			   
			   printMessage("First use above blank rows","errorsMessage");
			   document.getElementById(this.id).value  = "";
			}

		}
		else
		{	 
			printMessage("Plz enter claim ref no.","errorsMessage");
			document.getElementById(this.id).value  = "";
			setBorder(this.id);
		}
	
	}
	else
	{
		printMessage("You can't change already entered claim ref no. ","errorsMessage");
	}
}
function isNumberKey(evt)
{
	 if (isNaN(this.value)) 
	 {
	    printMessage("Please enter no. only ,"+this.value+" is wrong input" , "errorsMessage");
	    document.getElementById(this.id).value  = "";
	    return false;
	 }  
}
var paymentModeDecision="";

function calcAmtRemmitted()
{
	if(this.value!="0")
	{
		
		var idOfCurrentComponand=this.id;
		
		for (i = 0; i < arrayForTypeOfRecovery.length; i++) { 
			
			
			 var res = arrayForTypeOfRecovery[i].split(",");
			
			if(document.getElementById((Number(idOfCurrentComponand)-5)).value==res[0])
				{
				
				
				var options= document.getElementById(idOfCurrentComponand).options;
				for (var j= 0; j<options.length; j++) 
				{			
					if(this.value==options[j].value)
					{				
						for (var k= 1; k<res.length; k++) 
						{
							document.getElementById(res[k]).selectedIndex = j;
							if(this.value=="9")
							{
								document.getElementById((Number(res[k])+1)).value=0;
								document.getElementById((Number(res[k])+2)).value=0;
								document.getElementById((Number(res[k])+1)).readOnly  = true;
								document.getElementById((Number(res[k])+2)).readOnly  = true;
								document.getElementById((Number(res[k])+3)).value=document.getElementById((Number(res[k])-2)).value;
								document.getElementById((Number(res[k])+4)).checked=false;
							}
							else
							{
								document.getElementById((Number(res[k])+1)).readOnly  = false;
								document.getElementById((Number(res[k])+2)).readOnly  = false;
								document.getElementById((Number(res[k])+3)).value= (Number(document.getElementById((Number(res[k])+2)).value))-(Number(Number(document.getElementById((Number(res[k])+1)).value)));
							}
						}
						
					}
				}
			
				}
		}
		
		var hiddenFiledID=Number(idOfCurrentComponand)-5;
		if((document.getElementById((Number(idOfCurrentComponand)-4)).value.trim()!=""))
		{	
			if(document.getElementById("HIDDEN"+(hiddenFiledID)).value!="1")
			{
				if(this.value=="1" || this.value=="8" || this.value=="10" || this.value=="11")
				{
					document.getElementById((Number(idOfCurrentComponand)+1)).readOnly  = false;
					document.getElementById((Number(idOfCurrentComponand)+2)).readOnly  = false;			
					if(document.getElementById((Number(idOfCurrentComponand)+1)).value  != "" && document.getElementById((Number(idOfCurrentComponand)+2)).value  != "")
					{
						document.getElementById((Number(idOfCurrentComponand)+3)).value=Number(document.getElementById((Number(idOfCurrentComponand)+1)).value) - Number(document.getElementById((Number(idOfCurrentComponand)+2)).value);
						document.getElementById("errorsMessage").innerHTML ='';
					}
				}
				else if(this.value=="9")
				{	
					if((document.getElementById((Number(idOfCurrentComponand)-2)).value.trim()!=""))
					{
						document.getElementById((Number(idOfCurrentComponand)+1)).value  = "0";
						document.getElementById((Number(idOfCurrentComponand)+2)).value  = "0";
						document.getElementById((Number(idOfCurrentComponand)+1)).readOnly  = true;
						document.getElementById((Number(idOfCurrentComponand)+2)).readOnly  = true;
						document.getElementById((Number(idOfCurrentComponand)+3)).value  = document.getElementById((Number(idOfCurrentComponand)-2)).value;
						document.getElementById("errorsMessage").innerHTML ='';
					}
					else
					{
						printMessage("First Enter first Installment Amount for this record","errorsMessage");
					}
				}
			}
			else
			{
				printMessage("No need to select Type of Recovery when claim is not settled, Only input recovery amount","errorsMessage");
				document.getElementById(idOfCurrentComponand).selectedIndex=0;
				setBorder((Number(idOfCurrentComponand)+1));
				document.getElementById((Number(idOfCurrentComponand)+1)).readOnly  = false;
				document.getElementById((Number(idOfCurrentComponand)+2)).value  = "0";			
				document.getElementById((Number(idOfCurrentComponand)+2)).readOnly  = true;
				document.getElementById((Number(idOfCurrentComponand)+3)).value  = "0";			
				document.getElementById((Number(idOfCurrentComponand)+3)).readOnly  = true;
			}
		}
		else
		{
			printMessage("First Enter Claim Ref No. for this record","errorsMessage");
			document.getElementById(idOfCurrentComponand).selectedIndex=0;
			document.getElementById((Number(idOfCurrentComponand)+4)).checked  = false;
		}
	}
}

function indexOfForArray(str)
{
	var decision=false;
	for (i = 0; i < claimRefArray.length; i++) { 
		
	    if(claimRefArray[i]==str)
	    {
	    	decision=true;
	    }
	 }
	return decision;
}
var totalInstrumentalAmount=0;
function goForRecoveryPayment()
{	
//	alert("III="+calculateTotalRemmitedAmount());
	if(calculateTotalRemmitedAmount() > 0)
	{
		document.forms[0].tableRowCount.value=rowCounter-1;
		totalInstrumentalAmount=0;
		var flagForChkvalidRecord=false;
		var falgForCheckingCheckedRecord=false;	
		for (i = 1,j=0; i <= rowCounter-1; i++,j++) 
		{		
			var checkBoxID=Number(i)*10;	
			var hiddenFiledIDCalc=(Number(j)*10)+1;
			if(document.getElementById(checkBoxID).checked==true)
			{		
				
				falgForCheckingCheckedRecord=true;
				var claimRefNoID=(Number(checkBoxID)-9);
				var typeofRecoveryID=(Number(checkBoxID)-4);
				var recoveredAmtID=(Number(checkBoxID)-3);
				var legalExpenceID=(Number(checkBoxID)-2);
				var remmittedAmtID=(Number(checkBoxID)-1);			
			
				if(document.getElementById(claimRefNoID).value.trim()=="")
				{				
					flagForChkvalidRecord=false;
					printMessage("Without entering details of claim ref no. you can't save record no. "+i,"errorsMessage");
					setBorder(claimRefNoID);
					document.getElementById(checkBoxID).checked==false;
					break;
				}
				else if(document.getElementById(typeofRecoveryID).value.trim()=="0" && document.getElementById("HIDDEN"+hiddenFiledIDCalc).value.trim()=="0" )
				{
					flagForChkvalidRecord=false;
					printMessage("Without selecting Type of Recovery option you can't save record no. "+i ,"errorsMessage");
					setBorder(typeofRecoveryID);
					break;
				}
				else if(document.getElementById(recoveredAmtID).value.trim()=="")
				{
					flagForChkvalidRecord=false;
					printMessage("Without entering Recovered amount you can't save record no. "+i ,"errorsMessage");
					setBorder(recoveredAmtID);
					break;
				}
				else if(document.getElementById(legalExpenceID).value.trim()=="")
				{
					flagForChkvalidRecord=false;
					printMessage("Without entering Legal Expenses amount you can't save record no. "+i ,"errorsMessage");
					setBorder(legalExpenceID);
					break;
				}
				else if(document.getElementById(remmittedAmtID).value.trim()=="")
				{
					flagForChkvalidRecord=false;
					printMessage("Without entering Remitted amount you can't save record no. "+i ,"errorsMessage");
					setBorder(remmittedAmtID);
					break;
				}
				else
				{
					if(document.getElementById(remmittedAmtID).value.trim()!="")
					{
						var remmittedAmt=Number(document.getElementById(remmittedAmtID).value);
						if(remmittedAmt<0)
						{
							printMessage("If Remitted Amount is less than zero then you can't save record no. "+i ,"errorsMessage");
							setBorder(remmittedAmtID);
							flagForChkvalidRecord=false;
							break;
						}
						else
						{
							totalInstrumentalAmount=(Number(totalInstrumentalAmount)+(Number(document.getElementById(remmittedAmtID).value)));				
							flagForChkvalidRecord=true;
							printMessage("" ,"errorsMessage");						
							
						}
					}		
				}		
			}	
						
		}	
	//	alert("ClaimSettledOrnotSettledDecision "+ClaimSettledOrnotSettledDecision);
		//alert("flagForChkvalidRecord "+flagForChkvalidRecord);
		if(flagForChkvalidRecord==true && ClaimSettledOrnotSettledDecision==true)
		{	
			printMessage("" ,"errorsMessage");
			document.getElementById("tblRecoveryDetails").style.display="none";
			document.getElementById("recoverydetailTableForHeading").style.display="none";
			document.getElementById("tblPaymentInfo").style.display="table";		
			document.getElementById("tblButton").style.display="table";
			document.getElementById("tblRecoveryDetails22").style.display="none";
			document.getElementById("tblRecoveryDetails11").style.display="none";
			document.getElementById("tblInstructions").style.display="none";
			document.forms[0].backButton.style.display="table";
			document.forms[0].NextButton.style.display="none";
			document.forms[0].addNewRows.style.display="none";
			document.forms[0].resetRecoveryDetails.style.display="none";
			document.forms[0].cancelRecoveryDetails.style.display="none";
			document.getElementById("errorsMessage").innerHTML ='';
			if(paymentModeDecision=="1")
			{
				document.getElementById("DDTable").style.display="table";
				document.getElementById("RTGS_NEFT").style.display="none";
				document.forms[0].totalInstrumentAmount.value=totalInstrumentalAmount;	
				document.forms[0].submitRecoveryDetails.style.display="table";
			}
			else if(paymentModeDecision=="2")
			{		
				document.getElementById("RTGS_NEFT").style.display="table";
				document.getElementById("DDTable").style.display="none";
				document.forms[0].rtgsAmount.value=totalInstrumentalAmount;
				document.forms[0].submitRecoveryDetails.style.display="table";
			}		
			if(paymentModeDecision=="")
			{
				document.forms[0].submitRecoveryDetails.style.display="none";
			}
		}
		
		if(ClaimSettledOrnotSettledDecision==false && flagForChkvalidRecord==true)
		{
			var confirmMessage = confirm("Do you want to save Recovery Details ?");
			if (confirmMessage == true) 
			{
				document.forms[0].action="saveRecoverydataAction.do?method=saveRecoverydata";
				document.forms[0].target="_self";
				document.forms[0].method="POST";
				document.claimRecoveryForm.submit();		  
			} 	
		}
		
		if(falgForCheckingCheckedRecord==false)
		{
			printMessage("Please select atleast 1 record for recovery updation." ,"errorsMessage");		
		}
	}
	else
	{
		printMessage("You cant make payment for zero/minus Remitted amount" ,"errorsMessage");
	}
}


function recoveryOnloadMethod()
{

	addTableRowForRecovery();
	addTableRowForRecovery();
	document.getElementById("errorsMessage").innerHTML ='';
	document.getElementById("tblPaymentInfo").style.display="none";
	document.getElementById("RTGS_NEFT").style.display="none";
	document.getElementById("DDTable").style.display="none";
	document.forms[0].backButton.style.display="none";
	document.forms[0].submitRecoveryDetails.style.display="none";
	document.forms[0].resetRecoveryDetailsPayment.style.display="none";
	document.forms[0].cancelRecoveryDetailsPayment.style.display="none";
	document.forms[0].ddPaymentDate.value="";
	document.forms[0].rtgsPaymentDate.value="";
	document.forms[0].instrumentDate.value="";
	document.forms[0].totalAmountToDisplay.value="";
}

function recoveryFormBackButton()
{
	document.getElementById("errorsMessage").innerHTML ='';
	document.getElementById("tblRecoveryDetails").style.display="table";
	document.getElementById("recoverydetailTableForHeading").style.display="table";
	document.getElementById("tblPaymentInfo").style.display="none";
	document.getElementById("tblRecoveryDetails11").style.display="table";
	document.getElementById("tblRecoveryDetails22").style.display="table";
	document.getElementById("tblInstructions").style.display="table";
	document.getElementById("tblButton").style.display="none";
	document.forms[0].NextButton.style.display="table";
	document.forms[0].addNewRows.style.display="table";
	document.forms[0].resetRecoveryDetails.style.display="table";
	document.forms[0].cancelRecoveryDetails.style.display="table";
	document.getElementById("DDTable").style.display="none";
	document.getElementById("RTGS_NEFT").style.display="none";
	document.forms[0].backButton.style.display="none";	
}

function typeOfPaymentRecovery()
{
	document.getElementById("errorsMessage").innerHTML ='';
	var modPaymentValue=document.forms[0].modeOfPayment.value;
	if(modPaymentValue=="DD")
	{
		document.forms[0].totalInstrumentAmount.value=totalInstrumentalAmount;
		paymentModeDecision="1";
		document.getElementById("DDTable").style.display="table";
		document.getElementById("RTGS_NEFT").style.display="none";
		document.forms[0].submitRecoveryDetails.style.display="table";
		document.forms[0].resetRecoveryDetailsPayment.style.display="table";
		document.forms[0].cancelRecoveryDetailsPayment.style.display="table";
	}
	else if(modPaymentValue=="RTGS_NEFT")
	{
		document.forms[0].rtgsAmount.value=totalInstrumentalAmount;
		paymentModeDecision="2";
		document.getElementById("DDTable").style.display="none";
		document.getElementById("RTGS_NEFT").style.display="table";
		document.forms[0].submitRecoveryDetails.style.display="table";
		document.forms[0].resetRecoveryDetailsPayment.style.display="table";
		document.forms[0].cancelRecoveryDetailsPayment.style.display="table";
	}
	else
	{
		document.getElementById("DDTable").style.display="none";
		document.getElementById("RTGS_NEFT").style.display="none";
		document.forms[0].submitRecoveryDetails.style.display="none";
		document.forms[0].resetRecoveryDetailsPayment.style.display="none";
		document.forms[0].cancelRecoveryDetailsPayment.style.display="none";
	}
}

function saveRecoveryRequest(action)
{
	var flagForChkvalidPaymentDetails=true;
	var modPaymentValue=document.forms[0].modeOfPayment.value;	
	var confirmMessage = "";
	if(modPaymentValue=="DD")
	{	
		var flagddPaymentDate=true;
		if(document.forms[0].ddPaymentDate.value.trim()=="")
		{
			printMessage("Payment date should not be blank." ,"dderrorsMessage");
			document.forms[0].ddPaymentDate.style.borderColor='#3e779d';
			document.forms[0].ddPaymentDate.style.borderWidth='2px';
			flagForChkvalidPaymentDetails=false;	
			
		}
		else
		{
			if(!document.forms[0].ddPaymentDate.value.trim().match(/^(0[1-9]|[12][0-9]|3[01])[\ \/.](?:(0[1-9]|1[012])[\ \/.](19|20)[0-9]{2})$/))
	        {
				printMessage("Invalid Payment date. " ,"dderrorsMessage");	
				document.forms[0].ddPaymentDate.style.borderColor='#3e779d';
				document.forms[0].ddPaymentDate.style.borderWidth='2px';
				flagForChkvalidPaymentDetails=false;
	        }	
			else
			{
				flagddPaymentDate=false;
			}
		}
		
		var flagRTGSPaymentRemaningFields=true;
		if(flagddPaymentDate==false)
		{	
			var instrumentNumChk=false;		
			if(document.forms[0].instrumentNumber.value.trim()!="")
			{
				var instrumentNum=document.forms[0].instrumentNumber.value.trim();			
				var instrumentNumFirstChar=instrumentNum.substring(0,1);
				for(var i =1; i<instrumentNum.length; i++)
				{			
					if(instrumentNumFirstChar!=instrumentNum.substring(i,(Number(i)+1)))
					{			
						instrumentNumChk=true;
					}					
				}
				
				if(document.forms[0].instrumentNumber.value.trim().length < 6 || document.forms[0].instrumentNumber.value.trim().length > 6)
				{
					flagForChkvalidPaymentDetails=false;
					printMessage("Instrument Number should be 6 digit ","dderrorsMessage");
				}
				if(instrumentNumChk==false)
				{				
					flagForChkvalidPaymentDetails=false;
					printMessage("Instrument Number should not repeat same letter/number , invalid Instrument Number" ,"dderrorsMessage");	
				}
			}
			if(document.forms[0].instrumentNumber.value.trim()=="")
			{
				
				printMessage("Instrument Number should not be blank." ,"dderrorsMessage");		
				document.forms[0].instrumentNumber.style.borderColor='#3e779d';
				document.forms[0].instrumentNumber.style.borderWidth='2px';	
				flagForChkvalidPaymentDetails=false;		
			}		
			else if(document.forms[0].drawnAtBank.value.trim()=="")
			{
				printMessage("DrawnAt Bank should not be blank." ,"dderrorsMessage");
				document.forms[0].drawnAtBank.style.borderColor='#3e779d';
				document.forms[0].drawnAtBank.style.borderWidth='2px';	
				flagForChkvalidPaymentDetails=false;		
			}
			else if(document.forms[0].drawnAtBranch.value.trim()=="")
			{
				printMessage("DrawnAt Branch should not be blank." ,"dderrorsMessage");
				document.forms[0].drawnAtBranch.style.borderColor='#3e779d';
				document.forms[0].drawnAtBranch.style.borderWidth='2px';	
				flagForChkvalidPaymentDetails=false;		
			}
			else if(document.forms[0].instrumentpaybleAt.value.trim()=="")
			{
				printMessage("Instrument Payable at should not be blank." ,"dderrorsMessage");
				document.forms[0].instrumentpaybleAt.style.borderColor='#3e779d';
				document.forms[0].instrumentpaybleAt.style.borderWidth='2px';
				flagForChkvalidPaymentDetails=false;			
			}
			else
			{
				flagRTGSPaymentRemaningFields=false;
			}
		}
		
		if(flagRTGSPaymentRemaningFields==false)
		{
			if(document.forms[0].instrumentDate.value.trim()=="")
			{
				printMessage("Instrumenmt date should not be blank." ,"dderrorsMessage");
				document.forms[0].instrumentDate.style.borderColor='#3e779d';
				document.forms[0].instrumentDate.style.borderWidth='2px';
				flagForChkvalidPaymentDetails=false;		
			}
			else
			{
				if(!document.forms[0].instrumentDate.value.trim().match(/^(0[1-9]|[12][0-9]|3[01])[\ \/.](?:(0[1-9]|1[012])[\ \/.](19|20)[0-9]{2})$/))
		        {
					printMessage("Invalid instrument date. " ,"dderrorsMessage");
					document.forms[0].instrumentDate.style.borderColor='#3e779d';
					document.forms[0].instrumentDate.style.borderWidth='2px';
					flagForChkvalidPaymentDetails=false;
		        }
				else
				{
					var today = new Date();
					var month = today.getUTCMonth() + 1; //months from 1-12
					var day = today.getUTCDate();
					var year = today.getUTCFullYear();				
					var instrumentDate=document.forms[0].instrumentDate.value.trim();					
				    var idate = instrumentDate.split("/");				    
					var d1 = new Date(year,month,day);
					var d2 = new Date(idate[2],idate[1],idate[0]);
					if (d1.getTime()<d2.getTime()) {
						printMessage("Instrument date should not be greater than todays date. " ,"dderrorsMessage");
						flagForChkvalidPaymentDetails=false;
					  
					}
					
				}
			}
		}
		
	}
	else if(modPaymentValue=="RTGS_NEFT")
	{		
		var flagrtgsPaymentDate=true;
		
		if(document.forms[0].rtgsPaymentDate.value.trim()=="")
		{
			printMessage("Payment date should not be blank." ,"rtgserrorsMessage");
			document.forms[0].rtgsPaymentDate.style.borderColor='#3e779d';
			document.forms[0].rtgsPaymentDate.style.borderWidth='2px';
			flagForChkvalidPaymentDetails=false;			
		}
		else
		{			
			if(!document.forms[0].rtgsPaymentDate.value.trim().match(/^(0[1-9]|[12][0-9]|3[01])[\ \/.](?:(0[1-9]|1[012])[\ \/.](19|20)[0-9]{2})$/))
	        {
				printMessage("Invalid Payment date. " ,"rtgserrorsMessage");	
				document.forms[0].rtgsPaymentDate.style.borderColor='#3e779d';
				document.forms[0].rtgsPaymentDate.style.borderWidth='2px';
				flagForChkvalidPaymentDetails=false;
	        }
			else
			{
				//flagrtgsPaymentDate=false;
				
				var today = new Date();
				var month = today.getUTCMonth() + 1; //months from 1-12
				var day = today.getUTCDate();
				var year = today.getUTCFullYear();				
				var instrumentDate=document.forms[0].rtgsPaymentDate.value.trim();					
			    var idate = instrumentDate.split("/");				    
				var d1 = new Date(year,month,day);
				var d2 = new Date(idate[2],idate[1],idate[0]);
				if (d1.getTime()<d2.getTime()) {
					printMessage("payment date should not be greater than todays date. " ,"rtgserrorsMessage");
					flagForChkvalidPaymentDetails=false;
				  
				}
				else
				{
					flagrtgsPaymentDate=false;
				}
			}
		}
		
		if(flagrtgsPaymentDate==false)
		{
			if(document.forms[0].utrNumber.value.trim()=="")
			{			
				printMessage("UTR Number should not be blank." ,"rtgserrorsMessage");	
				document.forms[0].utrNumber.style.borderColor='#3e779d';
				document.forms[0].utrNumber.style.borderWidth='2px';
				flagForChkvalidPaymentDetails=false;			
			}
			else
			{
				if(document.forms[0].utrNumber.value.trim().length < 15 || document.forms[0].utrNumber.value.trim().length > 22)
				{
					printMessage("UTR Number should not be less than 15 digit and greter than 22 digit ." ,"rtgserrorsMessage");
					flagForChkvalidPaymentDetails=false;
				}
				
			}
		}
		
	}
	if(flagForChkvalidPaymentDetails==true)
	{	
		document.getElementById("errorsMessage").innerHTML ='';
		if(modPaymentValue=="DD")
		{
			confirmMessage = confirm("Total Amount :-"+totalInstrumentalAmount+"\n Instrument Number :- "+document.forms[0].instrumentNumber.value+" \n Do you want to save all recovery details?");
		}
		else
		{
			confirmMessage = confirm("Total Amount :-"+totalInstrumentalAmount+"\n DD/UTR Number :- "+document.forms[0].utrNumber.value+" \n Do you want to save all recovery details?");
		}
		if (confirmMessage == true) 
		{
			document.forms[0].action=action;
			document.forms[0].target="_self";
			document.forms[0].method="POST";
			document.claimRecoveryForm.submit();		  
		} 	
	}	
}
function resetRecoveryDetails1()
{
	
	var constant=10;
	for(var i=1; i<=rowCounter-1; i++)
	{
		var previouseNum=(Number(i)-1);
		var id=((constant*previouseNum)+1);
		document.getElementById(id).readOnly = false;	
	}
	claimRefArray.splice(0,claimRefArray.length);	
	arrayForTypeOfRecovery.splice(0,arrayForTypeOfRecovery.length);	
	
	var beginingId=1;
	for(var i=1; i<=rowCounter-1; i++)
	{
		var idforTypeOfRecovery=1;
		for(var j=beginingId; j<=i*10; j++)
		{			
			if(idforTypeOfRecovery==6)
			{
				//alert("hi");
				document.getElementById(j).selectedIndex=0;
				document.getElementById(j).disabled=false;
				document.getElementById(Number(j)+2).disabled=false;
				document.getElementById(Number(j)+3).disabled=false;
			}
			else if(idforTypeOfRecovery==10)
			{			
				document.getElementById(j).checked = false;
			}
			else
			{
				if(idforTypeOfRecovery==7 || idforTypeOfRecovery==8)
				{
					document.getElementById(j).readOnly  = false;
					document.getElementById(j).readOnly  = false;
				}
				document.getElementById(j).value="";
				
			}
			beginingId=j;		
			idforTypeOfRecovery++;
			if(idforTypeOfRecovery==11)
			{			
				beginingId++;				
			}
		}
	}

	 document.getElementById("errorsMessage").innerHTML ='';
	document.forms[0].totalAmountToDisplay.value="";		

}
function  cancelRecoveryDetails1()
{
	location.href = "home.do?method=getMainMenu&menuIcon=CP&mainMenu=Update Recovery Info";
}
var ClaimSettledOrnotSettledDecision=false;
function calculateTotalRemmitedAmount()
{
	document.getElementById("errorsMessage").innerHTML ='';
	totalInstrumentalAmount=0;	
	var localClaimSettledOrnotSettledDecision=false;
	
	for (i = 1; i <= rowCounter-1; i++) 
	{		
		var checkBoxID=Number(i)*10;		
		if(document.getElementById(checkBoxID).checked==true)
		{
			var remmittedAmtID=(Number(checkBoxID)-1);
			totalInstrumentalAmount=(Number(totalInstrumentalAmount)+(Number(document.getElementById(remmittedAmtID).value)));
			//alert((Number(checkBoxID)-9));
			if(document.getElementById("HIDDEN"+(Number(checkBoxID)-9)).value=="1")
			{
				
			}
			else
			{
				localClaimSettledOrnotSettledDecision=true;
				ClaimSettledOrnotSettledDecision=true;
			}
		}
	}
	document.forms[0].totalAmountToDisplay.value=totalInstrumentalAmount;	
//	alert("totalInstrumentalAmount "+totalInstrumentalAmount);
//	alert("localClaimSettledOrnotSettledDecision "+localClaimSettledOrnotSettledDecision);
	if(localClaimSettledOrnotSettledDecision==false && totalInstrumentalAmount ==0)
	{
	//	alert("Inside total amount");
		ClaimSettledOrnotSettledDecision=false;
		totalInstrumentalAmount=1;
	}
	return totalInstrumentalAmount;
}

if(typeof String.prototype.trim !== 'function') 
{
	  String.prototype.trim = function() 
	  {
	    return this.replace(/^\s+|\s+$/g, ''); 
	  };
}

function resetRecoveryPaymentDetails()
{
	
	document.getElementById("errorsMessage").innerHTML ='';
	var modPaymentValue=document.forms[0].modeOfPayment.value;
	if(modPaymentValue=="DD")
	{
		document.forms[0].ddPaymentDate.value="";		
		document.forms[0].instrumentNumber.value="";	
		document.forms[0].instrumentDate.value="";	
		document.forms[0].drawnAtBank.value="";
		document.forms[0].drawnAtBranch.value="";
		document.forms[0].instrumentpaybleAt.value="";	
	}
	else if(modPaymentValue=="RTGS_NEFT")
	{
		document.forms[0].rtgsPaymentDate.value="";		
		document.forms[0].utrNumber.value="";	
		
	}	
}

function setBorder(id)
{
	document.getElementById(id).style.borderColor='#3e779d';
	document.getElementById(id).style.borderWidth='2px';
}

function funcTenureModificationChk()
{
	var xmlhttp;
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
    }  
    xmlhttp.open("POST", "getUnitForTenureRequest.do?method=tenureModificationCGPanValidation&cgpanForClosure="+document.forms[0].cgpanForClosure.value+"&memberIdForClosure="+document.forms[0].memberIdForClosure.value, true);       		      
    xmlhttp.onreadystatechange = function() {    	
    if (xmlhttp.readyState == 4) 
	{    	   
            if (xmlhttp.status == 200)
            {            	
            	  var temp = new Array();
	              temp =xmlhttp.responseText;	         
	              if(temp!='')
	              {	           
		            document.getElementById("errorsMessage").innerHTML = temp;
		            var started = Date.now();
		            var interval = setInterval(function()
		            {		            	
		                if (Date.now() - started > 4500) 
		                {
		                	clearInterval(interval);

		                } else 
		                {		                 
		                	document.getElementById("errorsMessage").innerHTML ='';
		                }
		              }, 4000);
	              }
	              else
	              {
	                 document.gmClosureForm.submit();
	              }
            }
            else
            {
            	document.getElementById("errorsMessage").innerHTML = 'Something is wrong !! , Please contact CGTMSE support[support@cgtmse.in] team .';                
            }
        }
    };
    xmlhttp.send(null);
}


var arrayForSelectedBorrowalApprovalInput =new Array();
var arrayForSelectedTenureApprovalInput =new Array();
var counterForTenureApproval=0;
function validateSelectOptionBorrowerDetails(value,id)
{
	//alert(value+" "+id);	
	if(id.length > 10)
	{
		var res = id.substring(id.length-10, id.length -1);	
		if(value=='RE' || value=='AP')
		{	

			 if(arrayForSelectedBorrowalApprovalInput.length > 0)
			 {
				 var flag=false;
			        for(var i=0;i<arrayForSelectedBorrowalApprovalInput.length;i++)
			        {
			            if(arrayForSelectedBorrowalApprovalInput[i] == res)
			            {			    			
			    			flag=true;
			            	
			            }			                
			        }
			        
			        if(flag==false)
			        {
			        	//alert('value added as '+res);
			        	arrayForSelectedBorrowalApprovalInput.push(res);
			        }
			 }	
			 else
			 {
				// alert('value added as '+res);
				 arrayForSelectedBorrowalApprovalInput.push(res);
			 }
		}		
		else
		{			//alert('value removed as '+res);
				for(var i = 0; i<arrayForSelectedBorrowalApprovalInput.length; i++)
				{
					if (arrayForSelectedBorrowalApprovalInput[i] == res)
					{
						arrayForSelectedBorrowalApprovalInput.splice(i, 1);
					}
				}
			//var i = arrayForSelectedBorrowalApprovalInput.indexOf(res);
			//arrayForSelectedBorrowalApprovalInput.splice(res,arrayForSelectedBorrowalApprovalInput.length);
		}
	}
	
	var arrayLength = arrayForSelectedBorrowalApprovalInput.length;
	for (var i = 0; i < arrayLength; i++) {
	  //  alert("Last array value "+arrayForSelectedBorrowalApprovalInput[i]);
	    //Do something
	}
}

function validateSelectOptionTenureDetails(value,id)
{
	//alert(id);
	//alert(id.indexOf("("));
	var res1=id.substring((id.indexOf("(")+1), id.length -1);	
	//
//	var res = id.substring(id.length-10, id.length -1);	;
	//var res =value;
	
	var res=res1+value;
	//alert(res);
		if(value=='RE' || value=='AP')
		{	
			// alert("length "+arrayForSelectedTenureApprovalInput.length);
			 if(arrayForSelectedTenureApprovalInput.length > 0)
			 {
				 var flag=false;
			        for(var i=0;i<arrayForSelectedTenureApprovalInput.length;i++)
			        {
			            if(arrayForSelectedTenureApprovalInput[i] == res)
			            {			    			
			    			flag=true;
			            	
			            }			                
			        }
			        
			        if(flag==false)
			        {
			        //	alert('value added  '+res);
			        	arrayForSelectedTenureApprovalInput.push(res);
			        }
			 }	
			 else
			 {
				// alert('value added '+res);
				 arrayForSelectedTenureApprovalInput.push(res);
			 }
		}		
		else
		{			
			//alert("else block for remaval"+arrayForSelectedTenureApprovalInput.length);
				for(var i = 0; i<arrayForSelectedTenureApprovalInput.length; i++)
				{
					
					var arrayvalue=arrayForSelectedTenureApprovalInput[i].substring(0,arrayForSelectedTenureApprovalInput[i].length-2);
					//alert("Remaval "+arrayvalue+"and response "+res);
					if (arrayvalue == res)
					{
						//alert("removal both are equal");
						arrayForSelectedTenureApprovalInput.splice(i, 1);
						//alert('value removed as '+res);
					}
				}
			//var i = arrayForSelectedBorrowalApprovalInput.indexOf(res);
			//arrayForSelectedBorrowalApprovalInput.splice(res,arrayForSelectedBorrowalApprovalInput.length);
		}
		
		var arrayLength = arrayForSelectedTenureApprovalInput.length;
		for (var i = 0; i < arrayLength; i++) {
		  alert("Last array Iteration "+arrayForSelectedTenureApprovalInput[i]);
		}
	
}
function submitFormTenureBorrowerDetails(action)
{	
	var arrayLength = arrayForSelectedTenureApproval_ALL_InputUpdated.length;
	//alert("submitFormTenureBorrowerDetails array length "+arrayLength);
	if(arrayLength==0)
		{
			document.getElementById("errorsMessage").innerHTML = "Please select atleast 1 record for Tenure approval";
		}
	else
		{
	//	alert("inside else");
	var flag=false;

	if(arrayForSelectedTenureApproval_Rejected_InputUpdated.length > 0)
	{
		flag=true;
	}
		
	if(flag==true)
	{
		 if(document.forms[0].chkTermsAndCondition.checked==false)
			{		
				  document.getElementById("errorsMessage").innerHTML = "Please select 'Account is Standard and Regular' decision.";
			}			
			else if(document.forms[0].chkTermsAndCondition1.checked==false)
			{		
				  document.getElementById("errorsMessage").innerHTML = "Please select 'The changes made are as per the record available with the bank and are duly approved by the delegated authority' decision.";
			}	
			else
				{
				document.forms[0].action=action;
				document.forms[0].target="_self";
				document.forms[0].method="POST";
				document.forms[0].submit();
				arrayForSelectedTenureApproval_ALL_InputUpdated.length=0;
				arrayForSelectedTenureApproval_Rejected_InputUpdated=0;
				}
	}
	else
		{
			document.forms[0].action=action;
			document.forms[0].target="_self";
			document.forms[0].method="POST";
			document.forms[0].submit();
			arrayForSelectedTenureApproval_ALL_InputUpdated.length=0;
			arrayForSelectedTenureApproval_Rejected_InputUpdated=0;
		}
	
		}
	
	/*var arrayLength = arrayForSelectedTenureApprovalInput.length;
	//alert("submitFormTenureBorrowerDetails array length "+arrayLength);
	if(arrayLength==0)
		{
			document.getElementById("errorsMessage").innerHTML = "Please select atleast 1 record for borrower approval";
		}
	else
		{
	//	alert("inside else");
	var flag=false;
	for (var i = 0; i < arrayLength; i++) {
		//alert("submitFormTenureBorrowerDetails "+arrayForSelectedTenureApprovalInput[i]);
		var statusvalue=arrayForSelectedTenureApprovalInput[i].substring((arrayForSelectedTenureApprovalInput[i].length-2),arrayForSelectedTenureApprovalInput[i].length);
		if(statusvalue=="AP")
		{
		//	alert("AP record exist");
			flag=true;
		}
		else
			{
		//	alert("AP record not exist");
			}
	}
		
	if(flag==true)
	{
		 if(document.forms[0].chkTermsAndCondition.checked==false)
			{		
				  document.getElementById("errorsMessage").innerHTML = "Please select 'Account is Standard and Regular' decision.";
			}			
			else if(document.forms[0].chkTermsAndCondition1.checked==false)
			{		
				  document.getElementById("errorsMessage").innerHTML = "Please select 'The changes made are as per the record available with the bank and are duly approved by the delegated authority' decision.";
			}	
			else
				{
				document.forms[0].action=action;
				document.forms[0].target="_self";
				document.forms[0].method="POST";
				document.forms[0].submit();
				arrayForSelectedTenureApprovalInput.length=0;
				}
	}
	else
		{
	//	document.forms[0].action=action;
	//	document.forms[0].target="_self";
		//document.forms[0].method="POST";
		//document.forms[0].submit();
		arrayForSelectedTenureApprovalInput.length=0;
		}
	
		} */
    var started = Date.now();
    var interval = setInterval(function(){

        // for 1.5 seconds
        if (Date.now() - started > 4500) {

          // and then pause it
          clearInterval(interval);

        } else {
          // the thing to do every 100ms
        	document.getElementById("errorsMessage").innerHTML ='';
        }
      }, 4000); 
}
function submitFormApproveBorrowerDetails(action)
{	
	
	
		if(arrayForSelectedTenureApproval_ALL_InputUpdated.length==0)
		{		
			  document.getElementById("errorsMessage").innerHTML = "Please select atleast 1 record for borrower approval";
		}		
		else
			{
			var flag=false;			
			if(arrayForSelectedTenureApproval_Rejected_InputUpdated.length > 0)
			{
				flag=true;
			}
			
			
			if(flag==true)
			{
				 if(document.forms[0].declaration.checked==false)
					{		
						  document.getElementById("errorsMessage").innerHTML = "Please select 'Account is Standard and Regular' decision.";
					}			
					else if(document.forms[0].declaration1.checked==false)
					{		
						  document.getElementById("errorsMessage").innerHTML = "Please select 'The changes made are as per the record available with the bank and are duly approved by the delegated authority' decision.";
					}	
					else
						{
						submitForm(action);
						arrayForSelectedTenureApproval_ALL_InputUpdated.length=0;
						arrayForSelectedTenureApproval_Rejected_InputUpdated=0;
						}
			}
			else
				{
					submitForm(action);
					arrayForSelectedTenureApproval_ALL_InputUpdated.length=0;
					arrayForSelectedTenureApproval_Rejected_InputUpdated=0;
				
				}
			
		
			
			}
		
	      var started = Date.now();
          var interval = setInterval(function(){

              // for 1.5 seconds
              if (Date.now() - started > 4500) {

                // and then pause it
                clearInterval(interval);

              } else {
                // the thing to do every 100ms
              	document.getElementById("errorsMessage").innerHTML ='';
              }
            }, 4000); 
}



var arrayForSelectedTenureApproval_Rejected_InputUpdated =new Array();
var arrayForSelectedTenureApproval_ALL_InputUpdated =new Array();


function validateSelectOptionTenureDetailsUpdated(value,id)
{
	//alert("value "+value+" ID "+id);
	var res1=id.substring((id.indexOf("(")+1), id.length -1);
	var res=res1+value;
 
   value=value.substring(0,2);
	
		if(value=='AP')
		{
			//alert("AP");
			
			if(checkDubInArray(arrayForSelectedTenureApproval_ALL_InputUpdated,id)==false)
			{
			//	alert(checkDubInArray(arrayForSelectedTenureApproval_ALL_InputUpdated,id));
				arrayForSelectedTenureApproval_ALL_InputUpdated.push(id);
			}
			
		
			
			arrayForSelectedTenureApproval_Rejected_InputUpdated.push(id);
			
		}
		else if (value=='RE')
		{
			//alert("RE");
			//alert(checkDubInArray(arrayForSelectedTenureApproval_ALL_InputUpdated,id));
			if(checkDubInArray(arrayForSelectedTenureApproval_ALL_InputUpdated,id)==false)
				{
			arrayForSelectedTenureApproval_ALL_InputUpdated.push(id);
			
				}
			
			for (var i = 0; i < arrayForSelectedTenureApproval_Rejected_InputUpdated.length; i++) {
		        if (arrayForSelectedTenureApproval_Rejected_InputUpdated[i] === id) {
		        	arrayForSelectedTenureApproval_Rejected_InputUpdated.splice(i, 1);
		            i--;
		        }
		    }
		}
		else
		{
			// alert("Nathing selected");
			
			//arrayForSelectedTenureApproval_ALL_InputUpdated.splice(id);
			
			for (var i = 0; i < arrayForSelectedTenureApproval_ALL_InputUpdated.length; i++) {
		        if (arrayForSelectedTenureApproval_ALL_InputUpdated[i] === id) {
		        	arrayForSelectedTenureApproval_ALL_InputUpdated.splice(i, 1);
		            i--;
		        }
		    }
			
			for (var i = 0; i < arrayForSelectedTenureApproval_Rejected_InputUpdated.length; i++) {
		        if (arrayForSelectedTenureApproval_Rejected_InputUpdated[i] === id) {
		        	arrayForSelectedTenureApproval_Rejected_InputUpdated.splice(i, 1);
		            i--;
		        }
		    }
		}
		
		//alert("AP length "+arrayForSelectedTenureApproval_Approved_InputUpdated.length);
		////alert("ALL length "+arrayForSelectedTenureApproval_ALL_InputUpdated.length);
		
		for (var i = 0; i < arrayForSelectedTenureApproval_ALL_InputUpdated.length; i++) {
		//	  alert("Last array Iteration "+arrayForSelectedTenureApproval_ALL_InputUpdated[i]);
			  
			  
			}
		//alert("Approved array length "+arrayForSelectedTenureApproval_Rejected_InputUpdated.length);
		//alert("ALL array length "+arrayForSelectedTenureApproval_ALL_InputUpdated.length);
}
function checkDubInArray(array, id) {
	  for (var i = 0; i < array.length; i++) {
	    if (array[i] === id)
	      return true;
	  }
	  return false;
	}
	
	
	
	
	function fetchTenureApprovalData(RemarkValue)
{
	//alert('Hi'+RemarkValue);
	arrayForSelectedTenureApproval_ALL_InputUpdated.length=0;
	arrayForSelectedTenureApproval_Rejected_InputUpdated.length=0;
	if(RemarkValue=='Reschedulement_Rephasement'  || RemarkValue=='2')
	{

		 document.getElementById("divTenureApproval").style.display  = "inline";
			var xmlhttp;
		    if (window.XMLHttpRequest){
		        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
		    } else {
		        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
		    }  
		    xmlhttp.open("GET", "afterTenureApproval.do?method=fetchTenureApprovalData&Remark="+RemarkValue, true);       		      
		    xmlhttp.onreadystatechange = function() {    	
		    if (xmlhttp.readyState == 4) 
			{    	   
		            if (xmlhttp.status == 200)
		            {            	
		            	  var temp = new Array();
			              temp =xmlhttp.responseText;	
			      
			              var json ;
			              if(temp!='')
			              {
	         

var json = eval('(' + temp + ')');

	              
			            	    var i;
			            	    var out = "<table>" +
			            	    		"<tr>" +
				            	    		"<th align='left' valign='top' class='ColumnBackground' width='20' >SNo.</th>" +
				            	    		"<th align='left' valign='top' class='ColumnBackground' width='98' >Member Id</th>" +
				            	    		"<th align='left' valign='top' class='ColumnBackground' width='98' >CGPAN</th>" +
				            	    		"<th align='left' valign='top' class='ColumnBackground' width='98' >Unit Name</th>" +
				            	    		"<th align='left' valign='top' class='ColumnBackground' width='98' >App Status</th>" +
				            	    		"<th align='left' valign='top' class='ColumnBackground' width='83' > Original Tenure </th>" +
				            	    		"<th align='left' valign='top' class='ColumnBackground' width='114' >Revised Expiry Date</th>" +
				            	    		"<th align='left' valign='top' class='ColumnBackground' width='114' >Revised Tenure</th>" +
				            	    		"<th align='left' valign='top' class='ColumnBackground' width='114' >Sanctioned Date/Guarantee Start Date</th>" +
				            	    		"<th align='left' valign='top' class='ColumnBackground' width='98' >Modification Remarks</th>" +
				            	    		"<th align='left' valign='top' class='ColumnBackground' width='116' >Decision</th>" +
			            	    		"</tr>";
		
			            	    for(i = 0; i < json.length; i++) {
			            	    	var j=Number(i)+1;
			            	        out += "<tr><td class='ColumnBackground'  >" +
			            	        j +
			            	        "</td><td class='ColumnBackground' >" +
			            	        json[i].memberID +
			            	        "</td><td class='ColumnBackground' >" +
			            	        json[i].cgPan + "</td>" +
			            	        "</td><td class='ColumnBackground' >" +
			            	        json[i].unitName + "</td>" +
			            	        "</td><td class='ColumnBackground' >" +
			            	        json[i].applicationStatus + "</td>" +
			            	        "</td><td class='ColumnBackground' >" +
			            	        json[i].existingTenure + "</td>" +
			            	        "</td><td class='ColumnBackground' >" +
			            	        json[i].reviseExpiryDate + "</td>" +
			            	        "</td><td class='ColumnBackground' >" +
			            	        json[i].reviseTenure + "</td>" +
			            	        "</td><td class='ColumnBackground' >" +
			            	        json[i].sanctionDate + "</td>" +
			            	        "</td><td class='ColumnBackground' >" +
			            	        json[i].modificationRemark + "</td>" +
			            	        "</td><td class='ColumnBackground' >" +
			            	        "<select  name='closureCgpan("+json[i].cgPan+")' onchange=validateSelectOptionTenureDetailsUpdated(this.value,this.name); >" +
				            	        "<option value=''>SELECT</option>" +
				            	        "<option value='AP='"+json[i].modificationRemark+">APPROVE</option>" +
				            	        "<option value='RE='"+json[i].modificationRemark+">REJECT</option>" +
			            	        "</select>" +
			            	        "</td>" +
			            	        "</tr>" ;
	         
			            	        
			            	    }
			            	    
			            	    if(RemarkValue=='Reschedulement_Rephasement')
			            	    {
						            	 out += "<tr>" +
						            	 "<td class='ColumnBackground' colspan='11'  > In case of above mentioned 'Approved' cases , MLI accept and declare that " +	
						            	  "</td> " +	
						            	 "</tr>" +
						            	 "<tr>" +
					            	    "<td class='ColumnBackground' colspan='11'  >" +			    	        	
					    	        	"<input type='checkbox' id='chkTermsAndCondition1' name='gmPeriodicInfoForm'> The changes made are as per the record available with the bank and are duly approved by the delegated authority."+							   
					    	            "</td> " +			    	        
					    	            "</tr>"; 
			            	    }
			            	    else
			            	    {
			            	    	 	 out += "<tr>" +
			            	    		 "<td class='ColumnBackground' colspan='11'  > In case of above mentioned 'Approved' cases , MLI accept and declare that" +			
			            	    		 "</td> " +
			            	    	 	 "</tr>" +
			            	    	 	 "<tr>" +
			            	    	 "<td class='ColumnBackground' colspan='11'  >" +			    	        	
					    	        	"<input type='checkbox' id='chkTermsAndCondition' name='gmPeriodicInfoForm'> Account is Standard and Regular. "+							   
					    	            "</td> " +
					    	            "</tr>"+
					    	            "<tr>" +
					            	    "<td class='ColumnBackground' colspan='11'  >" +			    	        	
					    	        	"<input type='checkbox' id='chkTermsAndCondition1' name='gmPeriodicInfoForm'> The changes made are as per the record available with the bank and are duly approved by the delegated authority."+							   
					    	            "</td> " +			    	        
					    	            "</tr>"; 
			            	    }
			            	    
			            	        out += "<tr>" +
			            	    "<td class='ColumnBackground' colspan='11'  >" +			    	        	
			    	        	"<font size='2'><div align='center' id='errorsMessage' class='errorsMessageNew'></div></font>"+							   
			    	            "</td> " +			    	        
			    	            "</tr>"; 
			            	    
			            	    out += "<tr>" +
			            	    		"<td class='ColumnBackground' colspan='11' >" +
		        	        	"<DIV align='center'>" +
		        	        	"<A href=javascript:submitFormTenureBorrowerDetailsNew('afterTenureApproval.do?method=afterTenureApproval') > "+
							" <IMG src='images/Save.gif' alt='Save' width='49' height='37' border='0'></A> "+						
							" <A href='home.do?method=getMainMenu&menuIcon=GM&mainMenu=GM_TENURE_APPROVE'> " +
							" <IMG src='images/Cancel.gif' alt='Cancel' width='49' height='37' border='0'></A> "+
		        	        	"</DIV>" +
		        	        "</td> " +
		        	        
		        	        "</tr>";
			            	    out += "</table>";
	         
			            	    document.getElementById("divTenureApproval").innerHTML = out;
			              }
			              else
			              {
			            	  document.getElementById("errorsMessage").innerHTML = "Tenure approval data does not exist.";
			              }
		            }
		            else
		            {
		            //	document.getElementById("errorsMessage").innerHTML = 'Something is wrong !! , Please contact CGTMSE support[support@cgtmse.in] team .';                
		            }
		        }
		    };
		    xmlhttp.send(null);
	}
	else
	{	
	         
		document.getElementById("divTenureApproval").style.display  = "none";
	}
}


function submitFormTenureBorrowerDetailsNew(action)
{
	
	if(document.forms[0].remarksOnNpa.value=='Reschedulement_Rephasement')
	{
		if(arrayForSelectedTenureApproval_ALL_InputUpdated.length ==0)
		{
			document.getElementById("errorsMessage").innerHTML = "Please select atleast 1 record for borrower approval";
		}
		else
		{
			
			if(arrayForSelectedTenureApproval_Rejected_InputUpdated.length > 0)
			{
				
				if(document.forms[0].chkTermsAndCondition1.checked==false)
				{
					document.getElementById("errorsMessage").innerHTML = "Please select 'The changes made are as per the record available with the bank and are duly approved by the delegated authority' decision.";
				}
				else
				{
					document.forms[0].remarksOnNpa.selectedIndex =0;
					submitForm(action);
				}
			}
			else
			{
				document.forms[0].remarksOnNpa.selectedIndex =0;
				submitForm(action);
			}
		
		}
	}
	else
	{
		if(arrayForSelectedTenureApproval_ALL_InputUpdated.length ==0)
		{
			document.getElementById("errorsMessage").innerHTML = "Please select atleast 1 record for borrower approval";
		}
		else
		{
			if(arrayForSelectedTenureApproval_Rejected_InputUpdated.length > 0)
			{
				if(document.forms[0].chkTermsAndCondition.checked==false)
				{
					document.getElementById("errorsMessage").innerHTML = "Please select 'Account is Standard and Regular' decision.";
				}
				else if(document.forms[0].chkTermsAndCondition1.checked==false)
				{
					document.getElementById("errorsMessage").innerHTML = "Please select 'The changes made are as per the record available with the bank and are duly approved by the delegated authority' decision.";
				}
				else
				{
					document.forms[0].remarksOnNpa.selectedIndex =0;
					submitForm(action);					
				}
			}
			else
			{
				document.forms[0].remarksOnNpa.selectedIndex =0;
				submitForm(action);				
			}
		}
	}
	
    var started = Date.now();
    var interval = setInterval(function(){

        // for 1.5 seconds
        if (Date.now() - started > 4500) {

          // and then pause it
          clearInterval(interval);

        } else {
          // the thing to do every 100ms
        	document.getElementById("errorsMessage").innerHTML ='';
        }
      }, 4000); 
}

