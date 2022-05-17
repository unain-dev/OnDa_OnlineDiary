import '../styles/globals.css'
import '@fullcalendar/common/main.css'
import '@fullcalendar/daygrid/main.css'
import '@fullcalendar/timegrid/main.css'
import { wrapper } from '../core/store'

import type { AppProps } from 'next/app'

import Header from 'component/common/header/Header'
import 'styles/css/font.css'
import 'styles/css/framer.css'
import { useRouter } from 'next/router'

function MyApp({ Component, pageProps }: AppProps) {
  const router = useRouter()
  const path = router.asPath
  return (
    <>
      {path !== '/' && <Header />}
      <Component {...pageProps} />
    </>
  )
}

export default wrapper.withRedux(MyApp)
