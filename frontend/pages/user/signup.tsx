import React, { useState } from 'react';
import SignupForm from 'component/user/signupForm'
import { checkId, checkEmail, onSignup } from 'pages/api/memberApi'

const signup = () => {
  const [member, setMember] = useState({
    memberId: "",
    password: "",
    confirmPassword: "",
    nickname: "",
    email: "",
  })

  const handleChangeState = (e) => {
    setMember({
      ...member,
      [e.currentTarget.name]: e.currentTarget.value
    });
    if (e.currentTarget.name == "memberId") {
      setErrorState({
        ...errorState,
        memberIdUnique: false,
      })
    } else if (e.currentTarget.name == "email") {
      setErrorState({
        ...errorState,
        emailUnique: false,
      })
    }
  }

  // error State
  const [errorState, setErrorState] = useState({
    memberIdRegex: false, // 유효성
    memberIdUnique: false, // 중복
    passwordRegex: false,
    passwordConfirm: false,
    nicknameRegex: false,
    emailRegex: false,
    emailUnique: false,
    emailConfirm: false, // 인증
  });

  const toggleIsEmailConfirm = () => {
    if(errorState.emailUnique) {
      setErrorState({
      ...errorState,
      emailConfirm: !errorState.emailConfirm,
    })
    } else if(errorState.emailRegex) {
      alert("이메일 중복확인을 해주세요.")
    } else {
      alert("이메일을 입력해주세요.")
    }
  }

  // error Message
  const errorMsg = {
    memberIdRegex: '4자 이상의 영문 혹은 영문과 숫자를 조합, 영문으로 시작',
    memberIdUnique: '아이디 중복확인',
    passwordRegex: '8자 이상의 영문/숫자/특수문자(공백 제외)만 허용하며, 2개 이상 조합',
    passwordConfirm: '동일한 비밀번호를 입력해주세요.',
    nicknameRegex: '3자 이상의 한글/영문/숫자 조합',
    emailRegex: '잘못된 이메일 형식입니다.',
    emailUnique: '중복되는 이메일이 있습니다. ',
  };

  const signUpBtnMsg = {
    idInput: "아이디를 입력해주세요.",
    idUnique: "아이디 중복확인을 해주세요.",
    pwInput: "비밀번호를 입력해주세요.",
    nickInput: "닉네임을 입력해주세요.",
    emailInput: "이메일 형식을 확인해주세요.",
    emailUnique: "이메일 중복 확인을 해주세요.",
    emailConfirm: "이메일 인증을 해주세요.",
    signUp: "가입이 완료되었습니다.",
  }
  
  // 아이디 유효성 검사
  const checkIdValid = (e) => {
    const checkId = /^[A-Za-z]{1}[a-z|A-Z|0-9+|]{3,15}$/g.test(e.target.value);
    if (!checkId) {
      setErrorState({
        ...errorState,
        memberIdRegex: false,
      });
    } else {
      setErrorState({
        ...errorState,
        memberIdRegex: true,
      })
    }
  };
  
  // 아이디 중복 확인
  const checkIdUnique = async (id) => {
    if (id.length == 0) {
      alert("아이디를 입력해주세요.");
      return;
    }
    const result = await checkId(id);
    if (result.status == 204) {
      setErrorState({
        ...errorState,
        memberIdUnique: true,
      });
      alert(result.msg);
    } else if(result.status == 200 || result.status == 400) {
      setErrorState({
        ...errorState,
        memberIdUnique: false,
      });
      alert(result.msg);
    }
  };
  
  // 비밀번호 유효성 검사
  const checkPasswordValid = (e) => {
    const pw = e.target.value;
    const num = pw.search(/[0-9]/g);
    const eng = pw.search(/[A-Za-z]/ig);
    const spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

    if (pw.length < 8) {
      setErrorState({
        ...errorState,
        passwordRegex: false,
      })
    } else if ((num<0 && eng<0) || (eng<0 && spe<0) || (spe<0 && num<0)) {
      setErrorState({
        ...errorState,
        passwordRegex: false,
      })
    } else {
      setErrorState({
        ...errorState,
        passwordRegex: true,
      })
    }
  }

  // 비밀번호 재입력 확인
  const checkPasswordConfirm = (e) => {
    const pwc = e.target.value;
    if (pwc != member.password) {
      setErrorState({
        ...errorState,
        passwordConfirm: false,
      })
    } else {
      setErrorState({
        ...errorState,
        passwordConfirm: true,
      })
    }
  }

  // 닉네임 유효성 검사
  const checkNicknameValid = (e) => {
    const checkNick = /^[a-zA-Zㄱ-힣0-9]{3,12}$/g.test(e.target.value);
    if (!checkNick) {
      setErrorState({
        ...errorState,
        nicknameRegex: false,
      })
    } else {
      setErrorState({
        ...errorState,
        nicknameRegex: true,
      })
    }
  }

  // 이메일 유효성 검사
  const checkEmailValid = () => {
    var checkEmail = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/.test(member.email);
    if (!checkEmail) {
      setErrorState({
        ...errorState,
        emailRegex: false,
      })
    } else {
      setErrorState({
        ...errorState,
        emailRegex: true,
      })
    }
  }

  // 이메일 중복 확인
  const checkEmailUnique = async (email) => {
    if (email.length == 0) {
      alert("이메일을 입력해주세요.");
      return;
    }
    const result = await checkEmail(email);
    if (result.status == 204) {
      setErrorState({
        ...errorState,
        emailUnique: true,
      })
      alert(result.msg);
    } else if (result.status == 200 || result.status == 400) {
      setErrorState({
        ...errorState,
        emailUnique: false,
      })
      alert(result.msg);
    }
  }
  

  // 가입하기 버튼 클릭
  const signupFormSubmit = () => {
    // e.preventDefault();
    var result = "";
    if (!errorState.memberIdRegex) {
      result = signUpBtnMsg.idInput;
    } else if (!errorState.memberIdUnique) {
      result = signUpBtnMsg.idUnique;
    } else if (!errorState.passwordConfirm) {
      result = signUpBtnMsg.pwInput;
    } else if (!errorState.nicknameRegex) {
      result = signUpBtnMsg.nickInput;
    } else if (!errorState.emailRegex) {
      result = signUpBtnMsg.emailInput;
    } else if (!errorState.emailUnique) {
      result = signUpBtnMsg.emailUnique;
    // } else if (!errorState.emailConfirm) { // 이메일 인증
    //   result = signUpBtnMsg.emailConfirm;
    } else {
      result = signUpBtnMsg.signUp;
      handleSignup();
    }
    alert(result);
  }

  const handleSignup = async () => {
    const result = await onSignup(member);
    if (result.status == 201) { // 회원가입 완료
      alert(result.msg);
    } else {
      alert(result.msg);
    }
  }

  const props = {
    member,
    handleChangeState,
    errorState,
    errorMsg,
    checkIdValid,
    checkIdUnique,
    checkPasswordValid,
    checkPasswordConfirm,
    checkNicknameValid,
    checkEmailValid,
    checkEmailUnique, 
    signupFormSubmit
  }
  return (
    <div className='signup-page'>
      <SignupForm {...props} />
    </div>
  );
};

export default signup;