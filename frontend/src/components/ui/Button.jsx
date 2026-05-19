import { cn } from '../../lib/utils'

export function Button({ children, onClick, type = 'button', variant = 'default', className, disabled }) {
  const base = 'inline-flex items-center justify-center rounded-md px-4 py-2 text-sm font-medium transition-colors focus:outline-none disabled:opacity-50 disabled:cursor-not-allowed'
  const variants = {
    default: 'bg-slate-900 text-white hover:bg-slate-700',
    outline: 'border border-slate-300 bg-white text-slate-900 hover:bg-slate-50',
    destructive: 'bg-red-600 text-white hover:bg-red-700',
    ghost: 'text-slate-700 hover:bg-slate-100',
  }
  return (
    <button type={type} onClick={onClick} disabled={disabled} className={cn(base, variants[variant], className)}>
      {children}
    </button>
  )
}
