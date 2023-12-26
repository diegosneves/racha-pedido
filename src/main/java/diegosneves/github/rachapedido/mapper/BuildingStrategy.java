package diegosneves.github.rachapedido.mapper;

/**
 * A interface {@link BuildingStrategy} define uma estratégia para executar operações de mapeamento de objetos.
 *
 * @author diegosneves
 *
 * @param <T> o tipo da classe de destino
 * @param <E> o tipo do objeto de origem
 */
public interface BuildingStrategy<T, E> {

    /**
     * Executa a estratégia para realizar uma operação de mapeamento entre objetos.
     *
     * @param origem o objeto de origem que será convertido no objeto de destino
     * @return uma instância da classe de destino com seus campos preenchidos
     */
    T run(E origem);

}
