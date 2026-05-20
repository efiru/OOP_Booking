import { useEffect, useState } from 'react'
import Combobox from './ui/Combobox'

export default function HotelSelect({ value, onChange, required }) {
  const [options, setOptions] = useState([])

  useEffect(() => {
    fetch('/api/hotels').then(r => r.json()).then(hotels =>
      setOptions(hotels.map(h => ({ id: h.id, label: `${h.name} — ${h.city} (${h.stars}★)` })))
    )
  }, [])

  return <Combobox options={options} value={value} onChange={onChange} placeholder="Caută hotel..." />
}
