
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import core.ConsoleLog;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import resources.ClientResource;
import resources.FileResource;


import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Created by prasi on 7/2/17.
 */
public class App extends Application<AppConfiguration>
{
    private static final String TAG = App.class.getSimpleName();

    public static void main(String[] args)
    {
        try
        {
            new App().run("server", "config.yml");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap)
    {
        bootstrap.addBundle(new AssetsBundle("/views"));
        bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "css"));
        bootstrap.addBundle(new AssetsBundle("/assets", "/docs", "index.html"));
        bootstrap.addBundle(new ViewBundle<AppConfiguration>());

        ConsoleLog.i(TAG, "started");
    }

    public void run(AppConfiguration appConfiguration, Environment environment) throws Exception
    {
        enableCORS(environment);

        initSwagger(appConfiguration, environment);

        environment.jersey().register(MultiPartFeature.class);
        environment.jersey().register(FileResource.class);
        environment.jersey().register(ClientResource.class);
    }

    private void enableCORS(Environment environment)
    {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("cors", CrossOriginFilter.class);
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    private void initSwagger(AppConfiguration configuration, Environment environment) {
        // Swagger Resource
        environment.jersey().register(new ApiListingResourceJSON());

        // Swagger providers
        environment.jersey().register(new ApiDeclarationProvider());
        environment.jersey().register(new ResourceListingProvider());

        // Swagger Scanner, which finds all the resources for @Api Annotations
        ScannerFactory.setScanner(new DefaultJaxrsScanner());

        // Add the reader, which scans the resources and extracts the resource information
        ClassReaders.setReader(new DefaultJaxrsApiReader());

        // required CORS support
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter("allowedOrigins", "*"); // allowed origins comma separated
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowedMethods", "GET,PUT,POST,DELETE,OPTIONS,HEAD");
        filter.setInitParameter("preflightMaxAge", "5184000"); // 2 months
        filter.setInitParameter("allowCredentials", "true");

        // Set the swagger config options
        SwaggerConfig config = ConfigFactory.config();
        config.setApiVersion("1.0.1");
        config.setBasePath(configuration.getSwaggerBasePath());
    }

}
