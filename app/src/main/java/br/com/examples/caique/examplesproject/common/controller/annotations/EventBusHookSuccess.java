package br.com.examples.caique.examplesproject.common.controller.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
public @interface EventBusHookSuccess {}