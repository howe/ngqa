function() { 
	db.question.find().snapshot().forEach(
		function(obj) {
			if (obj['tags'].length > 0) {
				for (var i = 0; i <obj['tags'].length; i++) {
					var tag = obj['tags'][i] ;
					if (tag.indexOf('<') > -1 || tag.indexOf('>') > -1 || tag.indexOf('/') > -1 || tag.indexOf('\\') > -1) {
						db.question.update({_id:new ObjectId(obj._id.str)}, {$pull : {tags : tag}});
					}
				};
			}
        }
	);
}