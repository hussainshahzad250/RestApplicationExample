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
	Add a Bank Details
</h1>

<c:url var="addAction" value="/bankDetails/save" ></c:url>
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
			<form:label path="accountHolderName">
				<spring:message text="account Holder Name"/>
			</form:label>
		</td>
		<td>
			<form:input path="accountHolderName" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="bankName">
				<spring:message text="bank Name"/>
			</form:label>
		</td>
		<td>
			<form:input path="bankName" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="ifscCode">
				<spring:message text="ifsc Code"/>
			</form:label>
		</td>
		<td>
			<form:input path="ifscCode" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="accountNumber">
				<spring:message text="account Number"/>
			</form:label>
		</td>
		<td>
			<form:input path="accountNumber" />
		</td>
	</tr>
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
					value="<spring:message text="Add BankDetails"/>" />
			</c:if>
		</td>
	</tr>
</table>	
</form:form>
<br>
<h3>BankDetails List</h3>
<c:if test="${!empty allNameList}">
	<table class="tg">
	<tr>
		<th width="80">BankDetails ID</th>
		<th width="120">account Holder Name</th>
		<th width="120">Bank Name</th>
		<th width="120">IFSC COde</th>
		<th width="120">Account Number</th>
		<th width="120">Verification Amount</th>
		<th width="120">accountStatus</th>
		
		<!-- <th width="60">Edit</th>
		<th width="60">Delete</th> -->
	</tr>
	<c:forEach items="${allNameList}" var="bankDetailsModel">
		<tr>
			<td>${bankDetailsModel.id}</td>
			<td>${bankDetailsModel.accountHolderName}</td>
			<td>${bankDetailsModel.bankName}</td>
			<td>${bankDetailsModel.ifscCode}</td>
			<td>${bankDetailsModel.accountNumber}</td>
			<td>${bankDetailsModel.varificationAmount}</td>
			<td>${bankDetailsModel.accountStatus}</td>			
			<%-- <td><a href="<c:url value='/edit/${details.id}' />" >Edit</a></td>
			<td><a href="<c:url value='/remove/${details.id}' />" >Delete</a></td> --%>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
