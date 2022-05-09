import React, { useState, useEffect } from 'react'
import { useDispatch } from 'react-redux'
import { changeText, changeMemoState } from 'core/store/modules/diary'
import styles from '../../../styles/scss/Memo.module.scss'
interface Props {
  width: number
  height: number
  content: any
  header: any
  drag: any
  memoInfo: any
  onDeleteMemo: any
}
const MemoFinancialLedger = ({memoInfo, drag, onDeleteMemo}) => {
    const dispatch = useDispatch();
    const { width, height, info } = memoInfo

    const [financeLedger, setFinanceLedger] = useState(info)
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
        console.log(financeLedger)
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

  const addFinanceLedger = () => {
    if(inputData.income==='' && inputData.outcome===''){
      alert("수입 혹은 지출을 하나라도 입력해주세요!");
      return;
    }
    setFinanceLedger((prevState) => [
      ...prevState,
      {
        content: inputData.content,
        income: inputData.income,
        outcome: inputData.outcome,
      },
    ])
    setInputData({ content: '', income: '', outcome: '' })
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
          ...financeLedger
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
  const onDeleteButtonClick = () => {
    onDeleteMemo(memoInfo.id)
  }
  const [mouseState, setMouseState] = useState(false);
  
  const mouseOverEvent = () =>{
    setMouseState(true);
  }
  const mouseLeaveEvent = () =>{
    setMouseState(false);
  }
  return (
    <div style={{width: width, height: height}} className={styles.financialLedger} onMouseOver={mouseOverEvent} onMouseLeave={mouseLeaveEvent}>
      {mouseState && <div className={styles.deleteButton} onClick={onDeleteButtonClick}>
        ❌
      </div>}
      {mouseState && !isEditable && (
        <div className={styles.updateButton} onClick={onUpdateButtonClick}>
          ✏️
        </div>
      )}

      <div className={styles.financeContentHeader}>내용</div>
      <div className={styles.financeContentHeader}>들어온 돈</div>
      <div className={styles.financeContentHeader}>나간 돈</div>
      {financeLedger.map((fin) => {
        return (
          <div className={styles.financeContent}>
            <div className={styles.financeContentTag}>{fin.content}</div>
            <div className={styles.financeIncome}>
              {fin.income.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
            </div>
            <div className={styles.financeOutcome}>
              {fin.outcome.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
            </div>
          </div>
        )
      })}
      {isEditable && (
        <>
          <div className={styles.financeContent}>
            <input className={styles.financeContentInput} style={{ width: (width-30)/3 }} placeholder="내용 입력" value={inputData.content} onChange={()=>onInputContent("CONTENT", event)} />
            <input className={styles.financeContentInput} style={{ width: (width-30)/3 }} placeholder="수입" type='number' value={inputData.income} onChange={()=>onInputContent("INCOME", event)}/>
            <input className={styles.financeContentInput} style={{ width: (width-30)/3 }} placeholder="지출" type='number' value={inputData.outcome} onChange={()=>onInputContent("OUTCOME", event)}/>
            <button className={styles.financeAddButton} onClick={addFinanceLedger}>✓</button>
          </div>
        </>
      )}
      <div className={styles.financeFooter}>
        <div className={styles.financeContent}>
          {parseInt(total.total) >= 0 ? (
            <div className={styles.financeIncome}>
              <div>{total.total.replace(/\B(?=(\d{3})+(?!\d))/g, ',')+'원'}</div>
            </div>
          ) : (
            <div className={styles.financeOutcome}>
              <div>{total.total.replace(/\B(?=(\d{3})+(?!\d))/g, ',')+'원'}</div>
            </div>
          )}
          <div className={styles.financeIncome}>
            {total.income.replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '원'}
          </div>
          <div className={styles.financeOutcome}>
            {total.outcome.replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '원'}
          </div>
        </div>
      </div>
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

export default MemoFinancialLedger
