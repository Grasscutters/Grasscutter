package emu.grasscutter.game.entity.gadget;

import java.util.Arrays;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.gadget.worktop.WorktopWorktopOptionHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.SelectWorktopOptionReqOuterClass;
import emu.grasscutter.net.proto.WorktopInfoOuterClass.WorktopInfo;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

public class GadgetWorktop extends GadgetContent {
	private IntSet worktopOptions;

    private WorktopWorktopOptionHandler handler;

	public GadgetWorktop(EntityGadget gadget) {
		super(gadget);
	}

    public boolean onSelectWorktopOption(Player player, SelectWorktopOptionReqOuterClass.SelectWorktopOptionReq req) {
        this.handler.onSelectWorktopOption(player,this,req.getOptionId());
	    return false;
    }

    public IntSet getWorktopOptions() {
		return worktopOptions;
	}

	public void addWorktopOptions(int[] options) {
		if (this.worktopOptions == null) {
			this.worktopOptions = new IntOpenHashSet();
		}
		Arrays.stream(options).forEach(this.worktopOptions::add);
	}
    public void setOnSelectWorktopOptionEvent(WorktopWorktopOptionHandler handler) {
	    this.handler = handler;
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
		if (this.worktopOptions == null) {
			return;
		}

		WorktopInfo worktop = WorktopInfo.newBuilder()
				.addAllOptionList(this.getWorktopOptions())
				.build();

		gadgetInfo.setWorktop(worktop);
	}
}
