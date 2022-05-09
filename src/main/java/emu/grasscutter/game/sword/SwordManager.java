package emu.grasscutter.game.sword;

import org.jetbrains.annotations.Nullable;

public class SwordManager {
    private static final SwordManager sInstance = new SwordManager();
    public static SwordManager getInstance()
    {
        return sInstance;
    }

    public void inflateSwordData()
    {
        //TODO: Add inflate sword data from local file...
    }

    public int getSwordType(Sword sword)
    {
        return -1;
    }

    /**
     * @param sword Sword that use can use.
     * @return Can sword can delete and how much it can be sold.
     * -1 : Can not delete or destroy, 0 no mora after delete.
     */
    public int getSwordCanDestroyOrMora(Sword sword)
    {
        return -1;
    }

    @Nullable
    public String getSwordCategory(Sword sword)
    {
        return null;
    }

    public int getSwordEffect(Sword sword)
    {
        return 0;
    }
}
