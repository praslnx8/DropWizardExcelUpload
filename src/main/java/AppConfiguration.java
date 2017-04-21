import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by prasi on 7/2/17.
 */
public class AppConfiguration extends Configuration
{
    @Valid
    @NotNull
    @JsonProperty("swaggerBasePath")
    private String swaggerBasePath;

    public String getSwaggerBasePath(){ return swaggerBasePath; }
}
