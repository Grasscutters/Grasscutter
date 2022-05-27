package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import java.util.List;
import java.util.stream.Collectors;

@ResourceType(name = "CombineExcelConfigData.json")
public class CombineData extends GameResource {

    private int CombineId;

    private int PlayerLevel;

    private boolean IsDefaultShow;

    private int CombineType;

    private int SubCombineType;

    private int ResultItemId;

    private int ResultItemCount;

    private int ScoinCost;

    private List<CombineItemPair> RandomItems;

    private List<CombineItemPair> MaterialItems;

    private long EffectDescTextMapHash;

    private String RecipeType;

    @Override
    public int getId() {
        return this.CombineId;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        // clean data
        RandomItems = RandomItems.stream().filter(item -> item.Id > 0).collect(Collectors.toList());
        MaterialItems = MaterialItems.stream().filter(item -> item.Id > 0).collect(Collectors.toList());
    }

    public static class CombineItemPair {

        private int Id;

        private int Count;

        public CombineItemPair(int id, int count) {
            Id = id;
            Count = count;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int count) {
            Count = count;
        }
    }

    public int getCombineId() {
        return CombineId;
    }

    public void setCombineId(int combineId) {
        CombineId = combineId;
    }

    public int getPlayerLevel() {
        return PlayerLevel;
    }

    public void setPlayerLevel(int playerLevel) {
        PlayerLevel = playerLevel;
    }

    public boolean isDefaultShow() {
        return IsDefaultShow;
    }

    public void setDefaultShow(boolean defaultShow) {
        IsDefaultShow = defaultShow;
    }

    public int getCombineType() {
        return CombineType;
    }

    public void setCombineType(int combineType) {
        CombineType = combineType;
    }

    public int getSubCombineType() {
        return SubCombineType;
    }

    public void setSubCombineType(int subCombineType) {
        SubCombineType = subCombineType;
    }

    public int getResultItemId() {
        return ResultItemId;
    }

    public void setResultItemId(int resultItemId) {
        ResultItemId = resultItemId;
    }

    public int getResultItemCount() {
        return ResultItemCount;
    }

    public void setResultItemCount(int resultItemCount) {
        ResultItemCount = resultItemCount;
    }

    public int getScoinCost() {
        return ScoinCost;
    }

    public void setScoinCost(int scoinCost) {
        ScoinCost = scoinCost;
    }

    public List<CombineItemPair> getRandomItems() {
        return RandomItems;
    }

    public void setRandomItems(List<CombineItemPair> randomItems) {
        RandomItems = randomItems;
    }

    public List<CombineItemPair> getMaterialItems() {
        return MaterialItems;
    }

    public void setMaterialItems(List<CombineItemPair> materialItems) {
        MaterialItems = materialItems;
    }

    public long getEffectDescTextMapHash() {
        return EffectDescTextMapHash;
    }

    public void setEffectDescTextMapHash(long effectDescTextMapHash) {
        EffectDescTextMapHash = effectDescTextMapHash;
    }

    public String getRecipeType() {
        return RecipeType;
    }

    public void setRecipeType(String recipeType) {
        RecipeType = recipeType;
    }
}

