import '../styles/globals.css'
import '@fullcalendar/common/main.css'
import '@fullcalendar/daygrid/main.css'
import '@fullcalendar/timegrid/main.css'
import { wrapper } from '../core/store'

import type { AppProps } from 'next/app'

import Header from 'component/common/header/Header'
import 'styles/css/globals.css'

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <>
      <Header />
      <Component {...pageProps} />
    </>
  )
}

export default wrapper.withRedux(MyApp)
