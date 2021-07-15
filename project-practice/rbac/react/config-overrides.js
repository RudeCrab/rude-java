const {override, fixBabelImports, addLessLoader} = require('customize-cra');
module.exports = override(
    fixBabelImports('import', {
        libraryName: 'antd',
        libraryDirectory: 'es',
        // style的选项‘css’表示引入的css文件，true表示引入的less
        style: true,
    }),
    addLessLoader({
        lessOptions: {
            javascriptEnabled: true,
        }
    })
);
