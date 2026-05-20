import React, { useEffect, useState } from 'react'
import { Card, CardContent, CardHeader, CardTitle } from './ui/Card'
import { Button } from './ui/Button'
import { Input } from './ui/Input'
import { Label } from './ui/Label'
import { Badge } from './ui/Badge'
import HotelSelect from './HotelSelect'

export default function HotelsPage() {
  const [hotels, setHotels] = useState([])
  const [city, setCity] = useState('')
  const [expandedHotel, setExpandedHotel] = useState(null)
  const [rooms, setRooms] = useState([])
  const [error, setError] = useState('')

  const [hotelForm, setHotelForm] = useState({ name: '', city: '', stars: '' })
  const [standardRoomForm, setStandardRoomForm] = useState({ hotelId: '', number: '', capacity: '', pricePerNight: '', balcony: false })
  const [suiteRoomForm, setSuiteRoomForm] = useState({ hotelId: '', number: '', capacity: '', pricePerNight: '', livingArea: false })

  useEffect(() => { loadHotels() }, [])

  async function loadHotels(filterCity = '') {
    const url = filterCity ? `/api/hotels?city=${filterCity}` : '/api/hotels'
    const res = await fetch(url)
    setHotels(await res.json())
  }

  async function viewRooms(hotel) {
    if (expandedHotel?.id === hotel.id) { setExpandedHotel(null); return }
    const res = await fetch(`/api/hotels/${hotel.id}/rooms`)
    setRooms(await res.json())
    setExpandedHotel(hotel)
  }

  async function addHotel(e) {
    e.preventDefault()
    setError('')
    const res = await fetch('/api/hotels', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ ...hotelForm, stars: parseInt(hotelForm.stars) })
    })
    if (!res.ok) { const d = await res.json(); setError(d.error); return }
    setHotelForm({ name: '', city: '', stars: '' })
    loadHotels()
  }

  async function addStandardRoom(e) {
    e.preventDefault()
    setError('')
    const res = await fetch(`/api/hotels/${standardRoomForm.hotelId}/rooms/standard`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ number: standardRoomForm.number, capacity: parseInt(standardRoomForm.capacity), pricePerNight: parseFloat(standardRoomForm.pricePerNight), balcony: standardRoomForm.balcony })
    })
    if (!res.ok) { const d = await res.json(); setError(d.error); return }
    setStandardRoomForm({ hotelId: '', number: '', capacity: '', pricePerNight: '', balcony: false })
    loadHotels()
  }

  async function addSuiteRoom(e) {
    e.preventDefault()
    setError('')
    const res = await fetch(`/api/hotels/${suiteRoomForm.hotelId}/rooms/suite`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ number: suiteRoomForm.number, capacity: parseInt(suiteRoomForm.capacity), pricePerNight: parseFloat(suiteRoomForm.pricePerNight), livingArea: suiteRoomForm.livingArea })
    })
    if (!res.ok) { const d = await res.json(); setError(d.error); return }
    setSuiteRoomForm({ hotelId: '', number: '', capacity: '', pricePerNight: '', livingArea: false })
    loadHotels()
  }

  return (
    <div className="space-y-6">
      {error && <div className="rounded-md bg-red-50 border border-red-200 px-4 py-3 text-sm text-red-700">{error}</div>}

      <Card>
        <CardHeader>
          <CardTitle>Hoteluri</CardTitle>
          <div className="flex gap-2 pt-2">
            <Input placeholder="Filtrează după oraș..." value={city} onChange={e => setCity(e.target.value)} className="max-w-xs" />
            <Button onClick={() => loadHotels(city)}>Caută</Button>
            <Button variant="outline" onClick={() => { setCity(''); loadHotels() }}>Resetează</Button>
          </div>
        </CardHeader>
        <CardContent>
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b border-slate-200 text-left text-slate-500">
                <th className="pb-3 font-medium">ID</th>
                <th className="pb-3 font-medium">Nume</th>
                <th className="pb-3 font-medium">Oraș</th>
                <th className="pb-3 font-medium">Stele</th>
                <th className="pb-3 font-medium">Camere</th>
                <th className="pb-3 font-medium"></th>
              </tr>
            </thead>
            <tbody>
              {hotels.map(h => (
                <React.Fragment key={h.id}>
                  <tr className="border-b border-slate-100">
                    <td className="py-3 text-slate-500">{h.id}</td>
                    <td className="py-3 font-medium">{h.name}</td>
                    <td className="py-3">{h.city}</td>
                    <td className="py-3">{'★'.repeat(h.stars)}<span className="text-slate-300">{'★'.repeat(5 - h.stars)}</span></td>
                    <td className="py-3">{h.rooms?.length ?? 0} camere</td>
                    <td className="py-3"><Button variant="ghost" onClick={() => viewRooms(h)}>{expandedHotel?.id === h.id ? 'Ascunde' : 'Camere'}</Button></td>
                  </tr>
                  {expandedHotel?.id === h.id && (
                    <tr key={`rooms-${h.id}`}>
                      <td colSpan={6} className="bg-slate-50 px-4 py-3">
                        <p className="text-xs font-semibold text-slate-500 mb-2">CAMERE — {h.name}</p>
                        <div className="flex flex-wrap gap-2">
                          {rooms.map(r => (
                            <span key={r.id} className="rounded-lg border border-slate-200 bg-white px-3 py-2 text-xs">
                              <span className="font-medium">#{r.number}</span> <span className="text-slate-400">(ID: {r.id})</span> · {r.roomType} · {r.capacity} pers. · {r.pricePerNight} RON/noapte ·{' '}
                              <Badge variant={r.available ? 'success' : 'destructive'}>{r.available ? 'Disponibil' : 'Ocupat'}</Badge>
                            </span>
                          ))}
                        </div>
                      </td>
                    </tr>
                  )}
                </React.Fragment>
              ))}
            </tbody>
          </table>
        </CardContent>
      </Card>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card>
          <CardHeader><CardTitle>Adaugă hotel</CardTitle></CardHeader>
          <CardContent>
            <form onSubmit={addHotel} className="space-y-3">
              <div><Label>Nume</Label><Input value={hotelForm.name} onChange={e => setHotelForm({ ...hotelForm, name: e.target.value })} required /></div>
              <div><Label>Oraș</Label><Input value={hotelForm.city} onChange={e => setHotelForm({ ...hotelForm, city: e.target.value })} required /></div>
              <div><Label>Stele (1-5)</Label><Input type="number" min="1" max="5" value={hotelForm.stars} onChange={e => setHotelForm({ ...hotelForm, stars: e.target.value })} required /></div>
              <Button type="submit" className="w-full">Adaugă</Button>
            </form>
          </CardContent>
        </Card>

        <Card>
          <CardHeader><CardTitle>Adaugă cameră standard</CardTitle></CardHeader>
          <CardContent>
            <form onSubmit={addStandardRoom} className="space-y-3">
              <div><Label>Hotel</Label><HotelSelect value={standardRoomForm.hotelId} onChange={id => setStandardRoomForm({ ...standardRoomForm, hotelId: id })} /></div>
              <div><Label>Număr cameră</Label><Input value={standardRoomForm.number} onChange={e => setStandardRoomForm({ ...standardRoomForm, number: e.target.value })} required /></div>
              <div><Label>Capacitate</Label><Input type="number" value={standardRoomForm.capacity} onChange={e => setStandardRoomForm({ ...standardRoomForm, capacity: e.target.value })} required /></div>
              <div><Label>Preț / noapte (RON)</Label><Input type="number" value={standardRoomForm.pricePerNight} onChange={e => setStandardRoomForm({ ...standardRoomForm, pricePerNight: e.target.value })} required /></div>
              <div className="flex items-center gap-2"><input type="checkbox" id="balcony" checked={standardRoomForm.balcony} onChange={e => setStandardRoomForm({ ...standardRoomForm, balcony: e.target.checked })} /><Label htmlFor="balcony">Balcon</Label></div>
              <Button type="submit" className="w-full">Adaugă</Button>
            </form>
          </CardContent>
        </Card>

        <Card>
          <CardHeader><CardTitle>Adaugă cameră suite</CardTitle></CardHeader>
          <CardContent>
            <form onSubmit={addSuiteRoom} className="space-y-3">
              <div><Label>Hotel</Label><HotelSelect value={suiteRoomForm.hotelId} onChange={id => setSuiteRoomForm({ ...suiteRoomForm, hotelId: id })} /></div>
              <div><Label>Număr cameră</Label><Input value={suiteRoomForm.number} onChange={e => setSuiteRoomForm({ ...suiteRoomForm, number: e.target.value })} required /></div>
              <div><Label>Capacitate</Label><Input type="number" value={suiteRoomForm.capacity} onChange={e => setSuiteRoomForm({ ...suiteRoomForm, capacity: e.target.value })} required /></div>
              <div><Label>Preț / noapte (RON)</Label><Input type="number" value={suiteRoomForm.pricePerNight} onChange={e => setSuiteRoomForm({ ...suiteRoomForm, pricePerNight: e.target.value })} required /></div>
              <div className="flex items-center gap-2"><input type="checkbox" id="livingArea" checked={suiteRoomForm.livingArea} onChange={e => setSuiteRoomForm({ ...suiteRoomForm, livingArea: e.target.checked })} /><Label htmlFor="livingArea">Living area</Label></div>
              <Button type="submit" className="w-full">Adaugă</Button>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
