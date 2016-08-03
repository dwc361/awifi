setInterval(function() {
	$('.skillbar').each(function() {
		var arr = document.getElementsByClassName("testbar");
		var str = Math.round(Math.random() * 50 + 49) + '%';
		for(i= 0;i<arr.length;i++) {
			arr[i].attributes["data-percent"].value = str;
			document.getElementById("skill-bar-percenta").innerHTML = str;
			jQuery(this).find('.skillbar-bar').animate({
				width: jQuery(this).attr('data-percent')
			}, 1000);
		}

	});
}, 1000);