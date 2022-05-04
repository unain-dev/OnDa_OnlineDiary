import React, { useEffect, useState } from 'react'
import type { NextPage } from 'next'
import styles from '../../../styles/scss/Memo.module.scss'
import ReactHtmlParser from 'react-html-parser';

interface Props {
  header?: any
  content?: any
  onUpdateButtonClick: any
  onDeleteButtonClick: any
  onApproveUpdateClick: any
  isEditable: boolean
  memoInfo: any
}

const MemoFrame: NextPage<Props> = ({
  content,
  header,
  onUpdateButtonClick,
  onDeleteButtonClick,
  onApproveUpdateClick,
  isEditable,
  memoInfo,
}) => {
  // const [text, setText] = useState(JSON.stringify(content))
  const { width, height, info } = memoInfo;
  const [mouseState, setMouseState] = useState(false);
  
  const mouseOverEvent = () =>{
    setMouseState(true);
  }
  const mouseLeaveEvent = () =>{
    setMouseState(false);
  }
  return (
    <div className='temp' onMouseOver={mouseOverEvent} onMouseLeave={mouseLeaveEvent} style={{width: width, height: height}}>
      { mouseState && <div className={styles.deleteButton} onClick={onDeleteButtonClick}>
        ❌
      </div>}
      {mouseState && !isEditable && (
        <div className={styles.updateButton} onClick={onUpdateButtonClick}>
          ✏️
        </div>
      )}
      <div className={styles.header}>{header}</div>
      
      {
        !isEditable ? 
          (<div className={styles.content} style={{ wordBreak: 'break-all', margin: 10}}  >  
            {ReactHtmlParser(content)}
          </div>) : 
          (<div className={styles.content}  > 
            <div>
              {(content)}
            </div>
          </div>)}
      {mouseState && isEditable && (
        <div className={styles.approveUpdateButton} onClick={onApproveUpdateClick}>
          ✔️
        </div>
      )}
    </div>
  )
}

export default MemoFrame
