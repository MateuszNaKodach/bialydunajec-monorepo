package org.bialydunajec.ddd.domain.base.validation.constraints

import org.hibernate.validator.constraints.ConstraintComposition

import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.ReportAsSingleViolation
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Null
import org.hibernate.validator.constraints.CompositionType.OR
import kotlin.reflect.KClass


@ConstraintComposition(OR)
@Null
@NotBlank
@ReportAsSingleViolation
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [])
annotation class NullOrNotBlank(val message: String = "{org.hibernate.validator.constraints.NullOrNotBlank.message}", val groups: Array<KClass<*>> = [], val payload: Array<KClass<out Payload>> = [])
