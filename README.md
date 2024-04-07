[![Kotlin 1.9.23][kotlin_img]][kotlin_releases_url]
[![License][license_img]][repo_license_url]
## Implementation of binary search trees in the Kotlin programming language

* Binary search tree
* AVL tree
* Red-Black tree

## Description of the tree interface

K - the key type<br>
V - the value type<br>
N - the node type of the tree<br>


`TreeInterface<K : Comparable<K>, V>` defines the basic operations for working with search trees:

✭ `search(key: K): V?` - searches for values ​​by key.
- If the key is found, it returns its value.<br>
- If the key is not found returns null.<br>

✭ `insert(key: K, value: V)` - inserts a new key with the specified value into the tree.
- If such a key already exists in the tree, an exception: "IllegalArgumentException" is thrown.<br>
- If such a key and value pair already exists in the tree, an exception: "IllegalArgumentException" is thrown.<br>

✭ `remove(key: K)` - removes the specified key from the tree
- If the key is not found in the tree, an exception: "NoSuchElementException" is thrown.<br>

✭ `getKeys(): List<K>` - gets a list of all keys in ascending order.
- Returns a list of keys<br>
- If the tree is empty, it returns empty list.<br>

✭ `getValues(): List<V>` - gets a list of all values ​​in ascending order of their corresponding keys.
- Returns a list of values<br>
- If the tree is empty, it returns empty list.<br>

✭ `getMinKey(): K?` - gets the minimum key in the tree
- Returns the minimum key in the tree<br>
- If the tree is empty, returns null.<br>

✭ `getMaxKey(): K?` - gets the maximum key in the tree
- Returns the maximum key in the tree<br>
- If the tree is empty, returns null.<br>

✭ `insert(list: List<Pair<K, V>>)` - inserts multiple keys and specified values ​​in the order in which they appear in the specified list.<br>

✭ `remove(list: List<K>)` - removes multiple keys in the order in which they appear in the specified list.<br>

✭ `replaceValue(key: K, newValue: V)` - replaces the value of the specified key with the specified one
- If the key is not in the tree, an exception: "NoSuchElementException" is thrown<br>

✭ `clean()` - completely clears the entire tree, removing all keys<br>

## Technologies

❀Kotlin version for JVM: 1.9.22<br>
❀Kotlin Test Framework<br>
Kotest Runner for JUnit 5 (JVM)<br>
❀JVM 21<br>

## Launch

### Example of use on an AVL tree:
```kotlin
val avlTree = AVLTreeSearch<Int, String>()
avlTree.insert(listOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five"))
val keyRemove = avlTree.getMaxKey()
avlTree.remove(keyRemove)
for(i in avlTree.getValues()) {
  println(i)
}
```
Output:
```text
one
two
three
four
```

### Example of use on an Binary tree:
```kotlin
val tree = BinaryTreeSearch<Int, String>()
tree.insert(45, "apple")
tree.insert(30, "banana")
tree.insert(65, "apricot")
tree.insert(50, "lemon")
val keyRemove = tree.getMinKey()
tree.remove(keyRemove)
for(i in tree.getKeys()) {
  println(i)
}
println(tree.search(65))
tree.replaceValue(65, "orange")
println(tree.search(65))
```
Output:
```text
45
50
65
apricot
orange
```

### Example of use on an Red-Black tree:
```kotlin
val rbTree = RedBlackTreeSearch<Int, String>()
rbTree.insert(1, "bicycle")
rbTree.insert(2, "motorcycle")
rbTree.insert(3, "car")
rbTree.insert(4, "ship")
rbTree.insert(5, "plane")
rbTree.remove(6)
```
Output:
```text
Exception in thread "main" java.util.NoSuchElementException: The key: 6 was not found in the tree.
...
```
## License

Distributed under the [MIT License](https://choosealicense.com/licenses/mit/). See [`LICENSE`](LICENSE) for more information.<br>

## Status

❤️ All trees and tests are implemented<br>

## Contact

* [@Kirilich](https://github.com/Kirilich)
* [@b08lsoai](https://github.com/b08lsoai)
* [@MigunovaAnastasia1](https://github.com/MigunovaAnastasia1)

## Authors:

* Nabieva Liya♡
* Ekaterina Tenyaeva♡
* Anastasia Migunova♡

<!-- links -->

[kotlin_img]: https://img.shields.io/badge/Kotlin-%201.9.22-magenta
[license_img]: https://img.shields.io/badge/license-MIT-blue
[kotlin_releases_url]: https://kotlinlang.org/docs/releases.html#release-details
[repo_license_url]: https://github.com/spbu-coding-2023/trees-13/blob/main/LICENSE
