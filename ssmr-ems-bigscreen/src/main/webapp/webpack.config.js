var path = require('path');

var config = {
	entry : {
		main : path.join(__dirname, 'react/page/main.jsx'),
		vendors : [ 'react', 'reflux', 'react-mixin' ]
	},
	
	output : {
		path : path.join(__dirname, 'react/build'),
		filename : '[name].js',
		publicPath : '/'
	},

	devServer : {
		inline : true,
		port : 8081
	},

	module : {
		loaders : [ {
			test : /\.jsx?$/,
			exclude : /node_modules/,
			loader : 'babel',

			query : {
				presets : [ 'es2015', 'react' ]
			}
		} ]
	}

}

module.exports = config;