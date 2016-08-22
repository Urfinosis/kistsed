function deleteUser(username){
	$.ajax({
			url : '/deleteuser',
			type: 'POST',
			data : ({
			   "username": username
			}),
			success: function (user) {
				$('span').each(function(){
					if($(this).html() == user){
				   		$(this).parent().remove();
					}
				});
			}
	});
}
function editUser(username,id,firstName,lastName,email,password){
	$.ajax({
			url : '/edituser',
			type: 'POST',
			data : ({
			   "username": username,
			   "email": email,
			   "id": id,
			   "firstName": firstName,
			   "lastName": lastName,
			   "password": password
			}),
			success: function (userId) {
				$("#"+id).children(".checkboxF").children("input").attr('checked', false);
				$("#"+id).children(".passwordF").children("input").val("");
			}
	});
}
function editSelectedUsers(){
	$("input:checkbox:checked").each(function(){
		var id = $(this).parent("td").parent("tr").children(".idF").text();
		var lastName = $("#"+id).children(".lastNameF").children("input").val();
		var username = $("#"+id).children(".usernameF").text();
		var pass = $("#"+id).children(".passwordF").children("input").val();
		var firstName = $("#"+id).children(".firstNameF").children("input").val();
		var email = $("#"+id).children(".emailF").children("input").val();
		editUser(username,id,firstName,lastName,email,pass);
	});
}

function deleteSelectedUsers(){
	$("input:checkbox:checked").each(function(){
		var id = $(this).parent("td").parent("tr").children(".idF").text();
		var username = $("#"+id).children(".usernameF").text();
		deleteUser(username);
		$(this).parent($("td")).parent($("tr")).remove();
	});
}