export interface TransactionDto {
  title: string,
  amount: number,
  date: string,
  category: string
}

export interface Transaction extends TransactionDto {
  id: number,
}