import '../styles/globals.css'
import { wrapper } from '../core/store'

import type { AppProps } from 'next/app'

import 'styles/css/globals.css'

function MyApp({ Component, pageProps }: AppProps) {
  return <Component {...pageProps} />
}

export default wrapper.withRedux(MyApp)
