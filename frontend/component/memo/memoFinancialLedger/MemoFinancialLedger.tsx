import React, { useState, useEffect } from 'react';
import styles from '../../../styles/scss/Memo.module.scss'
interface Props {
    width: number,
    height: number,
    content: any,
    header: any,
    drag: any,
  }
const MemoFinancialLedger = ({drag}) => {
    const [financeLedger, setFinanceLedger] = useState([])
    const [inputData, setInputData] = useState({
        content: '',
        income: '',
        outcome: ''
    })
    const [total, setTotal] = useState({
        total: '',
        income: '',
        outcome: '',
    })
    const [isEditable, setIsEditable] = useState(false);
    useEffect(()=>{
        let tempIncome=0;
        let tempOutcome=0;
        for(let i = 0; i < financeLedger.length; i+=1){
            console.log(parseInt(financeLedger[i].income), financeLedger[i].outcome)
            if(financeLedger[i].income!=='') tempIncome+=parseInt(financeLedger[i].income);
            if(financeLedger[i].outcome!=='') tempOutcome+=parseInt(financeLedger[i].outcome);
        }
        setTotal({...total, total: (tempIncome-tempOutcome).toString(), income: tempIncome.toString(), outcome: tempOutcome.toString()})
        console.log(tempIncome, tempOutcome)
    },[financeLedger])

    const onInputContent = (type, event) => {
        if(type==='CONTENT'){
            setInputData({...inputData, content: event.target.value.toString()})
        }
        else if(type==='INCOME'){
            setInputData({...inputData, income: event.target.value.toString()})
        }
        else if(type==='OUTCOME'){
            setInputData({...inputData, outcome: event.target.value.toString()})
        }
    }
    const addFinanceLedger = () =>{
        setFinanceLedger(prevState => [...prevState, {content: inputData.content, income: inputData.income, outcome: inputData.outcome}])
        setInputData({content: '', income: '', outcome: ''})
    }
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
        <div className={styles.financialLedger}>
            <div className={styles.deleteButton} onClick={onDeleteButtonClick}>
                ❌
            </div>
            {!isEditable && (<div className={styles.updateButton} onClick={onUpdateButtonClick}>✏️</div>)}

            <div className={styles.financeContentHeader}>내용</div>
            <div className={styles.financeContentHeader}>들어온 돈</div>
            <div className={styles.financeContentHeader}>나간 돈</div>
            {financeLedger.map((fin)=>{
                return(<div className={styles.financeContent}>
                    <div>{fin.content}</div>
                    <div className={styles.financeIncome}>{fin.income.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}</div>
                    <div className={styles.financeOutcome}>{fin.outcome.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}</div>
                </div>)
            })}
            {isEditable &&<><div className={styles.financeContent}>
                <input value={inputData.content} onChange={()=>onInputContent("CONTENT", event)} />
                <input type='number' value={inputData.income} onChange={()=>onInputContent("INCOME", event)}/>
                <input type='number' value={inputData.outcome} onChange={()=>onInputContent("OUTCOME", event)}/>
            </div>
            <button className={styles.financeAddButton} onClick={addFinanceLedger}>추가하기</button></>}
            <div className={styles.financeFooter}>
                <div className={styles.financeContent}>
                    {parseInt(total.total) >= 0 ? 
                        <div className={styles.financeIncome}> 
                            <div>총액: {total.total.replace(/\B(?=(\d{3})+(?!\d))/g, ',')+'원'}</div>
                        </div> : 
                        <div className={styles.financeOutcome}>
                            <div>총액: {total.total.replace(/\B(?=(\d{3})+(?!\d))/g, ',')+'원'}</div>
                        </div>}
                    <div className={styles.financeIncome}>{total.income.replace(/\B(?=(\d{3})+(?!\d))/g, ',')+'원'}</div>
                    <div className={styles.financeOutcome}>{total.outcome.replace(/\B(?=(\d{3})+(?!\d))/g, ',')+'원'}</div>
                </div>
            </div>
            {isEditable && (
                <div className={styles.approveUpdateButton} onClick={onApproveUpdateClick}>
                ✔️
                </div>
            )}
        </div>
    );
};

export default MemoFinancialLedger;