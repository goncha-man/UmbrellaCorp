'use strict'

window.addEventListener('load', () => {
    $('#userList').DataTable();
    $('#checkEnable').checked = true;
    
    $('#v-pills-tab a[href="#v-pills-home"]').on('click', function (e) {
    	console.log("tocaste")
    	console.log(this)
    	  e.preventDefault()
    	  $(this).tab('show')
    	})
});