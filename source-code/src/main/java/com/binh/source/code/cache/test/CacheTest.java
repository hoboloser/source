/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/04	       binh              Initial
 */
package com.binh.source.code.cache.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.binh.source.code.cache.api.LocalCache;

/**
 * @ClassName @{link CacheTest}
 * @Description 基准线测试
 *
 * @author binh
 * @date 2018/08/04
 */
public class CacheTest {
    
    private AtomicInteger count = new AtomicInteger(0);
    
    @Benchmark
    @Warmup(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(1)
    public void test_v1_write() {
        final int value = count.incrementAndGet();
        
        LocalCache.instance().put("key" + value, "value" + value);
    }
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(CacheTest.class.getSimpleName()).output("F:/Benchmark.log").forks(1).build();
        new Runner(opt).run();
    }
}
