$(document).ready(function(){
	$("button[id*='btn_loan']").click(function() {
		$.ajax({
			type : "GET",
			url : "/project2015_30/LoanDurableServlet",
			data : {
				'borrowing_id' : $(this).val(),
			},
			success : function(data, textStatus) {
				window.location = "loandurable.jsp";
			},
			error : function(xhr) {

			},
			complete : function(xhr, textStatus) {

			}
		});
	});	
});
