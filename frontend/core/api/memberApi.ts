import { instance } from './axios'

const COMMON = '/members'

// 중복체크
export const checkId = async (memberId) => {
  const response = await instance.get(COMMON + '/memberId/' + memberId)
  return response.data
}

// 이메일 인증 메일 발송
export const emailAuth = async (email) => {
  const response = await instance.post(COMMON + '/email/auth', { email })
  return response.data
}

// 이메일 인증번호 확인
export const emailAuthCheck = async (email, emailAuth) => {
  const response = await instance.post(COMMON + '/email/auth/check', {
    email: email,
    emailAuth: emailAuth,
  })
  return response.data
}

// 회원가입
export const onSignup = async (memberData) => {
  const response = await instance.post(COMMON, memberData)
  return response.data
}

// 로그인
export const onLogin = async ({ memberId, password }) => {
  const response = await instance.post(COMMON + '/login', {
    memberId,
    password,
  })
  return response.data
}

// 회원 정보
export const getMemberInfo = async (token) => {
  const response = await instance.get(COMMON + '/mypage', {
    headers: {
      Authorization: `Bearer ` + token,
      'Content-Type': 'application/json',
    },
  })
  return response.data
}

// 회원 정보 수정
export const modifyMemberInfo = async (token, nickname) => {
  const response = await instance.put(
    COMMON + '/mypage/info',
    {
      nickname: nickname,
    },
    {
      headers: {
        Authorization: `Bearer ` + token,
        'Content-Type': 'application/json',
      },
    },
  )
  return response.data
}

// 회원 탈퇴
export const deleteMember = async (token, memberId, password) => {
  const response = await instance.delete(
    COMMON,
    {
      headers: {
        Authorization: `Bearer ` + token,
        'Content-Type': 'application/json',
      },
      data: {
        memberId: memberId,
        password: password,
      },
    },
  )
  return response.data
}

// 비밀번호 수정
export const modifyPassword = async (token, curPwd, newPwd) => {
  const response = await instance.put(COMMON + '/mypage/password',
    {
      prePassword: curPwd,
      newPassword: newPwd,
    },
    {
      headers: {
        Authorization: `Bearer ` + token,
        'Content-Type': 'application/json',
      },
    }
  )
  console.log(response.data)
  return response.data
}

// 토큰 유효성 검사
export const checkToken = async (token) => {
  console.log("api: "+ token)
  const response = await instance.get(COMMON + '/check', {
    headers: {
      Authorization: `Bearer ` + token,
      'Content-Type': 'application/json',
    },
  })
  return response.data;
}