package hu.blaura.budgey.modules.classification.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum BalanceGroup {
    // C, E, G
    UNKNOWN(EnumSet.of(BalanceMainGroup.C, BalanceMainGroup.E, BalanceMainGroup.G)),
    // A
    IMMATERIALIS_JAVAK(EnumSet.of(BalanceMainGroup.A)),
    TARGYI_ESZKOZOK(EnumSet.of(BalanceMainGroup.A)),
    BEFEKTETETT_PENZUGYI_ESZKOZOK(EnumSet.of(BalanceMainGroup.A)),
    // B
    KESZLETEK(EnumSet.of(BalanceMainGroup.B)),
    KOVETELESEK(EnumSet.of(BalanceMainGroup.B)),
    ERTEKPAPIROK(EnumSet.of(BalanceMainGroup.B)),
    PENZESZKOZOK(EnumSet.of(BalanceMainGroup.B)),
    // D
    JEGYZETT_TOKE(EnumSet.of(BalanceMainGroup.D)),
    JEGYZET_TOKE_BE_NEM_FIZ(EnumSet.of(BalanceMainGroup.D)),
    TOKETARTALEK(EnumSet.of(BalanceMainGroup.D)),
    EREDMENYTARTALEK(EnumSet.of(BalanceMainGroup.D)),
    LEKOTOTT_TARTALEK(EnumSet.of(BalanceMainGroup.D)),
    ERTEKELESI_TARTALEK(EnumSet.of(BalanceMainGroup.D)),
    ADOZOTT_EREDMENY(EnumSet.of(BalanceMainGroup.D)),
    // F
    HATRASOROLT(EnumSet.of(BalanceMainGroup.F)),
    HOSSZU_LEJARATU(EnumSet.of(BalanceMainGroup.F)),
    ROVID_LEJARATU(EnumSet.of(BalanceMainGroup.F));

    private final Set<BalanceMainGroup> balanceMainGroup;

    public static List<BalanceGroup> getByMainGroup(BalanceMainGroup balanceMainGroup) {
        return Stream.of(values())
                .filter(balanceGroup -> balanceGroup.getBalanceMainGroup().contains(balanceMainGroup))
                .collect(Collectors.toList());
    }
}
