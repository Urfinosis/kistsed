function submitFrom(){
  				var content = [];
  				$(".content").each(function(index) {
  					if($(this).children("textarea").val()){
  						contentT = $(this).children("textarea").val();
  						numContainer = $(this).parent($('.droppable')).attr("id");
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
  						var $temp1 = $(this).parent($(".content")).parent($("div#iframe"));
  						content.push($(this).children("iframe").attr('src'));
  						content.push("2");
  						content.push($temp1.parent($('.droppable')).attr("id"));
  						content.push(index);
  					}
  					if($(this).children(".navbar-right").attr('style')){
  						content.push("0");
  						content.push("3");
  						content.push($(this).parent($('.droppable')).attr("id"));
  						content.push(index);
  					}
  					if($(this).children(".comment").attr('style')){
  						var tempor = $(this).parent($(".content")).parent($(".modal-content")).parent($(".modal-dialog"));
  						content.push("0");
  						content.push("4");
  						content.push(tempor.parent($('.droppable')).attr("id"));
  						content.push(index);
  					}
  					if($(this).find(".el")){
  						number=index;
  						var $temp = $(this).parent($(".content")).parent($(".modal-content")).parent($(".modal-dialog"));
  						$textarea=$(this).find(".el");
  						$.each($textarea,function(){
  							content.push($(this).val());
  							content.push("5");
  							content.push($temp.parent($('.droppable')).attr("id"));
  							content.push(number);
  						});
  					}
  					if($(this).children(".images").attr('src')){
  						var temp1 = $(this).parent($(".content")).parent($('.droppable')).attr("id");
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