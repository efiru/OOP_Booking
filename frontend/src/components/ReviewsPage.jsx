import { useState } from 'react'
import { Card, CardContent, CardHeader, CardTitle } from './ui/Card'
import { Button } from './ui/Button'
import { Input } from './ui/Input'
import { Label } from './ui/Label'
import HotelSelect from './HotelSelect'
import GuestSelect from './GuestSelect'

export default function ReviewsPage() {
  const [reviews, setReviews] = useState([])
  const [hotelIdSearch, setHotelIdSearch] = useState('')
  const [form, setForm] = useState({ guestId: '', hotelId: '', stars: '', comment: '' })
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

  async function loadReviews() {
    if (!hotelIdSearch) return
    const res = await fetch(`/api/reviews?hotelId=${hotelIdSearch}`)
    setReviews(await res.json())
  }

  async function addReview(e) {
    e.preventDefault()
    setError(''); setSuccess('')
    const res = await fetch('/api/reviews', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ guestId: parseInt(form.guestId), hotelId: parseInt(form.hotelId), stars: parseInt(form.stars), comment: form.comment })
    })
    if (!res.ok) { const d = await res.json(); setError(d.error); return }
    setSuccess('Review adăugat cu succes!')
    setForm({ guestId: '', hotelId: '', stars: '', comment: '' })
  }

  return (
    <div className="space-y-6">
      {error && <div className="rounded-md bg-red-50 border border-red-200 px-4 py-3 text-sm text-red-700">{error}</div>}
      {success && <div className="rounded-md bg-green-50 border border-green-200 px-4 py-3 text-sm text-green-700">{success}</div>}

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card>
          <CardHeader><CardTitle>Adaugă review</CardTitle></CardHeader>
          <CardContent>
            <form onSubmit={addReview} className="space-y-3">
              <div><Label>Client</Label><GuestSelect value={form.guestId} onChange={id => setForm({ ...form, guestId: id })} /></div>
              <div><Label>Hotel</Label><HotelSelect value={form.hotelId} onChange={id => setForm({ ...form, hotelId: id })} /></div>
              <div><Label>Stele (1-5)</Label><Input type="number" min="1" max="5" value={form.stars} onChange={e => setForm({ ...form, stars: e.target.value })} required /></div>
              <div><Label>Comentariu</Label><textarea value={form.comment} onChange={e => setForm({ ...form, comment: e.target.value })} required rows={3} className="flex w-full rounded-md border border-slate-300 bg-white px-3 py-2 text-sm shadow-sm placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-slate-900" /></div>
              <Button type="submit" className="w-full">Adaugă review</Button>
            </form>
          </CardContent>
        </Card>

        <div className="md:col-span-2">
          <Card>
            <CardHeader>
              <CardTitle>Review-uri hotel</CardTitle>
              <div className="flex gap-2 pt-2">
                <div className="max-w-xs w-full"><HotelSelect value={hotelIdSearch} onChange={id => setHotelIdSearch(id)} /></div>
                <Button onClick={loadReviews}>Caută</Button>
              </div>
            </CardHeader>
            <CardContent>
              <div className="space-y-3">
                {reviews.map(r => (
                  <div key={r.id} className="rounded-lg border border-slate-200 p-4">
                    <div className="flex items-center justify-between mb-1">
                      <span className="font-medium text-sm">{r.guest.name}</span>
                      <span className="text-amber-500">{'★'.repeat(r.stars)}<span className="text-slate-200">{'★'.repeat(5 - r.stars)}</span></span>
                    </div>
                    <p className="text-sm text-slate-600">{r.comment}</p>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  )
}
