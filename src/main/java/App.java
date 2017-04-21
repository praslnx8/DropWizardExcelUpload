
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
        bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "css"));
        bootstrap.addBundle(new ViewBundle());

        ConsoleLog.i(TAG, "started");
    }

    public void run(AppConfiguration appConfiguration, Environment environment) throws Exception
    {
        enableCORS(environment);

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
}
