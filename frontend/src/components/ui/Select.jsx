export function Select({ value, onChange, required, disabled, children }) {
  return (
    <select
      value={value}
      onChange={onChange}
      required={required}
      disabled={disabled}
      className="flex h-9 w-full rounded-md border border-slate-300 bg-white px-3 py-1 text-sm shadow-sm focus:outline-none focus:ring-2 focus:ring-slate-900 disabled:opacity-50 disabled:cursor-not-allowed"
    >
      {children}
    </select>
  )
}
