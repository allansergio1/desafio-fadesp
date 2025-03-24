package br.com.bank.util;

public class Util {

    private static final int[] PESO_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESO_CNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private Util() {
    }

    public static boolean isValidCpfCnpj(String cpfCnpj) {
        cpfCnpj = cpfCnpj.trim().replaceAll("\\D", "");
        return (isValidCPF(cpfCnpj) || isValidCNPJ(cpfCnpj));
    }

    public static boolean isValidCPF(String cpf) {
        if (cpf.length() != 11 || hasPadraoInvalido(cpf)) return false;

        Integer firstDigit = calcularDigitoVerificador(cpf.substring(0, 9), PESO_CPF);
        Integer secondDigit = calcularDigitoVerificador(cpf.substring(0, 9) + firstDigit, PESO_CPF);

        return cpf.equals(cpf.substring(0, 9) + firstDigit + secondDigit);
    }

    public static boolean isValidCNPJ(String cnpj) {
        if (cnpj.length() != 14) return false;

        Integer firstDigit = calcularDigitoVerificador(cnpj.substring(0, 12), PESO_CNPJ);
        Integer secondDigit = calcularDigitoVerificador(cnpj.substring(0, 12) + firstDigit, PESO_CNPJ);

        return cnpj.equals(cnpj.substring(0, 12) + firstDigit + secondDigit);
    }

    private static Integer calcularDigitoVerificador(String base, int[] weight) {
        int sum = 0;
        for (int i = 0; i < base.length(); i++) {
            int digit = Character.getNumericValue(base.charAt(i));
            sum += digit * weight[weight.length - base.length() + i];
        }
        int checkDigit = 11 - (sum % 11);
        return checkDigit > 9 ? 0 : checkDigit;
    }

    private static boolean hasPadraoInvalido(String cpf) {
        String invalidPattern = cpf.charAt(0) + String.valueOf(cpf.charAt(0)).repeat(10);
        return cpf.equals(invalidPattern);
    }
}
