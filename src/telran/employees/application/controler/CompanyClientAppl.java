package telran.employees.application.controler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import telran.employees.Company;
import telran.employees.net.NetworkCompany;
import telran.net.NetworkClient;
import telran.net.TcpClient;
import telran.sportsmen.Sportsman;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandartInputOutput;

public class CompanyClientAppl {
	private static final String PACKAGE_NAME = "telran.net.";
	private static Properties props = new Properties();

	public static void main(String[] args) throws Exception {
		StandartInputOutput io = new StandartInputOutput();
		NetworkClient client = getClient("transport", args);
		Company company = new NetworkCompany(client);
		String[] departments = getDepartments("departments", args);
		Item[] companyItems = CompanyControllerItems.getCompanyItems(company, departments);
		ArrayList<Item> items = new ArrayList<>(Arrays.asList(companyItems));
		items.add(GetExitItem(company));
		Menu menu = new Menu("Company app", items);
		menu.perform(io);
	}

	private static Item GetExitItem(Company company) {
		return Item.of("Exit & close conection", io1 -> {
			try {
				((NetworkCompany) company).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, true);
	}

	private static String[] getDepartments(String key, String[] args) {
		String departments = getProperties(key, args);
		String[] res = null;
		if (departments != null) {
			res = departments.split(", ");
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private static NetworkClient getClient(String key, String[] args) {
		NetworkClient res = null;
		String transport = getProperties(key, args);
		int port = Integer.parseInt(getProperties("port", args));
		String host = getProperties("hostName", args);
		if (transport != null) {
			try {
				Class<NetworkClient> clientClass = (Class<NetworkClient>) Class
						.forName(PACKAGE_NAME + transport + "Client");
				Constructor<NetworkClient> constructor = clientClass.getConstructor(String.class,
						int.class);
				res = constructor.newInstance(host, port);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	private static String getProperties(String key, String[] args) {
		String res = null;
		try {
			props.load(new FileInputStream(args[0]));
			res = props.getProperty(key);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Wrong file name");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
