<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	    <meta name="viewport" content="width=device-width,initial-scale=1">
        <title></title>
        <meta name="description" content="">
       
        
		 
	
<script src="DataTables/jquery-3.3.1.min.js" ></script>
<script src="DataTables/DataTables-1.10.16/js/jquery.dataTables.min.js" ></script>
<script src="DataTables/DataTables-1.10.16/js/dataTables.fixedColumns.min.js" ></script> 
<link rel="stylesheet" type="text/css" href="DataTables/datatables.min.css"/>





<script type="text/javascript" class="init">
$(document).ready(function() {
  var table = $('#table_id').DataTable( {
        scrollY:        "300px",
        scrollX:        true,
        scrollCollapse: true,
        paging:         true
     
/*        fixedColumns:   {
            leftColumns: 3,
            rightColumns: 0
        }/**/		
    } );
});

     </script>
  <style type="text/css">
  th, td { white-space: nowrap; }
    div.dataTables_wrapper {
        width: 100%;
        margin: 0 auto;
    }
  </style>
 
</head>

<body>

<br><br>
<%int columnCount=0;%>

<table id="table_id" class="stripe row-border order-column"  style="width:100%"  border=4 >
		
 		<thead>
             <tr bgcolor="teal" >
                <logic:iterate id="object" name="reportForm" property="colletralCoulmnName" indexId="index">			
					<%	
						String str=(String)object;
						columnCount++;
					%>
		      <th ><%=str%></th>
		</logic:iterate>
            </tr>
        </thead>
       
        <tbody>
          <logic:iterate id="object1" name="reportForm" property="colletralCoulmnValue"  indexId="index1">
             <tr >
				<%	
					ArrayList value =  (ArrayList)object1;
					for(int i=0;i<value.size();i++){
				    %>
		    <td onMouseOver="this.style.backgroundColor='yellow'"; onMouseOut="this.style.backgroundColor=''" ><%=value.get(i) %></td>
				<% } %>
		    </tr>
		
		</logic:iterate>	
        </tbody>
          <tfoot>
          <tr bgcolor="teal">
                <th> </th>
                <th> </th>
                <th> </th>
                <th> </th>
                <th> </th>
                <th> </th>
				<th> </th>
				<th> </th>
				<th> </th>
				<th> </th>
				<th> </th>				
				<th> </th>				
				<th> </th>				
				<th> </th>
				<th> </th>				
				<th> </th>
				<th> </th>
				<th> </th>				
				<th> </th>
				<th> </th>
				<th> </th>
				<th> </th>
				<th> </th>
				<th> </th>
				<th> </th>				
				<th> </th>
            </tr>
        </tfoot>
    </table>

</body>
	
</html>