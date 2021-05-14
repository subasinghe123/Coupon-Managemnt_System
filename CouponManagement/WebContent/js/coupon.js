$(document).ready(function()
{ 
	if ($("#alertSuccess").text().trim() == "") 
 	{ 
 		$("#alertSuccess").hide(); 
 	} 
 	$("#alertError").hide(); 
}); 

// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{ 
	// Clear alerts---------------------
 	$("#alertSuccess").text(""); 
 	$("#alertSuccess").hide(); 
 	$("#alertError").text(""); 
 	$("#alertError").hide(); 
 	
	// Form validation-------------------
	var status = validateItemForm(); 
	if (status != true) 
 	{ 
 		$("#alertError").text(status); 
 		$("#alertError").show(); 
 		
 		return; 
 	} 
 	
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT"; 
 	$.ajax( 
 	{ 
 		url : "CouponApi", 
 		type : type, 
 		data : $("#formItem").serialize(), 
 		dataType : "text", 
 		complete : function(response, status) 
 		{ 
 			onItemSaveComplete(response.responseText, status); 
 		} 
 	}); 
});

function onItemSaveComplete(response, status)
{ 
	if (status == "success") 
 	{ 
 		var resultSet = JSON.parse(response);
 		console.log(resultSet);
 		if (resultSet.status.trim() == "success") 
 		{ 
 			$("#alertSuccess").text("Successfully saved."); 
 			$("#alertSuccess").show(); 
 			$("#divItemsGrid").html(resultSet.data); 
 		} 
 		else if (resultSet.status.trim() == "error") 
 		{ 
 			$("#alertError").text(resultSet.data); 
 			$("#alertError").show(); 
 		} 
 	} 
 	else if (status == "error") 
 	{ 
 		$("#alertError").text("Error while saving."); 
 		$("#alertError").show(); 
 	} 
 	else
 	{ 
 		$("#alertError").text("Unknown error while saving.."); 
 		$("#alertError").show(); 
 	} 
 	
 	$("#hidItemIDSave").val(""); 
 	$("#formItem")[0].reset();
}

// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{    
	$("#hidItemIDSave").val($(this).data("couponid"));    
	$("#name").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#expireDate").val($(this).closest("tr").find('td:eq(3)').text());
	$("#saving").val($(this).closest("tr").find('td:eq(4)').text());
	$("#description").val($(this).closest("tr").find('td:eq(5)').text());
	 
}); 

// DELETE =============================================================
$(document).on("click", ".btnRemove", function(event)
{ 
 	$.ajax( 
 	{ 
 		url : "CouponApi", 
 		type : "DELETE", 
 		data : "couponid=" + $(this).data("couponid"),
 		dataType : "text", 
 		complete : function(response, status) 
 		{ 
 			onItemDeleteComplete(response.responseText, status); 
 		} 
 	}); 
});

function onItemDeleteComplete(response, status)
{ 
	console.log(JSON.parse(response));
	if (status == "success") 
 	{ 
 		var resultSet = JSON.parse(response); 
 		
 		if (resultSet.status.trim() == "success") 
 		{ 
 			$("#alertSuccess").text("Successfully deleted."); 
 			$("#alertSuccess").show(); 
 			$("#divItemsGrid").html(resultSet.data); 
 		} 
 		else if (resultSet.status.trim() == "error") 
 		{ 
 			$("#alertError").text(resultSet.data); 
 			$("#alertError").show(); 
 		} 
 	} 
 	else if (status == "error") 
 	{ 
 		$("#alertError").text("Error while deleting."); 
 		$("#alertError").show(); 
 	} 
 	else
 	{ 
 		$("#alertError").text("Unknown error while deleting.."); 
 		$("#alertError").show(); 
 	} 
}

// CLIENT-MODEL========================================================================= 
function validateItemForm() 
{  
	// coupon name  
	if ($("#name").val().trim() == "")  
	{   
		return "Insert Coupon Name";  
	} 
 
	// expireDate price  
	if ($("#expireDate").val().trim() == "")  
	{   
		return "Insert Coupon Expire Date.";  
	} 
	// saving  
	if ($("#saving").val().trim() == "")  
	{   
		return "Insert Coupon Saving.";  
	}   
	
	// description
	if ($("#description").val().trim() == "")  
	{   
		return "Insert Coupon Description.";  
	}

	return true; 
}