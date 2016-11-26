<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:url var="actionUrl" value="save" />

<form:form id="clientmandateForm" commandName="clientmandate" method="post"
	action="${actionUrl }" class="pure-form pure-form-aligned">

	<fieldset>
		<legend></legend>

		<div class="pure-control-group">
			<label for="name">ClientId</label>
			<form:input name = "clientid" path="clientid" placeholder="ClientId" />
		</div>
		<div class="pure-control-group">
			<label for="code">SubClientId</label>
			<form:input name="merocode" id = "clientsubid" path="clientsubid" placeholder="ClientSubid" maxlength="15" />
		</div>
		<div class="pure-control-group">
			<label for="isbn">no_of_vehicles</label>
			<form:input path="noOfVehicles" placeholder="no_of_vehicles" />
		</div>
		<div class="pure-control-group">
			<label for="publisher">mandate_type</label>
			<form:input path="mandateType" placeholder="mandateType" />
		</div>
		<div class="pure-control-group">
			<label for="publishedOn">mandate_start_date</label>
			<form:input path="mandateStartDate"
				placeholder="YYYY-MM-DD" class="datepicker" />
		</div>
		<div class="pure-control-group">
			<label for="publishedOn">mandate_end_date</label>
			<form:input path="mandateEndDate"
				placeholder="YYYY-MM-DD" class="datepicker" />
		</div>
		<div class="pure-control-group">
			<label for="publishedOn">createddate</label>
			<form:input path="createddate"
				placeholder="YYYY-MM-DD" class="datepicker" />
		</div>
		<div class="pure-control-group">
			<label for="isbn">createdby</label>
			<form:input path="createdby" placeholder="createdby" />
		</div>
		<form:input path="id" type="hidden" />

	</fieldset>
</form:form>