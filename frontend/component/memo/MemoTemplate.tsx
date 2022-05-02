import React, { useState } from 'react';
import styles from '../../../styles/scss/Memo.module.scss'
interface Props {
    width: number,
    height: number,
    content: any,
    header: any,
    drag: any,
  }
const MemoSticker = ({drag}) => {
    const [isEditable, setIsEditable] = useState(false);
    const onUpdateButtonClick = () =>{
        setIsEditable(true);
        drag.disableDragging();
    }
    const onApproveUpdateClick = () => {
        setIsEditable(false);
        drag.enableDragging();
    }
    const onDeleteButtonClick = () =>{

    }
    return (
        <div className={styles.checklist}>
            <div className={styles.deleteButton} onClick={onDeleteButtonClick}>
                ❌
            </div>
            {!isEditable && (<div className={styles.updateButton} onClick={onUpdateButtonClick}>✏️</div>)}
           
            {isEditable && (
                <div className={styles.approveUpdateButton} onClick={onApproveUpdateClick}>
                ✔️
                </div>
            )}
        </div>
    );
};

export default MemoSticker;