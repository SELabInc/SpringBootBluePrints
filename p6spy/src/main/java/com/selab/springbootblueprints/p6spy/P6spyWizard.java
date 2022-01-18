package com.selab.springbootblueprints.p6spy;

import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import lombok.Builder;

import javax.annotation.PostConstruct;

@Builder
public class P6spyWizard {

    @Builder.Default
    private Class<? extends MessageFormattingStrategy> MessageFormattingStrategyClass = CustomMessageFormattingStrategy.class;

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(MessageFormattingStrategyClass.getName());
    }
}