package views;

import io.dropwizard.views.View;

/**
 * Created by Juan on 25/02/2015.
 */
public class ParseView extends View {

    public ParseView() {
        super("/views/parse.mustache");
    }
}
