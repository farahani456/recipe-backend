CREATE TABLE prod.recipe_categories (
    category_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_by BIGINT NOT NULL,
    updated_by BIGINT NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    deleted_by BIGINT,
    deleted_date TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);
