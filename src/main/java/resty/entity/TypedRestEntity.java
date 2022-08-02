package resty.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static java.util.Collections.emptyList;
import static resty.entity.RestEntity.getByType;

/**
 * Models the entities for a REST request by organizing them based on their type.
 * In this way it becomes possible to distinguish between headers and parameters.
 */
@Getter
@AllArgsConstructor
public class TypedRestEntity {

    /**
     * Provides empty entities when there's none specified.
     */
    public TypedRestEntity() {
        this.headers = emptyList();
        this.parameters = emptyList();
    }

    /**
     * Given a collection of entities for the REST request, it provides them organized based on their type.
     *
     * @param entities entities for the request.
     * @return entities organized based on their type.
     */
    public static TypedRestEntity buildFromEntities(RestEntity[] entities) {
        if (entities == null) {
            return new TypedRestEntity();
        }

        return new TypedRestEntity(
                getByType(entities, RestEntityType.HEADER),
                getByType(entities, RestEntityType.PARAMETER)
        );
    }

    /**
     * Headers for the request.
     */
    private List<RestEntity> headers;

    /**
     * Query parameters for the request.
     */
    private List<RestEntity> parameters;
}
