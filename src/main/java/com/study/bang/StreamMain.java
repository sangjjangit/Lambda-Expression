package com.study.bang;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamMain {
    public static void main(String[] args) {
        // 스트림
        // 데이터의 집합(배열이나 콜렉션 등)에 대한 처리를 함수형 프로그래밍(람다식)으로 간결하게 기술하기 위한 새로운 개념
        // java.io 패키지의 스트림이랑 전혀 다른 것.
        // JAVA8에서 도입된 새로운 개념으로 취급.
        // java.util.stream 패키지
        // Stream<T>, IntStream, LongStream, DoubleStream, 슈퍼 인터페이스 BaseStream을 계승

        List<String> list = Arrays.asList("1","2","3","4");
        Stream<String> stream = list.stream();

        String[] arr1 = {"1","2","3","4"};
        Stream<String> stream1 = Arrays.stream(arr1);

        // 각 스트림 인터페이스에는 of 라고 하는 static 메소드를 사용해서 직접 스트림 객체를 만들 수 있다.
        Stream<String> stream2 = Stream.of("1","2","3","4");

        /*
        함수형 인터페이스: 하나의 추상 메소드를 선언을 포함한 인터페이스
        JAVA8에서 43개의 함수형 인터페이스가 추가
        극단적으로 말하자면, 람다식을 표현하는 함수 오브젝트는 이 43개의 함수형 인터페이스 중 하나다.
        ** 43개나 있는 인터페이스를 모도 이해해야 하나?
        중요한 것은 43개 중 7개 정도.
        나머지는, 그 7개의 어렌지 버전(파생형,프리미티브 특수화형)에 불과.
        * 추상메소드[SAM(Single Abstract Method)]
        */

        // 1. Supplier<T>
        // - 인수없이 T형의 값을 반환하는 추상메소드 [T get()]을 SAM으로 가진다.
        Stream<Double> stream3 = Stream.generate(()->Math.random());
        stream3 = Stream.generate(Math::random);
        stream3 = Stream.generate(Math::random).limit(1000); // 1000개 가진 스트림 오브젝트 생성

        // 2. Consumer
        // - T형으로 받은 인수에 대해서 어떠한 처리를 수행하는 반환값이 없는 추상메소드 [void accept(T t)]를 SAM으로 가진다.
        List<String> list1 = Arrays.asList("1","2","3","4");
        list.stream().forEach(System.out::println);

        // 3. Predicate
        // T형으로 받은 인수를 평가하는 추상메소드 [boolean test(T t)]를 SAM으로 가진다.
        List<String> list2 = Arrays.asList("ABC","DE","FGHI");
        Predicate<String> p = s -> {
            return s.length() >= 3;
        };
        boolean all = list2.stream().allMatch(p);
        boolean any = list2.stream().anyMatch(p);
        boolean none = list2.stream().noneMatch(p);
        Consumer c = System.out::println;
        c.accept(all);
        c.accept(any);
        c.accept(none);

        // 4. Function<T,R>
        // - T형으로 받은 인수를 처리하고, 결과로 R형의 값을 반환하는 추상메소드 [R apply(T t)]를 SAM으로 가진다.
        Function<String, Integer> function = (string) -> {
            return string.length();
        };
        function = String::length;
        function = Integer::valueOf;

        // 5. BiFunction<T,U,R>
        // - T형과 U형 2개의 인수를 받아, 결과로 R형의 값을 반환하는 추상 메소드 [R apply(T t, U u)]를 SAM으로 가진다.


        // 6. UnaryOperator<T>
        // - Function 인터페이스의 서브 인터페이스
        // - 단항연산자
        // - T형으로 받은 인수를 처리하고, 결과로 동일한 T형의 값을 반환하는 추상메소드 [T apply(T t)]를 SAM으로 가진다.
        Stream.iterate(1, x -> x * 2).limit(5).forEach(System.out::println);

        // 7.BinaryOperator<T>
        // - BiFunction 인터페이스의 서브 인터페이스
        // - 2항연산자
        // - 2개의 T형 인수를 처리하고, 결과로 동일한 T형의 값을 반환하는 추상메소드 [T apply(T t1, T t2)]를 SAM으로 가진다.

        // 스트림의 특징
        // - 중간조작, 종단조작
        // - 종단조작: count, max, min, sum, average,
        //           reduce(요소들을 하나의 데이터로..)
        //            collect(원하는 자료형으로 변환)
        // - 중간조작: filter, map, sorted 메소드

        List<String> list3 = Arrays.asList("ABC","DE","FGHI");
        list3.stream().count();

        // ---
        // NullPointException 예외의 위험성을 해소하기 위해, Optional 클래스 도입.
//        String max1 = list3.stream().max(Comparator.comparingInt(String::length)).orElse("x");
        String max1 = list3.stream().max((x,y)->{
            return x.length() - y.length();
        }).orElse("X");
        String min1 = list3.stream().min((x,y)->{
            return x.length() - y.length();
        }).orElse("x");
        c.accept(max1);
        c.accept(min1);
        // ---
        int[] arr = {1,2,3,4,5};
        System.out.println(Arrays.stream(arr).sum());
        System.out.println(Arrays.stream(arr).average().orElse(0));

        // ---
        String reduce = list3.stream().reduce((x,y)-> x+y).orElse("x");
        c.accept(reduce);

        // ---
        Stream<String> stream4 = Stream.of("A", "B", "Ba", "AB", "AB");
        Set<String> set = stream4.collect(Collectors.toSet());
        set.stream().forEach(System.out::println);

        // ---
        stream4 = Stream.of("A", "B", "Ba", "AB", "AB");
        c.accept("---");
        stream4.filter(s -> s.indexOf("A") != -1 ).collect(Collectors.toList()).forEach(System.out::println);

        // ---
        stream4 = Stream.of("A", "B", "Ba", "AB", "AB");
        c.accept("---");
        stream4.map(s -> s.length()).forEach(System.out::println);
    }
}
