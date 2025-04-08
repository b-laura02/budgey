package hu.blaura.budgey.modules.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionCategory {
    // BEVÉTELEK
    SALE("Termékértékesítés"),
    SERVICE("Szolgáltatás"),
    RENTAL("Bérbeadás"),
    LEASE("Tartós bérlet"),
    SUBSIDY("Támogatás"),
    INVESTMENT_INCOME("Befektetési bevétel"),

    // KIADÁSOK – Működési
    UTILITIES("Rezsi (víz, áram, internet)"),
    RENT("Irodabérlés"),
    SALARY("Bérek és járulékok"),
    OFFICE_SUPPLIES("Irodaszerek"),
    TRANSPORT("Közlekedés, üzemanyag"),
    TELECOMMUNICATION("Telekommunikáció"),
    INSURANCE("Biztosítás"),

    // KIADÁSOK – Üzleti költségek
    PURCHASE("Beszerzés"),
    MARKETING("Marketing"),
    SUBSCRIPTION("Szoftver előfizetés"),
    MAINTENANCE("Karbantartás"),
    PROFESSIONAL_FEES("Tanácsadói díjak"),

    // ADMINISZTRATÍV
    TAX("Adó"),
    SOCIAL_SECURITY("Társadalombiztosítás"),
    BANK_FEES("Bankköltség"),
    TRAINING("Továbbképzés"),

    // EGYÉB
    OTHER_INCOME("Egyéb bevétel"),
    OTHER_EXPENSE("Egyéb kiadás");

    private final String displayName;
}
