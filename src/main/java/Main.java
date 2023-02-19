import features.clientService.ClientService;
import features.dbQueryService.DatabaseQueryService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //1. run DBInitAndPopulateService
        //2. run this class

        System.out.println("--------------DatabaseQueryService--------------");
        DatabaseQueryService queryService = new DatabaseQueryService();
        printList(queryService.find_longest_project(), "Find longest project");
        printList(queryService.find_max_projects_client(), "Find max projects client");
        printList(queryService.find_max_salary_worker(), "Find max salary worker");
        printList(queryService.find_youngest_eldest_workers(), "Find youngest eldest workers");
        printList(queryService.print_project_prices(), "Print project prices");
        System.out.println();

        System.out.println("--------------ClientService--------------");
        System.out.println("--------------Add new client 1--------------");
        ClientService clientServ = new ClientService();
        long id = clientServ.create("Valerij");
        System.out.println("New client with name Valerij has id - " + id);

        System.out.println("\n--------------Add new client 2 (with wrong data)--------------");
        try {
            clientServ.create(null);
        } catch (IllegalArgumentException ex) {
            System.out.println("!!! ------- ERROR ------- : " + ex);
        }

        System.out.println("\n--------------Get client name by id--------------");
        String clientName = clientServ.getById(5);
        System.out.println("Client with id 5 has name - " + clientName);

        System.out.println("\n--------------Change client name--------------");
        String prevName = clientServ.getById(1);
        clientServ.setName(1, "Olga");
        String newName = clientServ.getById(1);
        System.out.println("Client with id 1 changed name from " + prevName + " to " + newName);

        System.out.println("\n--------------Delete client by id--------------");
        String delClientName = clientServ.getById(9);
        clientServ.deleteById(9);
        System.out.println("Client with id 9 and name " + delClientName +
                " was deleted and now request by id 9 returns '" + clientServ.getById(9) + "'");

        printList(clientServ.listAll(), "Print all clients");

        System.gc();
    }

    private static <T> void printList(List<T> list, String topic) {
        System.out.println("--------------" + topic + "--------------");
        for (T object : list) {
            System.out.println(object);
        }
        System.out.println();
    }
}