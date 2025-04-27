package emu.grasscutter.game.combine;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CombineBonusData {
    private int avatarId;
    private int combineType;
    private BonusType bonusType;
    private List<Double> paramVec;

    public enum BonusType {
        COMBINE_BONUS_DOUBLE,
        COMBINE_BONUS_REFUND,
        COMBINE_BONUS_REFUND_RANDOM,
    }
}
