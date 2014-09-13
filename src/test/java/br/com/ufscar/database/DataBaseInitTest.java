package br.com.ufscar.database;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import br.com.ufscar.controller.DepartmentController;
import br.com.ufscar.controller.UserController;
import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.dao.ItemGroupDAO;
import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.Department;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.ItemGroup;
import br.com.ufscar.entity.ItemMovimentation;
import br.com.ufscar.entity.ItemMovimentationDAO;
import br.com.ufscar.entity.ItemMovimentationType;
import br.com.ufscar.entity.Role;
import br.com.ufscar.entity.User;

public class DataBaseInitTest extends TestCase {

	DepartmentController departmentController = new DepartmentController();
	UserController userController = new UserController();

	private final String DEPARTMENT = "department1";
	private final String EMPLOYEE = "employee1";
	private final String USER_ADMIN = "admin1";
	private final String USER_CLIENT = "userClient1";
	private final String ESCRITORIO = "Escritório";
	private final String LIMPEZA = "Limpeza";
	private final String IMFORMATICA = "Informática";
	private final String ITEM = "Item1";
	private final String[] ITEM_GROUPS = {ESCRITORIO, LIMPEZA, IMFORMATICA};
	private final String ITEM_MOVIMENTATION = "Item_Movimentation";
	private final String ITEM_MOVIMENTATION2 = "Item_Movimentation2";
	
	
	
	@Test
	public void testeDataBaseInit() {

		try {
			createUserAdmin();
			createUserClient();
			createEmployeesAndDepartament();
			createItemGroup();
			createItem();
			createItemMovimentation();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(verificaSeExisteDepartment());
		assertTrue(verificaSeExisteUserEmployee());
		assertTrue(verificaSeExisteUserAdmin());
		assertTrue(verificaSeExisteUserClient());
		assertTrue(verificaSeExisteItemGroup(ITEM_GROUPS[0]));
		assertTrue(verificaSeExisteItemGroup(ITEM_GROUPS[1]));
		assertTrue(verificaSeExisteItemGroup(ITEM_GROUPS[2]));
		assertTrue(verificaSeExisteItem());
		assertTrue(verificaSaldo());
	}

	private void createEmployeesAndDepartament() {
		Department department = new Department(DEPARTMENT);
		if (!verificaSeExisteDepartment()) {
			departmentController.persist(department);
		}

		if (!verificaSeExisteUserEmployee()) {
			User userEmployee = new User();
			userEmployee.setEmail(EMPLOYEE + "@estoque.com");
			userEmployee.setName(EMPLOYEE);
			userEmployee.setPassword(EMPLOYEE);
			userEmployee.setRole(Role.USER);
			userEmployee.setDepartment(department);
			userController.persist(userEmployee);
		}
	}

	private void createUserAdmin() {
		if (!verificaSeExisteUserAdmin()) {
			User user = new User();
			user.setEmail(USER_ADMIN+"@estoque.com");
			user.setName(USER_ADMIN);
			user.setPassword(USER_ADMIN);
			user.setRole(Role.ADMIN);
			userController.persist(user);
		}
	}

	private void createUserClient() {
		if (!verificaSeExisteUserClient()) {
			User user = new User();
			user.setEmail(USER_CLIENT+"@estoque.com");
			user.setName(USER_CLIENT);
			user.setPassword(USER_CLIENT);
			user.setRole(Role.USER);
			userController.persist(user);
		}
	}

	private void createItemGroup() {
		ItemGroupDAO itemGroupDAO = new ItemGroupDAO();
		for (String groupName : this.ITEM_GROUPS) {
			if (!verificaSeExisteItemGroup(groupName)) {
				ItemGroup itemGroup = new ItemGroup();
				itemGroup.setName(groupName);
				itemGroupDAO.save(itemGroup);
			}
		}
	}
	
	private void createItem() {
		GenericDAO dao = new GenericDAO();
		if (!verificaSeExisteItem()) {
			Item item = new Item();
			item.setName(ITEM);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("name", ESCRITORIO);
			ItemGroup group = dao.findOneResult(ItemGroup.FIND_BY_NAME, param);
			group.setName(ESCRITORIO);
			item.setItemGroup(group);
			dao.save(item);
		}
	}
	
	
	

	private boolean verificaSeExisteItem() {
		GenericDAO genericDAO = new GenericDAO();

		String query = "SELECT c FROM " + Item.class.getSimpleName()
				+ " c WHERE c.name = :name";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", ITEM);
		Integer tamanho = genericDAO.findResults(query, parameters).size();

		return tamanho > 0 ? true : false;
	}

	private Boolean verificaSeExisteUserEmployee() {
		GenericDAO genericEmployeesDAO = new GenericDAO();

		String query = "SELECT c FROM " + User.class.getSimpleName()
				+ " c WHERE c.name = :name";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", EMPLOYEE);
		Integer tamanho = genericEmployeesDAO.findResults(query, parameters)
				.size();

		return tamanho > 0 ? true : false;
	}

	private Boolean verificaSeExisteDepartment() {
		GenericDAO genericDepartmentDAO = new GenericDAO(
				);

		String query = "SELECT c FROM " + Department.class.getSimpleName()
				+ " c WHERE c.name = :name";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", DEPARTMENT);
		Integer tamanho = genericDepartmentDAO.findResults(query, parameters)
				.size();

		return tamanho > 0 ? true : false;
	}

	private Boolean verificaSeExisteUserAdmin() {
		GenericDAO genericUserDAO = new GenericDAO();

		String query = "SELECT c FROM " + User.class.getSimpleName()
				+ " c WHERE c.name = :name";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", USER_ADMIN);
		Integer tamanho = genericUserDAO.findResults(query, parameters).size();

		return tamanho > 0 ? true : false;
	}

	private Boolean verificaSeExisteUserClient() {
		GenericDAO genericUserDAO = new GenericDAO();

		String query = "SELECT c FROM " + User.class.getSimpleName()
				+ " c WHERE c.name = :name";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", USER_CLIENT);
		Integer tamanho = genericUserDAO.findResults(query, parameters).size();

		return tamanho > 0 ? true : false;
	}

	private Boolean verificaSeExisteItemGroup(String itemGroupName) {
		GenericDAO genericItemGroupDAO = new GenericDAO();

		String query = "SELECT ig FROM " + ItemGroup.class.getSimpleName()
				+ " ig WHERE UPPER(ig.name) = UPPER(:name)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", itemGroupName);
		List<Object> results = genericItemGroupDAO.findResults(query, parameters);

		return !results.isEmpty() ? true : false;
	}
	
