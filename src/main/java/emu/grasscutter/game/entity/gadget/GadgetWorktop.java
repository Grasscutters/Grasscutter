package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.gadget.worktop.WorktopWorktopOptionHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.SelectWorktopOptionReqOuterClass.SelectWorktopOptionReq;
import emu.grasscutter.net.proto.WorktopInfoOuterClass.WorktopInfo;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.*;

public final class GadgetWorktop extends GadgetContent {
    private Set<Integer> worktopOptions;
    private WorktopWorktopOptionHandler handler;

    public GadgetWorktop(EntityGadget gadget) {
        super(gadget);
    }

    public Set<Integer> getWorktopOptions() {
        if (this.worktopOptions == null) {
            this.worktopOptions = new HashSet<>();
        }

        return worktopOptions;
    }

    public void addWorktopOptions(int[] options) {
        if (this.worktopOptions == null) {
            this.worktopOptions = new IntOpenHashSet();
        }
        Arrays.stream(options).forEach(this.worktopOptions::add);
    }

    public void removeWorktopOption(int option) {
        if (this.worktopOptions == null) {
            return;
        }
        this.worktopOptions.remove(option);
    }

    public boolean onInteract(Player player, GadgetInteractReq req) {
        return false;
    }

    public void onBuildProto(SceneGadgetInfo.Builder gadgetInfo) {
        var options = this.getWorktopOptions();
        if (options == null) return;

        try {
            var worktop = WorktopInfo.newBuilder().addAllOptionList(options).build();
            gadgetInfo.setWorktop(worktop);
        } catch (NullPointerException ignored) {
            // "this.wrapped" is null.
            gadgetInfo.setWorktop(
                    WorktopInfo.newBuilder().addAllOptionList(Collections.emptyList()).build());
            Grasscutter.getLogger().warn("GadgetWorktop.onBuildProto: this.wrapped is null");
        }
    }

    public void setOnSelectWorktopOptionEvent(WorktopWorktopOptionHandler handler) {
        this.handler = handler;
    }

    public boolean onSelectWorktopOption(SelectWorktopOptionReq req) {
        return this.handler != null && this.handler.onSelectWorktopOption(this, req.getOptionId());
    }
}
