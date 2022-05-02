import React, { useState } from 'react';
import styles from '../../../styles/scss/Memo.module.scss'
import { useDispatch } from 'react-redux'
import { changeText, changeMemoState } from '../../../core/store/modules/diary'

interface Props {
    memoInfo: any
    // width: number,
    // height: number,
    // content: any,
    // header: any,
    drag: any,
  }
const MemoChecklist = ({memoInfo, drag}) => {
    const { width, height, info } = memoInfo
    const dispatch = useDispatch();
    const [checkboxInfo, setCheckboxInfo] = useState(info)
    const [content, setContent] = useState('');
    const [isEditable, setIsEditable] = useState(false);
    const onCheckboxClick = (index) =>{
        checkboxInfo[index].isChecked = !checkboxInfo[index].isChecked
        setCheckboxInfo([...checkboxInfo])
    }
    const inputChecklistContent = (e) =>{
        setContent(e.target.value)
    }
    const addCheckboxList = () =>{
        if(content===''){
            alert("내용을 넣어주세요!");
            return
        }
        setCheckboxInfo([...checkboxInfo, {content: content, isChecked: false}])
        setContent('')
    }
    const onUpdateButtonClick = () =>{
        setIsEditable(true);
        drag.disableDragging();
        dispatch(
            changeMemoState({
                ...memoInfo,
                isEditing: true,
            }),
        )
    }
    const onApproveUpdateClick = () => {
        setIsEditable(false);
        drag.enableDragging();
        dispatch(
            changeText({
                ...memoInfo,
                info: [...checkboxInfo],
            }),
        )
        dispatch(
            changeMemoState({
                ...memoInfo,
                isEditing: false,
            }),
        )
    }
    const onDeleteButtonClick = () =>{

    }
    return (
        <div className={styles.checklist}>
            <div className={styles.deleteButton} onClick={onDeleteButtonClick}>
                ❌
            </div>
            {!isEditable && (<div className={styles.updateButton} onClick={onUpdateButtonClick}>✏️</div>)}
            {checkboxInfo.length > 0 && checkboxInfo.map((checkbox, index)=>{
                return(
                    <div>
                        <input type='checkbox' checked={checkbox.isChecked} onClick={()=>onCheckboxClick(index)}/>
                        {checkbox.content}
                    </div>
                )
            })}
            {isEditable && <div className={styles.checklistButton} >
                <input value={content} onChange={inputChecklistContent} type="text"/>
                <button onClick={addCheckboxList} >추가하기</button>
            </div>}
            {isEditable && (
                <div className={styles.approveUpdateButton} onClick={onApproveUpdateClick}>
                ✔️
                </div>
            )}
        </div>
    );
};

export default MemoChecklist;