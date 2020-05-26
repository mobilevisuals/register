package com.example.registertester.constraint;



import javax.validation.Payload;
import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/*Vi skapar en speciell annotation för att stödja valideringsprocessen genom om 2 fält har samma värde.
 Vi kan mata in två fält först och andra och ett valfritt meddelande.*/
/*Detta betyder att annotionen är en sammansatt annotation, som består av flera annotationer.
Detta kallas meta-annotation. */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
//Vi definerar klassen, som ska användas för validering.
@Constraint(validatedBy = FieldMatchValidator.class)
/*@interface är för att deklarera en annotion. Vi skapar här vår egen annotation FieldMatch */
public @interface FieldMatch
{
    /*Sättet att deklarera variabler för en annotation liknar hur metoder deklareras i ett interface.
    * Message är en variabel för det meddelande, som visas om valideringen inte godkänns.
    * UserRegistrationDao lägger till meddelanden här, t.ex. "The password fields must match" .
    * default är ett standardmeddelande, som visas om inget annat meddelande har lagts till.
    * */
    String message() default "{constraints.field-match}";
    /* Utan nedanstående deklaration, så blir det:
    * com.example.registertester.constraint.FieldMatch contains Constraint annotation, but does not contain a groups parameter.*/
   Class<?>[] groups() default {};
   /*Utan nedanstående deklaration för att gruppera annotationen, så blir det:
   * com.example.registertester.constraint.FieldMatch contains Constraint annotation, but does not contain a payload parameter.*/
    Class<? extends Payload>[] payload() default {};
    String first();
    String second();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
 /*@interface är för att deklarera en annotion. Vi skapar här annotationen List, som ligger inom den yttre
 annotationen FieldMatch. Annotationen har en variabel, som är en matris av FieldMatch. */
    @interface List
    {
        FieldMatch[] value();
    }
}
