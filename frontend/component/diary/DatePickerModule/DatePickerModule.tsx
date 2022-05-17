import DatePicker from 'react-datepicker'
import React, { useEffect, useState } from 'react'
import 'react-datepicker/dist/react-datepicker.css'
import styles from './DatePickerModule.module.scss'
import ko from 'date-fns/locale/ko'
import { getDiaryDays } from 'core/api/diary'
import moment from 'moment'

const DatePickerModule = ({ startDate, setStartDate, token }) => {
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

  const month = Number.parseInt(moment(startDate).format('M')) - 1

  return (
    <>
      <DatePicker
        locale={ko}
        selected={startDate}
        dateFormat="yyyy-MM-dd"
        onChange={(date) => setStartDate(date)}
        className={styles.datePicker}
        dayClassName={(d) => {
          const nowMonth = d.getMonth()
          const nowDate = d.getDate()
          let isMatched = false
          diaryDays.map((m) => {
            if (nowDate == m && nowMonth == month) {
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
