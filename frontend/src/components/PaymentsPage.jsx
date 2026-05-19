import { useState } from 'react'
import { Card, CardContent, CardHeader, CardTitle } from './ui/Card'
import { Button } from './ui/Button'
import { Input } from './ui/Input'
import { Label } from './ui/Label'

export default function PaymentsPage() {
  const [form, setForm] = useState({ bookingId: '', method: '' })
  const [result, setResult] = useState(null)
  const [error, setError] = useState('')

  async function registerPayment(e) {
    e.preventDefault()
    setError(''); setResult(null)
    const res = await fetch('/api/payments', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ bookingId: parseInt(form.bookingId), method: form.method })
    })
    if (!res.ok) { const d = await res.json(); setError(d.error); return }
    setResult(await res.json())
    setForm({ bookingId: '', method: '' })
  }

  return (
    <div className="space-y-6">
      {error && <div className="rounded-md bg-red-50 border border-red-200 px-4 py-3 text-sm text-red-700">{error}</div>}

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card>
          <CardHeader><CardTitle>Înregistrează plată</CardTitle></CardHeader>
          <CardContent>
            <form onSubmit={registerPayment} className="space-y-3">
              <div><Label>ID Rezervare</Label><Input type="number" value={form.bookingId} onChange={e => setForm({ ...form, bookingId: e.target.value })} required /></div>
              <div>
                <Label>Metodă plată</Label>
                <select value={form.method} onChange={e => setForm({ ...form, method: e.target.value })} required className="flex h-9 w-full rounded-md border border-slate-300 bg-white px-3 py-1 text-sm shadow-sm focus:outline-none focus:ring-2 focus:ring-slate-900">
                  <option value="">Selectează...</option>
                  <option value="card">Card</option>
                  <option value="cash">Cash</option>
                  <option value="transfer">Transfer bancar</option>
                </select>
              </div>
              <Button type="submit" className="w-full">Înregistrează plata</Button>
            </form>
          </CardContent>
        </Card>

        {result && (
          <Card>
            <CardHeader><CardTitle>Plată confirmată</CardTitle></CardHeader>
            <CardContent className="space-y-2 text-sm">
              <div className="flex justify-between"><span className="text-slate-500">ID Plată</span><span className="font-medium">{result.id}</span></div>
              <div className="flex justify-between"><span className="text-slate-500">ID Rezervare</span><span className="font-medium">{result.booking.id}</span></div>
              <div className="flex justify-between"><span className="text-slate-500">Sumă</span><span className="font-medium">{result.amount} RON</span></div>
              <div className="flex justify-between"><span className="text-slate-500">Metodă</span><span className="font-medium capitalize">{result.method}</span></div>
              <div className="flex justify-between"><span className="text-slate-500">Status</span><span className="font-medium text-green-600">Plătit</span></div>
            </CardContent>
          </Card>
        )}
      </div>
    </div>
  )
}
