package app.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String NAME_PATTERN = "^(([À-ß¨][à-ÿ¸]+)|([A-Z][a-z]+))$";

  
    public void initialize(final ValidName constraintAnnotation) {
    }

   
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        return (validateName(username));
    }

    private boolean validateName(final String email) {
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
