import React from 'react';
import styles from 'styles/scss/Signup.module.scss'

const signupForm = ({
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
  emailSend,
  emailSendCheck,
  signupFormSubmit
}) => {
  return (
    <div className={styles.signupForm}>
      <div className={styles.title}>
        <h2>회원가입</h2>
      </div>    
      <div className={styles.content}>
        <table className={styles.tblComm}>
          <tbody>
            <tr>
              <th>아이디</th>
              <td>
                <input type="text" name='memberId' value={member.memberId} onChange={handleChangeState} onKeyUp={checkIdValid} maxLength={16} placeholder="4자 이상의 영문 혹은 영문과 숫자를 조합" required />
                <button type='button' onClick={() => checkIdUnique(member.memberId)} >중복확인</button>
                <p className={ member.memberId.length==0 ? styles.txt_guide_none : errorState.memberIdRegex ? styles.txt_guide_none : styles.txt_guide_block}>
                  <span>{errorMsg.memberIdRegex}</span>
                </p>
              </td>
            </tr>
            <tr>
              <th>비밀번호</th>
              <td>
                <input type="password" name='password' value={member.password} onChange={handleChangeState} onKeyUp={checkPasswordValid} maxLength={16} placeholder="비밀번호를 입력해주세요." required />
                <p className={ member.password.length==0 ? styles.txt_guide_none : errorState.passwordRegex ? styles.txt_guide_none : styles.txt_guide_block}>
                  <span>{errorMsg.passwordRegex}</span>
                </p>
              </td>
            </tr>
            <tr>
              <th>비밀번호확인</th>
              <td>
                <input type="password" name='confirmPassword' value={member.confirmPassword} onChange={handleChangeState} onKeyUp={checkPasswordConfirm} maxLength={16} placeholder="비밀번호를 한번 더 입력해주세요." required />
                <p className={ member.confirmPassword.length==0 ? styles.txt_guide_none : member.password == member.confirmPassword ? styles.txt_guide_none : styles.txt_guide_block}>
                  <span>{errorMsg.passwordConfirm}</span>
                </p>
              </td>
            </tr>
            <tr>
              <th>닉네임</th>
              <td>
                <input type="text" name='nickname' value={member.nickname} onChange={handleChangeState} onKeyUp={checkNicknameValid} maxLength={12} placeholder="닉네임을 입력해주세요" required />
                <p className={ member.nickname.length==0 ? styles.txt_guide_none : errorState.nicknameRegex ? styles.txt_guide_none : styles.txt_guide_block}>
                  <span>{errorMsg.nicknameRegex}</span>
                </p>
              </td>
            </tr>
            <tr>
              <th>이메일</th>
              <td>
                <input type="text" name='email' value={member.email} onChange={handleChangeState} onKeyUp={checkEmailValid} placeholder="예:ondiary@onda.com" required />
                <button type='button' onClick={() => emailSend(member.email)} >인증번호 받기</button>
                <p className={ member.email.length==0 ? styles.txt_guide_none : errorState.emailRegex ? styles.txt_guide_none : styles.txt_guide_block}>
                  <span>{errorMsg.emailRegex}</span>
                </p>
              </td>
            </tr>
            <tr>
              <th></th>
              <td>
                <input type="text" name='authCode' value={member.authCode} onChange={handleChangeState} placeholder='인증번호 입력' />
                <button type='button' onClick={emailSendCheck} >인증번호 확인</button>
              </td>
            </tr>
          </tbody>
        </table>
        <div className={styles.footer}>
          <a href="#" className={styles.cancle}>취소하기</a>
          <button type='button' onClick={signupFormSubmit} className="submit">가입하기</button>
        </div>
      </div>
    </div>
  );
};

export default signupForm;