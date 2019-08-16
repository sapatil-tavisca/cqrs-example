package com.affinion.customereventsource.config;

import com.affinion.customereventsource.converter.CreateCustomerRequestToCustomerConverter;
import com.affinion.customereventsource.converter.CustomerToCustomerResponseConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ConverterConfig {

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        defaultConversionService.addConverter(new CustomerToCustomerResponseConverter());
        defaultConversionService.addConverter(new CreateCustomerRequestToCustomerConverter());
        return defaultConversionService;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
