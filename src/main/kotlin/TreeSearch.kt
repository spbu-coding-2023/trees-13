interface TreeInterface<K : Comparable<K>, V> {
  /**
   * ✭ `search(key: K): V?` - searches for values by key.
   * - If the key is found, it returns its value.
   * - If the key is not found returns null.
   */
  fun search(key: K): V?
  /**✭ `insert(key: K, value: V)` - inserts a new key with the specified value into the tree.
   * - If such a key already exists in the tree, an exception: "IllegalArgumentException" is thrown.
   * - If such a key and value pair already exists in the tree, an exception: "IllegalArgumentException" is thrown.
   */
  fun insert(key: K, value: V)
  /**✭ `remove(key: K)` - removes the specified key from the tree
   * - If the key is not found in the tree, an exception: "NoSuchElementException" is thrown.
   */
  fun remove(key: K)
  /**✭ `getKeys(): List<K>` - gets a list of all keys in ascending order.
   * - Returns a list of keys
   * - If the tree is empty, it returns empty list.
   */
  fun getKeys(): List<K>
  /**✭ `getValues(): List<V>` - gets a list of all values in ascending order of their corresponding keys.
   * - Returns a list of values
   * - If the tree is empty, it returns empty list.
   */
  fun getValues(): List<V>
  /**✭ `getMinKey(): K?` - gets the minimum key in the tree
   * - Returns the minimum key in the tree
   * - If the tree is empty, returns null.
   */
  fun getMinKey(): K?
  /**✭ `getMaxKey(): K?` - gets the maximum key in the tree
   * - Returns the maximum key in the tree
   * - If the tree is empty, returns null.
   */
  fun getMaxKey(): K?
  /**✭ `insert(list: List<Pair<K, V>>)` - inserts multiple keys and specified values in the order in which they appear in the specified list.
   */
  fun insert(list: List<Pair<K, V>>)
  /**✭ `remove(list: List<K>)` - removes multiple keys in the order in which they appear in the specified list.
   */
  fun remove(list: List<K>)
  /**✭ `replaceValue(key: K, newValue: V)` - replaces the value of the specified key with the specified one
   * - If the key is not in the tree, an exception: "NoSuchElementException" is thrown
   */
  fun replaceValue(key: K, newValue: V)
  /**✭ `clean()` - completely clears the entire tree, removing all keys
   */
  fun clean()
}

abstract class TreeNode<K, V, N : TreeNode<K, V, N>>(var key: K, var value: V) {
  var leftChild: N? = null
  var rightChild: N? = null
}

abstract class TreeSearch<K : Comparable<K>, V, N : TreeNode<K, V, N>> : TreeInterface<K, V> {
  internal var root: N? = null
  override fun search(key: K): V? {
    return search(root, key)?.value
  }

  private fun search(root: N?, key: K): N? {
    if (root == null) {
      return null
    }
    if (key == root.key) {
      return root
    }
    if (key < root.key) {
      return search(root.leftChild, key)
    }
    return search(root.rightChild, key)
  }

  // values are written in ascending order of the key
  override fun getValues(): List<V> {
    val values = mutableListOf<V>()
    inOrderTraversal(root) { values.add(it.value) }
    return values
  }

  // keys are written in ascending order
  override fun getKeys(): List<K> {
    val keys = mutableListOf<K>()
    inOrderTraversal(root) { keys.add(it.key) }
    return keys
  }

  private fun inOrderTraversal(root: N?, action: (N) -> Unit) {
    if (root == null) return
    inOrderTraversal(root.leftChild, action)
    action(root)
    inOrderTraversal(root.rightChild, action)
  }

  override fun getMaxKey(): K? {
    return getMaxKey(root)
  }

  // maximal key is the rightmost key
  private fun getMaxKey(root: N?): K? {
    if (root == null) {
      return null
    }
    if (root.rightChild == null) {
      return root.key
    }
    return getMaxKey(root.rightChild)
  }

  override fun getMinKey(): K? {
    return getMinKey(root)
  }

  // minimal key is the leftmost key
  private fun getMinKey(root: N?): K? {
    if (root == null) {
      return null
    }
    if (root.leftChild == null) {
      return root.key
    }
    return getMinKey(root.leftChild)
  }

  // minimal node is the leftmost node
  protected fun minNode(node: N): N {
    var nodeCurrent = node
    while (true)
      nodeCurrent = nodeCurrent.leftChild ?: break
    return nodeCurrent
  }

  override fun insert(list: List<Pair<K, V>>) {
    for (i in list) {
      insert(i.first, i.second)
    }
  }

  override fun remove(list: List<K>) {
    for (i in list) {
      remove(i)
    }
  }

  //if the key is in the tree, we look for it and change the value, otherwise we throw an exception
  override fun replaceValue(key: K, newValue: V) {
    val result = search(root, key) ?: throw NoSuchElementException("The key: $key was not found in the tree.")
    result.value = newValue
  }

  override fun clean() {
    for (i in getKeys()) {
      remove(i)
    }
  }
}
