package net.apachegui.directives;

import net.apachegui.db.SettingsDao;
import net.apachegui.global.Constants;
import net.apachegui.modules.SharedModuleHandler;
import net.apachegui.modules.StaticModuleHandler;

import org.apache.log4j.Logger;

import apache.conf.parser.DirectiveParser;

public class ListenBackLog extends GlobalSingletonDirective {

    private static Logger log = Logger.getLogger(ListenBackLog.class);

    private int backLogLength;
    private final int defaultBackLogLength = 511;

    public ListenBackLog() {
        super(Constants.listenBackLogDirective);

        this.backLogLength = defaultBackLogLength;
    }

    public ListenBackLog(int backLogLength) {
        super(Constants.listenBackLogDirective);

        this.backLogLength = backLogLength;
    }

    /**
     * Creates a ListenBackLog object from the directive. The directive should conform to the following format:
     * 
     * ListenBackLog backlog
     * 
     * @param directiveValue
     */
    public ListenBackLog(String directiveValue) {
        super(Constants.listenBackLogDirective);

        directiveValue = directiveValue.trim().toLowerCase();

        try {
            this.backLogLength = Integer.parseInt(directiveValue);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public int getBackLogLength() {
        return backLogLength;
    }

    public void setBackLogLength(int backLogLength) {
        this.backLogLength = backLogLength;
    }

    /**
     * Static function to get the current configured ListenBackLog in apache. If there is no ListenBackLog found then a ListenBackLog is returned with a default value of 511
     * 
     * @return a ListenBackLog object, is no ListenBackLog is found then a ListenBackLog is returned a value of 511
     * @throws Exception
     */
    public static ListenBackLog getListenBackLog() throws Exception {
        return (new ListenBackLog().getGlobalConfigured());
    }

    /**
     * Static function to get the current configured ListenBackLog in apache. If there is no ListenBackLog found then a ListenBackLog is returned with a value of 511
     * 
     * @return a ListenBackLog object, is no ListenBackLog is found then a ListenBackLog is returned a value 511
     * @throws Exception
     */
    @Override
    public ListenBackLog getGlobalConfigured() throws Exception {
        String listenBackLog[] = new DirectiveParser(SettingsDao.getInstance().getSetting(Constants.confFile), SettingsDao.getInstance().getSetting(Constants.serverRoot),
                StaticModuleHandler.getStaticModules(), SharedModuleHandler.getSharedModules()).getDirectiveValue(Constants.listenBackLogDirective, false);

        ListenBackLog listenBackLogFound = null;

        if (listenBackLog.length == 0) {
            listenBackLogFound = new ListenBackLog(defaultBackLogLength);
        } else {
            listenBackLogFound = new ListenBackLog(listenBackLog[0].trim());
        }

        return listenBackLogFound;
    }

    @Override
    public String toString() {
        return Constants.listenBackLogDirective + " " + backLogLength;
    }

}
