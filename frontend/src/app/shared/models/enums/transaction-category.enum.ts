export enum TransactionCategory {
  SALE = 'SALE',
  SERVICE = 'SERVICE',
  RENTAL = 'RENTAL',
  LEASE = 'LEASE',
  SUBSIDY = 'SUBSIDY',
  INVESTMENT_INCOME = 'INVESTMENT_INCOME',

  UTILITIES = 'UTILITIES',
  RENT = 'RENT',
  SALARY = 'SALARY',
  OFFICE_SUPPLIES = 'OFFICE_SUPPLIES',
  TRANSPORT = 'TRANSPORT',
  TELECOMMUNICATION = 'TELECOMMUNICATION',
  INSURANCE = 'INSURANCE',

  PURCHASE = 'PURCHASE',
  MARKETING = 'MARKETING',
  SUBSCRIPTION = 'SUBSCRIPTION',
  MAINTENANCE = 'MAINTENANCE',
  PROFESSIONAL_FEES = 'PROFESSIONAL_FEES',

  TAX = 'TAX',
  SOCIAL_SECURITY = 'SOCIAL_SECURITY',
  BANK_FEES = 'BANK_FEES',
  TRAINING = 'TRAINING',

  OTHER_INCOME = 'OTHER_INCOME',
  OTHER_EXPENSE = 'OTHER_EXPENSE'
}

export const IncomeCategory: TransactionCategory[] = [
  TransactionCategory.SALE,
  TransactionCategory.SERVICE,
  TransactionCategory.RENTAL,
  TransactionCategory.LEASE,
  TransactionCategory.SUBSIDY,
  TransactionCategory.INVESTMENT_INCOME,
  TransactionCategory.OTHER_INCOME
];

export const ExpenseCategory: TransactionCategory[] = [
  TransactionCategory.UTILITIES,
  TransactionCategory.RENT,
  TransactionCategory.SALARY,
  TransactionCategory.OFFICE_SUPPLIES,
  TransactionCategory.TRANSPORT,
  TransactionCategory.TELECOMMUNICATION,
  TransactionCategory.INSURANCE ,
  TransactionCategory.PURCHASE,
  TransactionCategory.MARKETING,
  TransactionCategory.SUBSCRIPTION,
  TransactionCategory.MAINTENANCE,
  TransactionCategory.PROFESSIONAL_FEES,
  TransactionCategory.TAX,
  TransactionCategory.SOCIAL_SECURITY,
  TransactionCategory.BANK_FEES,
  TransactionCategory.TRAINING,
  TransactionCategory.OTHER_EXPENSE,
];