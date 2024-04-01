## Implementation of binary search trees in the Kotlin programming language

* Binary search tree
* AVL tree
* Red and black tree

## Description of the tree interface

K - the key type<br>
V - the value type<br>
N - the node type of the tree<br>


`TreeInterface<K : Comparable<K>, V>` defines the basic operations for working with search trees:

✭ `search(key: K): V?` - search for a value by key
- If it does not find the key in the tree, it returns null.<br>

✭ `insert(key: K, value: V)` - insert a new element into the tree.
- If the value or the value and the key match, an exception is thrown.<br>

✭ `remove(key: K)` - deleting an element by key.
- If the key is not found in the tree, an exception is thrown.<br>

✭ `getKeys(): List<K>` - getting a list of all keys in the tree.
- If the tree is empty, it returns nothing.<br>

✭ `getValues(): List<V>` - getting a list of all values in the tree.
- If the tree is empty, it returns nothing.<br>

✭ `getMinKey(): K?` - getting the maximum key in the tree.
- If the tree is empty, returns null.<br>

✭ `getMaxKey(): K?` - getting the minimum key in the tree.
- If the tree is empty, returns null.<br>

✭ `insert(list: List<Pair<K, V>>)` - inserts a list of items into the tree.<br>
✭ `remove(list: List<K>)` - deletes the list of items in the tree.<br>
✭ `replaceValue(key: K, newValue: V)` - changing the value by key in the tree
- If the key is not in the tree, an exception is thrown<br>

## Technologies

❀Kotlin version for JVM: 1.9.22<br>
❀Kotlin Test Framework<br>
Kotest Runner for JUnit 5 (JVM)<br>
❀JVM 21<br>

## Launch

### Example of use on an AVL tree:
```
val avlTree = AVLTreeSearch<Int, String>()
avlTree.insert(listOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five"))
val keyRemove = avlTree.getMaxKey()
avlTree.remove(keyRemove!!)
for(i in avlTree.getValues()) {
println(i)
}
```
Output:
```
one
two
three
four
```

### Example of use on an AVL tree:
```
val tree = BinaryTreeSearch<Int, String>()
tree.insert(45, "apple")
tree.insert(30, "banana")
tree.insert(65, "apricot")
tree.insert(50, "lemon")
val keyRemove = tree.getMinKey()
tree.remove(keyRemove!!)
for(i in tree.getKeys()) {
println(i)
}
println(tree.search(65))
tree.replaceValue(65, "orange")
println(tree.search(65))
```
Output:
```
45
50
65
apricot
orange
```

## Status
❤️ Red-black tree in development<br>
## Authors:

* Nabieva Liya♡
* Ekaterina Tenyaeva♡
* Anastasia Migunova♡
