import { Pipe, PipeTransform } from '@angular/core';
import { TransactionCategory } from '../models/enums/transaction-category.enum';

@Pipe({
  name: 'transactionCategory',
  standalone: true,
})
export class TransactionCategoryPipe implements PipeTransform {
  private readonly categoryTranslations: Record<TransactionCategory, string> = {
    // Bevétel
    [TransactionCategory.SALE]: 'Termékértékesítés',
    [TransactionCategory.SERVICE]: 'Szolgáltatás',
    [TransactionCategory.RENTAL]: 'Bérbeadás',
    [TransactionCategory.LEASE]: 'Tartós bérlet',
    [TransactionCategory.SUBSIDY]: 'Támogatás',
    [TransactionCategory.INVESTMENT_INCOME]: 'Befektetési bevétel',
    [TransactionCategory.OTHER_INCOME]: 'Egyéb bevétel',

    // Kiadás – működési
    [TransactionCategory.UTILITIES]: 'Rezsi (víz, áram, internet)',
    [TransactionCategory.RENT]: 'Irodabérlés',
    [TransactionCategory.SALARY]: 'Bérek és járulékok',
    [TransactionCategory.OFFICE_SUPPLIES]: 'Irodaszerek',
    [TransactionCategory.TRANSPORT]: 'Közlekedés, üzemanyag',
    [TransactionCategory.TELECOMMUNICATION]: 'Telekommunikáció',
    [TransactionCategory.INSURANCE]: 'Biztosítás',

    // Kiadás – üzleti
    [TransactionCategory.PURCHASE]: 'Beszerzés',
    [TransactionCategory.MARKETING]: 'Marketing',
    [TransactionCategory.SUBSCRIPTION]: 'Szoftver előfizetés',
    [TransactionCategory.MAINTENANCE]: 'Karbantartás',
    [TransactionCategory.PROFESSIONAL_FEES]: 'Tanácsadói díjak',

    // Adminisztratív
    [TransactionCategory.TAX]: 'Adó',
    [TransactionCategory.SOCIAL_SECURITY]: 'Társadalombiztosítás',
    [TransactionCategory.BANK_FEES]: 'Bankköltség',
    [TransactionCategory.TRAINING]: 'Továbbképzés',

    // Egyéb
    [TransactionCategory.OTHER_EXPENSE]: 'Egyéb kiadás',
  };

  transform(category: TransactionCategory): string {
    return this.categoryTranslations[category] || category;
  }
}
