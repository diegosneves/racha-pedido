package diegosneves.github.rachapedido.mapper;

public interface BuildingStrategy<T, E> {

    T run(E origem);

}
