package diegosneves.github.rachapedido.utils;

/**
 * Esta classe fornece um conjunto de métodos de utilidade para arredondar números decimais.
 * Os métodos de arredondamento presente aqui realizam um arredondamento para duas casas decimais.
 * Como é uma classe utilitária, seu construtor é privado para prevenir a criação de instâncias.
 *
 * @author diegosneves
 */
public final class RoundUtil {

    private RoundUtil(){}

    /**
     * Este método realiza o arredondamento do valor decimal fornecido para duas casas decimais.
     *
     * @param value o valor decimal a ser arredondado.
     * @return O valor arredondado até duas casas decimais.
     */
    public static Double round(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}