<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
 <% //session.setAttribute("CurrentPage","displayClaimApproval.do?method=displayClaimApproval");%>
 
 
<%-- <% session.setAttribute("CurrentPage","saveTcClaimProcessDetails.do?method=saveTcClaimProcessDetailsNew");%> --%>

<html:errors />
<html:form action="displayTCQueryDetail.do?method=displayTCQueryDetail" method="POST" enctype="multipart/form-data">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

<tr>
<td class="FontStyle">&nbsp;</td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
<td background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
<td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
</tr>
<tr>
<td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
<td><table width="100%" border="0" cellspacing="1" cellpadding="0">
  <tr>
    <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td width="35%" class="Heading">&nbsp;TC QUERY REPORT</td>
	  <td colspan="3"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
	</tr>
	<tr>
	  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
	</tr>
      </table></td>
  </tr>
  
  
  <tr><td>
 <table width="100%" border="0" cellspacing="1" cellpadding="0">
 <tr>
				<td class="TableData"> &nbsp;<font color="red">Q1 :</font>&nbsp;<bean:message key="question1"/></td>
 </tr> 
				<tr>
				<td class="TableData"> &nbsp;<font color="red">Q2:</font>&nbsp;<bean:message key="question2"/></td>
        </tr> 
				<tr>
				<td class="TableData"> &nbsp;<font color="red">Q3:</font>&nbsp;<bean:message key="question3"/></td>
        </tr> 
				<tr>
				<td class="TableData"> &nbsp;<font color="red">Q4:</font>&nbsp;<bean:message key="question4"/></td>
        </tr> 
        <tr>
        <td class="TableData"> &nbsp;<font color="red">Q5:</font>&nbsp;<bean:message key="question5"/></td>
        </tr> 
        <tr>
         <td class="TableData"> &nbsp;<font color="red">Q6:</font>&nbsp;<bean:message key="question6"/></td>
        </tr> 
        <tr>
         <td class="TableData"> &nbsp;<font color="red">Q7:</font>&nbsp;<bean:message key="question7"/></td>
        </tr>
        <tr>
         <td class="TableData"> &nbsp;<font color="red">Q8:</font>&nbsp;<bean:message key="question8"/></td>
        </tr>
        <tr>
         <td class="TableData"> &nbsp;<font color="red">Q9:</font>&nbsp;<bean:message key="question9"/></td>
        </tr>
        <tr>
         <td class="TableData"> &nbsp;<font color="red">Q10:</font>&nbsp;<bean:message key="question10"/></td>
       </tr>
       <tr>
         <td class="TableData"> &nbsp;<font color="red">Q11:</font>&nbsp;<bean:message key="question11"/></td>
       </tr>
       <tr>
         <td class="TableData"> &nbsp;<font color="red">Q12:</font>&nbsp;<bean:message key="question12"/></td>
       </tr>
       <tr>
         <td class="TableData"> &nbsp;<font color="red">Q13:</font>&nbsp;<bean:message key="question13"/></td>
       </tr>
       <tr>
         <td class="TableData"> &nbsp;<font color="red">Q14:</font>&nbsp;<bean:message key="question14"/></td>
       </tr>
 </table>
 </td></tr>
  
   <logic:notEqual property="danSummary" value="0" name="cpTcDetailsForm">
  <tr>
    <td colspan="18" class="SubHeading"><br>
      &nbsp;Temporary Closed Claims</td>
  </tr>
  <tr>
    <td colspan="18"><table width="100%" border="0" cellspacing="1" cellpadding="0">
    
	<tr class="HeadingBg">
	  <td><strong>Sr.No.</strong></td>
	  <td> <div align="center">&nbsp;<bean:message key="firstclaim"/></div></td>
          <td> <div align="center">&nbsp;CGPAN</div></td>
    <td> <div align="center">&nbsp;Q1&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q2&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q3&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q4&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q5&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q6&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q7&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q8&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q9&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q10&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q11&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q12&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q13&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q14&nbsp;</div></td> 
    <td> <div align="center">&nbsp;Ltr Ref No&nbsp;</div></td>
    <td> <div align="center">&nbsp;<bean:message key="referenceDate"/></div></td>
    
	</tr>
  
	<%
	java.util.ArrayList tcqry=(java.util.ArrayList)request.getAttribute("danSummary");

    int r=0;
    System.out.println("tcqry.size()....."+tcqry.size());
     for( int y=0;y<tcqry.size();y++)
        {
        String str[]=new String[19];
        
        str=(String [])tcqry.get(y);
     
       r=r+1;
       
        
        %>
        
 <tr class="TableData">
	  <td><div align="center">&nbsp;<%=r%></div></td>
  
      <td><div align="center">&nbsp;<%=str[1]%>
              &nbsp;&nbsp;</div></td>
	  <td><div align="center">&nbsp;<%=str[0]%></div></td>
	  <td><div align="center">&nbsp;
           <%
            String qn1=str[2];  
            if(qn1.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn1" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn1" value="Y"  disabled/>
            
          <%  }
            %>
          
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp; <%
            String qn2=str[3];  
            if(qn2.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn2" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn2" value="Y"  disabled/>
            
          <%  }
            %>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;
              <%
            String qn3=str[4];  
            if(qn3.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn3" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn3" value="Y"  disabled/>
            
          <%  }
            %>
               &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;
              
                <%
            String qn4=str[5];  
            if(qn4.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn4" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn4" value="Y"  disabled/>
            
          <%  }
            %>
              
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;
              
                <%
            String qn5=str[6];  
            if(qn5.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn5" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn5" value="Y"  disabled/>
            
          <%  }
            %>
          
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;
                <%
            String qn6=str[7];  
            if(qn6.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn6" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn6" value="Y"  disabled/>
            
          <%  }
            %>
              
      
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;
               <%
            String qn7=str[8];  
            if(qn7.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn7" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn7" value="Y"  disabled/>
            
          <%  }
            %>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;  <%
            String qn8=str[9];  
            if(qn8.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn8" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn8" value="Y"  disabled/>
            
          <%  }
            %>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<%
            String qn9=str[10];  
            if(qn9.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn9" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn9" value="Y"  disabled/>
            
          <%  }
            %>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<%
            String qn10=str[11];  
            if(qn10.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn10" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn10" value="Y"  disabled/>
            
          <%  }
            %>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<%
            String qn11=str[12];  
            if(qn11.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn11" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn11" value="Y"  disabled/>
            
          <%  }
            %>
              &nbsp;&nbsp;</div></td>      
              <td><div align="center">&nbsp;
              <%
            String qn12=str[13];  
            if(qn12.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn12" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn12" value="Y"  disabled/>
            
          <%  }
            %>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;
               <%
            String qn13=str[14];  
            if(qn13.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn13" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn13" value="Y"  disabled/>
            
          <%  }
            %>
              
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;
               <%
            String qn14=str[15];  
            if(qn14.equals("Y"))
            {
           %>
            <input type="checkbox" name="qn14" value="Y" checked disabled/>
           <%
            }
            else
            {%>
            <input type="checkbox" name="qn14" value="Y"  disabled/>
            
          <%  }
            %>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;
              <%=str[16]%>
              &nbsp;&nbsp;</div></td>
   
              <td><div align="center">&nbsp; 
       <%=str[17]%></div></td>
              
               
              
	</tr>
        
        
        <%  
          
          
        }
          
     %>
     
     
    
	

</table></td>
  </tr>
  </logic:notEqual>
  
  
  <tr align="center" valign="baseline">
    <td colspan="4"> 
            <div align="center">  
            
            <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/OK.gif" width="49" height="37" border="0"></a></div></td>
  </tr>
</table></td>
<td background="images/TableVerticalRightBG.gif">&nbsp;</td>
</tr>
<tr>
<td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
<td background="images/TableBackground2.gif"><div align="center"></div></td>
<td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
</tr>
</table>
</html:form>
</body>
</html>
