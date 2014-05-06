/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package begining.ejbremote.lib;

import java.util.Properties;
import java.util.logging.Level;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Eiichi Tanaka
 */
public class EjbUtil {
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(EjbUtil.class.getName());
    
    //セパレータ
    private static final String SEPARATOR = "/";

    //EJBコンテナ設定    
    private static final String EJB_HOST = "localhost";
    private static final String EJB_PORT = "3700";
    
    //EJBアプリケーション/モジュール名
    private static final String EJB_APPLICATION = "EJBRemote-ear";
    private static final String EJB_MODULE = "EJBRemote-ejb-1.0-SNAPSHOT";

    //Bean名称
    public static final String EJB_BEAN_SERVICE  = "EjbService";
    public static final String EJB_BEAN_SERVICE2 = "EjbService2";
    
    //完全修飾インターフェース名
    public static final String IF_SERVICE2_REMOTE 
            = "begining.ejbremote.lib.EjbRemoteInterface";
    
    public static Object ejbLookup(final String beanName) {
        Object ejb = null;

        final String jndiLookupName = "java:global" + SEPARATOR +
                            EJB_APPLICATION + SEPARATOR +
                            EJB_MODULE + SEPARATOR +
                            beanName;

        logger.log(Level.INFO, jndiLookupName);
        
        try {
            InitialContext ic = new InitialContext(getEjbProperty());
            ejb = ic.lookup(jndiLookupName);
        } catch (NamingException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        
        return ejb;
    }
    
    //コンテナの外部リソースJNDIで登録したものを取得
    public static Object ejbLookupFormContainer(final String jndiName) {
        Object ejb = null;
        
        try {
            InitialContext ic = new InitialContext();
            ejb = ic.lookup(jndiName);
        } catch (NamingException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        
        return ejb;
    }

    //スタンドアロンアプリからEJB取得時に利用
    private static Properties getEjbProperty() {
        final Properties property = new Properties();
        property.setProperty("java.naming.factory.initial"
                , "com.sun.enterprise.naming.SerialInitContextFactory");
        property.setProperty("org.omg.CORBA.ORBInitialHost", EJB_HOST);
        property.setProperty("org.omg.CORBA.ORBInitialPort", EJB_PORT);
        
        return property;
    }
}
