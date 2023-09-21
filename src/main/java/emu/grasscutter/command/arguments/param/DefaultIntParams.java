package emu.grasscutter.command.arguments.param;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "of")
public class DefaultIntParams {
    @Builder.Default
    public int lvl = -1;
    @Builder.Default
    public int amount = -1;
    @Builder.Default
    public int refine = -1;
    @Builder.Default
    public int rank = -1;
    @Builder.Default
    public int constellation = -1;
    @Builder.Default
    public int skillLevel = -1;
    @Builder.Default
    public int state = -1;
    @Builder.Default
    public int block = -1;
    @Builder.Default
    public int group = -1;
    @Builder.Default
    public int config = -1;
    @Builder.Default
    public int hp = -1;
    @Builder.Default
    public int maxHP = -1;
    @Builder.Default
    public int atk = -1;
    @Builder.Default
    public int def = -1;
    @Builder.Default
    public int ai = -1;
    @Builder.Default
    public int scene = -1;
    @Builder.Default
    public int suite = -1;

    public DefaultIntParams copy() {
        return DefaultIntParams.of()
            .lvl(this.lvl)
            .amount(this.amount)
            .refine(this.refine)
            .rank(this.rank)
            .constellation(this.constellation)
            .skillLevel(this.skillLevel)
            .state(this.state)
            .block(this.block)
            .group(this.group)
            .config(this.config)
            .hp(this.hp)
            .maxHP(this.maxHP)
            .atk(this.atk)
            .def(this.def)
            .ai(this.ai)
            .scene(this.scene)
            .suite(this.suite)
            .build();
    }
}
