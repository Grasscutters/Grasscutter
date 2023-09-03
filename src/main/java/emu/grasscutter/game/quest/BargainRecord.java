package emu.grasscutter.game.quest;

import dev.morphia.annotations.Entity;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.BargainData;
import emu.grasscutter.net.proto.BargainResultTypeOuterClass.BargainResultType;
import emu.grasscutter.net.proto.BargainSnapshotOuterClass.BargainSnapshot;
import emu.grasscutter.utils.Utils;
import lombok.*;

@Data
@Entity
@Builder
public final class BargainRecord {
    /**
     * Provides an instance of a bargain record. Uses information from game resources.
     *
     * @param bargainId The ID of the bargain.
     * @return An instance of a bargain record.
     */
    public static BargainRecord resolve(int bargainId) {
        var bargainData = GameData.getBargainDataMap().get(bargainId);
        if (bargainData == null)
            throw new RuntimeException("No bargain data found for " + bargainId + ".");

        return BargainRecord.builder().bargainId(bargainId).build().determineBase(bargainData);
    }

    private int bargainId;
    private int lowestPrice;
    private int expectedPrice;

    private int currentMood;

    private boolean finished;
    private BargainResultType result;

    /** Determines the price of the bargain. */
    public BargainRecord determineBase(BargainData data) {
        // Set the expected price.
        var price = data.getExpectedValue();
        this.setExpectedPrice(Utils.randomRange(price.get(0), price.get(1)));
        // Set the lowest price.
        this.setLowestPrice(price.get(0));

        // Set the base mood.
        var mood = data.getRandomMood();
        this.setCurrentMood(Utils.randomRange(mood.get(0), mood.get(1)));

        return this;
    }

    /**
     * Computes an offer's validity.
     *
     * @param offer The offer to compute.
     * @return The result of the offer.
     */
    public BargainResultType applyOffer(int offer) {
        if (offer < this.getLowestPrice()) {
            // Decrease the mood.
            this.currentMood -= Utils.randomRange(1, 3);
            // Return a failure.
            return this.result = BargainResultType.BARGAIN_SINGLE_FAIL;
        }

        if (offer > this.getExpectedPrice()) {
            // Complete the bargain.
            this.setFinished(true);
            // Return a success.
            return this.result = BargainResultType.BARGAIN_COMPLETE_SUCC;
        }

        // Compare the offer against the mood and expected price.
        // The mood is out of 100; 1 mood should decrease the price by 100.
        var moodAdjustment = (int) Math.floor(this.getCurrentMood() / 100.0);
        var expectedPrice = this.getExpectedPrice() - moodAdjustment;
        if (offer < expectedPrice) {
            // Decrease the mood.
            this.currentMood -= Utils.randomRange(1, 3);
            // Return a failure.
            return this.result = BargainResultType.BARGAIN_SINGLE_FAIL;
        } else {
            // Complete the bargain.
            this.setFinished(true);
            // Return a success.
            return this.result = BargainResultType.BARGAIN_COMPLETE_SUCC;
        }
    }

    /**
     * @return A snapshot of this bargain record.
     */
    public BargainSnapshot toSnapshot() {
        return BargainSnapshot.newBuilder()
                .setBargainId(this.getBargainId())
                .setCurMood(this.getCurrentMood())
                .setBALOPACHCDB(this.getExpectedPrice())
                .setIOCNPJJNHLD(this.getLowestPrice())
                .build();
    }
}
