import { NextPage } from 'next/types';
import React from 'react';
import MemoText from '../memoText/MemoText' 
/**
 * 
 */

interface Props {
    width: number,
    height: number,
    content: any,
    header: any,
    memoTypeSeq: number,
  }
const MemoSeparator: NextPage<Props> = ({width, height, content, header, memoTypeSeq}) => {
    if(memoTypeSeq===1){
        return (
            <MemoText
                width={width}
                height={height}
                content={content}
                header={header}
              />
        );
    }
    else{
        return <div></div>
    }
};

export default MemoSeparator;