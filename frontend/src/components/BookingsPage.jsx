import { useState } from 'react'
import { Card, CardContent, CardHeader, CardTitle } from './ui/Card'
import { Button } from './ui/Button'
import { Input } from './ui/Input'
import { Label } from './ui/Label'
import { Badge } from './ui/Badge'

export default function BookingsPage() {
  const [bookings, setBookings] = useState([])
  const [guestIdSearch, setGuestIdSearch] = useState('')
  const [form, setForm] = useState({ guestId: '', hotelId: '', roomId: '', checkInDate: '', nights: '' })
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

  async function loadBookings() {
    if (!guestIdSearch) return
    const res = await fetch(`/api/bookings?guestId=${guestIdSearch}`)
    setBookings(await res.json())
  }

  async function createBooking(e) {
    e.preventDefault()
    setError(''); setSuccess('')
    const res = await fetch('/api/bookings', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ guestId: parseInt(form.guestId), hotelId: parseInt(form.hotelId), roomId: parseInt(form.roomId), checkInDate: form.checkInDate, nights: parseInt(form.nights) })
    })
    if (!res.ok) { const d = await res.json(); setError(d.error); return }
    const booking = await res.json()
    setSuccess(`Rezervare creată cu succes! ID: ${booking.id} · Total: ${booking.totalPrice} RON`)
    setForm({ guestId: '', hotelId: '', roomId: '', checkInDate: '', nights: '' })
  }

  async function cancelBooking(id) {
    setError(''); setSuccess('')
    const res = await fetch(`/api/bookings/${id}/cancel`, { method: 'PUT' })
    if (!res.ok) { setError('Nu s-a putut anula rezervarea.'); return }
    setSuccess('Rezervare anulată.')
    loadBookings()
  }

  return (
    <div className="space-y-6">
      {error && <div className="rounded-md bg-red-50 border border-red-200 px-4 py-3 text-sm text-red-700">{error}</div>}
      {success && <div className="rounded-md bg-green-50 border border-green-200 px-4 py-3 text-sm text-green-700">{success}</div>}

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card>
          <CardHeader><CardTitle>Crează rezervare</CardTitle></CardHeader>
          <CardContent>
            <form onSubmit={createBooking} className="space-y-3">
              <div><Label>ID Client</Label><Input type="number" value={form.guestId} onChange={e => setForm({ ...form, guestId: e.target.value })} required /></div>
              <div><Label>ID Hotel</Label><Input type="number" value={form.hotelId} onChange={e => setForm({ ...form, hotelId: e.target.value })} required /></div>
              <div><Label>ID Cameră</Label><Input type="number" value={form.roomId} onChange={e => setForm({ ...form, roomId: e.target.value })} required /></div>
              <div><Label>Data check-in</Label><Input type="date" value={form.checkInDate} onChange={e => setForm({ ...form, checkInDate: e.target.value })} required /></div>
              <div><Label>Număr nopți</Label><Input type="number" min="1" value={form.nights} onChange={e => setForm({ ...form, nights: e.target.value })} required /></div>
              <Button type="submit" className="w-full">Rezervă</Button>
            </form>
          </CardContent>
        </Card>

        <div className="md:col-span-2">
          <Card>
            <CardHeader>
              <CardTitle>Rezervările unui client</CardTitle>
              <div className="flex gap-2 pt-2">
                <Input type="number" placeholder="ID client..." value={guestIdSearch} onChange={e => setGuestIdSearch(e.target.value)} className="max-w-xs" />
                <Button onClick={loadBookings}>Caută</Button>
              </div>
            </CardHeader>
            <CardContent>
              <table className="w-full text-sm">
                <thead>
                  <tr className="border-b border-slate-200 text-left text-slate-500">
                    <th className="pb-3 font-medium">ID</th>
                    <th className="pb-3 font-medium">Hotel</th>
                    <th className="pb-3 font-medium">Cameră</th>
                    <th className="pb-3 font-medium">Check-in</th>
                    <th className="pb-3 font-medium">Nopți</th>
                    <th className="pb-3 font-medium">Total</th>
                    <th className="pb-3 font-medium">Status</th>
                    <th className="pb-3 font-medium"></th>
                  </tr>
                </thead>
                <tbody>
                  {bookings.map(b => (
                    <tr key={b.id} className="border-b border-slate-100">
                      <td className="py-3 text-slate-500">{b.id}</td>
                      <td className="py-3 font-medium">{b.hotel.name}</td>
                      <td className="py-3">#{b.room.number}</td>
                      <td className="py-3">{b.checkInDate}</td>
                      <td className="py-3">{b.nights}</td>
                      <td className="py-3">{b.totalPrice} RON</td>
                      <td className="py-3"><Badge variant={b.status === 'CONFIRMED' ? 'success' : 'destructive'}>{b.status}</Badge></td>
                      <td className="py-3">{b.status === 'CONFIRMED' && <Button variant="destructive" onClick={() => cancelBooking(b.id)}>Anulează</Button>}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  )
}
