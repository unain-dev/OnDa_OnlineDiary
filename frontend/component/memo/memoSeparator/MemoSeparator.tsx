import { NextPage } from 'next/types'
import React, { useEffect } from 'react'
import MemoText from '../memoText/MemoText'
import MemoFinancialLedger from '../memoFinancialLedger/MemoFinancialLedger'
import MemoImage from '../memoImage/MemoImage'
import MemoChecklist from '../memoCheckList/MemoChecklist'
import MemoSticker from '../memoSticker/MemoSticker'
/**
 * MemoTypeSeq index
 * 1번 : memoText 텍스트 떡메
 * 2번 : memoFinancialLedger 가계부 떡메
 */

interface Props {
  memoInfo: any
  memoTypeSeq: number
  drag: any
  onDeleteMemo: any
}
const MemoSeparator: NextPage<Props> = ({
  memoInfo, //memoInfo = memoList의 한 요소 전체 정보(width, height, x, y, info(content, header))
  memoTypeSeq,
  drag,
  onDeleteMemo,
}) => {
  console.log(memoInfo)
  const props = {
    memoInfo,
    drag,
    onDeleteMemo,
  }
  if (memoTypeSeq === 1) {
    return <MemoText {...props} />
  } else if (memoTypeSeq === 2) {
    return <MemoFinancialLedger {...props} />
  } else if (memoTypeSeq === 3) {
    return <MemoChecklist {...props} />
  } else if (memoTypeSeq === 4) {
    return <MemoImage {...props} />
  } else if (memoTypeSeq === 5) {
    return <MemoSticker {...props} />
  }
}

export default MemoSeparator
