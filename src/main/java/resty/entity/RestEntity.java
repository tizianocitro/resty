package resty.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static resty.entity.RestEntityType.HEADER;
import static resty.entity.RestEntityType.PARAMETER;

/**
 * Models an entity for a REST request.
 * It can be either a header or a parameter.
 */
@Getter
@AllArgsConstructor
public class RestEntity {

    /**
     * Builds a query parameter as a pair (name, value) for it to be used in the request,
     * where the value is a string.
     *
     * @param name the name to use for the parameter.
     * @param value the value to use for the parameter.
     * @return the built parameter.
     */
    public static RestEntity withParameter(String name, String value) {
        return new RestEntity(PARAMETER, name, value);
    }

    /**
     * Builds a query parameter as a pair (name, value) for it to be used in the request,
     * where the value is an integer.
     *
     * @param name the name to use for the parameter.
     * @param value the value to use for the parameter.
     * @return the built parameter.
     */
    public static RestEntity withParameter(String name, int value) {
        return new RestEntity(PARAMETER, name, valueOf(value));
    }

    /**
     * Builds a query parameter as a pair (name, value) for it to be used in the request,
     * where the value is a double.
     *
     * @param name the name to use for the parameter.
     * @param value the value to use for the parameter.
     * @return the built parameter.
     */
    public static RestEntity withParameter(String name, double value) {
        return new RestEntity(PARAMETER, name, valueOf(value));
    }

    /**
     * Builds a query parameter as a pair (name, value) for it to be used in the request,
     * where the value is a float.
     *
     * @param name the name to use for the parameter.
     * @param value the value to use for the parameter.
     * @return the built parameter.
     */
    public static RestEntity withParameter(String name, float value) {
        return new RestEntity(PARAMETER, name, valueOf(value));
    }

    /**
     * Builds a query parameter as a pair (name, value) for it to be used in the request,
     * where the value is boolean.
     *
     * @param name the name to use for the parameter.
     * @param value the value to use for the parameter.
     * @return the built parameter.
     */
    public static RestEntity withParameter(String name, boolean value) {
        return new RestEntity(PARAMETER, name, valueOf(value));
    }

    /**
     * Builds a header as a pair (name, value) for it to be used in the request,
     * where the value is a string.
     *
     * @param name the name to use for the header.
     * @param value the value to use for the header.
     * @return the built header.
     */
    public static RestEntity withHeader(String name, String value) {
        return new RestEntity(HEADER, name, value);
    }

    /**
     * Builds a header as a pair (name, value) for it to be used in the request,
     * where the value is an integer.
     *
     * @param name the name to use for the header.
     * @param value the value to use for the header.
     * @return the built header.
     */
    public static RestEntity withHeader(String name, int value) {
        return new RestEntity(HEADER, name, valueOf(value));
    }

    /**
     * Builds a header as a pair (name, value) for it to be used in the request,
     * where the value is a double.
     *
     * @param name the name to use for the header.
     * @param value the value to use for the header.
     * @return the built header.
     */
    public static RestEntity withHeader(String name, double value) {
        return new RestEntity(HEADER, name, valueOf(value));
    }

    /**
     * Builds a header as a pair (name, value) for it to be used in the request,
     * where the value is a float.
     *
     * @param name the name to use for the header.
     * @param value the value to use for the header.
     * @return the built header.
     */
    public static RestEntity withHeader(String name, float value) {
        return new RestEntity(HEADER, name, valueOf(value));
    }

    /**
     * Builds a header as a pair (name, value) for it to be used in the request,
     * where the value is boolean.
     *
     * @param name the name to use for the header.
     * @param value the value to use for the header.
     * @return the built header.
     */
    public static RestEntity withHeader(String name, boolean value) {
        return new RestEntity(HEADER, name, valueOf(value));
    }

    /**
     * Given a collection of entities, it provides only the entity with the given type.
     *
     * @param entities entities to filter based on their type.
     * @param type type for filtering the entities.
     * @return the filter entities.
     */
    public static List<RestEntity> getByType(RestEntity[] entities, RestEntityType type) {
        return Arrays.stream(entities)
                .filter(e -> e.getType().equals(type))
                .collect(Collectors.toList());
    }

    /**
     * Entity type that allows to handle the entity in the proper way as it needs to be for the request.
     */
    private RestEntityType type;

    /**
     * Entity name.
     */
    private String name;

    /**
     * Entity value.
     */
    private String value;

    /**
     * Specifies that no entity is required for the request.
     */
    public static final RestEntity[] NO_ENTITY = null;

    /**
     * Specifies that body is not required for the request.
     */
    public static final RestEntity NO_BODY = null;
}
