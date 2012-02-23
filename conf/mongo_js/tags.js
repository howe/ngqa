function() { 
	var result = {};
	db.question.find().forEach(
		function(obj) {
			if (obj['tags'].length > 0) {
				print(obj['tags']);
				print(typeof(obj['tags']));
				for (var i = 0; i <obj['tags'].length; i++) {
					var tag = obj['tags'][i] ;
					if (result[tag]) {
						result[tag] += 1;
					} else {
						result[tag] = 1;
					}
				};
			}
        }
	);
	
	return result;
}