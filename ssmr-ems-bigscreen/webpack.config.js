var path = require('path');

var config = {
	entry : {
		main : path.join(__dirname, 'src/main/webapp/react/page/bigscreen_backstage/main.jsx'),
		secondScreen : path.join(__dirname, 'src/main/webapp/react/page/bigscreen_show/secondScreen.jsx'),
		thirdScreen : path.join(__dirname,'src/main/webapp/react/page/bigscreen_show/thirdScreen.jsx'),
		vendors : [ 'react', 'reflux', 'react-mixin' ]
	},
	
	output : {
		path : path.join(__dirname, 'src/main/webapp/react/build'),
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