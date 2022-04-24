import { NextPage } from 'next/types';
import React from 'react';
import MemoText from '../memoText/MemoText' 
/**
 * MemoTypeSeq index
 * 1번 : memoText 텍스트 떡멘
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
    else{
        return <div></div>
    }
};

export default MemoSeparator;