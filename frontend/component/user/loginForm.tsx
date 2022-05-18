
import router, { Router } from 'next/router';
import styles from 'styles/scss/User.module.scss'


const loginForm = ({
  memberId,
  password,
  memberIdHandler,
  passwordHandler,
  loginFormSubmit,
  onkeydown,
}) => {
  

  return (
    <div className={styles.loginForm}>
      <div className={styles.title}>
        <h2>로그인</h2>
      </div>
      <div className={styles.content}>
        <input type="text" className='member_id' value={memberId} onChange={memberIdHandler} onKeyDown={onkeydown} placeholder='아이디를 입력해주세요' required />
        <input type="password" className='password' value={password} onChange={passwordHandler} onKeyDown={onkeydown} placeholder='비밀번호를 입력해주세요' />
        <button className={styles.btn_login} type='button' onClick={loginFormSubmit}>로그인</button>
        <button className={styles.btn_signup} onClick={() => { router.push(`/user/signup`)}}>회원가입</button>
      </div>
    </div>
  );
};

export default loginForm;