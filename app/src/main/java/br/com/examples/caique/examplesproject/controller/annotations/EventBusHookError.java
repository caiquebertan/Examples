package br.com.examples.caique.examplesproject.controller.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
public @interface EventBusHookError {
}