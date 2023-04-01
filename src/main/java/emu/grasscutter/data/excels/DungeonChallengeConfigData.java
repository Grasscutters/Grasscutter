package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import emu.grasscutter.game.dungeons.challenge.enums.ChallengeType;
import lombok.Getter;

import java.util.HashSet;

@Getter
@ResourceType(name = "DungeonChallengeConfigData.json")
public class DungeonChallengeConfigData extends GameResource {
	private int id;
    private ChallengeType challengeType;
    private boolean noSuccessHint;
    private boolean noFailHint;
    private boolean isBlockTopTimer;
    private int subChallengeFadeOutDelayTime;
    private int activitySkillId;
    private HashSet<String> teamAbilityGroupList;

    private SubChallengeFadeOutType subChallengeFadeOutRule;
    private SubChallengeBannerType subChallengeBannerRule;
    private InterruptButtonType interruptButtonType;

    @SerializedName(value = "subChallengeSortType", alternate={"PNCLDNBHKDJ"})
    private SubChallengeSortType subChallengeSortType;
    @SerializedName(value = "animationOnSubStart", alternate={"DNFAFNMMMDP"})
    private AllowAnimationType animationOnSubStart;
    @SerializedName(value = "animationOnSubSuccess", alternate={"ENONHOGJDDN"})
    private AllowAnimationType animationOnSubSuccess;
    @SerializedName(value = "animationOnSubFail", alternate={"NJBJIKAIENN"})
    private AllowAnimationType animationOnSubFail;

    public int getId() {
        return id;
    }

    public enum InterruptButtonType{
        INTERRUPT_BUTTON_TYPE_NONE,
        INTERRUPT_BUTTON_TYPE_HOST,
        INTERRUPT_BUTTON_TYPE_ALL
    }

    public enum SubChallengeFadeOutType{
        SUBCHALLENGE_FADEOUT_TYPE_NONE,
        SUBCHALLENGE_FADEOUT_TYPE_SUCCESS,
        SUBCHALLENGE_FADEOUT_TYPE_FAIL,
        SUBCHALLENGE_FADEOUT_TYPE_FINISH
    }

    public enum SubChallengeBannerType{
        SUBCHALLENGE_BANNER_TYPE_NONE,
        SUBCHALLENGE_BANNER_TYPE_SUCCESS,
        SUBCHALLENGE_BANNER_TYPE_FAIL,
        SUBCHALLENGE_BANNER_TYPE_HIDE_FINAL,
        SUBCHALLENGE_BANNER_TYPE_SHOW_FINAL
    }
    public enum SubChallengeSortType{
        SUB_CHALLENGE_SORT_TYPE_DEFAULT,
        SUB_CHALLENGE_SORT_TYPE_CHALLENGEINDEX
    }

    public enum AllowAnimationType{
        SUB_CHALLENGE_ANIM_TYPE_DEFAULT,
        SUB_CHALLENGE_ANIM_TYPE_FORBID,
        SUB_CHALLENGE_ANIM_TYPE_SUCCESS,
        SUB_CHALLENGE_ANIM_TYPE_FAIL
    }
}
