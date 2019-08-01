package com.epm.recipe.persistence.jdbc;

import com.epm.recipe.domain.Recipe;
import com.epm.recipe.persistence.RecipeRepository;
import com.epm.recipe.persistence.jdbc.config.DBConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcRecipeRepository implements RecipeRepository {

    private final static Logger log = LoggerFactory.getLogger(JdbcRecipeRepository.class);
    private final DBConfiguration dbConfiguration;

    public JdbcRecipeRepository(DBConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }

    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection conn = dbConfiguration.getConnection();
             PreparedStatement st = conn.prepareStatement("SELECT * FROM recipes")) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                recipes.add(new Recipe(rs.getString("title"), rs.getLong("id")));
            }
        } catch (SQLException e) {
            log.error("SQL Error get all recipes " + e.getMessage());
        }
        return recipes;
    }
}
