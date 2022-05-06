import React, { useState } from 'react'
import styles from '../../../styles/scss/Memo.module.scss'
import { useDispatch } from 'react-redux'
import { changeText, changeMemoState } from '../../../core/store/modules/diary'

interface Props {
  memoInfo: any
  // width: number,
  // height: number,
  // content: any,
  // header: any,
  drag: any
  onDeleteMemo: any
}
const MemoChecklist = ({memoInfo, drag, onDeleteMemo}) => {
    const { width, height, info } = memoInfo
    const dispatch = useDispatch();
    const [checkboxFullInfo, setCheckboxFullInfo] = useState({...info})
    const [checkboxInfo, setCheckboxInfo] = useState([...checkboxFullInfo.checklistItems])
    const [content, setContent] = useState('');
    const [isEditable, setIsEditable] = useState(false);
    const onCheckboxClick = (index) =>{
        const temp = JSON.parse(JSON.stringify(checkboxInfo));
        temp[index].isChecked = !temp[index].isChecked
        setCheckboxInfo([...temp])
    }
  const inputChecklistContent = (e) => {
    setContent(e.target.value)
  }
  const addCheckboxList = () => {
    if (content === '') {
      alert('내용을 넣어주세요!')
      return
    }
    setCheckboxInfo([...checkboxInfo, { content: content, isChecked: false }])
    setContent('')
  }
  const onUpdateButtonClick = () => {
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
    setIsEditable(false)
    drag.enableDragging()
    dispatch(
      changeText({
        ...memoInfo,
        info: {
          checklistHeader: header,
          checklistItems: [...checkboxInfo],
        },
      }),
    )
    dispatch(
      changeMemoState({
        ...memoInfo,
        isEditing: false,
      }),
    )
  }
  const onDeleteButtonClick = () => {}
  const [mouseState, setMouseState] = useState(false);
  
  const mouseOverEvent = () =>{
    setMouseState(true);
  }
  const mouseLeaveEvent = () =>{
    setMouseState(false);
  }
  const [header, setHeader] = useState(info.checklistHeader);
  return (
    <div className={styles.checklist} style={{width: width-10, height: height}}  onMouseOver={mouseOverEvent} onMouseLeave={mouseLeaveEvent} >
      {mouseState && <div
        className={styles.deleteButton}
        onClick={() => {
          onDeleteMemo(memoInfo.id)
        }}
      >
        ❌
      </div>}
      {mouseState && !isEditable && (
        <div className={styles.updateButton} onClick={onUpdateButtonClick}>
          ✏️
        </div>
      )}
      {!isEditable && <div className={styles.checklistHeader}>{header}</div>}
      {isEditable &&         <input
          defaultValue={header}
          onChange={(e) => {
            setHeader(e.target.value)
          }}
          className={styles.checklistHeader}
          style={{ width: width - 45, background: 'transparent', border: '0px solid'}}
          placeholder="제목을 입력해주세요"
        />}
      {checkboxInfo.length > 0 &&
        checkboxInfo.map((checkbox, index) => {
          return (
            <div className={styles.checklistBody}>
              <input
                type="checkbox"
                checked={checkbox.isChecked}
                onClick={() => onCheckboxClick(index)}
              />
              {checkbox.content}
            </div>
          )
        })}
      {isEditable && (
        <div className={styles.checklistButton}>
          <input style={{ width: width-30 }} className={styles.checklistInput} value={content} onChange={inputChecklistContent} type="text" placeholder='내용을 입력해주세요!' />
          <button className={styles.checklistAddButton} onClick={addCheckboxList}>✓</button>
        </div>
      )}
      {mouseState && isEditable && (
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

export default MemoChecklist
