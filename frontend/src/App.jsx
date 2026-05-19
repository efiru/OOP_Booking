import { useState } from 'react'
import HotelsPage from './components/HotelsPage'
import GuestsPage from './components/GuestsPage'
import EmployeesPage from './components/EmployeesPage'
import BookingsPage from './components/BookingsPage'
import PaymentsPage from './components/PaymentsPage'
import ReviewsPage from './components/ReviewsPage'

const TABS = [
  { id: 'hotels', label: 'Hoteluri' },
  { id: 'guests', label: 'Clienți' },
  { id: 'bookings', label: 'Rezervări' },
  { id: 'payments', label: 'Plăți' },
  { id: 'reviews', label: 'Review-uri' },
  { id: 'employees', label: 'Angajați' },
]

export default function App() {
  const [activeTab, setActiveTab] = useState('hotels')

  return (
    <div className="min-h-screen bg-slate-50">
      <header className="bg-slate-900 text-white px-6 py-4 shadow">
        <h1 className="text-xl font-semibold tracking-tight">Hotel Booking</h1>
      </header>

      <nav className="bg-white border-b border-slate-200 px-6">
        <div className="flex gap-1">
          {TABS.map(tab => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id)}
              className={`px-4 py-3 text-sm font-medium border-b-2 transition-colors ${
                activeTab === tab.id
                  ? 'border-slate-900 text-slate-900'
                  : 'border-transparent text-slate-500 hover:text-slate-700'
              }`}
            >
              {tab.label}
            </button>
          ))}
        </div>
      </nav>

      <main className="p-6 max-w-7xl mx-auto">
        {activeTab === 'hotels' && <HotelsPage />}
        {activeTab === 'guests' && <GuestsPage />}
        {activeTab === 'bookings' && <BookingsPage />}
        {activeTab === 'payments' && <PaymentsPage />}
        {activeTab === 'reviews' && <ReviewsPage />}
        {activeTab === 'employees' && <EmployeesPage />}
      </main>
    </div>
  )
}
