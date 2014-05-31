/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and other Contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM - initial implementation
 *******************************************************************************/

var express = require('express');
var fs = require('fs');
var app = express();
   

app.use(express.bodyParser());
app.use(express.static(__dirname + '/public'));

var router = express.Router();


app.get('/country/:country.html', function(req, res, next) {

	var country = req.params.country;
	
	res.type('.html');
	
	fs.readdir("public/country/"+country, function(err, files) {
		var content = "<h3>Customer Records in this region</h3></br></hr>";
		if (!err && files.length > 0) {
			files.forEach(function(file) {
				var customer_id = file.split('.')[0];
				content += '<a href="'+country+'/'+file+'">'+customer_id+'</a><br/>';
			});
			
		} else {
			content += "No customer records in this region";
		}
		
		res.send(content);
	});

});

app.post('/country/:country', function(req, res, next) {

	var country = req.params.country;
	var country_path =  "public/country/"+country;

	var customer_id = req.body.id;
	var customer_name = req.body.name;
	var customer_email = req.body.email;
	var customer_city = req.body.city;
	
	console.log(country);
	console.log(req.body);
	
  fs.exists(country_path, function(exists) {
		if (!exists) {
			fs.mkdirSync(country_path);
		}
		fs.writeFile(country_path+"/"+customer_id+".txt", 'Name: '+customer_name+"\nEmail: "+customer_email+"\n"+"City: "+customer_city, function(err) {
			if(err) {
				console.log(err);
			} else {
				console.log("Record Saved");
			}
		}); 
	});
	
	res.send(200);
});


var server = app.listen((process.argv[2] == undefined) ? 3000 : process.argv[2], function() {
    console.log('Listening on port %d', server.address().port);
});