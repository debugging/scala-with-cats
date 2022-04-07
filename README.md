# scala-with-cats

# Definitions


# Monoids
    1. an operation combine with type (A, A) => A
    2. an element empty of type A
```scala
trait Monoid[A] {
    def combine(x: A, y: A): A
    def empty: A
}
```    
    3. formally obey several laws, namely associtive and empty must be an identity element
```scala
def associativeLaw[A](x: A, y: A, z: A)
        (implicit m: Monoid[A]): Boolean = {
    m.combine(x, m.combine(y, z)) ==
    m.combine(m.combine(x, y), z)
}
def identityLaw[A](x: A)
        (implicit m: Monoid[A]): Boolean = {
    (m.combine(x, m.empty) == x) &&
    (m.combine(m.empty, x) == x)
}
```
# Semigroups
# Functors
# Monads
# Semigroupal
# applicative
# foldable
# traversable
