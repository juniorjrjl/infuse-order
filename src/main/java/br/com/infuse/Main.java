package br.com.infuse;


import br.com.infuse.adapter.di.ApiModule;
import br.com.infuse.adapter.inbound.route.ErrorRoute;
import br.com.infuse.adapter.inbound.route.GetAllOrderRoute;
import br.com.infuse.adapter.inbound.route.NewOrderRoute;
import br.com.infuse.core.exception.ControlIdInUseException;
import br.com.infuse.core.exception.InfuseException;
import br.com.infuse.core.exception.OrderNotFoundException;
import br.com.infuse.core.exception.ValidatorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.notFound;
import static spark.Spark.port;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {
        runMigration();

        Injector injector = configureDependencyInjection();


        NewOrderRoute newOrderRoute = injector.getInstance(NewOrderRoute.class);
        GetAllOrderRoute getAllOrderRoute = injector.getInstance(GetAllOrderRoute.class);
        ErrorRoute errorRoute = injector.getInstance(ErrorRoute.class);

        get("/orders", getAllOrderRoute.resolve());
        post("/orders", newOrderRoute.resolve());

        notFound(errorRoute.notFoundRoute());
        exception(Exception.class, errorRoute.exceptionHandler());
        exception(InfuseException.class, errorRoute.infuseExceptionHandler());
        exception(OrderNotFoundException.class, errorRoute.orderNotFoundExceptionHandler());
        exception(ValidatorException.class, errorRoute.validatorExceptionHandler());
        exception(JsonProcessingException.class, errorRoute.jsonProcessingExceptionHandler());
        exception(ControlIdInUseException.class, errorRoute.controlIdInUseException());
    }

    private static Injector configureDependencyInjection() {
        Injector injector = Guice.createInjector(new ApiModule());
        ApiModule apiModule = injector.getInstance(ApiModule.class);
        port(apiModule.port());
        return injector;
    }

    private static void runMigration() {
        try {
            String changeLogFile = "db/changelog/db.changelog-master.yml";
            String url = "jdbc:mysql://localhost:3306/infuse_order?createDatabaseIfNotExist=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "infuse_order";
            String password = "infuse_order";
            Connection connection = DriverManager.getConnection(url, user, password);
            try (JdbcConnection jdbc = new JdbcConnection(connection)) {
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbc);
                Liquibase liquibase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), database);
                liquibase.update(new Contexts(), new LabelExpression());
            }
        }catch (LiquibaseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}