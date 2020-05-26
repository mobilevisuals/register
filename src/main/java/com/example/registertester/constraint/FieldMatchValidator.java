package com.example.registertester.constraint;

import org.apache.commons.beanutils.BeanUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/*vi skapar en validerare , som validerar om de 2 inmatningsfälten har samma värde.*/
/* Vår klass ärver från ConstraintValidator<A extends Annotation,T>
Denna klass definerar logiken för att validera objekt av typen T med annotionen A.
I vårt fall betyder det att FieldMatchValidator validerar alla objekt med vår annotation FieldMatch.*/
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;
//de 2 metoderna som måste implementeras för ConstraintValidator:
    //den här metoden initierar valideringen i förberedelse för isValid()
    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        //första fältet
        firstFieldName = constraintAnnotation.first();
        //andra fältet
        secondFieldName = constraintAnnotation.second();
    }
//Logiken för valideringen. object blir här det objekt som ska valideras, alltså ett objekt av UserRegistrationDtoUserRegistrationDto
    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context) {
        try {
            /*Vi hämtar värdet på de 2 fälten som måste vara samma. BeanUtils är den klass ifrån Apache-biblioteket
            commons-beanutils*/
            final Object firstObj = BeanUtils.getProperty(object, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(object, secondFieldName);
            //returnerar true om det 2 fälten har samma värden
            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception ignore) {}
        return true;
    }
}
