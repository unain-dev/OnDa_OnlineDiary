import React, { useRef, useState } from 'react'
import { motion, useCycle } from 'framer-motion'
import Logo from 'public/asset/image/logo/onda_logo.png'

const variants = {
  open: { opacity: 1, x: 0 },
  closed: { opacity: 0, x: '-100%' },
}

const sidebar = {
  open: (height = 1000) => ({
    clipPath: `circle(${height * 2 + 200}px at 40px 40px)`,
    transition: {
      type: 'spring',
      stiffness: 20,
      restDelta: 2,
    },
  }),
  closed: {
    clipPath: 'circle(30px at 40px 40px)',
    transition: {
      delay: 0.5,
      type: 'spring',
      stiffness: 400,
      damping: 40,
    },
  },
}

const intro = () => {
  const constraintsRef = useRef(null)
  const config = {
    type: 'spring',
    damping: 100,
    stiffness: 100,
  }
  return (
    <div>
      <motion.div ref={constraintsRef} className="drag-box">
        <motion.div
          drag
          dragConstraints={constraintsRef}
          // transition={config}
          initial={{ scale: 0, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          exit={{ x: 0, opacity: 0 }}
          className="inline"
        >
          <span className="grab">
            <h3>오늘의 기록, 온 : 다</h3>
          </span>
        </motion.div>
        <motion.div
          drag
          dragConstraints={constraintsRef}
          // transition={config}
          initial={{ scale: 0, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          exit={{ x: 0, opacity: 0 }}
          className="inline"
        >
          <span className="grab">
            온라인 다이어리 <b>온 : 다</b> 를 통해 오늘의 기록을 남기세요
          </span>
        </motion.div>
        <motion.div
          drag
          dragConstraints={constraintsRef}
          // transition={config}
          initial={{ scale: 0, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          exit={{ x: 0, opacity: 0 }}
          className="inline"
        >
          <span className="grab imo" role="img" aria-label="fire emoji">
            🖋
          </span>
        </motion.div>
        <motion.div
          drag
          dragConstraints={constraintsRef}
          // transition={config}
          initial={{ scale: 0, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          exit={{ x: 0, opacity: 0 }}
          className="inline"
        >
          <span className="grab">
            내가 썼던 기록이 언제였는지 기억이 안날 때
            <br />
            <br />
            여러 기록을 모아 한 눈에 보고싶을 때
          </span>
        </motion.div>
        <p className="dragger">Click to drag the items above!</p>
      </motion.div>
    </div>
  )
}

export default intro
