import { useEffect, useState } from 'react'
import Combobox from './ui/Combobox'

export default function RoomSelect({ hotelId, value, onChange }) {
  const [options, setOptions] = useState([])

  useEffect(() => {
    if (!hotelId) { setOptions([]); return }
    fetch(`/api/hotels/${hotelId}/rooms`).then(r => r.json()).then(rooms =>
      setOptions(rooms.map(r => ({
        id: r.id,
        label: `#${r.number} · ${r.roomType} · ${r.pricePerNight} RON/noapte${!r.available ? ' · Ocupată' : ''}`,
        disabled: !r.available
      })))
    )
  }, [hotelId])

  return (
    <Combobox
      options={options}
      value={value}
      onChange={onChange}
      placeholder={hotelId ? 'Caută cameră...' : 'Selectează mai întâi hotelul'}
      disabled={!hotelId}
    />
  )
}
