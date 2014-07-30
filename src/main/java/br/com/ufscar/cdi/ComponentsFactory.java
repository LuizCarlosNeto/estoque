package br.com.ufscar.cdi;

//import javax.annotation.Resource;
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.context.RequestScoped;
//import javax.enterprise.inject.Disposes;
//import javax.enterprise.inject.Produces;
//import javax.enterprise.inject.spi.BeanManager;
//import javax.enterprise.inject.spi.InjectionPoint;
//import javax.inject.Named;
//import javax.jms.MessageListener;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.PersistenceContext;
//import javax.transaction.UserTransaction;
//import javax.validation.Validation;
//import javax.validation.ValidatorFactory;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import br.com.pst.sso.api.infra.configuration.SettingsConfiguration;
//import br.com.pst.sso.api.infra.jms.EventJmsConnection;
//import br.com.pst.sso.api.infra.jms.EventMessageListener;
//import br.com.pst.sso.api.infra.jms.JmsConnection;
//import br.com.pst.sso.common.context.ExecutionContext;
//import br.com.pst.sso.common.persistence.GenericDAO;
//import br.com.pst.sso.common.persistence.NativeSQL;
//import br.com.pst.sso.common.persistence.XMLNativeSQL;
//import br.com.pst.sso.common.persistence.jpa.JPAGenericDAO;
//import br.com.pst.sso.common.transaction.GenericTransaction;
//import br.com.pst.sso.common.transaction.JPAGenericTransaction;
//import br.com.pst.sso.common.transaction.UserGenericTransaction;
//import br.com.pst.sso.common.util.CdiUtils;
//import br.com.pst.sso.common.util.FileUtils;
//import br.com.pst.sso.services.ws.GroupService;
//import br.com.pst.sso.services.ws.Groupservice_Service;
//import br.com.pst.sso.services.ws.SsoService;
//import br.com.pst.sso.services.ws.Ssoservice_Service;
//import br.com.pst.sso.services.ws.TrackableService;
//import br.com.pst.sso.services.ws.Trackableservice_Service;
//import br.com.pst.sso.services.ws.UserService;
//import br.com.pst.sso.services.ws.Userservice_Service;

/*
 * Obs 1: nao use um atributo com @Inject com um respectivo metodo @Produces
 * dentro de ComponentesFactory: entra em loop infinito. Ex:
 * @Inject private Foo foo;
 * @Produces public Foo createFoo() { ...
 * 
 * Obs 2: uma classe injetada via
 * metodos @Produces tem que ter um construtor vazio ou immplementar uma
 * interface, no caso da interface o metodo anotado com @Produces deve ter
 * como tipo de retorno a interface.
 */
//@ApplicationScoped
public class ComponentsFactory {
//
//	private static final String EXTERNAL_CONFIG_PROPERTIES_NAME = "settings.properties";
//
//	@Resource(mappedName = "java:comp/UserTransaction")
//	private UserTransaction userTransaction;
//
//	@PersistenceContext(name = "SSOPersistenceUnit")
//	private EntityManager entityManager;
//
//	@Named
//	@Produces
//	public Logger createLogger(InjectionPoint injectionPoint) {
//		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
//	}
//
//	@Produces
//	@ApplicationScoped
//	public NativeSQL createNativeSQL() {
//		return new XMLNativeSQL();
//	}
//
//	@Produces
//	@ApplicationScoped
//	public ValidatorFactory createValidatorFactory() {
//		return Validation.buildDefaultValidatorFactory();
//	}
//
//	@Produces
//	@ApplicationScoped
//	public EntityManagerFactory createEntityManagerFactory() {
//		return entityManager != null ?
//				entityManager.getEntityManagerFactory() :
//				Persistence.createEntityManagerFactory("SSOPersistenceUnit");
//	}
//
//	@Produces
//	@RequestScoped
//	public EntityManager createEntityManager(EntityManagerFactory entityManagerFactory) {
//		return entityManagerFactory.createEntityManager();
//	}
//
//	@SuppressWarnings("resource")
//	@Produces
//	@RequestScoped
//	public GenericTransaction createGenericTransaction(EntityManager entityManager) {
//		return userTransaction != null ?
//				new UserGenericTransaction(userTransaction, entityManager) : new JPAGenericTransaction(entityManager);
//	}
//
//	@Produces
//	@RequestScoped
//	public GenericDAO createGenericDAO(EntityManager entityManager, GenericTransaction transaction, NativeSQL nativeSQL) {
//		return new JPAGenericDAO(entityManager, transaction, nativeSQL);
//	}
//
//	@Produces
//	@RequestScoped
//	public ExecutionContext createExecutionContext() {
//		return new ExecutionContext();
//	}
//
//	@Produces
//	@ApplicationScoped
//	public SettingsConfiguration createExternalConfiguration() {
//		return new SettingsConfiguration(FileUtils.loadPropertiesInClasspath(EXTERNAL_CONFIG_PROPERTIES_NAME));
//	}
//
//	@Produces
//	@ApplicationScoped
//	public JmsConnection createJmsConnection(BeanManager beanManager, SettingsConfiguration configuration) {
//		MessageListener listener = CdiUtils.wrapMessageListenerInBoundRequest(beanManager, EventMessageListener.class);
//		return new EventJmsConnection(configuration, listener);
//	}
//
//	@Produces
//	@ApplicationScoped
//	public GroupService createGroupService(SettingsConfiguration config) {
//		return new Groupservice_Service(config.getGroupServiceEndPoint()).getGroupServiceEndpointPort();
//	}
//
//	@Produces
//	@ApplicationScoped
//	public SsoService createSsoService(SettingsConfiguration config) {
//		return new Ssoservice_Service(config.getSsoServiceEndPoint()).getSsoServiceEndpointPort();
//	}
//
//	@Produces
//	@ApplicationScoped
//	public TrackableService createTrackableService(SettingsConfiguration config) {
//		return new Trackableservice_Service(config.getTrackableServiceEndPoint()).getTrackableServiceEndpointPort();
//	}
//
//	@Produces
//	@ApplicationScoped
//	public UserService createUserService(SettingsConfiguration config) {
//		return new Userservice_Service(config.getUserServiceEndPoint()).getUserServiceEndpointPort();
//	}
//
//	public void disposeEntityManagerFactory(@Disposes EntityManagerFactory entityManagerFactory) {
//		entityManagerFactory.close();
//	}
//
//	public void disposeEntityManager(@Disposes EntityManager entityManager) {
//		entityManager.clear();
//		entityManager.close();
//	}
//
//	public void disposeGenericDAO(@Disposes GenericDAO genericDAO) {
//		genericDAO.close();
//	}
//
//	public void disposeGenericTransaction(@Disposes GenericTransaction genericTransaction) {
//		genericTransaction.close();
//	}
//
//	public void disposeJmsConnection(@Disposes JmsConnection jmsConnection) {
//		jmsConnection.stop();
//	}

}
