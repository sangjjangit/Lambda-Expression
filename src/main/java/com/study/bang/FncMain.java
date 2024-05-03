package com.study.bang;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

public class FncMain {
    public static void main(String[] args) {
        IntBinaryOperator op = new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        };
        System.out.println("op:" + op.applyAsInt(1,1));

        IntBinaryOperator op2 = (x,y) -> {
            return x + y;
        };
        System.out.println("op2:" + op2.applyAsInt(1,1));

        IntBinaryOperator op3 = (x,y) -> {
            return x + y;
        };
        System.out.println("op3:" + op3.applyAsInt(1,1));

        // IntBinaryOperator인터페이스는 함수형 인터페이스이므로, 추상 메소드를 하나 가지고 있다.
        // 람다식은 오브젝트이기에 당연히 형이 존재한다.
        // JAVA8 에서는 [형추론]이 강화되었다.

        Function<String, Integer> fn = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };
        System.out.println("fn:" + fn.apply("가나다"));
        Function<String, Integer> fn2 = (s) -> {
            return s.length();
        };
        System.out.println("fn2:" + fn2.apply("1234"));
        // 인수 하나일경우, 괄호 생략
        Function<String, Integer> fn3 = s -> {
            return s.length();
        };
        System.out.println("fn3:" + fn3.apply("1234"));
        // 더 심플
        Function<String, Integer> fn4 = String::length; // String형의 인수가 가진 length 메소드를 심플하게 호출하는 것.
        System.out.println("fn4:" + fn4.apply("1234"));

        // 메소드 참조
        Consumer<String> c = System.out::println; // System.out 객체의 println 메소드를 심플하게 호출하는 것.
        c.accept("hello world");
        Supplier<Double> s = () -> Math.random();
        System.out.println("s:" + s.get());
        Supplier<Double> s2 = Math::random; // Math 객체의 random 메소드를 심플하게 호출하는 것.
        System.out.println("s2:" + s2.get());
        BinaryOperator<Integer> b = (x, y) -> Integer.max(x,y);
        System.out.println("b:" + b.apply(10,20));
        BinaryOperator<Integer> b2 = Integer::max;
        System.out.println("b2:" + b2.apply(10,20));
        Comparator<String> cp = String::compareToIgnoreCase;
        System.out.println("cp:" + cp.compare("1","2"));

        Function<String, Integer> fn5 = str -> {
            return Integer.valueOf(str);
        };
        System.out.println("fn5:" + fn5.apply("10"));
        Function<String, Integer> fn6 = Integer::valueOf;
        System.out.println("fn6:" + fn6.apply("10"));

        // --- 문제: 짝수 조사
        List<Integer> list = Arrays.asList(5,8,1,3,4,9,2,7);
        final long cnt = list.stream().filter(x -> x%2 == 0).count();
        System.out.println("cnt:" + cnt);

        // 정리
        // 람다식은, 개념적으로 함수를 표현하는 리터럴이면서 오브젝트입니다.
        // 하지만 현실적으로는, 람다식은 함수형 인터페이스의 익명 클래스에 의한 구현을 간결하게 기술할 수 있게 한 것에 불과하다.
        // 그저 [함수]라고 심플하게 생각하고, 깊이있게 이해하고, 심플하게 사용하는 것이 베스트라고 말할 수 있다.

    }
}
