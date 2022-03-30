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
	alert("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
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
		{alert("select");
			performAction(setSubMenuOptions.arguments[1]+"/"+homeAction+"&menuIcon="+selection+"&mainMenu="+mainMenuItem+"&subMenu="+subMenuItem);
			document.forms[0].SubMenu.selectedIndex=0;
			subMenu.disabled=true;
		}
		else
		{alert("else");
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
						newStr+="%20"
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

function submitForm(action)
{
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
	var obj=obj1.options[obj1.selectedIndex].value
	var objName=findObj("sourceId");
	var objValue=findObj("sourceType");
	
	if(objValue.value == "")
	{
		objName.value = "";
	}
	else
	{
		var str = "001"
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
	var obj=obj1.options[obj1.selectedIndex].value
	var objName=findObj("referenceId");
	var objValue=findObj("destinationType");
	if(objValue.value == "")
	{
		objName.value = "";
	}
	else
	{
		var str = "001"
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
			if (stmtBalVal==0 || unclBalVal==0 || amtCaVal==0)
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