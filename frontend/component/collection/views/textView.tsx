import React from "react";
import styles from '../../../styles/scss/Collection.module.scss'
import ReactHtmlParser from 'react-html-parser';
const TextView = ({memoSeq}) =>{

    return (
        <div className={styles.textView}>
            <div className={styles.header}>{memoSeq.header}</div>
            <div className={styles.content}>{ReactHtmlParser(memoSeq.content)}</div>
        </div>
    )
} 

export default TextView;