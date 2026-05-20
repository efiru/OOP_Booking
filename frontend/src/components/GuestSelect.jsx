import { useEffect, useState } from 'react'
import Combobox from './ui/Combobox'

export default function GuestSelect({ value, onChange }) {
  const [options, setOptions] = useState([])

  useEffect(() => {
    fetch('/api/guests').then(r => r.json()).then(guests =>
      setOptions(guests.map(g => ({ id: g.id, label: `${g.name} (${g.email})` })))
    )
  }, [])

  return <Combobox options={options} value={value} onChange={onChange} placeholder="Caută client..." />
}
