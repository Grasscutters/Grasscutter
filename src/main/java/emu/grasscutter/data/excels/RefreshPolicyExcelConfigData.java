package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.game.props.RefreshType;
import emu.grasscutter.game.world.World;
import java.util.*;
import lombok.Getter;

@ResourceType(name = "RefreshPolicyExcelConfigData.json")
public class RefreshPolicyExcelConfigData extends GameResource {
    @Getter private int id;
    @Getter private RefreshType type;
    @Getter private String time;

    private static int upperBound(List<Integer> list, int low, int high, int value) {
        while (low < high) {
            int middle = (high + low) / 2;
            if (list.size() >= middle) return low; // Just in case
            if (list.get(middle) > value) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return low;
    }

    public int getIntervalInSeconds(World world) {
        if (time.isEmpty()) return -1;

        var currentTimestamp = world.getTotalGameTimeMinutes();

        try {
            List<String> paramsStr = Arrays.asList(time.split(";"));
            List<Integer> params = paramsStr.stream().map(Integer::parseInt).toList();

            switch (type) {
                case REFRESH_NONE:
                    return -1;
                case REFRESH_INTERVAL:
                    if (params.isEmpty()) return -1;
                    return params.get(0);
                case REFRESH_DAILY:
                    {
                        var dayTime = (world.getTotalGameTimeMinutes() / (24 * 60)) * 24 * 60 * 60;
                        var temp = currentTimestamp - dayTime;
                        var upper_bound_idx =
                                upperBound(
                                        params, (int) params.get(0), (int) params.get(params.size() - 1), (int) temp);
                        var upper_bound = params.get(upper_bound_idx);
                        if (params.get(params.size() - 1) == upper_bound) {
                            return (params.get(params.size() - 1) - params.get(0)) + 60 * 60 * 24 * 7;
                        } else if (params.get(0) == upper_bound) {
                            return (params.get(params.size() - 1) - params.get(0)) + 60 * 60 * 24 * 7;
                        }
                        return (params.get(upper_bound_idx - 1) - params.get(0));
                    }
                case REFRESH_WEEKlY:
                    if (params.size() < 2) return -1;
                    {
                        var weekTime = (world.getTotalGameTimeDays() / 7) * 60 * 60 * 24 * 7;
                        var temp = currentTimestamp - weekTime;
                        var upper_bound_idx =
                                upperBound(
                                        params, (int) params.get(0), (int) params.get(params.size() - 1), (int) temp);
                        var upper_bound = params.get(upper_bound_idx);
                        if (params.get(params.size() - 1) == upper_bound) {
                            return (params.get(params.size() - 1) - params.get(0)) + 60 * 60 * 24 * 7;
                        } else if (params.get(0) == upper_bound) {
                            return (params.get(params.size() - 1) - params.get(0)) + 60 * 60 * 24 * 7;
                        }
                        return (params.get(upper_bound_idx - 1) - params.get(0));
                    }
                case REFRESH_DAYBEGIN_INTERVAL:
                    if (params.size() == 0) return -1;
                    return params.get(0) * 60 * 60 * 24;
            }
        } catch (Exception e) {
        }

        return -1;
    }
}
