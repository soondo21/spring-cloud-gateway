package com.nh.test.scg.filter;

import com.nh.test.scg.config.GlobalConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalConfig> {

    private Logger logger = LoggerFactory.getLogger(GlobalFilter.class);
    public GlobalFilter() {
        super(GlobalConfig.class);  
    }
    @Override
    public GatewayFilter apply(GlobalConfig config) {
        // TODO Auto-generated method stub
        return (exchange, chain)->{
            logger.info("[GlobalFilter] baseMessage: " + config.getBaseMessage());

            if(config.isPreLogger()) {
                logger.info("[GlobalFilter] Start: " + exchange.getRequest());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if(config.isPostLogger()){
                    logger.info("[GlobalFilter] End: " + exchange.getResponse());
                }
            }));
        };
    }
}