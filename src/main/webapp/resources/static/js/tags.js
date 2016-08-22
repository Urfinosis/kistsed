$(document).ready(function() {
			$(".tagContainer").each(function(){
				var idSite = $(this).attr("id");
				$.ajax({
					url : '/getsitetags',
					type: 'POST',
					data : ({
					   "idSite": idSite
					}),
					success: function (tags) {
						$('.tagContainer[id="'+idSite+'"]').append('<p class="text-ellipsis" style="background-color: silver;"> <span class="glyphicon glyphicon-tag"  data-toggle="tooltip" data-placement="right" title="Site tags"></span> '+tags+'</p>');
					}	
					});
					});
			});