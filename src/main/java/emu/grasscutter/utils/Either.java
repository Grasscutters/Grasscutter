package emu.grasscutter.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Either<L, R> {
    private static final class Left<L, R> extends Either<L, R> {
        private final L value;

        public Left(L value) {
            this.value = value;
        }

        @Override
        public <U, V> Either<U, V> mapBoth(
                Function<? super L, ? extends U> f1, Function<? super R, ? extends V> f2) {
            return new Left<>(f1.apply(this.value));
        }

        @Override
        public <T> T map(Function<? super L, ? extends T> l, Function<? super R, ? extends T> r) {
            return l.apply(this.value);
        }

        @Override
        public Either<L, R> ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.value);
            return this;
        }

        @Override
        public Either<L, R> ifRight(Consumer<? super R> consumer) {
            return this;
        }

        @Override
        public Optional<L> left() {
            return Optional.of(this.value);
        }

        @Override
        public Optional<R> right() {
            return Optional.empty();
        }

        @Override
        public String toString() {
            return "Left[" + this.value + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Left<?, ?> left = (Left<?, ?>) o;
            return Objects.equals(value, left.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    private static final class Right<L, R> extends Either<L, R> {
        private final R value;

        public Right(R value) {
            this.value = value;
        }

        @Override
        public <U, V> Either<U, V> mapBoth(
                Function<? super L, ? extends U> f1, Function<? super R, ? extends V> f2) {
            return new Right<>(f2.apply(this.value));
        }

        @Override
        public <T> T map(Function<? super L, ? extends T> l, Function<? super R, ? extends T> r) {
            return r.apply(this.value);
        }

        @Override
        public Either<L, R> ifLeft(Consumer<? super L> consumer) {
            return this;
        }

        @Override
        public Either<L, R> ifRight(Consumer<? super R> consumer) {
            consumer.accept(this.value);
            return this;
        }

        @Override
        public Optional<L> left() {
            return Optional.empty();
        }

        @Override
        public Optional<R> right() {
            return Optional.of(this.value);
        }

        @Override
        public String toString() {
            return "Right[" + this.value + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Right<?, ?> right = (Right<?, ?>) o;
            return Objects.equals(value, right.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    private Either() {}

    public abstract <U, V> Either<U, V> mapBoth(
            Function<? super L, ? extends U> f1, Function<? super R, ? extends V> f2);

    public abstract <T> T map(Function<? super L, ? extends T> l, Function<? super R, ? extends T> r);

    public abstract Either<L, R> ifLeft(Consumer<? super L> consumer);

    public abstract Either<L, R> ifRight(Consumer<? super R> consumer);

    public abstract Optional<L> left();

    public abstract Optional<R> right();

    public <T> Either<T, R> mapLeft(Function<? super L, ? extends T> l) {
        return map(t -> left(l.apply(t)), Either::right);
    }

    public <T> Either<L, T> mapRight(Function<? super R, ? extends T> l) {
        return map(Either::left, t -> right(l.apply(t)));
    }

    public static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }
}
