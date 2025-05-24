package com.demo.backend.infrastructure.mapper;


import com.demo.backend.domain.Account;
import com.demo.backend.domain.Client;
import com.demo.backend.domain.Person;
import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.service.dto.AccountDto;
import com.demo.backend.infrastructure.service.dto.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MapperProfile {
    MapperProfile INSTANCE = Mappers.getMapper(MapperProfile.class);

    //PERSON
    Person toDomainPerson(com.demo.backend.infrastructure.persistence.entities.Person person);
    com.demo.backend.infrastructure.persistence.entities.Person toEntityPerson(Person person);

    //CLIENT
    Client toDomainClient(ClientDto clientDto);
    ClientDto toDtoClient(Client client);
    Client toDomainClient(com.demo.backend.infrastructure.persistence.entities.Client client);
    com.demo.backend.infrastructure.persistence.entities.Client toEntityClient(Client client);

    //ACCOUNT
    Account toDomainAccount(com.demo.backend.infrastructure.persistence.entities.Account account);
    com.demo.backend.infrastructure.persistence.entities.Account toEntityAccount(Account account);
    com.demo.backend.infrastructure.persistence.entities.Account toEntityAccount(AccountDto accountDto);

    //TRANSACTION
    Transaction toDomainTransaction(com.demo.backend.infrastructure.persistence.entities.Transaction transaction);
    com.demo.backend.infrastructure.persistence.entities.Transaction toEntityTransaction(Transaction transaction);
}
