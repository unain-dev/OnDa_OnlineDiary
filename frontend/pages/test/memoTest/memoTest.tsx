import React from 'react'
import 'react-quill/dist/quill.snow.css';
import dynamic from 'next/dynamic'

const QuillWrapper = dynamic(import('react-quill'), {
  ssr: false,
  loading: () => <p>Loading ...</p>,
})

const memoTest = () => {
  return (
    <div>
       <QuillWrapper  theme="snow" />
    </div>
  )
}

export default memoTest
