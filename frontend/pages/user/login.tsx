import LoginForm from 'component/user/loginForm'
import { onLogin } from 'core/api/memberApi'
import { useState } from 'react'
import { useRouter } from 'next/router'
import cookies from 'next-cookies'
import { getIsMember } from 'core/api/auth'

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

  // 로그인 버튼 클릭
  const loginFormSubmit = async () => {
    const result = await onLogin({ memberId, password })
    if (result.status == 200) {
      document.cookie = `member = ${result.data.jwtToken}; path=/`
      router.push(`/collection/month`)
    } else {
      alert(result.msg)
    }
  }

  const onkeydown = (e) => {
    if (e.key == 'Enter') {
      loginFormSubmit()
    }
  }

  // 로그아웃 테스트 버튼
  const logout = () => {
    document.cookie = `member = ; path=/; expires=Thu, 01 Jan 1970 00:00:01 GMT`
    router.push(`/user/login`)
  }

  const props = {
    memberId,
    password,
    memberIdHandler,
    passwordHandler,
    loginFormSubmit,
    onkeydown,
    logout,
  }

  return (
    <div className="login-page">
      <LoginForm {...props} />
    </div>
  )
}

export async function getServerSideProps(context) {
  const t = cookies(context).member
  const token = t === undefined ? null : t
  const isMember =
    t === undefined ? false : await getIsMember(cookies(context).member)

  return {
    props: {
      // diaryDate: context.params.diaryDate,
      isMember: isMember,
      token: token,
    },
  }
}

export default login
