function addBook() {
	$('#clientmandateDialog').dialog("option", "title", 'Add Book');
	$('#clientmandateDialog').dialog('open');
}

function editBook(id) {

	$.get("get/" + id, function(result) {

		$("#clientmandateDialog").html(result);

		$('#clientmandateDialog').dialog("option", "title", 'Edit Book');

		$("#clientmandateDialog").dialog('open');

		initializeDatePicker();
	});
}

function initializeDatePicker() {
	$(".datepicker").datepicker({
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true
	});
}

function resetDialog(form) {

	form.find("input").val("");
}

$(document).ready(function() {

	$('#clientmandateDialog').dialog({

		autoOpen : false,
		position : 'center',
		modal : true,
		resizable : false,
		width : 440,
		buttons : {
			"Save" : function() {
				$('#clientmandateForm').submit();
			},
			"Cancel" : function() {
				$(this).dialog('close');
			}
		},
		close : function() {

			resetDialog($('#clientmandateForm'));

			$(this).dialog('close');
		}
	});

	initializeDatePicker();
});
