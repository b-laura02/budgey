import { BalanceGroup } from "../enums/balance-group.enum";
import { BalanceMainGroup } from "../enums/balance-main-group.enum";

export interface BalanceGroupDto {
  balanceMainGroup: BalanceMainGroup;
  balanceGroup: BalanceGroup;
}