package org.iesfm.shop.services;

import org.iesfm.shop.Client;
import org.iesfm.shop.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public boolean insert(Client client) {
        if (clientRepository.existsById(client.getNif())) {
            return false;
        }
        clientRepository.insert(client);
        return true;
    }

    public List<Client> list() {
        return clientRepository.findAll();
    }
}
