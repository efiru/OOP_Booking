import { useEffect, useState } from 'react'
import { Card, CardContent, CardHeader, CardTitle } from './ui/Card'
import { Button } from './ui/Button'
import { Input } from './ui/Input'
import { Label } from './ui/Label'

export default function EmployeesPage() {
  const [employees, setEmployees] = useState([])
  const [form, setForm] = useState({ name: '', email: '', position: '' })
  const [error, setError] = useState('')

  useEffect(() => { loadEmployees() }, [])

  async function loadEmployees() {
    const res = await fetch('/api/employees')
    setEmployees(await res.json())
  }

  async function registerEmployee(e) {
    e.preventDefault()
    setError('')
    const res = await fetch('/api/employees', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(form)
    })
    if (!res.ok) { const d = await res.json(); setError(d.error); return }
    setForm({ name: '', email: '', position: '' })
    loadEmployees()
  }

  return (
    <div className="space-y-6">
      {error && <div className="rounded-md bg-red-50 border border-red-200 px-4 py-3 text-sm text-red-700">{error}</div>}

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="md:col-span-2">
          <Card>
            <CardHeader><CardTitle>Angajați</CardTitle></CardHeader>
            <CardContent>
              <table className="w-full text-sm">
                <thead>
                  <tr className="border-b border-slate-200 text-left text-slate-500">
                    <th className="pb-3 font-medium">ID</th>
                    <th className="pb-3 font-medium">Nume</th>
                    <th className="pb-3 font-medium">Email</th>
                    <th className="pb-3 font-medium">Funcție</th>
                  </tr>
                </thead>
                <tbody>
                  {employees.map(e => (
                    <tr key={e.id} className="border-b border-slate-100">
                      <td className="py-3 text-slate-500">{e.id}</td>
                      <td className="py-3 font-medium">{e.name}</td>
                      <td className="py-3">{e.email}</td>
                      <td className="py-3">{e.position}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </CardContent>
          </Card>
        </div>

        <Card>
          <CardHeader><CardTitle>Adaugă angajat</CardTitle></CardHeader>
          <CardContent>
            <form onSubmit={registerEmployee} className="space-y-3">
              <div><Label>Nume</Label><Input value={form.name} onChange={e => setForm({ ...form, name: e.target.value })} required /></div>
              <div><Label>Email</Label><Input type="email" value={form.email} onChange={e => setForm({ ...form, email: e.target.value })} required /></div>
              <div><Label>Funcție</Label><Input value={form.position} onChange={e => setForm({ ...form, position: e.target.value })} required /></div>
              <Button type="submit" className="w-full">Adaugă</Button>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
