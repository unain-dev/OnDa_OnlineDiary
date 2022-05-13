import LoginForm from 'component/user/loginForm'
// import { getCookie, removeCookie, setCookie } from 'core/common/cookie'
import { onLogin } from 'core/api/memberApi'
import { useState } from 'react'
import { useRouter } from 'next/router'
// import { getCookies, removeCookies, setCookies } from 'cookies-next'
import SsrCookie from "ssr-cookie";

const login = () => {
  const [memberId, setMemberId] = useState('')
  const [password, setPassword] = useState('')

  const memberIdHandler = (e) => {
    setMemberId(e.currentTarget.value)
  }

  const passwordHandler = (e) => {
    setPassword(e.currentTarget.value)
  }

  const router = useRouter()

  const cookie = new SsrCookie();
  // 로그인 버튼 클릭
  const loginFormSubmit = async () => {
    const result = await onLogin({ memberId, password })
    if (result.status == 200) {
      cookie.set('member', result.data.jwtToken, {
        path: '/',
        secure: true,
        sameSite: 'none',
        // domain: 'http://k6a107.p.ssafy.io/',
      })
      // setCookies('member', result.data, {
      //   path: '/',
      //   secure: true,
      //   sameSite: 'none',
      //   // domain: 'http://k6a107.p.ssafy.io/',
      // })
      router.push(`/collection/month`)
    } else {
      alert(result.msg)
    }
  }

  // 로그아웃 테스트 버튼
  const logout = () => {
    // console.log(cookie.get('member'))
    cookie.remove('member', {
      path: '/',
      secure: true,
      sameSite: 'none',
      // domain: 'http://k6a107.p.ssafy.io/',
    })
    // console.log(getCookies());
    // removeCookies('member', {
    //   path: '/',
    //   secure: true,
    //   sameSite: 'none',
    //   // domain: 'http://k6a107.p.ssafy.io/',
    // })
  }

  const props = {
    memberId,
    password,
    memberIdHandler,
    passwordHandler,
    loginFormSubmit,
    logout,
  }

  return (
    <div className="login-page">
      <LoginForm {...props} />
    </div>
  )
}

export default login
