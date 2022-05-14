import React, { useEffect, useState } from "react";
import styles from '../../../styles/scss/Collection.module.scss'
const FinanceView = ({memoSeq}) =>{
    console.log(memoSeq)
    const [total, setTotal] = useState({total: 0, income: 0, outcome: 0});

    useEffect(()=>{
        let total = 0;
        let income = 0;
        let outcome =0;
        memoSeq.accountBookItems.map((item)=>{
            if(item.income!==''){
                income+=Number(item.income);
                total+=Number(item.income);
            }
            if(item.outcome!==''){
                outcome+=Number(item.outcome);
                total-=Number(item.outcome);
            }
        })
        setTotal({total: total, income: income, outcome:outcome})
    },[])
    return (
        <div className={styles.financeView}>
            <div className={styles.checklistHeader}>{memoSeq.checklistHeader}</div>
            <div className={styles.financeNavHeader}>            
                <div className={styles.financeContentHeader}>내용</div>
                <div className={styles.financeContentHeader}>들어온 돈</div>
                <div className={styles.financeContentHeader}>나간 돈</div>
            </div>

            {memoSeq.accountBookItems.length > 0 &&
                memoSeq.accountBookItems.map((item, index) => {
                return (
                <div className={styles.financeBody}>
                    <div className={styles.financeContent}>{item.content}</div>
                    <div className={styles.income} style={{color:'blue'}}>{item.income.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}</div>
                    <div className={styles.outcome} style={{color:'red'}}>{item.outcome.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}</div>
                </div>
                )
            })}
            <div className={styles.financeFooter}>
                {total.total >= 0 ? 
                    <div className={styles.financeContent} style={{color:'blue'}}>{total.total}원</div> : 
                    <div className={styles.financeContent} style={{color:'red'}}>{total.total}원</div>}
                <div className={styles.income} style={{color:'blue'}}>{total.income.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}원</div>
                <div className={styles.outcome} style={{color:'red'}}>{total.outcome.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}원</div>
            </div>
        </div>
    )
} 

export default FinanceView;