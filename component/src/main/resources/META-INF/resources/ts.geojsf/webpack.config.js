const path = require('path');
module.exports = {
  module: {
    rules: [
      {
        test: require.resolve("./geo"),
        loader: "expose-loader",
        options: {
          exposes: ["geoapi", "./geo"],
        },
      },
      {
        test: require.resolve("./geo-util"),
        loader: "expose-loader",
        options: {
          exposes: ["geoutil", "./geo-util"],
        },
      }
    ]
  },
  mode: process.env.NODE_ENV === 'development' ? 'development' : 'production',
  entry: './index.js',
  output: {
    filename: 'geojsf.js',
    path: path.resolve(__dirname, 'dist/')
    },
  devtool: 'source-map',
  optimization: {
    minimize: false
  }
};