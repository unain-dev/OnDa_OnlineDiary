import React, { useEffect } from 'react'
import type { NextPage } from 'next'
import styles from '../../../styles/scss/Memo.module.scss'
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
      <div className={styles.content} > {content}</div>
      {isEditable && (
        <div className={styles.approveUpdateButton} onClick={onApproveUpdateClick}>
          ✔️
        </div>
      )}
    </div>
  )
}

export default MemoFrame
