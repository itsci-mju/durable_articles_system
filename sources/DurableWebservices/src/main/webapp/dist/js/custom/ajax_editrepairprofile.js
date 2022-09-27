$(document).ready(function(){
	$("button[id*='btn_edit']").click(function() {
		$.ajax({
			type : "GET",
			url : "/Project2015_30/EditRepairServlet",
			data : {
				'repair_id' : $(this).val(),
			},
			success : function(data, textStatus) {
				window.location = "editrepairprofile.jsp";
			}
		});
	});	
});
