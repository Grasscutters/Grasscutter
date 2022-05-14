package emu.grasscutter.scripts;

import ch.ethz.globis.phtree.PhTree;
import emu.grasscutter.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SceneIndexManager {

    public static <T> void buildIndex(PhTree<T> tree, List<T> elements, Function<T, long[]> extractor){
        elements.forEach(e -> tree.put(extractor.apply(e), e));
    }
    public static <T> List<T> queryNeighbors(PhTree<T> tree, Position position, int range){
        var result = new ArrayList<T>();
        var arrPos = position.toLongArray();
        var query = tree.query(calRange(arrPos, -range), calRange(arrPos, range));
        while(query.hasNext()){
            var element = query.next();
            result.add(element);
        }
        return result;
    }
    public static <T> List<T> queryNeighbors(PhTree<T> tree, long[] position, int range){
        var result = new ArrayList<T>();
        var query = tree.query(calRange(position, -range), calRange(position, range));
        while(query.hasNext()){
            var element = query.next();
            result.add(element);
        }
        return result;
    }
    private static long[] calRange(long[] position, int range){
        var newPos = position.clone();
        for(int i=0;i<position.length;i++){
            newPos[i] += range;
        }
        return newPos;
    }
}
