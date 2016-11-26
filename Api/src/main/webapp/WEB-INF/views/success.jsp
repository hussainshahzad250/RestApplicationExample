<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
	<title>BankDetails Page</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
<h1>
	Verify Account Details
</h1>

<c:url var="addAction" value="/bankDetails/verify" ></c:url>
<center>${msg}</center>
<form:form action="${addAction}" commandName="bankDetailsModel">
<table>
	<c:if test="${!empty bankDetailsModel.accountHolderName}">
	<tr>
		<td>
			<form:label path="id">
				<spring:message text="ID"/>
			</form:label>
		</td>
		<td>
			<form:input path="id" readonly="true" size="8"  disabled="true" />
			<form:hidden path="id" />
		</td> 
	</tr>
	</c:if>
	
	
	<tr>
		<td>
			<form:label path="varificationAmount">
				<spring:message text="varification Amount "/>
			</form:label>
		</td>
		<td>
			<form:input path="varificationAmount" />
		</td>
	</tr>
	
	
	<tr>
		<td colspan="2">
			<c:if test="${!empty bankDetailsModel.accountHolderName}">
				<input type="submit"
					value="<spring:message text="Edit BankDetails"/>" />
			</c:if>
			<c:if test="${empty bankDetailsModel.accountHolderName}">
				<input type="submit"
					value="<spring:message text="Verify Account"/>" />
			</c:if>
		</td>
	</tr>
</table>	
</form:form>


</body>
</html>
