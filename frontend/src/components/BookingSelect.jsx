import { useEffect, useState } from 'react'
import Combobox from './ui/Combobox'

export default function BookingSelect({ value, onChange }) {
  const [options, setOptions] = useState([])

  useEffect(() => {
    fetch('/api/bookings').then(r => r.json()).then(bookings =>
      setOptions(bookings.map(b => ({
        id: b.id,
        label: `#${b.id} · ${b.guest.name} · ${b.hotel.name} · cam. ${b.room.number} · ${b.status}`
      })))
    )
  }, [])

  return <Combobox options={options} value={value} onChange={onChange} placeholder="Caută rezervare..." />
}
