-- RUN THIS AFTER ALL TABLE IS CREATED

-- Add foreign key constraint for uom_id referencing uoms table
ALTER TABLE prod.ingredients
ADD CONSTRAINT fk_ingredients_uom FOREIGN KEY (uom_id) REFERENCES prod.uoms(uom_id);

-- Add foreign key constraint for ingredient_id referencing ingredients table
ALTER TABLE prod.recipe_ingredients
ADD CONSTRAINT fk_recipe_ingredients_ingredient FOREIGN KEY (ingredient_id) 
REFERENCES prod.ingredients(ingredient_id);

-- Add foreign key constraint for recipe_id referencing recipes table
ALTER TABLE prod.recipe_ingredients
ADD CONSTRAINT fk_recipe_ingredients_recipe FOREIGN KEY (recipe_id) 
REFERENCES prod.recipes(recipe_id);

-- Add foreign key constraint for category_id referencing recipe_categories table
ALTER TABLE prod.recipes
ADD CONSTRAINT fk_recipes_category FOREIGN KEY (category_id) 
REFERENCES prod.recipe_categories(category_id);

-- created_by and updated_by FK
-- Add foreign key constraint for created_by referencing users table
ALTER TABLE prod.recipes
ADD CONSTRAINT fk_recipes_created_by FOREIGN KEY (created_by) 
REFERENCES prod.users(user_id);

ALTER TABLE prod.ingredients
ADD CONSTRAINT fk_ingredients_created_by FOREIGN KEY (created_by) 
REFERENCES prod.users(user_id);

ALTER TABLE prod.recipe_categories
ADD CONSTRAINT fk_recipe_categories_created_by FOREIGN KEY (created_by) 
REFERENCES prod.users(user_id);

ALTER TABLE prod.recipe_ingredients
ADD CONSTRAINT fk_recipe_ingredients_created_by FOREIGN KEY (created_by) 
REFERENCES prod.users(user_id);

ALTER TABLE prod.uoms
ADD CONSTRAINT fk_uoms_created_by FOREIGN KEY (created_by) 
REFERENCES prod.users(user_id);

-- Add foreign key constraint for updated_by referencing users table
ALTER TABLE prod.recipes
ADD CONSTRAINT fk_recipes_updated_by FOREIGN KEY (updated_by) 
REFERENCES prod.users(user_id);

ALTER TABLE prod.ingredients
ADD CONSTRAINT fk_ingredients_updated_by FOREIGN KEY (updated_by) 
REFERENCES prod.users(user_id);

ALTER TABLE prod.recipe_categories
ADD CONSTRAINT fk_recipe_categories_updated_by FOREIGN KEY (updated_by) 
REFERENCES prod.users(user_id);

ALTER TABLE prod.recipe_ingredients
ADD CONSTRAINT fk_recipe_ingredients_updated_by FOREIGN KEY (updated_by) 
REFERENCES prod.users(user_id);

ALTER TABLE prod.uoms
ADD CONSTRAINT fk_uoms_updated_by FOREIGN KEY (updated_by) 
REFERENCES prod.users(user_id);
