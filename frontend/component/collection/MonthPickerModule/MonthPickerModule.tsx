import DatePicker from 'react-datepicker'
import React, { useEffect, useState } from 'react'
import 'react-datepicker/dist/react-datepicker.css'
import styles from './MonthPickerModule.module.scss'
import ko from 'date-fns/locale/ko'
import { NextPage } from 'next/types'
interface Props {
  value?: any;
  onClick?: any;
}

const MonthPickerModule = ({gotoDate}) => {
  const [startDate, setStartDate] = useState(new Date());

  const ExampleCustomInput: NextPage<Props> = ({ value, onClick }) => (
    <button className={styles.monthBtn} onClick={onClick}>
      월별 선택
    </button>
  );

  return (
    <>
      <DatePicker
        locale={ko}
        selected={startDate}
        dateFormat="MM/yyyy"
        onChange={(date) => {
          setStartDate(date)
          gotoDate(date);
        }}
        className={styles.datePicker}
        showMonthYearPicker
        customInput={<ExampleCustomInput />}
      />
    </>
  )
}

export default MonthPickerModule
