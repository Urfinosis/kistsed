window.onload = function() {
			var idPage = $("#idPage").text();
			var idSite = $("#idSite").text();
			$.ajax({
				url : '/getTable',
				type : 'POST',
				data : ({
					"idPage" : idPage,
					"idSite" : idSite
				}),
				success : function(data) {
					var result = data;
					$.plot($("#placeholder"), [ result ]);
					$(".graphic").addClass("yes");
				}
			});
}