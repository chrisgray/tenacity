package com.yammer.tenacity.core.logging;

import com.google.common.collect.ImmutableList;
import com.netflix.hystrix.HystrixInvokable;
import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;

import java.util.List;

/**
 * A command execution hook that attaches to the onRunError and when an exception is thrown,
 * finds an appropriate ExceptionLogger (if one is available) and sends the exception to it to be logged.
 *
 * The order of the ExceptionLoggers is important, it will use the first one that declares it can handle the type
 * of exception, so the order in which you pass the ExceptionLoggers on constructing the Hook will determine this
 *
 * This hook can (needs to) be registered as a command hook with the HystrixPlugin like so:
 * <pre>
 * {@code
 * HystrixPlugins.getInstance().registerCommandExecutionHook(new ExceptionLoggingCommandHook());
 * }
 * </pre>
 */
public class ExceptionLoggingCommandHook extends HystrixCommandExecutionHook {

    private final List<ExceptionLogger<? extends Exception>> exceptionLoggers;

    /**
     * Empty constructor only sets the DefaultExceptionLogger, which will log every type of exception
     */
    public ExceptionLoggingCommandHook() {
        this(new DefaultExceptionLogger());
    }

    public ExceptionLoggingCommandHook(ExceptionLogger<? extends Exception> exceptionLogger) {
        this(ImmutableList.of(exceptionLogger));
    }

    public ExceptionLoggingCommandHook(Iterable<ExceptionLogger<? extends Exception>> exceptionLoggers) {
        this.exceptionLoggers = ImmutableList.copyOf(exceptionLoggers);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Exception onExecutionError(HystrixInvokable<T> commandInstance, Exception exception) {
        for (ExceptionLogger<? extends Exception> logger: exceptionLoggers) {
            if (logger.canHandleException(exception) && isHystrixInvokableInfo(commandInstance)) {
                logger.log(exception, (HystrixInvokableInfo<T>)commandInstance);
                return exception;
            }
        }

        return exception;
    }

    private <T> boolean isHystrixInvokableInfo(HystrixInvokable<T> commandInstance) {
        return commandInstance instanceof HystrixInvokableInfo;
    }
}