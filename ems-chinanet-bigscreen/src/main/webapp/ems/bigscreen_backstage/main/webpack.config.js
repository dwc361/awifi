 var config = {
   entry: './js/index.jsx',
	
   output: {
      path:__dirname +'/',
      filename: './js/main.js',
      publicPath: '/'
   },
	
   devServer: {
      inline: true,
      port: 8081
   },
	
   module: {
      loaders: [ {
         test: /\.jsx?$/,
         exclude: /node_modules/,
         loader: 'babel',
			
         query: {
            presets: ['es2015', 'react']
         }
      }]
   }
	
}

module.exports = config;