	private void createItemMovimentation() {
		if (verificaSeExisteItemMovimentation()) {
			GenericDAO dao = new GenericDAO();
			UserDAO userDAO = new UserDAO();
			User userAdmin = userDAO.findUserByEmail(USER_ADMIN+"@estoque.com");
			User userClient = userDAO.findUserByEmail(USER_CLIENT+"@estoque.com");
			Item item = new Item();
			item.setName(ITEM_MOVIMENTATION);
			dao.save(item);
			
			Item item2 = new Item();
			item2.setName(ITEM_MOVIMENTATION2);
			dao.save(item2);
			
			Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM_MOVIMENTATION);
			ItemMovimentation itemMovimentation = new ItemMovimentation();
			itemMovimentation.setQuantity(3);
			itemMovimentation.setType(ItemMovimentationType.IN);
			itemMovimentation.setItem(itemDB);
			itemMovimentation.setUserAdmin(userAdmin);
			itemMovimentation.setUserClient(userClient);
			itemMovimentation.setDate(new Date());
			dao.save(itemMovimentation);
			
			itemMovimentation = new ItemMovimentation();
			itemMovimentation.setQuantity(1);
			itemMovimentation.setType(ItemMovimentationType.OUT);
			itemMovimentation.setItem(itemDB);
			itemMovimentation.setUserAdmin(userAdmin);
			itemMovimentation.setUserClient(userClient);
			itemMovimentation.setDate(new Date());
			dao.save(itemMovimentation);
			
			Item itemDB2 = dao.findOneByCustomField(Item.class, "name", ITEM_MOVIMENTATION2);
			itemMovimentation = new ItemMovimentation();
			itemMovimentation.setQuantity(2);
			itemMovimentation.setType(ItemMovimentationType.IN);
			itemMovimentation.setItem(itemDB2);
			itemMovimentation.setUserAdmin(userAdmin);
			itemMovimentation.setUserClient(userClient);
			itemMovimentation.setDate(new Date());
			dao.save(itemMovimentation);
		}
	}
	
	private Boolean verificaSeExisteItemMovimentation() {
		GenericDAO genericDAO = new GenericDAO();
		List<ItemMovimentation> results = genericDAO.findByCustomField(ItemMovimentation.class, "name", ITEM_MOVIMENTATION);
		return results.isEmpty();
	}

	private Boolean verificaSaldo() {
		ItemMovimentationDAO dao = new ItemMovimentationDAO();
		Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM_MOVIMENTATION);
		return dao.saldoItem(itemDB) == 2;
	}
}
