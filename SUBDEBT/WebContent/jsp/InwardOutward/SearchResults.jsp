<%@ page language="java"%>
<%@ page import="java.util.Hashtable"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","searchResult.do?method=searchResult");%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="searchResult.do?method=searchResult" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InwardOutwardHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="searchResultHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="documentReference" />
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="subject" />
									</TD>
									</TR>	
								
									<logic:iterate id="element"  property="documentDetails"  name="ioForm">
						
								


                                    <TR>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
									<%
									/*java.util.Hashtable elements=(java.util.Hashtable)element;
									java.util.Set keySet=elements.keySet();
									java.util.Iterator keyIterator=keySet.iterator();
									
									String key="";
									while(keyIterator.hasNext())
									{
										key=(String)keyIterator.next();
										out.println(key);
									}*/
									java.util.Map.Entry map=(java.util.Map.Entry)element;
									String key=(String)map.getKey() ;
									//out.println(key);
									String url="documentDetailsSearch.do?method=documentDetailsSearch&id="+key;
									%>
									<html:link href="<%= url%>" ><bean:write name="element"  property="key" />	
									</html:link>
									</TD>
									<TD width="25%" align="left" valign="top" class="ColumnBackground1">
									<bean:write name="element"  property="value" />	
									</TD>
									</TR>	
									</logic:iterate>


							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
									<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
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
	</html:form>
</TABLE>

