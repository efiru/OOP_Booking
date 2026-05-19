import { cn } from '../../lib/utils'

export function Card({ children, className }) {
  return <div className={cn('rounded-xl border border-slate-200 bg-white shadow-sm', className)}>{children}</div>
}

export function CardHeader({ children, className }) {
  return <div className={cn('flex flex-col space-y-1 p-6 pb-4', className)}>{children}</div>
}

export function CardTitle({ children, className }) {
  return <h3 className={cn('text-lg font-semibold text-slate-900', className)}>{children}</h3>
}

export function CardContent({ children, className }) {
  return <div className={cn('p-6 pt-0', className)}>{children}</div>
}
