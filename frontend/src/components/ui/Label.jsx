import { cn } from '../../lib/utils'

export function Label({ children, className, htmlFor }) {
  return (
    <label htmlFor={htmlFor} className={cn('text-sm font-medium text-slate-700', className)}>
      {children}
    </label>
  )
}
