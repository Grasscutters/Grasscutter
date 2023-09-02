package emu.grasscutter.scripts;

import com.github.davidmoten.rtreemulti.*;
import com.github.davidmoten.rtreemulti.geometry.*;
import java.util.*;
import java.util.function.Function;

public class SceneIndexManager {

    public static <T> RTree<T, Geometry> buildIndex(
            int dimensions, Collection<T> elements, Function<T, Geometry> extractor) {
        RTree<T, Geometry> rtree = RTree.dimensions(dimensions).create();
        return rtree.add(elements.stream().map(e -> Entry.entry(e, extractor.apply(e))).toList());
    }

    public static <T> List<T> queryNeighbors(RTree<T, Geometry> tree, double[] position, int range) {
        var result = new ArrayList<T>();
        Rectangle rectangle = Rectangle.create(calRange(position, -range), calRange(position, range));
        var queryResult = tree.search(rectangle);
        queryResult.forEach(q -> result.add(q.value()));
        return result;
    }

    private static double[] calRange(double[] position, int range) {
        var newPos = position.clone();
        for (int i = 0; i < newPos.length; i++) {
            newPos[i] += range;
        }
        return newPos;
    }
}
