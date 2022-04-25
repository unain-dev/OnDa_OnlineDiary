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
}

const MemoFrame: NextPage<Props> = ({
  content,
  header,
  onUpdateButtonClick,
  onDeleteButtonClick,
  onApproveUpdateClick,
  isEditable,
}) => {
  // const [text, setText] = useState(JSON.stringify(content))
  return (
    <div>
      <div className={styles.deleteButton} onClick={onDeleteButtonClick}>
        ❌
      </div>
      {!isEditable && (
        <div className={styles.updateButton} onClick={onUpdateButtonClick}>
          ✏️
        </div>
      )}
      <div className={styles.header}>{header}</div>
      {!isEditable ? (<div className={styles.content}  > {ReactHtmlParser(content)}</div>) : (<div className={styles.content}  > {(content)}</div>)}
      {isEditable && (
        <div className={styles.approveUpdateButton} onClick={onApproveUpdateClick}>
          ✔️
        </div>
      )}
    </div>
  )
}

export default MemoFrame
