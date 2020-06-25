package io.github.lrzeszotarski.accountmanager.domain.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdentifierServiceImpl implements IdentifierService {
    @Override
    public UUID generateIdentifier() {
        return UUID.randomUUID();
    }
}
