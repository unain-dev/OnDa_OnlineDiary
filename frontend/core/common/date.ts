export const calToday = (year, month, day) => {
  const today = new Date()
}

const dateToString = (date: Date) => {
  const year = date.getFullYear()
  let month: any = date.getMonth() + 1
  let day: any = date.getDate()

  if (month < 10) month = '0' + month
  if (day < 10) day = '0' + day

  return year + '-' + month + '-' + day
}

export const calNextDate = (diaryDate) => {
  const date = new Date(diaryDate)
  date.setDate(date.getDate() + 1)
  return dateToString(date)
}

export const calPrevDate = (diaryDate) => {
  const date = new Date(diaryDate)
  date.setDate(date.getDate() - 1)
  return dateToString(date)
}
