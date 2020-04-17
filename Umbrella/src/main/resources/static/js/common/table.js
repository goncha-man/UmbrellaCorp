'use strict'

window.addEventListener('load', () => {
    $('#userList').DataTable();
});

function confirmDelete(id){
	$('#deleteModal').modal('show');
	$("#userIdHiddenInput").val(id);
}

function deleteUser(){
	console.log("hola")
	var id = $("#userIdHiddenInput").val();
    window.location = "deleteUser/"+id;
}