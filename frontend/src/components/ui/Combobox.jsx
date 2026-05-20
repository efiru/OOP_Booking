import { useState, useEffect, useRef } from 'react'

export default function Combobox({ options, value, onChange, placeholder, disabled }) {
  const [query, setQuery] = useState('')
  const [open, setOpen] = useState(false)
  const ref = useRef(null)

  const selected = options.find(o => String(o.id) === String(value))

  useEffect(() => {
    setQuery(selected ? selected.label : '')
  }, [value, options.length])

  useEffect(() => {
    function onClickOutside(e) {
      if (ref.current && !ref.current.contains(e.target)) setOpen(false)
    }
    document.addEventListener('mousedown', onClickOutside)
    return () => document.removeEventListener('mousedown', onClickOutside)
  }, [])

  const filtered = query
    ? options.filter(o => o.label.toLowerCase().includes(query.toLowerCase()))
    : options

  function handleSelect(option) {
    onChange(option.id)
    setQuery(option.label)
    setOpen(false)
  }

  function handleChange(e) {
    setQuery(e.target.value)
    setOpen(true)
    if (!e.target.value) onChange('')
  }

  function handleBlur() {
    setTimeout(() => {
      if (!selected) { setQuery(''); onChange('') }
      setOpen(false)
    }, 150)
  }

  return (
    <div ref={ref} className="relative">
      <input
        type="text"
        value={query}
        onChange={handleChange}
        onFocus={() => setOpen(true)}
        onBlur={handleBlur}
        placeholder={placeholder || 'Caută...'}
        disabled={disabled}
        className="flex h-9 w-full rounded-md border border-slate-300 bg-white px-3 py-1 text-sm shadow-sm placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-slate-900 disabled:opacity-50 disabled:cursor-not-allowed"
      />
      {open && filtered.length > 0 && (
        <ul className="absolute z-20 mt-1 max-h-52 w-full overflow-auto rounded-md border border-slate-200 bg-white shadow-lg text-sm">
          {filtered.map(o => (
            <li
              key={o.id}
              onMouseDown={() => handleSelect(o)}
              className={`cursor-pointer px-3 py-2 hover:bg-slate-100 ${o.disabled ? 'opacity-40 cursor-not-allowed' : ''}`}
            >
              {o.label}
            </li>
          ))}
        </ul>
      )}
      {open && filtered.length === 0 && (
        <div className="absolute z-20 mt-1 w-full rounded-md border border-slate-200 bg-white px-3 py-2 text-sm text-slate-400 shadow-lg">
          Niciun rezultat
        </div>
      )}
    </div>
  )
}
