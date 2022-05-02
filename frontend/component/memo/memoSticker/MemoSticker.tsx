import React, { useState, useEffect } from 'react'
import styles from '../../../styles/scss/Memo.module.scss'
import InputEmoji from 'react-input-emoji'
import { useDispatch } from 'react-redux'
import { changeMemoState } from 'core/store/modules/diary'
const MemoSticker = ({ memoInfo, drag, onDeleteMemo }) => {
  const dispatch = useDispatch()
  const { width, height, info } = memoInfo
  console.log(memoInfo)
  const [isEditable, setIsEditable] = useState(false)
  const [text, setText] = useState(info)
  const [finalEmoji, setFinalEmoji] = useState(info)
  const [size, setSize] = useState((width * height) / 500)

  useEffect(() => {
    setSize(Math.pow(Math.min(width, height), 2) / 500)
    console.log(width)
  }, [width, height])
  const handleOnEnter = (text) => {
    console.log('enter', text)
    setFinalEmoji(text)
  }
  const onUpdateButtonClick = () => {
    console.log('d')
    setIsEditable(true)
    drag.disableDragging()
    dispatch(
      changeMemoState({
        ...memoInfo,
        isEditing: true,
      }),
    )
  }
  const onApproveUpdateClick = () => {
    console.log('d')
    setIsEditable(false)
    if (text !== '') handleOnEnter(text)
    drag.enableDragging()
    dispatch(
      changeMemoState({
        ...memoInfo,
        isEditing: false,
      }),
    )
  }
  const onDeleteButtonClick = () => {
    onDeleteMemo(memoInfo.id)
  }
  return (
    <div className={styles.checklist}>
      <div className={styles.deleteButton} onClick={onDeleteButtonClick}>
        ❌
      </div>
      {!isEditable && (
        <div className={styles.updateButton} onClick={onUpdateButtonClick}>
          ✏️
        </div>
      )}
      {finalEmoji !== '' && (
        <div style={{ fontSize: size.toString() + 'px' }}>{finalEmoji}</div>
      )}
      {isEditable && (
        <div className={styles.emojiInput}>
          <InputEmoji
            value={text}
            onChange={setText}
            cleanOnEnter
            onEnter={handleOnEnter}
            placeholder="이모지를 선택해주세요!"
            maxLength={1}
          />
        </div>
      )}
      {isEditable && (
        <div
          className={styles.approveUpdateButton}
          onClick={onApproveUpdateClick}
        >
          ✔️
        </div>
      )}
    </div>
  )
}

export default MemoSticker
