import React, { useState } from "react";
import styles from '../../../styles/scss/Collection.module.scss'
const CheckListView = ({memoSeq}) =>{
    console.log(memoSeq)
    const [info, setInfo] = useState();
    return (
        <div className={styles.checklistView}>
            <div className={styles.checklistHeader}>{memoSeq.checklistHeader}</div>
            {memoSeq.checklistItems.length > 0 &&
                memoSeq.checklistItems.map((item, index) => {
                return (
                <div className={styles.checklistBody}>
                    <input type="checkbox" checked={item.isChecked} />
                    {item.content}
                </div>
            )
        })}
        </div>
    )
} 

export default CheckListView;