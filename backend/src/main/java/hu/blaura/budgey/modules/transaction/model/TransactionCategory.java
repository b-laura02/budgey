package hu.blaura.budgey.modules.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionCategory {
    // BEVÉTELEK
    SALE("Termékértékesítés", false),
    SERVICE("Szolgáltatás", false),
    RENTAL("Bérbeadás", false),
    LEASE("Tartós bérlet", false),
    SUBSIDY("Támogatás", false),
    INVESTMENT_INCOME("Befektetési bevétel", false),

    // KIADÁSOK – Működési
    UTILITIES("Rezsi (víz, áram, internet)", true),
    RENT("Irodabérlés", true),
    SALARY("Bérek és járulékok", true),
    OFFICE_SUPPLIES("Irodaszerek", true),
    TRANSPORT("Közlekedés, üzemanyag", true),
    TELECOMMUNICATION("Telekommunikáció", true),
    INSURANCE("Biztosítás", true),

    // KIADÁSOK – Üzleti költségek
    PURCHASE("Beszerzés", true),
    MARKETING("Marketing", true),
    SUBSCRIPTION("Szoftver előfizetés", true),
    MAINTENANCE("Karbantartás", true),
    PROFESSIONAL_FEES("Tanácsadói díjak", true),

    // ADMINISZTRATÍV
    TAX("Adó", true),
    SOCIAL_SECURITY("Társadalombiztosítás", true),
    BANK_FEES("Bankköltség", true),
    TRAINING("Továbbképzés", true),

    // EGYÉB
    OTHER_INCOME("Egyéb bevétel", false),
    OTHER_EXPENSE("Egyéb kiadás", true);

    private final String displayName;
    private final boolean isExpense;
}
