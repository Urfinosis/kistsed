function disable(){
		$("#submitForm").submit(function () {
	        $("#submitBtn").attr("disabled", true);
	        return true;
	    });
	}
function disable2(){
	$("#submitForm").submit(function () {
		submitFrom();
        $("#submitBtn").attr("disabled", true);
        return true;
    });
}