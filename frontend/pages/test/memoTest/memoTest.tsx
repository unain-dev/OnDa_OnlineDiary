import React from 'react'
import MemoFrame from 'component/memo/memoCommon/MemoFrame'

const memoTest = () => {
  return (
    <div>
      <MemoFrame
        width={200}
        height={200}
        content={'helloWorld'}
        header={'this is header'}
      />
      <MemoFrame
        width={200}
        height={200}
        content={'helloWorld'}
        header={'this is header'}
      />
    </div>
  )
}

export default memoTest
