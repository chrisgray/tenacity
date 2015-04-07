package com.yammer.tenacity.core.config;

import com.netflix.hystrix.HystrixCommandProperties;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TenacityConfiguration {
    @NotNull @Valid
    private ThreadPoolConfiguration threadpool = new ThreadPoolConfiguration();

    @NotNull @Valid
    private CircuitBreakerConfiguration circuitBreaker = new CircuitBreakerConfiguration();

    @NotNull @Valid
    private SemaphoreConfiguration semaphore = new SemaphoreConfiguration();

    @Min(value = 0)
    @Max(Integer.MAX_VALUE)
    private int executionIsolationThreadTimeoutInMillis = 1000;

    private HystrixCommandProperties.ExecutionIsolationStrategy executionIsolationStrategy =
            HystrixCommandProperties.ExecutionIsolationStrategy.THREAD;

    public TenacityConfiguration() { /* Jackson */ }

    public TenacityConfiguration(ThreadPoolConfiguration threadpool,
                                 CircuitBreakerConfiguration circuitBreaker,
                                 SemaphoreConfiguration semaphore,
                                 int executionIsolationThreadTimeoutInMillis) {
        this(threadpool, circuitBreaker, semaphore, executionIsolationThreadTimeoutInMillis,
                HystrixCommandProperties.ExecutionIsolationStrategy.THREAD);
    }

    public TenacityConfiguration(ThreadPoolConfiguration threadpool,
                                 CircuitBreakerConfiguration circuitBreaker,
                                 SemaphoreConfiguration semaphore,
                                 int executionIsolationThreadTimeoutInMillis,
                                 HystrixCommandProperties.ExecutionIsolationStrategy executionIsolationStrategy) {
        this.threadpool = threadpool;
        this.circuitBreaker = circuitBreaker;
        this.semaphore = semaphore;
        this.executionIsolationThreadTimeoutInMillis = executionIsolationThreadTimeoutInMillis;
        this.executionIsolationStrategy = executionIsolationStrategy;
    }

    public ThreadPoolConfiguration getThreadpool() {
        return threadpool;
    }

    public CircuitBreakerConfiguration getCircuitBreaker() {
        return circuitBreaker;
    }

    public int getExecutionIsolationThreadTimeoutInMillis() {
        return executionIsolationThreadTimeoutInMillis;
    }

    public void setThreadpool(ThreadPoolConfiguration threadpool) {
        this.threadpool = threadpool;
    }

    public void setCircuitBreaker(CircuitBreakerConfiguration circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }

    public void setExecutionIsolationThreadTimeoutInMillis(int executionIsolationThreadTimeoutInMillis) {
        this.executionIsolationThreadTimeoutInMillis = executionIsolationThreadTimeoutInMillis;
    }

    public SemaphoreConfiguration getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(SemaphoreConfiguration semaphore) {
        this.semaphore = semaphore;
    }

    public HystrixCommandProperties.ExecutionIsolationStrategy getExecutionIsolationStrategy() {
        return executionIsolationStrategy;
    }

    public void setExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy executionIsolationStrategy) {
        this.executionIsolationStrategy = executionIsolationStrategy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(threadpool, circuitBreaker, semaphore, executionIsolationThreadTimeoutInMillis, executionIsolationStrategy);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TenacityConfiguration other = (TenacityConfiguration) obj;
        return Objects.equals(this.threadpool, other.threadpool)
                && Objects.equals(this.circuitBreaker, other.circuitBreaker)
                && Objects.equals(this.semaphore, other.semaphore)
                && Objects.equals(this.executionIsolationThreadTimeoutInMillis, other.executionIsolationThreadTimeoutInMillis)
                && Objects.equals(this.executionIsolationStrategy, other.executionIsolationStrategy);
    }
}