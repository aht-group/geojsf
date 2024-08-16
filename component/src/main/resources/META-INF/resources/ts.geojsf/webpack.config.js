const path = require('path');
module.exports = {
  mode: process.env.NODE_ENV === 'development' ? 'development' : 'production',
  entry: {
    index: './index.ts',
    geo: './geo.ts',
    'geo-util': './geo-util.ts',
    viewport: './viewport.ts',    
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'], // resolve these extensions
  },
  output: {
    filename: '[name].js',
    path: path.resolve(__dirname, './dist/')
    },

    module: {
      rules: [
        {
          test: /\.tsx?$/, // regex to select only .ts or .tsx files
          use: 'ts-loader', // use ts-loader to transpile TypeScript code to JavaScript
          exclude: /node_modules/, // exclude node_modules directory
        }
      ]
    },
   
    devtool: 'source-map',
    optimization: {
      minimize: false
    }
};