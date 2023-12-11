package edu.hw7.task2;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.LongStream;

public class ParallelFactorial {
    final int n;

    public ParallelFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Факториал вычисляется только от натурального числа!");
        }
        this.n = n;
    }

    public BigInteger compute() {
        List<BigInteger> range = LongStream.range(1L, n + 1).mapToObj(BigInteger::valueOf).toList();
        return range.parallelStream().reduce(BigInteger::multiply).orElse(BigInteger.ONE);
    }
}
