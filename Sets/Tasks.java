import java.util.*;

public class Tasks {
    public static void main(String[] args) {
        ArrayList<HashSet<Integer>> list = new ArrayList<>();
        int numSets = (int) (Math.random() * 11) + 2;
        for (int i = 0; i < numSets; i++) {
            HashSet<Integer> newSet = new HashSet<>();
            while (newSet.size() < 10)
                newSet.add((int) (Math.random() * 30) + 1);
            list.add(newSet);
            System.out.println(newSet);
        }

        HashSet<Integer> iSet = new HashSet<>();
        HashSet<Integer> uSet = new HashSet<>();
        HashSet<Integer> eiSet = new HashSet<>();
        HashSet<Integer> euSet = new HashSet<>();

        for (int i = 0; i < list.size() - 1; i++) {
            if (i == 0) {
                iSet = (intersection(list.get(i), list.get(i + 1)));
                uSet = (union(list.get(i), list.get(i + 1)));
                eiSet = (evenIntersection(list.get(i), list.get(i + 1)));
                euSet = (evenUnion(list.get(i), list.get(i + 1)));
            } else {
                iSet = (intersection(iSet, list.get(i + 1)));
                uSet = (union(uSet, list.get(i + 1)));
                eiSet = (evenIntersection(eiSet, list.get(i + 1)));
                euSet = (evenUnion(euSet, list.get(i + 1)));
            }
        }
        System.out.println("\nIntersection: " + iSet);
        System.out.println("Union: " + uSet);
        System.out.println("Even Intersection: " + eiSet);
        System.out.println("Even Union: " + euSet);

    }

    @SafeVarargs
    public static <E> Set<E> ToHash(E... objs) {
        HashSet<E> hashSet = new HashSet<>();
        for (E obj : objs)
            hashSet.add(obj);
        return hashSet;
    }

    public static <E> HashSet<E> intersection(HashSet<E> one, HashSet<E> two) {
        HashSet<E> tempOne = new HashSet<>(one);
        HashSet<E> tempTwo = new HashSet<>(two);
        tempOne.retainAll(tempTwo);
        return tempOne;
    }

    public static <E> HashSet<E> union(HashSet<E> one, HashSet<E> two) {
        HashSet<E> tempOne = new HashSet<>(one);
        HashSet<E> tempTwo = new HashSet<>(two);
        tempOne.addAll(tempTwo);
        return tempOne;
    }

    public static interface Filter<E> {
        public boolean apply(E val);
    }

    public static <E> HashSet<E> filter(Filter<E> filter, HashSet<E> set) {
        HashSet<E> newSet = new HashSet<>();
        for (E val : set) {
            if (filter.apply(val))
                newSet.add(val);
        }
        return newSet;
    }

    public static HashSet<Integer> evenIntersection(HashSet<Integer> one, HashSet<Integer> two) {
        return filter(i -> i % 2 == 0, intersection(one, two));
    }

    public static HashSet<Integer> evenUnion(HashSet<Integer> one, HashSet<Integer> two) {
        return filter(i -> i % 2 == 0, union(one, two));
    }
}
