import React, { useState, useRef, useEffect } from 'react'
import closeBtnImg from 'public/asset/image/diaryImage/closeBtnImg.png'
import Image from 'next/image'
import styles from '../../styles/scss/Collection.module.scss'
import CheckListView from './views/checkListView'
import FinanceView from './views/financeView'
import TextView from './views/textView'
import { useDispatch, useSelector } from 'react-redux'
import { getCollectionAction,getCollectionMemoAction } from 'core/store/actions/collection'
import { AppDispatch } from 'core/store'
import { useRouter } from 'next/router'
import cookies from 'next-cookies'

const CollectionPannel = ({ onCloseBtn, info, token }) => {
  console.log(info)
  const router = useRouter();
  const previewInfo = useSelector(({ collection }) => collection)
  console.log(previewInfo)
  const appDispatch:AppDispatch = useDispatch();

  const moveToDate = () =>{
    router.push(`/diary/${info.dateProp}`)
  }
  return (
    <div className={styles.pannel}>
      <div className={styles.closeBtnImgContainer}>
        <Image
          src={closeBtnImg}
          className={styles.closeBtnImg}
          width="36"
          height="36"
          onClick={onCloseBtn}
        />
        <button className={styles.dateMoveButton} onClick={moveToDate}>{info.dateProp} 날짜 상세보기</button>
        {info.memoTypeSeq === 1 && previewInfo.collectionMemoInfo.memoList !== undefined && previewInfo.collectionMemoInfo.memoList.map((memoSeq)=>{
          return (<TextView memoSeq={memoSeq}/>)
        })}
        {info.memoTypeSeq === 2 && previewInfo.collectionMemoInfo.memoList !== undefined && previewInfo.collectionMemoInfo.memoList.map((memoSeq)=>{
          return (<FinanceView memoSeq={memoSeq}/>)
        })}
        {info.memoTypeSeq === 3 && previewInfo.collectionMemoInfo.memoList !== undefined && previewInfo.collectionMemoInfo.memoList.map((memoSeq)=>{
          return (<CheckListView memoSeq={memoSeq}/>)
        })}
      </div>
      
    </div>
  )
}
export async function getServerSideProps(context) {
  return {
    props: {
      diaryDate: context.params.diaryDate,
      token: cookies(context).member,
    },
  }
}
export default CollectionPannel
