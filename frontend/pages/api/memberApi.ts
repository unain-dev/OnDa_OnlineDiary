import {instance} from './axios'

const COMMON = '/members';

// 중복체크
export const checkId = async (userId) => {
  const response = await instance.get(COMMON + '/memberId/' + userId);
  return response.data;
};

// 이메일 중복검사
export const checkEmail = async (userEmail) => {
  const response = await instance.get(COMMON + '/email/' + userEmail);
  return response.data;
};

// 이메일 인증 메일 발송
export const emailConfirm = async (userEmail) => {
  const response = await instance.post(COMMON + '/email', {
    params: {
      userEmail: userEmail,
    },
  });
  return response.data;
};

// 회원가입
export const onSignup = async (memberData) => {
  const response = await instance.post(COMMON, memberData);
  return response.data;
};
