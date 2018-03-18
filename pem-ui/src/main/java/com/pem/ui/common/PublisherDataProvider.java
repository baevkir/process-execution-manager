package com.pem.ui.common;

import com.google.common.base.Preconditions;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.SerializableComparator;
import com.vaadin.server.SerializablePredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class PublisherDataProvider<T> extends AbstractDataProvider<T, Predicate<T>> implements DataProvider<T, Predicate<T>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherDataProvider.class);

    private Supplier<Flux<T>> dataLoader;
    private Flux<T> data;
    private Predicate<T> filter;
    private Comparator<T> sorting;

    private PublisherDataProvider() {
        LOGGER.debug("Create new instance for PublisherDataProvider.");
    }

    public static <S> Builder<S> builder() {
        return new Builder<S>();
    }

    @Override
    public boolean isInMemory() {
        return false;
    }

    @Override
    public Stream<T> fetch(Query<T, Predicate<T>> query) {
        Flux<T> result = data.filter(getFilter(query));

        Optional<Comparator<T>> sort = getSorting(query);
        if (sort.isPresent()) {
            result = result.sort(sort.get());
        }

        return result.skip(query.getOffset())
                .take(query.getLimit())
                .doOnNext(value -> LOGGER.trace("Fetch value: {}", value))
                .toStream();
    }

    @Override
    public int size(Query<T, Predicate<T>> query) {
        return data.filter(getFilter(query))
                .count().map(Long::intValue)
                .block();
    }

    public void reloadData(){
        data = dataLoader.get();
        refreshAll();
    }

    public void setFilter(Predicate<T> filter) {
        this.filter = filter;
        refreshAll();
    }

    public void addFilter(SerializablePredicate<T> filter) {
        Objects.requireNonNull(filter, "Filter cannot be null");

        Predicate<T> resultFilter = Optional.ofNullable(this.filter)
                .map(oldFilter -> oldFilter.and(filter)).orElse(filter);

        setFilter(resultFilter);
    }

    private Predicate<T> getFilter(Query<T, Predicate<T>> query) {
        return Optional.ofNullable(filter).orElse(getEmptyPredicate())
                .and(query.getFilter().orElse(getEmptyPredicate()));
    }

    private Optional<Comparator<T>> getSorting(Query<T, Predicate<T>> query) {
        return Stream.of(query.getInMemorySorting(), sorting)
                .filter(sort -> sort != null)
                .reduce((firstSort, secondSort) -> firstSort.thenComparing(secondSort));
    }
    private <S> SerializablePredicate<S> getEmptyPredicate() {
        return value -> true;
    }

    public static class Builder<T> {
        private Supplier<Flux<T>> dataLoader;
        private SerializablePredicate<T> filter;
        private SerializableComparator<T> sorting;

        public Builder<T> setDataLoader(Supplier<Flux<T>> dataLoader) {
            this.dataLoader = dataLoader;
            return this;
        }

        public Builder<T> setFilter(SerializablePredicate<T> filter) {
            this.filter = filter;
            return this;
        }

        public Builder<T> setSorting(SerializableComparator<T> sorting) {
            this.sorting = sorting;
            return this;
        }

        public PublisherDataProvider<T> build() {
            PublisherDataProvider<T> dataProvider = new PublisherDataProvider<>();
            dataProvider.dataLoader = Preconditions.checkNotNull(dataLoader, "Cannot create data provider with NUll data loader.");
            dataProvider.data = Preconditions.checkNotNull(dataLoader.get(), "Data is NULL.");

            dataProvider.filter = filter;
            dataProvider.sorting = sorting;

            return dataProvider;

        }
    }
}
