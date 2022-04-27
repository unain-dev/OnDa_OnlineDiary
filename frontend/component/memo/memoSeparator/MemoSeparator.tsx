import { NextPage } from 'next/types';
import React from 'react';
import MemoText from '../memoText/MemoText' 
import MemoFinancialLedger from '../memoFinancialLedger/MemoFinancialLedger'
import MemoChecklist from '../memoCheckList/MemoChecklist'
/**
 * MemoTypeSeq index
 * 1번 : memoText 텍스트 떡메
 * 2번 : memoFinancialLedger 가계부 떡메
 */

interface Props {
    width: number,
    height: number,
    content: any,
    header: any,
    memoTypeSeq: number,
    drag: any,
  }
const MemoSeparator: NextPage<Props> = ({width, height, content, header, memoTypeSeq, drag}) => {
    if(memoTypeSeq===1){
        return (
            <MemoText
                width={width}
                height={height}
                content={content}
                header={header}
                drag={drag}
              />
        );
    }
    else if(memoTypeSeq===2){
        return <MemoFinancialLedger drag={drag}/>
    }
    else if(memoTypeSeq===3){
        return <MemoChecklist drag={drag}/>
    }
};

export default MemoSeparator;