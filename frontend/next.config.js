/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: false,
  // sassOptions: {
  //   includePaths: [path.join(__dirname, 'styles')],
  // },
  ignoreBuildErrors: true,
}
const withTM = require('next-transpile-modules')([
  '@fullcalendar/common',
  '@babel/preset-react',
  '@fullcalendar/common',
  '@fullcalendar/daygrid',
  '@fullcalendar/interaction',
  '@fullcalendar/react',
  '@fullcalendar/timegrid',
])
// module.exports = nextConfig;

module.exports = withTM({
  // your custom config goes here
  nextConfig,
})
