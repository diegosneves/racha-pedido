package diegosneves.github.rachapedido.utils;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.context.Context;

import java.lang.reflect.Field;

/**
 * A classe {@link ThymeLeafContextUtil} oferece métodos auxiliares para a geração de um objeto {@link Context}
 * ThymeLeaf a partir de um dado objeto de dados.
 *
 * @author diegosneves
 */
@Slf4j
public class ThymeLeafContextUtil {

    private static final Context context = new Context();
    private static final String CONTEXT_GENERATION_ERROR = "Erro ao gerar contexto para o e-mail";

    private ThymeLeafContextUtil() {
    }

    /**
     * Gera um objeto ThymeLeaf {@link Context} com base no dado fornecido.
     *
     * @param data o objeto de dados a partir do qual o contexto será gerado
     * @param <T>  o tipo do objeto de dados
     * @return o {@link Context contexto} ThymeLeaf gerado
     */
    public static <T> Context generateBy(T data) {
        Field[] emailFields = data.getClass().getDeclaredFields();
        for (Field field : emailFields) {
            field.setAccessible(true);
            try {
                context.setVariable(field.getName(), field.get(data));
            } catch (IllegalAccessException e) {
                log.error(CONTEXT_GENERATION_ERROR, e);
            }
        }
        return context;
    }

}
