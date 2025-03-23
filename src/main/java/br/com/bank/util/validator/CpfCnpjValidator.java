package br.com.bank.util.validator;

import br.com.bank.util.Util;
import br.com.bank.util.annotation.CpfCnpj;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

    @Override
    public boolean isValid(String cpfCnpj, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(cpfCnpj)) {
            return true;
        } else {
            String cpfCnpjNumerico = cpfCnpj.replaceAll("\\D", "");
            if (!(cpfCnpjNumerico.length() == 11 || cpfCnpjNumerico.length() == 14)) {
                addMensagemViolation(context, "O campo deve ter 11 (CPF) ou 14 (CNPJ) dígitos numéricos");
                return false;
            }
            if (!Util.isValidCpfCnpj(cpfCnpjNumerico)) {
                addMensagemViolation(context, "CPF ou CNPJ inválido");
                return false;
            }
            return true;
        }
    }

    private void addMensagemViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
