import { getMemberInfo } from 'core/api/memberApi';
import router from 'next/router';
import { useEffect, useState } from 'react';
import styles from 'styles/scss/User.module.scss'

const mypageForm = (
  {memberId,
  email,
  nickname,
  errorState,
  errorMsg,
  password,
  curPassword,
  newPassword,
  checkPassword,
  setNickname,
  setPassword,
  setCurPassword,
  setNewPassword,
  setCheckPassword,
  checkNicknameValid,
  onModify,
  onWithdraw,
  checkPasswordValid,
  checkPasswordConfirm,
  changePassword}
) => {
  return (
    <div className={styles.mypageForm}>
      <div className={styles.title}>
        <h2>회원 정보</h2>
      </div>
      <div className={styles.content}>
        <table className={styles.info_tbl}>
          <tbody>
            <tr className={styles.top}>
              <th>아이디</th>
              <td>
                <input type="text" name='memberId' defaultValue={memberId} disabled></input>
              </td>
            </tr>
            <tr>
              <th>이메일</th>
              <td>
                <input type="text" name='email' defaultValue={email} disabled />
                </td>
              </tr>
            <tr>
              <th>닉네임</th>
              <td>
                <input type="text" name='nickname' value={nickname} onChange={(e) => setNickname(e.currentTarget.value)} onKeyUp={checkNicknameValid} maxLength={12} placeholder="닉네임을 입력해주세요" required />
                <button type='button' className={styles.btn_modify} onClick={onModify} >정보수정</button>
                <p className={ nickname.length==0 ? styles.txt_guide_none : errorState.nicknameRegex ? styles.txt_guide_none : styles.txt_guide_block}>
                  <span>{errorMsg.nicknameRegex}</span>
                </p>
              </td>
              </tr>
              <tr>
                <th>비밀번호</th>
                <td>
                <input type="password" name='password' value={password} onChange={(e) => setPassword(e.currentTarget.value)} />
                <button type='button' className={styles.btn_withdraw} onClick={onWithdraw} >탈퇴하기</button>
                </td>
            </tr>
            <tr className={styles.password_title}>
              <th></th>
              <td><h3>비밀번호 변경</h3></td>
            </tr>
            <tr className={styles.top}>
              <th>현재 비밀번호</th>
              <td>
                <input type="password" name='curPassword' value={curPassword} onChange={(e) => setCurPassword(e.currentTarget.value)} onKeyUp={checkPasswordValid} maxLength={16} required />
              </td>
            </tr>
            <tr>
              <th>새로운 비밀번호</th>
              <td>
                <input type="password" name='newPassword' value={newPassword} onChange={(e) => setNewPassword(e.currentTarget.value)} onKeyUp={checkPasswordValid} maxLength={16} required />
                <p className={ newPassword.length==0 ? styles.txt_guide_none : errorState.passwordRegex ? styles.txt_guide_none : styles.txt_guide_block}>
                  <span>{errorMsg.passwordRegex}</span>
                </p>
              </td>
            </tr>
            <tr>
              <th>비밀번호 확인</th>
              <td>
                <input type="password" name='checkPassword' value={checkPassword} onChange={(e) => setCheckPassword(e.currentTarget.value)} onKeyUp={checkPasswordConfirm} maxLength={16} required />
                <button type='button' className={styles.btn_password} onClick={changePassword} >비밀번호 변경</button>
                <p className={ checkPassword.length==0 ? styles.txt_guide_none : newPassword == checkPassword ? styles.txt_guide_none : styles.txt_guide_block}>
                  <span>{errorMsg.passwordConfirm}</span>
                </p>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default mypageForm;