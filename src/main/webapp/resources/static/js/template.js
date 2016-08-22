function effect(url){
	$.ajax({
		url : '/sepiaimage',
		type : 'POST',
		data : ({
			"url" : url
		}),
		success : function(newUrl) {
			$("img[src='"+url+"']").attr("src",newUrl);
			$('span[onclick="scaleOp(\''+url+'\',\'10\')"]').attr("onclick","scaleOp(\'"+newUrl+"\',\'10\')");
			$('span[onclick="scaleOp(\''+url+'\',\'-10\')"]').attr("onclick","scaleOp(\'"+newUrl+"\',\'-10\')");
			$('span[onclick="effect(\''+url+'\')"]').attr("onclick","effect(\'"+newUrl+"\')");
		}
	});
}
function scaleOp(url,scale){
	$.ajax({
		url : '/scaleimage',
		type : 'POST',
		data : ({
			"url" : url,
			"scale" : scale
		}),
		success : function(newUrl) {
			$("img[src='"+url+"']").attr("src",newUrl);
			alert($('span[onclick="scaleOp(\''+url+'\',\'10\')"]').attr("onclick"));
			$('span[onclick="scaleOp(\''+url+'\',\'10\')"]').attr("onclick","scaleOp(\'"+newUrl+"\',\'10\')");
			$('span[onclick="scaleOp(\''+url+'\',\'-10\')"]').attr("onclick","scaleOp(\'"+newUrl+"\',\'-10\')");
			$('span[onclick="effect(\''+url+'\')"]').attr("onclick","effect(\'"+newUrl+"\')");
		}
	});
}
refresh()
$(".draggable-item").draggable({
     revert: 'invalid',
	 connectToSortable: ".droppable"
});
function selectFirstTemplate(){
  	$("#template").val("1");
  	$("#dropableContainer>div").detach();
  	$("#dropableContainer").append('<div id="1" class="jumbotron droppable" style="border: solid 1px black; height: auto;"></div>');
  	refresh()
 }
  			
 function selectSecondTemplate(){
  	$("#template").val("2");
  	$("#dropableContainer>div").detach();
  	$("#dropableContainer").append('<div class="row">' +
  	          '<div class="col-md-6">'+
  	          '<div id="1" class="jumbotron droppable" style="border: solid 1px black; height: auto;">'+
  	          '</div>'+
  	          '</div>'+
  	          '<div class="col-md-6">'+
  	          '<div id="2" class="jumbotron droppable" style="border: solid 1px black; height: auto;">'+
  	          '</div>'+
  	          '</div>'+
  	          '</div>');
  	refresh()
  }
  			
 function selectThirdTemplate(){
  	$("#template").val("3");
  	$("#dropableContainer").empty();
  	$("#dropableContainer").append('<div class="row">' +
  	          '<div class="col-md-6">'+
  	          '<div id="1" class="jumbotron droppable" style="border: solid 1px black; height: auto;">'+
  	          '</div>'+
  	          '</div>'+
  	          '<div class="col-md-6">'+
  	          '<div id="2" class="jumbotron droppable" style="border: solid 1px black; height: auto;">'+
  	          '</div>'+
  	          '</div>'+
  	          '</div>'+
  	          '<div id="3" class="jumbotron droppable" style="border: solid 1px black; height: auto;">'+
  	          '</div>');
  	refresh()
}
 function refresh(){
	 $(".draggable-video").draggable({
		    helper: "clone", connectToSortable: ".droppable",
		    start: function (event, ui) {
		        var clone = $(ui.helper);
		    },
		    stop: function (event, ui) {
		        if ($('.droppable').children().is($(ui.helper))){
		            var statesdemo = {
		                state0: {
		                    title: 'Video block',
		                    html: '<label>Youtube link <input class="form-control" type="text" name="link" value=""></label><br />',
		                    buttons: {OK: 1, Cancel: false},

		                    submit: function (e, v, m, f) {
		                        var url = (f.link);
		                        if (url) {
		                            var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
		                            var match = url.match(regExp);
		                            if (match && match[2].length == 11) {
		                                url = match[2];
		                                $(ui.helper).empty();
		                                $(ui.helper).removeClass('btn-default');
		                                $(ui.helper).removeClass('btn');
		                                $(ui.helper).append('<div id="iframe" class="container-fluid">'+
		                                		'<div class="content col-md-8 padding-zero embed-responsive embed-responsive-16by9">'+
		                                        '<iframe class="embed-responsive-item"' +
		                                    ' src="https://www.youtube.com/embed/' + url + '" frameborder="0" allowfullscreen></iframe>'+
		                                    '</div></div>');
		                                $(ui.helper).removeClass('btn-default');
		                                $(ui.helper).removeClass('btn');
		                                $(ui.helper).css('height', '100%');
		                                $(ui.helper).css('width', '100%');
		                            } else {
		                                $(ui.helper).remove();
		                            }
		                        } else {
		                            $(ui.helper).remove();
		                        }
		                    },
		                    close: function (e, v, m, f) {
		                        $(ui.helper).remove()
		                    }
		                }
		            };
		            $.prompt(statesdemo);
		        }
		    }
		});

		$(".draggable-comment").draggable({
			helper: "clone", connectToSortable: ".droppable",
		    start: function (event, ui) {
		        var clone = $(ui.helper);
		    },
		    stop: function (event, ui) {
		    	var temp = $(".existing-draggable-comment");
		    	if(temp.val()!=undefined){
		    		$(ui.helper).remove();
		    		return;
		    	}
		        $(ui.helper).empty();
		        $(ui.helper).removeClass('btn-default');
		        $(ui.helper).removeClass('btn');
		        $(ui.helper).addClass('existing-draggable-comment');
		        $(ui.helper).css('height', 'auto');
		        $(ui.helper).css('width', '100%');
		        $(ui.helper).append('<div class="modal-dialog" style="margin-left: 2%;resize:vertical; width:95%"><div class="modal-content"><div class="modal-header"></div><div class="modal-body content">'+
		        		'<textarea style="resize:vertical; width:95%" class="form-control markdown-field comment"></textarea>'+
		        		'<div class="modal-footer">'+
		        		'<div class="col-md-13"><a class="btn btn-primary" th:href="@{/}">Send</a></div></div></div>');
		    }
		});

		$(".draggable-tabble").draggable({
			helper: "clone", connectToSortable: ".droppable",
		    start: function (event, ui) {
		        var clone = $(ui.helper);
		    },
		    stop: function (event, ui) {
		    	var temp = $(".existing-draggable-table");
		    	if(temp.val()!=undefined){
		    		$(ui.helper).remove();
		    		return;
		    	}
		        $(ui.helper).empty();
		        $(ui.helper).removeClass('btn-default');
		        $(ui.helper).removeClass('btn');
		        $(ui.helper).addClass('existing-draggable-table');
		        $(ui.helper).css('height', 'auto');
		        $(ui.helper).css('width', '100%');
		        $(ui.helper).append('<div class="modal-dialog" style="margin-left: 2%;resize:vertical; width:60%"><div class="modal-content"><table id="table" class="content table table-bordered"><thead><tr><td>x</td><td>y</td></tr></thead><tbody><tr><td><textarea style="margin-left: 2%;height:98%;width:98%" rows="1" class="el" style="resize:none;"></textarea></td><td><textarea style="margin-left: 2%;height:98%;width:98%" class="el" rows="1" style="resize:none;"></textarea></td></tr></tbody></table><div class="modal-footer"><a id="addRow" class="btn btn-primary" onclick="cl()">Add row</a></div></div></div>');
		    }
		});

		$(".draggable-text").draggable({
		    helper: "clone", connectToSortable: ".droppable",
		    start: function (event, ui) {
		        var clone = $(ui.helper);
		    },
		    stop: function (event, ui) {
		        $(ui.helper).empty();
		        $(ui.helper).removeClass('btn-default');
		        $(ui.helper).removeClass('btn');
		        $(ui.helper).addClass('content');
		        $(ui.helper).css('height', 'auto');
		        $(ui.helper).css('width', '100%');
		        $(ui.helper).append('<textarea style="margin-left: 2%;resize:vertical; width:95%" class="form-control markdown-field"></textarea>');
		    }
		});

		$(".draggable-raiting").draggable({
			    helper: "clone", connectToSortable: ".droppable",
			    start: function (event, ui) {
			        var clone = $(ui.helper);
			    },
			    stop: function (event, ui) {
			    	var temp = $(".existing-draggable-like");
			    	if(temp.val()!=undefined){
			    		$(ui.helper).remove();
			    		return;
			    	}
			        $(ui.helper).empty();
			        $(ui.helper).removeClass('btn-default');
			        $(ui.helper).removeClass('btn');
			        $(ui.helper).addClass('content');
			        $(ui.helper).addClass('existing-draggable-like');
			        $(ui.helper).css('height', '45px');
			        $(ui.helper).css('width', '100%');
			        $(ui.helper).append('    <div class="navbar-right" style="font-size: 17px;margin-right: 20px;"><span class="btn glyphicon glyphicon-thumbs-up" style="font-size: 20px;">'+
			            '</span><text>0</text></div>');
			    }
			});

		$(".draggable-gallery").draggable({
		    helper: "clone", connectToSortable: ".droppable",
		    start: function (event, ui) {
		        var clone = $(ui.helper);
		    },
		    stop: function (event, ui) {
		        $(ui.helper).empty();
		        $(ui.helper).removeClass('btn-default');
		        $(ui.helper).removeClass('btn');
		        $(ui.helper).addClass('container');
		        $(ui.helper).css('height', 'auto');
		        $(ui.helper).css('padding-bottom', '20px');
		        $(ui.helper).css('width', '100%');
		        $(ui.helper).append('<div class="image-left glyphicon glyphicon-picture" style="width:200px; height:200px;font-size:200px;"></div>');
		        window.ondragover = function(e) {e.preventDefault()}
  	  			document.getElementsByClassName("image-left")[0].ondrop = function(e) {
  	  				e.preventDefault(); 
  	  				var file = e.dataTransfer.files[0]; 
  	  				var fd = new FormData;
  	  				fd.append('img', file);
  	  				$.ajax({
						url : '/saveimage',
						type : 'POST',
						data : fd,
						dataType: 'text',
					    processData: false,
					    contentType: false,
						success : function(url) {
							$(".image-left").remove();
							$(ui.helper).append('<div class="content well row" style="margin-left:5%;width:70%;"><img class="images col-md-9" src="'+url+'"></img><div class="col-md-3">'+
									'<span class="glyphicon glyphicon-zoom-in" data-toggle="tooltip" onclick="scaleOp(\''+url+'\',\'10\')" style="font-size:60px;" data-placement="right" title="Zoom in" aria-hidden="true"></span>'+
									'<span class="glyphicon glyphicon-zoom-out" data-toggle="tooltip" onclick="scaleOp(\''+url+'\',\'-10\')" style="font-size:60px;" data-placement="right" title="Zoom out" aria-hidden="true"></span>'+
									'<span class="glyphicon glyphicon-adjust" data-toggle="tooltip" onclick="effect(\''+url+'\')" style="font-size:60px;" data-placement="right" title="Some effect" aria-hidden="true"></span>'+
									'</div></div>');
						}
					});
  	  			}	
		    }
		});

		$(".droppable").sortable({
		    over: function () {
		        removeIntent = false
		    },
		    out: function () {
		        removeIntent = true
		    },
		    beforeStop: function (event, ui) {
		        if (removeIntent == true) {	
		            ui.item.remove()
		        }
		    }
		});
 }
 function cl() {
	    $("#table").append('<tr><td><textarea style="margin-left: 2%;height:98%;width:98%" rows="1" class="el" style="resize:none;"></textarea></td><td><textarea style="margin-left: 2%;height:98%;width:98%" rows="1" class="el" style="resize:none;"></textarea></td></tr>');
	}
 
