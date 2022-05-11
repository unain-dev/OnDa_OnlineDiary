import DatePicker from 'react-datepicker'
import React, { useEffect, useState } from 'react'
import 'react-datepicker/dist/react-datepicker.css'
import styles from './DatePickerModule.module.scss'
import ko from 'date-fns/locale/ko'
import { getDiaryDays } from 'core/api/diary'
import moment from 'moment'

const DatePickerModule = ({ startDate, setStartDate }) => {
  const token =
    'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MDEiLCJpc3MiOiJvbmRhLnNzYWZ5LmNvbSIsImV4cCI6MTY1MzM1Nzk4NywiaWF0IjoxNjUyMDYxOTg3fQ._yDfuQ4lL5tbYci6CFY-x08muvg71L5wo1uTH6FMMls_2IVep7jGlh5BMVWtqPXYoLp5Zm6UbzRY1aJYagiLrg'

  const [diaryDays, setDiaryDays] = useState([])
  const gotDiaryDays = (date) => {
    console.log(date)
    const params = {
      diaryDate: date,
      token: token,
    }
    getDiaryDays(params, (list) => {
      setDiaryDays(list)
    })
  }

  // 월바뀔때만 통신하도록 리팩토링 필요
  useEffect(() => {
    if (startDate != undefined && startDate != null) {
      gotDiaryDays(moment(startDate).format('YYYY-MM-DD'))
    }
  }, [startDate])

  return (
    <>
      <DatePicker
        locale={ko}
        selected={startDate}
        dateFormat="yyyy-MM-dd"
        onChange={(date) => setStartDate(date)}
        className={styles.datePicker}
        dayClassName={(d) => {
          // console.log(d.getMonth())
          // if (d.getMonth() != startDate.getMonth()) {
          //   //   setStartDate(moment(d).format('YYYY-MM-DD'))
          //   console.log('unmatched')
          // }
          const date = d.getDate()
          let isMatched = false
          diaryDays.map((m) => {
            if (date == m) {
              isMatched = true
            }
          })
          return isMatched ? styles.diaryDate : ''
        }}
        onMonthChange={(d) => {
          const year = d.getFullYear().toString()
          let month = d.getMonth() + 1
          let calMonth = ''

          if (month < 10) calMonth = '0' + month

          gotDiaryDays(year + '-' + calMonth + '-' + '01')
        }}
      />
    </>
  )
}

export default DatePickerModule
