package kr.hhplus.be.server.infra.wallet;

import kr.hhplus.be.server.domain.wallet.Wallet;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class DummyWalletRepository implements WalletRepository {
    private final Map<String, Wallet> store = new ConcurrentHashMap<>();

    @Override
    public void flush() {

    }

    @Override
    public <S extends WalletRepository> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends WalletRepository> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<WalletRepository> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public WalletRepository getOne(String s) {
        return null;
    }

    @Override
    public WalletRepository getById(String s) {
        return null;
    }

    @Override
    public WalletRepository getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends WalletRepository> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends WalletRepository> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends WalletRepository> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends WalletRepository> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WalletRepository> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends WalletRepository> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends WalletRepository, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends WalletRepository> S save(S entity) {
        return null;
    }

    @Override
    public <S extends WalletRepository> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<WalletRepository> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<WalletRepository> findAll() {
        return List.of();
    }

    @Override
    public List<WalletRepository> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(WalletRepository entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends WalletRepository> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<WalletRepository> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<WalletRepository> findAll(Pageable pageable) {
        return null;
    }
}
