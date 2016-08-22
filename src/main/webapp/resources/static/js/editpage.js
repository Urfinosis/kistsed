function submitFrom(){
  				var content = [];
  				$(".content").each(function(index) {
  					if($(this).children("textarea").val()){
  						contentT = $(this).children("textarea").val();
  						numContainer = $(this).parent($('.droppable')).attr("id");
  						if (!numContainer){
  							numContainer=$(this).parent($('div')).parent($('div')).parent($('div')).parent($('.droppable')).attr("id");
  						}
  						$.ajax({
  							url : '/savecontent',
  							type : 'POST',
  							data : ({
  								"content" : contentT,
  								"type": 1,
  								"numContainer" : numContainer,
  								"position": index
  							}),
  							success : function() {
  								
  							}
  						});
  					}
  					if($(this).children("iframe").attr('src')){
  						var $temp1 = $(this).parent($("div#iframe"));
  						content.push($(this).children("iframe").attr('src'));
  						content.push("2");
  						var numContainer = $temp1.parent($('.droppable')).attr("id");
  						if (!numContainer){
  							numContainer = $temp1.parent($('div')).parent($('div')).parent($('div')).parent($('.droppable')).attr("id");	
  						}
  						content.push(numContainer);
  						content.push(index);
  					}
  					if($(this).children(".navbar-right").attr('style')){
  						var contentT = $(this).children(".navbar-right").children("b").text();
  						var numContainerLike = $(this).parent($('div')).parent($('div')).parent($('div')).parent($('.droppable')).attr("id");
  						if (!numContainerLike){
  							numContainerLike = $(this).parent($('.droppable')).attr("id");
  						}
  						if (!contentT){
  							contentT=0;
  						}
  						alert(contentT);
  						content.push(contentT);
  						content.push("3");
  						content.push(numContainerLike);
  						content.push(index);
  					}
  					if($(this).children(".comment").attr('style')){
  						var tempor3 = $(this).parent($('div')).parent($('div')).parent($('div'));
						numContainerComment = tempor3.parent($('.droppable')).attr("id");
  						if(!numContainerComment){
  							var tempor2 = $(this).parent($(".content")).parent($(".modal-content")).parent($(".modal-dialog"));
  							numContainerComment = tempor2.parent($('.droppable')).attr("id");
  						}
  						if(!numContainerComment){
  							var tempor = $(this).parent($('div')).parent($('div')).parent($('div')).parent($('div')).parent($('div'));
  	  						var numContainerComment = tempor.parent($('.droppable')).attr("id");
  						}
  						content.push("0");
  						content.push("4");
  						content.push(numContainerComment);
  						content.push(index);
  					}
  					if($(this).children(".yes").attr("style")){
  					}
  					if($(this).children(".images").attr('src')){
  						var temp1 = $(this).parent($('div')).parent($('div')).parent($('div')).parent($('.droppable')).attr("id");
  						var tempcontent = $(this).children(".images").attr('src');
  						alert(tempcontent);
  						tempcontent = tempcontent.replace(",","#");
  						tempcontent = tempcontent.replace(",","#");
						content.push(tempcontent);
						content.push("6");
						content.push(temp1);
						content.push(index);
  					}
  				});
  				$("[name=content]").val(content);
  			}		