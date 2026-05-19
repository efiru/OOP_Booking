import { useEffect, useState } from 'react'
import { Card, CardContent, CardHeader, CardTitle } from './ui/Card'
import { Button } from './ui/Button'
import { Input } from './ui/Input'
import { Label } from './ui/Label'

export default function GuestsPage() {
  const [guests, setGuests] = useState([])
  const [form, setForm] = useState({ name: '', email: '' })
  const [error, setError] = useState('')

  useEffect(() => { loadGuests() }, [])

  async function loadGuests() {
    const res = await fetch('/api/guests')
    setGuests(await res.json())
  }

  async function registerGuest(e) {
    e.preventDefault()
    setError('')
    const res = await fetch('/api/guests', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(form)
    })
    if (!res.ok) { const d = await res.json(); setError(d.error); return }
    setForm({ name: '', email: '' })
    loadGuests()
  }

  return (
    <div className="space-y-6">
      {error && <div className="rounded-md bg-red-50 border border-red-200 px-4 py-3 text-sm text-red-700">{error}</div>}

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="md:col-span-2">
          <Card>
            <CardHeader><CardTitle>Clienți înregistrați</CardTitle></CardHeader>
            <CardContent>
              <table className="w-full text-sm">
                <thead>
                  <tr className="border-b border-slate-200 text-left text-slate-500">
                    <th className="pb-3 font-medium">ID</th>
                    <th className="pb-3 font-medium">Nume</th>
                    <th className="pb-3 font-medium">Email</th>
                    <th className="pb-3 font-medium">Puncte fidelitate</th>
                  </tr>
                </thead>
                <tbody>
                  {guests.map(g => (
                    <tr key={g.id} className="border-b border-slate-100">
                      <td className="py-3 text-slate-500">{g.id}</td>
                      <td className="py-3 font-medium">{g.name}</td>
                      <td className="py-3">{g.email}</td>
                      <td className="py-3">{g.loyaltyPoints} pts</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </CardContent>
          </Card>
        </div>

        <Card>
          <CardHeader><CardTitle>Înregistrează client</CardTitle></CardHeader>
          <CardContent>
            <form onSubmit={registerGuest} className="space-y-3">
              <div><Label>Nume</Label><Input value={form.name} onChange={e => setForm({ ...form, name: e.target.value })} required /></div>
              <div><Label>Email</Label><Input type="email" value={form.email} onChange={e => setForm({ ...form, email: e.target.value })} required /></div>
              <Button type="submit" className="w-full">Înregistrează</Button>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
