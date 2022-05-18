import '../styles/globals.css'
import '@fullcalendar/common/main.css'
import '@fullcalendar/daygrid/main.css'
import '@fullcalendar/timegrid/main.css'
import { wrapper } from '../core/store'

import type { AppProps } from 'next/app'

import Header from 'component/common/header/Header'
import 'styles/css/font.css'
import 'styles/css/framer.css'
import Head from 'next/head'
import cookies from 'next-cookies'
import { getIsMember } from 'core/api/auth'
import { useEffect, useState } from 'react'
import Router from 'next/router'

function MyApp({ Component, pageProps }: AppProps) {
  console.log(pageProps.isMember)
  const [isAccessable, setIsAccessable] = useState(false)
  useEffect(() => {
    if (
      (pageProps.isMember && pageProps.path == '/') ||
      (pageProps.isMember && pageProps.path == '/user/login') ||
      (pageProps.isMember && pageProps.path == '/user/signup')
    ) {
      alert('로그인 상태에서 접근 불가능한 경로입니다.')
      Router.push('/collection/month')
    } else if (
      !pageProps.isMember &&
      pageProps.path !== '/' &&
      !pageProps.isMember &&
      pageProps.path !== '/user/login' &&
      !pageProps.isMember &&
      pageProps.path !== '/user/signup'
    ) {
      alert('비로그인 상태에서 접근 불가능한 경로입니다.')
      Router.push('/')
    } else {
      setIsAccessable(true)
    }

    return () => {
      setIsAccessable(false)
    }
  }, [pageProps])
  return (
    <>
      <Head>
        <title>온 다: 온라인 다이어리</title>
      </Head>
      {pageProps.path !== '/' && <Header {...pageProps} />}
      {isAccessable ? <Component {...pageProps} /> : ''}
    </>
  )
}

MyApp.getInitialProps = async (context) => {
  const { ctx, Component, req } = context
  let props = {}
  if (Component.getInitialProps) {
    props = await Component.getInitialProps(ctx)
  }

  const t = cookies(context).member
  const token = t === undefined ? null : t
  const isMember =
    t === undefined ? false : await getIsMember(cookies(context).member)

  return {
    pageProps: {
      ...props,
      path: ctx.asPath,
      diaryDate: ctx.query.diaryDate,
      token: cookies(context).member,
      isMember: isMember,
    },
  }
}

export default wrapper.withRedux(MyApp)
