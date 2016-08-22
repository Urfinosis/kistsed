$(document).ready(function() {
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
				}
			});
			loadTags()
		});
function deletePage(idPage){
	$.ajax({
			url : '/deletepage',
			type: 'POST',
			data : ({
			   "idPage": idPage
			}),
			success: function (pageId) {
				$("li[id='"+pageId+"']").remove();
			}
	});
}
function deleteSite(idSite){
		$.ajax({
				url : '/deletesite',
				type: 'POST',
				data : ({
				   "idSite": idSite
				}),
				success: function (id) {
					$('li').each(function(){
						if($(this).attr("id") == id){
					   		$(this).remove();
						}
					});
				}
		});
	}
   function confirmAchievement(){
	   $("#acivementMessage").remove();
   }
   function addLike(idPage) {
		var pageId = $("#divPageId").text();
		$.post("/addLike", {
			"idPage" : idPage
		}, function successLike(data) {
			$("#likeId").text(data);
		});
	}
	function send(comentColor) {
		var author = $("#name").text();
		var message = $("#message").val();
		var idPage = $("#idPage").text();
		$.ajax({
			url : '/sendcomment',
			type : 'POST',
			data : ({
				"idPage" : idPage,
				"username" : author,
				"message" : message
			}),
			success : function(data) {
				var result = data;
				$("#blockComment").prepend(
						'<p class="well well-sm">' + result + "</p>");
				$("#blockComment").children($("p")).attr("style","background:"+comentColor);
				$("#message").val("");
			}
		});
	}
	function loadTags() {
		idSite = $("#idSite").text();
		$.ajax({
			url : '/gettags',
			type : 'POST',
			data : ({
				"idSite" : idSite
			}),
			success : function(data) {
				$(".tags").autocomplete({
					source : data
				});
			}
		});
	}